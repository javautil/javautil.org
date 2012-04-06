package org.javautil.jdbc.metadata.renderer;

import org.javautil.text.StringUtils;

// todo this is redundant with respect to the code in oracle table
public class SQLGenerator {
	private static final String newline = System.getProperty("line.separator");
	private final boolean reservedWordsUpperCase = true;
	private final boolean attributesLowerCase = true;
	private final int attributeNameLength = 30;

	public String getEscapedPaddedAttribute(final String text) {
		String retval = null;
		if (text != null) {
			final String asAttribute = getAttribute(text);
			final String escaped = escape(asAttribute);
			final String padded = StringUtils.rightPad(escaped,
					attributeNameLength);
			retval = padded;
		}
		return retval;
	}

	public String getAttribute(final String text) {
		String retval = null;
		if (text != null) {
			retval = attributesLowerCase ? text.toLowerCase() : text
					.toUpperCase();
		}
		return retval;
	}

	public String getReserved(final String text) {
		String retval = null;
		if (text != null) {
			retval = reservedWordsUpperCase ? text.toUpperCase() : text
					.toLowerCase();
		}
		return retval;
	}

	public static String escape(final String string) {
		String retval = null;
		if (string != null) {
			for (int i = 0; i < string.length(); i++) {
				switch (string.charAt(i)) {
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
				case 'h':
				case 'i':
				case 'j':
				case 'k':
				case 'l':
				case 'm':
				case 'n':
				case 'o':
				case 'p':
				case 'q':
				case 'r':
				case 's':
				case 't':
				case 'u':
				case 'v':
				case 'w':
				case 'x':
				case 'y':
				case 'z':
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
				case 'G':
				case 'H':
				case 'I':
				case 'J':
				case 'K':
				case 'L':
				case 'M':
				case 'N':
				case 'O':
				case 'P':
				case 'Q':
				case 'R':
				case 'S':
				case 'T':
				case 'U':
				case 'V':
				case 'W':
				case 'X':
				case 'Y':
				case 'Z':
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case '$':
				case '_':
					continue;
				default:
					retval = "\"" + string + "\"";
					break;
				}
			}
		}
		if (retval == null) {
			retval = string;
		}
		return retval;

	}

}
