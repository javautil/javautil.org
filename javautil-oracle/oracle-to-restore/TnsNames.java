package com.dbexperts.oracle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.dbexperts.io.TextFileHelper;

public class TnsNames {
	private static final String TNS_ADMIN = "TNS_ADMIN";

	private static final String ORACLE_HOME = "ORACLE_HOME";
	private static final String newline = System.getProperty("line.separator");

	public static final int OPEN_PAREN = 1;

	public static final int EQUALS = 2;

	public static final int CLOSE_PAREN = 3;

	public static final int TEXT = 4;
	private static final int WHITE = 5;
	private static Logger logger = Logger.getLogger(TnsNames.class.getName());

	private String message = null;

	// public static void main(String[] args) throws IOException {
	// TnsNames tn = new TnsNames();
	// Logger logger = Logger.getLogger("");
	// ConsoleHandler consoleHandler = new ConsoleHandler();
	// // LoggerHelper.setRootHandler(consoleHandler);
	// //
	// // SimpleProcessFormatter spf = new SimpleProcessFormatter();
	//
	// // consoleHandler.setFormatter(spf);
	// // consoleHandler.setLevel(Level.FINEST);
	//
	// logger.setLevel(Level.FINEST);
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
	//
	// }

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
				// logger.info("discarding " + line);
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
	public ArrayList<String> getNonDescriptionLines(final ArrayList<String> lines) {
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

	public static String getOracleHome() {
		return System.getenv(ORACLE_HOME);
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

	public String getTnsAdmin() {
		return System.getenv(TNS_ADMIN);
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

	public String getMessage() {
		return message;
	}

	private String getQuoted(final String text) {
		final String rc = text == null ? "null" : "'" + text + "'";
		return rc;
	}

	public File getTnsNamesFile() {
		File f = getTnsAdminFile();
		if (f != null) {
			logger.debug("using " + f.getAbsolutePath() + " from " + TNS_ADMIN);

		} else {
			f = getOracleHomeTnsNamesFile();
			if (f == null) {
				message = "no file can be found: " + newline + ORACLE_HOME
				+ " is " + getQuoted(getOracleHome()) + newline
				+ TNS_ADMIN + " is " + getQuoted(getTnsAdmin());
				logger.warn(message);
				throw new IllegalArgumentException(message);
			}
		}
		return f;
	}


	public String getEntryText() throws IOException {
		final ArrayList<String> allLines = TextFileHelper.getLines(getTnsNamesFile());
		final ArrayList<String> nonCommentLines = getNonCommentLines(allLines);
		final ArrayList<String> lines = getNonDescriptionLines(nonCommentLines);
		final StringBuilder sb = new StringBuilder();
		for (final String line : lines) {
			sb.append(line);
		}
		final String text = sb.toString();
		return text;
	}

	public Collection<TnsnamesEntryBean> getEntries() throws IOException {
			final Map<String,TnsnamesEntryBean> entryMap = new HashMap<String,TnsnamesEntryBean>();
			final Element el = getAsElement();
			return entryMap.values();
		}

	public void writeAsXmlToFile(final File tnsXmlFile) throws IOException {
		final Document tnsDoc = getAsDocument();


		final OutputFormat formatter = OutputFormat.createPrettyPrint();

		final FileWriter fw = new FileWriter(tnsXmlFile);
		final XMLWriter xw = new XMLWriter(fw, formatter);
		xw.write(tnsDoc);
		fw.close();
	}


	public Document getAsDocument() throws IOException {
		final Element root = getAsElement();

		  final Document document = DocumentHelper.createDocument();
		  document.setRootElement(root);
		  return document;

	}

	public String getAsXml() throws IOException {
		final Element el = getAsElement();
		final OutputFormat formatter = OutputFormat.createPrettyPrint();
		final StringWriter sw = new StringWriter();
		final XMLWriter xw = new XMLWriter(sw, formatter);
		xw.write(el);
		return sw.toString();
	}

	public Element getAsElement() throws IOException {
		return getAsElement(getEntryText());
	}

	/**
	 * Get TNS Names entries. Assumes standard tnsmanes.ora formatting of NetMgr
	 *
	 * @param tnsnames
	 *            content of tnsnames.ora
	 * @return tns names or null
	 * @throws IOException
	 */
	private Element getAsElement(final String text) {
		final Element root = DocumentHelper.createElement("tnsnames");
		Element entry = null;
		Element currentElement = root;

		Token token;

		final int ADD_ENTRY = 3;
		final int ADD_ELEMENT = 1;
		final int ADD_ATTRIBUTE = 2;
		int nextAction = ADD_ENTRY;

		final TnsNamesTokenizer st = new TnsNamesTokenizer(text);
		while (st.hasMoreTokens()) {
			token = st.nextToken();
			switch (token.getType()) {
			case WHITE:
				continue;
			case OPEN_PAREN:
				nextAction = ADD_ELEMENT;
				token = st.nextToken();
				currentElement = currentElement.addElement(token.getValue().toLowerCase());
				continue;
			case CLOSE_PAREN:
				if (currentElement == null) {
					throw new IllegalStateException("unbalanced parens");
				}
				currentElement = currentElement.getParent();
				if (currentElement == entry) {
					nextAction = ADD_ENTRY;
				}
				continue;
			case EQUALS:
				nextAction = ADD_ATTRIBUTE;
				continue;
			}

			switch (nextAction) {
			case ADD_ATTRIBUTE:
				currentElement.setText(token.getValue());
				break;
			case ADD_ELEMENT:
				final String elementName = token.getValue().toLowerCase();
				currentElement.addElement(elementName);
				break;
			case ADD_ENTRY:
				entry = currentElement = root.addElement("entry");
				currentElement.addAttribute("name", token.getValue());
				break;
			}

		}

		return root;
	}

	class Token {

		String val;
		int tokenType;

		Token(final String val) {
			this.val = val;
			if ("(".equals(val)) {
				tokenType = OPEN_PAREN;
			} else if ("=".equals(val)) {
				tokenType = EQUALS;
			} else if (")".equals(val)) {
				tokenType = CLOSE_PAREN;
			} else if (" ".equals(val) || "\t".equals(val)) {
				tokenType = WHITE;
			} else {
				tokenType = TEXT;
			}
			//System.out.println("token: '" + val + "'");
		}

		String getValue() {
			return val;
		}

		boolean compareTo(final String other) {
			return val.equals(other);
		}

		int getType() {
			return tokenType;
		}
	}

	class TnsNamesTokenizer {

		StringTokenizer st = null;

		TnsNamesTokenizer(final String text) {
			st = new StringTokenizer(text, "= ()", true);
		}

		Token nextToken() {
			return new Token(st.nextToken());
		}

		boolean hasMoreTokens() {
			return st.hasMoreTokens();
		}
	}
} // ConfigOracle
