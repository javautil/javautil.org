package org.javautil.jdbc.metadata;

public class NameEscaper {
	public static String escape(final String string) {
		String retval = null;
		if (string != null) {
			for (int i = 0; i < string.length(); i++) {
				switch (string.charAt(i)) {
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
