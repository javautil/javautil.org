package org.javautil.core;

import java.util.List;

/**
 * 
 * 
 * <p>
 * Provides a subset of the functionality of command line parsing as supported
 * in Unix and variants. Does not properly handle single quoted strings \'
 * </p>
 */
public class CommandLineTokenizer {
	private final List<String> resultBuffer = new java.util.ArrayList<String>();

	private final StringBuffer tokenBuffer = new StringBuffer();

	private void addToken() {
		if (tokenBuffer.length() > 0) {
			resultBuffer.add(tokenBuffer.toString());
			tokenBuffer.setLength(0);
		}
	}

	// /**
	// * Goofy internal utility to avoid duplicated code. If the specified
	// * StringBuffer is not empty, its contents are appended to the resulting
	// * array (temporarily stored in the specified ArrayList). The StringBuffer
	// * is then emptied in order to begin storing the next argument.
	// * @param resultBuffer the List temporarily storing the resulting
	// * argument array.
	// * @param buf the StringBuffer storing the current argument.
	// */
	// private static void appendToBuffer(
	// List<String> resultBuffer,
	// StringBuffer buf) {
	// if (buf.length() > 0) {
	// resultBuffer.add(buf.toString());
	// buf.setLength(0);
	// }
	// }

	/**
	 * Parses the specified command line into an array of String tokens.
	 * Arguments containing spaces should be enclosed in quotes. Quotes that
	 * should be in the argument string should be escaped with a preceding
	 * backslash ('\') character. Backslash characters that should be in the
	 * argument string should also be escaped with a preceding backslash
	 * character.
	 * 
	 * @param commandLine
	 *            the command line to parse
	 * @return an argument array representing the specified command line.
	 */
	public String[] tokenize(final String commandLineText) {

		char[] chars;
		if (commandLineText != null) {
			chars = commandLineText.toCharArray();
			final int z = commandLineText.length();
			boolean insideQuotes = false;

			for (int i = 0; i < z; ++i) {
				final char c = chars[i];
				switch (c) {
				case '"':
					addToken();
					insideQuotes = !insideQuotes;
					break;
				case '\\':
					if ((z > i + 1)
							&& ((chars[i + 1] == '"') || (chars[i + 1] == '\\'))) {
						tokenBuffer.append(chars[i + 1]);
						++i;
					} else {
						tokenBuffer.append('\\');
					}
					break;
				default:

					if (insideQuotes) {
						tokenBuffer.append(c);
					} else {
						if (Character.isWhitespace(c)) {
							addToken();

						} else {
							tokenBuffer.append(c);
						}
					}
				}

			}
			addToken();

		}

		final String[] result = new String[resultBuffer.size()];
		return (resultBuffer.toArray(result));
	}

}
