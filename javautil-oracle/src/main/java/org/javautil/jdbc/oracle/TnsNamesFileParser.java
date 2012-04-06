package org.javautil.jdbc.oracle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TnsNamesFileParser {
	private static final String TNS_ADMIN = "TNS_ADMIN";

	private static String ORACLE_HOME = "ORACLE_HOME";

	private final Logger logger = Logger.getLogger(getClass());

	// public static void main(String[] args) throws IOException {
	// TnsNamesFileParser tn = new TnsNamesFileParser();
	// Logger logger = Logger.getLogger("");
	//
	//
	// ArrayList<String> allLines = tn.getTnsNamesLines();
	// ArrayList<String> nonCommentLines = tn.getNonCommentLines(allLines);
	// ArrayList<String> entryLines =
	// tn.getNonDescriptionLines(nonCommentLines);
	// Element r = tn.getAsElement(entryLines);
	// OutputFormat of = OutputFormat.createPrettyPrint();
	// XMLWriter xw = new XMLWriter(of);
	// StringWriter sw = new StringWriter();
	// XMLWriter xe = new XMLWriter(sw,of);
	//
	// xw.write(r);
	// System.out.println(sw.toString());
	// }

	public String getTnsNamesAsXml() {
		try {
			return getTnsnamesAsXmlInternal();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getTnsnamesAsXmlInternal() throws IOException {
		final TnsNamesFileParser tn = new TnsNamesFileParser();
		Logger.getLogger("");

		final ArrayList<String> allLines = tn.getTnsNamesLines();
		final ArrayList<String> nonCommentLines = tn
				.getNonCommentLines(allLines);
		final ArrayList<String> entryLines = tn
				.getNonDescriptionLines(nonCommentLines);
		final Element r = tn.getAsElement(entryLines);
		final OutputFormat of = OutputFormat.createPrettyPrint();
		XMLWriter xw;

		xw = new XMLWriter(of);

		final StringWriter sw = new StringWriter();
		new XMLWriter(sw, of);

		xw.write(r);

		return sw.toString();
	}

	/**
	 * Filters out lines where the first non white character is '#'
	 * 
	 * @param lines
	 * @return
	 */
	public ArrayList<String> getNonCommentLines(final Collection<String> lines) {
		final ArrayList<String> retval = new ArrayList<String>();

		for (final String line : lines) {

			boolean isComment = false;
			for (int i = 0; i < line.length(); i++) {
				final char c = line.charAt(i);
				if (c == ' ' || c == '\t') {
					continue;
				}
				if (c == '#') {
					isComment = true;
					break;
				}
				isComment = false;
				break;
			}
			if (isComment) {
				logger.debug("discarding " + line);
			} else {
				// logger.info("adding " + line);
				retval.add(line);
			}

		}
		return retval;
	}

	/**
	 * Filters out all lines up to and including the last line with a '[' or ']'
	 * character
	 * 
	 * @param lines
	 * @return
	 */
	public ArrayList<String> getNonDescriptionLines(
			final ArrayList<String> lines) {
		final ArrayList<String> retval = new ArrayList<String>();
		int lastDescrLineNumber = -1;
		for (int i = 0; i < lines.size(); i++) {
			final String s = lines.get(i);
			if (s.indexOf("]") > -1 || s.indexOf("[") > -1) {
				lastDescrLineNumber = i;
			}
		}

		for (int i = lastDescrLineNumber + 1; i < lines.size(); i++) {
			retval.add(lines.get(i));
		}

		return retval;
	}

	public File getOracleHomeTnsNamesFile() {
		final String pathName = System.getenv(ORACLE_HOME);
		File retval = null;
		if (pathName != null) {
			final String fileName = pathName + "/network/admin/tnsnames.ora";
			retval = new File(fileName);
		}
		return retval;
	}

	public File getTnsAdminFile() {
		final String pathName = System.getenv(TNS_ADMIN);
		File retval = null;
		if (pathName != null) {
			final String fileName = pathName + "/tnsnames.ora";
			retval = new File(fileName);
		} else {
			logger.debug(TNS_ADMIN + " is not set");
		}
		return retval;
	}

	public File getTnsNamesFile() {
		File f = getTnsAdminFile();
		if (f != null) {
			logger.info("using " + f.getAbsolutePath() + " from " + TNS_ADMIN);

		} else {
			f = getOracleHomeTnsNamesFile();
			if (f == null) {
				logger.info("no file can be found using " + TNS_ADMIN + " or "
						+ ORACLE_HOME);
			}
		}
		return f;
	}

	/**
	 * gets all of the lines in the File;
	 * 
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> getTnsNamesLines() throws IOException {
		final File file = getTnsNamesFile();
		final ArrayList<String> lines = new ArrayList<String>();
		if (file == null) {
			throw new IllegalStateException("cannot locate a tnsnames file");
		}

		new StringBuilder((int) (file.length() * 1.1));
		final BufferedReader buffFile = new BufferedReader(new FileReader(file));
		if (!file.canRead()) {
			throw new IllegalArgumentException("cannot read file "
					+ file.getAbsolutePath());
		}
		while (buffFile.ready()) {
			lines.add(buffFile.readLine());

		}
		buffFile.close();

		return lines;
	}

	public StringBuilder getTnsNamesText() throws IOException {
		final File file = getTnsNamesFile();

		if (file == null) {
			throw new IllegalStateException("cannot locate a tnsnames file");
		}

		final StringBuilder buff = new StringBuilder(
				(int) (file.length() * 1.1));
		final BufferedReader buffFile = new BufferedReader(new FileReader(file));
		if (!file.canRead()) {
			throw new IllegalArgumentException("cannot read file "
					+ file.getAbsolutePath());
		}
		while (buffFile.ready()) {
			buff.append(buffFile.readLine());
			buff.append("\n");
			// System.out.println("read: " + line);
		}
		buffFile.close();

		return buff;
	}

	/**
	 * Get TNS Names entries. Assumes standard tnsmanes.ora formatting of NetMgr
	 * 
	 * @param tnsnames
	 *            content of tnsnames.ora
	 * @return tns names or null
	 */
	private Element getAsElement(final Collection<String> lines) {
		final Element root = DocumentHelper.createElement("tnsnames");
		Element entry = null;
		Element currentElement = root;
		if (lines == null) {
			throw new IllegalArgumentException("lines is null");
		}

		final StringBuilder sb = new StringBuilder();
		// int depth = 0;
		for (final String line : lines) {
			sb.append(line);
		}
		final String text = sb.toString();
		String token;
		final StringTokenizer st = new StringTokenizer(text, "= ()", true);

		final int ADD_ENTRY = 3;
		final int ADD_ELEMENT = 1;
		final int ADD_ATTRIBUTE = 2;
		int nextAction = ADD_ENTRY;
		while (st.hasMoreTokens()) {

			token = st.nextToken();

			if (token.equals(" ") || token.equals("\t")) {
				continue;
			}

			if (token.equals("(")) {
				nextAction = ADD_ELEMENT;
				token = st.nextToken();
				currentElement = currentElement.addElement(token);
				continue;
			}
			if (token.equals(")")) {

				if (currentElement == null) {
					throw new IllegalStateException("unbalanced parens");
				}
				currentElement = currentElement.getParent();
				if (currentElement == entry) {
					nextAction = ADD_ENTRY;
				}
				continue;
			}
			if (token.equals("=")) {
				nextAction = ADD_ATTRIBUTE;
				continue;
			}
			// not special what should we do
			switch (nextAction) {
			case ADD_ATTRIBUTE:
				currentElement.setText(token);
				break;
			case ADD_ELEMENT:
				currentElement.addElement(token);
				break;
			case ADD_ENTRY:
				entry = currentElement = root.addElement("entry");
				currentElement.addAttribute("name", token);
				break;
			}

		}

		return root;
	}
}