package org.javautil.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Formatter;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 */
public class StringHelper {
	private static final String specialSaveChars = "=: \t\r\n\f#!";

	/** A table of hex digits */
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static Logger logger = Logger.getLogger(StringHelper.class
			.getName());

	private static Pattern firstWordPattern = Pattern
			.compile("[\t ]*([^\t ]*)");

	private String escapingCharacters = null;

	private String escapeString = null;

	private boolean cookCalled = false;

	/**
	 * Returns a string with no trailing whitespace. If the input is null,
	 * returns null. If the input has trailing whitespace, returns the
	 * appropriate substring. If the input has no trailing whitespace the input
	 * is returned;
	 * 
	 * @param rawData
	 * @return
	 */
	public static String trimRight(final String rawData) {
		String ret = null;
		if (rawData != null) {
			for (int i = rawData.length() - 1; i >= 0; i--) {
				if (!Character.isWhitespace(rawData.charAt(i))) {
					if (i == rawData.length() - 1) {
						ret = rawData;
					} else {
						ret = rawData.substring(0, i + 1);
					}
					break;
				}
				ret = "";
			}
		}
		return ret;
	}

	/**
	 * Converts a sql object name to a java identifier.
	 * 
	 * The identifier is formed by stripping all "_" characters and converting
	 * all characters following an "_" to upper case.
	 */
	public static String attributeName(final String columnName) {
		final StringBuffer buff = new StringBuffer(columnName.length());
		int i = 0;
		String upperCase = null;
		String rc;
		while (i < columnName.length()) {
			if (columnName.charAt(i) == '_') {
				if (i <= columnName.length()) {
					upperCase = "" + columnName.charAt(++i);
					buff.append(upperCase.toUpperCase().charAt(0));
					i++;
				}
			} else {
				final String lowerCase = "" + columnName.charAt(i++);
				buff.append(lowerCase.toLowerCase());
			}
		}
		rc = new String(buff);
		rc = rc.replaceAll("#", "Nbr");
		rc = rc.replaceAll("\\$", "Dollar");
		return rc;
	}

	public static String attributeNameInitCap(final String name) {
		if (name == null || name.length() == 0) {
			throw new java.lang.IllegalArgumentException(
					"name is null or empty");
		}
		final String temp = attributeName(name);
		final String returnValue = temp.substring(0, 1).toUpperCase()
				+ temp.substring(1);
		return returnValue;
	}

	/**
	 * Converts a sql object name to a java identifier with the initial
	 * character upper case.
	 * 
	 * The identifier is formed by uppercasing the first character, stripping
	 * all "_" characters and converting all characters following an "_" to
	 * upper case.
	 */
	public static String attributeNameUpper(final String columnName) {
		String rc = null;
		rc = attributeName(columnName);
		rc = rc.substring(0, 1).toUpperCase() + rc.substring(1);
		return rc;
	}

	/**
	 * @return <b>true</B> if both strings are <b>null</b> or they are equal
	 *         (letter case ignored); <b>false</b> otherwise.
	 */
	public final static boolean compare(final String s1, final String s2) {
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1 == null || s2 == null) {
			return false;
		}
		return s1.toUpperCase().equals(s2.toUpperCase());
	}

	public final static boolean compare(final String[] s1, final String[] s2) {
		boolean rc = true;
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1 == null || s2 == null) {
			return false;
		}
		final ArrayList<String> v1 = new ArrayList<String>();
		final ArrayList<String> v2 = new ArrayList<String>();
		int i;
		for (i = 0; i < s1.length; i++) {
			if (s1[i] == null) {
				continue;
			}
			final String s = s1[i].trim();
			if (s.length() > 0) {
				v1.add(s);
			}
		}
		for (i = 0; i < s2.length; i++) {
			if (s2[i] == null) {
				continue;
			}
			final String s = s2[i].trim();
			if (s.length() > 0) {
				v2.add(s);
			}
		}
		if (v1.size() != v2.size()) {
			return false;
		}
		for (i = 0; i < v1.size(); i++) {
			final String str1 = v1.get(i);
			final String str2 = (String) v2.get(i);
			if (!compare(str1, str2)) {
				rc = false;
			}
		}
		return rc;
	}

	/**
	 * Provides a 'cooked' StringBuffer for a canonical StringBuffer by
	 * prefixing any escapingCharacters in the raw String with the escapeString
	 * 
	 * @param raw
	 * @param escapingCharacters
	 *            a string of characters that require escaping
	 * @param escapeString
	 *            The string that should precede any characters that require
	 *            escaping.
	 * @return The cooked version of the string.
	 */
	public static String cooked(final String raw,
			final String escapingCharacters, final String escapeString) {
		// optimistic view, no escape characters
		String rc = null;
		StringBuffer buff = null;
		if (raw != null) {
			buff = new StringBuffer(raw.length());
			// check to see if we have any work to do
			final StringTokenizer tokenizer = new StringTokenizer(raw,
					escapingCharacters);
			if (tokenizer.countTokens() > 1) { // an escapable character was
				// found
				for (int index = 0; index < raw.length(); index++) {
					final char currChar = raw.charAt(index);
					if (escapingCharacters.indexOf(currChar) > -1) {
						buff.append(escapeString);
					}
					buff.append(currChar);
				}
				rc = new String(buff);
			} else { // no work required, just return the original string
				rc = raw;
			}
		}
		return rc;
	}

	public static String emit(final Date date) {
		return date == null ? "" : date + "";
	}

	public static String emit(final double value, final boolean wasNull) {
		return wasNull ? "" : "" + value;
	}

	public static String emit(final int value, final boolean wasNull) {
		return wasNull ? "" : "" + value;
	}

	public static String emit(final String string) {
		return string == null ? "" : string;
	}

	public static String emitCell(final double value, final boolean wasNull) {
		return wasNull ? "&nbsp;" : "" + value;
	}

	public static String emitCell(final int value, final boolean wasNull) {
		return wasNull ? "&nbsp;" : "" + value;
	}

	public static String emitCell(final Integer val) {
		String rc = null;
		rc = val == null ? "&nbsp;" : val.toString();
		return rc;
	}

	public static String emitCell(final Object o) {
		return o == null ? "&nbsp;" : o.toString();
	}

	/**
	 * Returns the literal inside a quoted string whether delimited by ' or ".
	 * If not
	 * 
	 * @param in
	 * @return
	 */
	public static String extractLiteral(final String in) {

		String retval = null;
		final char dquote = '\"';
		final char quote = '\'';
		final char first = in.charAt(0);
		final char last = in.charAt(in.length() - 1);
		if (first == dquote || first == quote) {

			if (first == last) {
				retval = in.length() > 2 ? in.substring(1, in.length() - 1)
						: "";
			} else {
				throw new IllegalArgumentException(
						"improperly delimited string " + in);
			}
		} else {
			retval = in;
		}
		return retval;

	}

	/*
	 * public int firstNonWhite(String text) { for (keyStart=0; keyStart<len;
	 * keyStart++) { firstChar = line.charAt(keyStart);
	 * if(whiteSpaceChars.indexOf(firstChar) == -1) { }
	 */
	public static int firstNotIn(final String text, final String excludeText) {
		return firstNotIn(text, excludeText, 0);
	}

	public static int firstNotIn(final String text, final String excludeText,
			final int offset) {
		int rc = -1;
		final int len = text.length();
		char testChar;
		for (int index = offset; index < len; index++) {
			testChar = text.charAt(index);
			if (excludeText.indexOf(testChar) == -1) {
				rc = index;
				break;
			}
		}
		return rc;
	}

	/**
	 * 
	 * maxLineLength is only
	 * 
	 * @param words
	 * @param leadInLength
	 * @param maxLineLength
	 * @return
	 */
	public static String formatWithCommas(final Collection<String> words,
			final int leadInLength, final int maxLineLength) {
		int maxLength = 0;
		for (final String word : words) {
			if (word.length() > maxLength) {
				maxLength = word.length();
			}
		}
		final StringBuilder mb = new StringBuilder();
		for (int i = 0; i < leadInLength; i++) {
			mb.append(" ");
		}
		final String margin = mb.toString();
		final int workLength = maxLineLength - maxLength;
		final int wordsPerLine = workLength / (maxLength + 2);

		final StringBuilder b = new StringBuilder();
		int wordsThisLine = 0;

		b.append(margin);

		System.out.println(b.toString());
		int wordNbr = 0;
		for (final String word : words) {
			wordNbr++;

			b.append(word);
			if (wordNbr < words.size()) {
				b.append(",");
			}
			for (int i = word.length(); i <= maxLength; i++) {
				b.append(" ");
			}

			wordsThisLine++;

			if (wordsThisLine >= wordsPerLine) {
				b.append("\n");
				b.append(margin);
				wordsThisLine = 0;
			}

		}
		final String retval = b.toString();
		System.out.println(b);
		return retval;
	}

	public static String getClassName(final String fullName) {
		String returnValue = fullName;
		if (fullName.indexOf('.') > 0) {
			final String[] nodes = fullName.split("\\.");

			if (nodes.length < 1) {
				throw new IllegalStateException("index error on " + fullName
						+ " " + nodes.length);
			}
			returnValue = nodes[nodes.length - 1];
		}
		// logger.info("was ' " + fullName + "' returning '" + returnValue +
		// "'");
		return returnValue;

	}

	public static Reader getResourceReader(final Object caller,
			final String resourceName) throws IOException {
		BufferedReader in = null;
		if (caller == null) {
			throw new IllegalArgumentException("caller is null");
		}
		try {
			final InputStream stream = caller.getClass().getClassLoader()
					.getResourceAsStream(resourceName);
			if (stream == null) {
				throw new java.lang.IllegalArgumentException(
						"irresolvable resource " + resourceName);
			}
			in = new BufferedReader(new InputStreamReader(stream, "8859_1"));

		} catch (final java.io.IOException i) {
			throw new java.io.IOException("Failed to load resource: '"
					+ resourceName + "'\n" + i.getMessage());
		}
		return in;
	}

	public static String getRevision(final String in) {
		final String REVISION = "$Revision:";
		final String upper = in.toUpperCase();
		String version = in;
		if (upper.startsWith(REVISION)) {
			version = in.substring(REVISION.length());
		}
		version.replaceAll("$", "");
		return version.trim();
	}

	public static ArrayList<String> getTokens(final String in) {
		boolean inQuote = false;
		final ArrayList<String> rc = new ArrayList<String>();
		final StringTokenizer sst = new StringTokenizer(in, "\"\t' ", true);
		while (sst.hasMoreTokens()) {
			final String t = sst.nextToken();
			if (t.equals(" ") || t.equals("\t")) {
				continue;
			}
			if (!(t.equals("'") || t.equals("\""))) {
				rc.add(t);
			} else { // starts with " or '
				final StringBuilder quoted = new StringBuilder();
				// String delim = t;
				while (sst.hasMoreTokens()) {
					inQuote = true;
					final String u = sst.nextToken();
					if (u.equals(t)) {
						inQuote = false;
						break;
					}
					quoted.append(u);

				}
				if (inQuote) {
					throw new IllegalArgumentException(
							"improperly terminated quoted string " + in);
				}
				rc.add(quoted.toString());

			}
		}
		return rc;
	}

	/**
	 * Return the first index of any of the characters in anyText in the string
	 * text
	 */
	public static int indexOfAny(final String text, final String anyText) {
		return indexOfAny(text, anyText, 0);
	}

	/**
	 * Return the index of the first occurrence of any of the characters in the
	 * string anyText within the String text starting with an index of offset;
	 */
	public static int indexOfAny(final String text, final String anyText,
			final int offset) {
		int rc = -1;
		final int len = text.length();
		char test;
		for (int index = offset; index < len; index++) {
			test = text.charAt(index);
			if (anyText.indexOf(test) != -1) {
				rc = index;
				break;
			}
		}
		return rc;
	}

	/**
	 * Return a new String with the first character converted to upper case.
	 * 
	 * @param val
	 */
	public static String initCap(final String val) {
		String outVal = "";
		for (int c = 0; c < val.length(); c++) {
			if (c == 0) {
				outVal += val.toUpperCase().charAt(c);
			} else {
				outVal += val.charAt(c);
			}
		}
		return outVal;
	}

	/**
	 * Case insensitive comparison of two strings with leading and trailing
	 * white space removed.
	 * 
	 */
	public static boolean isFuzzyMatch(final String a, final String b) {
		return a.trim().compareToIgnoreCase(b.trim()) == 0 ? true : false;
	}

	public static boolean isTrue(final String val) {
		final String lowerCase = val.toLowerCase().trim();
		boolean returnValue = false;
		if (lowerCase.equals("true") || lowerCase.equals("yes")
				|| lowerCase.equals("1")) {
			returnValue = true;
		} else {
			if (lowerCase.equals("false") || lowerCase.equals("no")
					|| lowerCase.equals("0")) {
				returnValue = false;
			} else {
				throw new java.lang.IllegalArgumentException("argument is not "
						+ "'true', 'yes','1','false','no','0' is: '" + val
						+ "'");
			}
		}
		return returnValue;
	}

	public static String labelName(final String columnName) {
		final StringBuffer buff = new StringBuffer(columnName.length());
		int i = 0;
		String upperCase = null;
		while (i < columnName.length()) {
			if (i == 0) {
				buff.append(columnName.charAt(i++));
			} else if (columnName.charAt(i) == '_') {
				if (i <= columnName.length()) {
					buff.append(" ");
					upperCase = "" + columnName.charAt(++i);
					buff.append(upperCase.toUpperCase().charAt(0));
					i++;
				}
			} else {
				final String lowerCase = "" + columnName.charAt(i++);
				buff.append(lowerCase.toLowerCase());
			}
		}
		return new String(buff);
	}

	// TODO this does not belong here
	public static String loadFromResource(final Object caller,
			final String resourceName) throws java.io.IOException {
		final StringBuffer buff = new StringBuffer();
		BufferedReader in;
		if (caller == null) {
			throw new IllegalArgumentException("caller is null");
		}
		try {
			final ClassLoader cl = caller.getClass().getClassLoader();
			final InputStream stream = cl.getResourceAsStream(resourceName);
			if (stream == null) {
				throw new java.lang.IllegalArgumentException(
						"irresolvable resource " + resourceName);
			}
			in = new BufferedReader(new InputStreamReader(stream, "8859_1"));
			while (true) {
				final String line = in.readLine();
				if (line == null) {
					break;
				}
				buff.append(line);
			}
		} catch (final java.io.IOException i) {
			throw new java.io.IOException("Failed to load resource: '"
					+ resourceName + "'\n" + i.getMessage());
		}
		return new String(buff);
	}

	public static String nvl(final Object o, final String dflt) {
		return o == null ? dflt : o.toString();
	}

	public static String nvl(final String arg) {
		return nvl(arg, "");
	}

	public static String nvl(final String arg, final String dflt) {
		return arg == null ? dflt : arg;
	}

	/**
	 * @todo fix this, this is expensive
	 * @param src
	 * @return
	 */
	public static final String removeEOL(final String src) {
		if (src == null) {
			return null;
		}
		int n = src.length() - 1;
		while (n >= 0) {
			if (src.charAt(n) != ' ' && src.charAt(n) != '\t'
					&& src.charAt(n) != '\n' && src.charAt(n) != '\0') {
				return src.substring(0, n + 1);
			}
			n--;
		}
		return "";
	}

	// TODO what is this and why can we just use the replaceAll method that has
	// been around for
	public static String replaceAll(final String src, final String find,
			final String replace) {
		String ret = src;
		int index = ret.indexOf(find);
		boolean substitution = index > -1;
		if (substitution) {
			logger.info("callparm '" + src + "' '" + find + "' '" + replace);
			while (substitution) {
				logger.info("ret loop'" + ret + "'");
				if (index == 0) {
					final String left = replace;
					final String right = ret.substring(find.length());
					ret = left + right;
					logger.info("beginning '" + ret + "'" + left + "' '"
							+ right + "'");
				} else if (index == ret.length() - find.length()) {
					final String left = ret.substring(0, index - 1);
					final String right = replace;
					ret = left + right;
					logger.info("end '" + ret + "' ' " + left + "' '" + right
							+ "'");
				} else {
					final String left = ret.substring(0, index - 1);
					final String right = ret.substring(index + find.length());
					ret = left + replace + right;
					logger.info("middle '" + left + "' '" + replace + "' '"
							+ right + "'");

				}
				index = ret.indexOf(find);
				substitution = index > -1;
			}
			logger.info("returning '" + ret + "'");
		}
		return ret;
	}

	public static String rightPadNoFail(final String val, final int length) {

		final StringBuilder b = new StringBuilder(length);
		b.append(val);
		// b = val == null ? new StringBuilder(length) : new StringBuilder(val);
		// int blen = b.length();
		// if (val != null && val.length() > length) {
		// throw new IllegalArgumentException("val length is " + val.length() +
		// " longer than specified length " + length);
		// }
		while (b.length() < length) {
			b.append(" ");
		}
		return b.toString();
	}

	public static String rightPad(final String val, final int length) {

		final StringBuilder b = new StringBuilder(length);
		b.append(val);
		// b = val == null ? new StringBuilder(length) : new StringBuilder(val);
		// int blen = b.length();
		if (val != null && val.length() > length) {
			throw new IllegalArgumentException("val length is " + val.length()
					+ " longer than specified length " + length);
		}
		while (b.length() < length) {
			b.append(" ");
		}
		return b.toString();
	}

	public static String leftPad(final String val, final int length) {

		final StringBuilder b = new StringBuilder(length);
		// b = val == null ? new StringBuilder(length) : new StringBuilder(val);
		// int blen = b.length();
		if (val != null && val.length() > length) {
			throw new IllegalArgumentException("val length is " + val.length()
					+ " longer than specified length " + length);
		}
		while (b.length() < (length - val.length())) {
			b.append(" ");
		}
		b.append(val);
		return b.toString();
	}

	/**
	 * The pad character can be a string such as "&nbsp;" for length calculation
	 * purposes it is treated as a single character.
	 * 
	 * @param val
	 * @param length
	 * @param paddingChar
	 * @return
	 */
	public static String leftPadWithChar(final String val, final int length,
			final String paddingChar) {

		final StringBuilder b = new StringBuilder(length);
		if (val != null && val.length() > length) {
			throw new IllegalArgumentException("val length is " + val.length()
					+ " longer than specified length " + length);
		}
		int padLength = length - val.length();

		while (padLength-- > 0) {
			b.append(paddingChar);
		}
		b.append(val);
		return b.toString();
	}

	public static String[] split(final String inString, final String separator) {
		final StringTokenizer toke = new StringTokenizer(inString, separator);
		final String strings[] = new String[toke.countTokens()];
		for (int i = 0; toke.hasMoreTokens(); i++) {
			strings[i] = toke.nextToken();
		}
		return strings;
	}

	/*
	 * Converts unicodes to encoded &#92;uxxxx and writes out any of the
	 * characters in specialSaveChars with a preceding slash
	 */
	public static String toEncodedSave(final String theString,
			final boolean escapeSpace) {
		final int len = theString.length();
		final StringBuffer outBuffer = new StringBuffer(len * 2);
		for (int x = 0; x < len; x++) {
			final char aChar = theString.charAt(x);
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace) {
					outBuffer.append('\\');
				}
				outBuffer.append(' ');
				break;
			case '\\':
				outBuffer.append('\\');
				outBuffer.append('\\');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			default:
				if (aChar < 0x0020 || aChar > 0x007e) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex(aChar >> 12 & 0xF));
					outBuffer.append(toHex(aChar >> 8 & 0xF));
					outBuffer.append(toHex(aChar >> 4 & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					if (specialSaveChars.indexOf(aChar) != -1) {
						outBuffer.append('\\');
					}
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	public static String toHexDump(final String in) {
		final String alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final StringBuilder b = new StringBuilder();
		StringBuilder left = new StringBuilder();
		StringBuilder right = new StringBuilder();
		for (int i = 0; i < in.length(); i++) {
			if (i > 0 && i % 4 == 0) {
				left.append(' ');
			}
			if (i > 0 && i % 16 == 0) {
				b.append(left);
				b.append("  ");
				b.append(right);
				b.append("\n");
				left = new StringBuilder();
				right = new StringBuilder();
			}
			final char ch = in.charAt(i);
			final int ich = ch;
			left.append(Integer.toHexString(ch));
			if (ich > 0 && ich < 27) {
				right.append("^");
				right.append(alphabet.charAt(ich));
			} else if (ich >= 32 && ich <= 127) {
				right.append(ch);
			} else {
				right.append('#');
			}
		}
		b.append(left);
		b.append(" ");
		b.append(right);
		b.append("\n");
		return b.toString();

	}

	// move to the capitalizer routine
	// private static String getFirstWordUpper(String text) {
	// return getFirstWordUpper(text, false);
	// }
	//
	// private static String getFirstWordUpper(String text, boolean debug) {
	// if (text == null) {
	// throw new IllegalArgumentException("text is null");
	// }
	// Matcher m = firstWordPattern.matcher(text);
	// String firstWord = null;
	// if (m.find()) {
	// firstWord = m.group();
	// }
	// if (debug) {
	// logger.info("text " + text);
	// logger.info("first word '" + firstWord + "'");
	// }
	// if (firstWord == null) {
	// throw new
	// IllegalStateException("there were no matches for first word in '" + text
	// + "'");
	// }
	// String firstUpper = firstWord.toUpperCase();
	// if (debug) {
	// logger.info("firstUpper: '" + firstUpper + "'");
	// }
	// return firstUpper;
	//
	// }

	/**
	 * Convert a nibble to a hex character
	 * 
	 * @param nibble
	 *            the nibble to convert.
	 */
	private static char toHex(final int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	public StringHelper() {
	}

	public void cook(final String escapingCharacters, final String escapeString)
			throws java.lang.IllegalArgumentException {
		cookCalled = true;
		if (escapingCharacters == null) {
			throw new java.lang.IllegalArgumentException(
					"escapingCharacters is null");
		}
		if (escapingCharacters == "") {
			throw new java.lang.IllegalArgumentException(
					"escapingCharacters is empty");
		}
		if (escapeString == null) {
			throw new java.lang.IllegalArgumentException("escapeString is null");
		}
		if (escapeString == "") {
			throw new java.lang.IllegalArgumentException(
					"escapingString is empty");
		}
		this.escapingCharacters = escapingCharacters;
		this.escapeString = escapeString;
	}

	public String cooked(final String raw)
			throws java.lang.IllegalStateException {
		if (escapingCharacters == null || escapeString == null) {
			throw new java.lang.IllegalStateException(
					"must call cook before invoking this method cook called "
							+ cookCalled);
		}
		return cooked(raw, escapingCharacters, escapeString);
	}

	/**
	 * @todo currently expands all tabs and doesn't consider tabstops
	 * @param text
	 *            StringBuffer
	 * @param tabSize
	 *            int
	 * @return StringBuffer
	 */
	public StringBuffer expandLeadingTabs(final String text, final int tabSize) {
		final String[] lines = text.split("\n");
		final StringBuffer pad = new StringBuffer();
		for (int i = 0; i < tabSize; i++) {
			pad.append(" ");
		}
		final StringBuffer buff = new StringBuffer();
		for (final String line : lines) {
			buff.append(line.replaceAll("\t", "    "));
			// buff.append(line);
			buff.append("\n");
		}
		return buff;
	}

	public String format(final String formatMask, final Object o) {
		final Object[] objects = { o };
		final Formatter formatter = new Formatter();
		final String returnValue = formatter.format(formatMask, objects)
				.toString();
		return returnValue;
	}

	public String formatAsJavaString(final String text) {
		final String[] lines = text.split("\n");
		final StringBuffer buff = new StringBuffer();
		StringBuffer lineBuffer;
		int lineNumber = 1;
		for (final String line : lines) {
			lineBuffer = new StringBuffer();
			lineBuffer.append("\"");
			lineBuffer.append(line.replaceAll("\t", "    "));
			lineBuffer.append("\\n\"");
			if (lineNumber++ < lines.length) {
				lineBuffer.append(" +\n");
			}
			buff.append(lineBuffer);
		}
		if (!(text.charAt(text.length() - 1) == ';')) {
			buff.append(";");
		}
		// System.out.println(buff.toString());
		return buff.toString();
	}

	/**
	 * @todo delete this, test for non static method
	 * @deprecated
	 * @param columnName
	 * @return
	 */
	@Deprecated
	public String getAttributeName(final String columnName) {
		return attributeName(columnName);
	}

	public String indentWithSpaces(final String text,
			final int leadingSpaceCount) {
		final String[] lines = text.split("\n");
		final StringBuffer pad = new StringBuffer();
		final StringBuffer buff = new StringBuffer(text.length() + 32);
		for (int i = 0; i < leadingSpaceCount; i++) {
			pad.append(" ");
		}
		for (final String line : lines) {
			buff.append(pad);
			buff.append(line);
			buff.append("\n");
		}
		return buff.toString();
	}

	/**
	 * Returns the array of characters in the input string.
	 * 
	 * If the input String is null, the result is null.
	 * 
	 * @param input
	 * @return all of the characters in the input string.
	 */

	public static char[] getChars(final String input) {
		char[] retval = null;
		if (input != null) {
			retval = new char[input.length()];
			input.getChars(0, input.length(), retval, 0);
		}
		return retval;
	}

	public void setNullDefault(final String val) {
	}
}
