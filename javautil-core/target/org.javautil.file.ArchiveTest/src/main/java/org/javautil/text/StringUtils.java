package org.javautil.text;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.CharUtils;
import org.apache.log4j.Logger;

/**
 * A Collection of utilities for use with Strings.
 * 
 * @author bcm, jjs
 */
public class StringUtils {

	private static Logger logger = Logger.getLogger(StringUtils.class);

	public static final String EMPTY_STRING = "";

	public static final char SPACE_CHAR = ' ';

	private StringUtils() {
		// prevent instantiation
	}

	/**
	 * Performs a right trim operation on the input String. If the String may
	 * contain non-printable characters, and the String is to printed, it is
	 * also recommended that the getPrintableAscii method be performed first.
	 * 
	 * @param rawData
	 * @return a right trimmed string
	 */
	public static String trimRight(final String rawData) {
		String ret = null;
		if (rawData != null) {
			for (int i = rawData.length() - 1; i >= 0; i--) {
				if (!Character.isWhitespace(rawData.charAt(i))) {
					if (i == 0) {
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
	 * Performs a right trim operation on the input String. If the String may
	 * contain non-printable characters, and the String is to printed, it is
	 * also recommended that the getPrintableAscii method be performed first.
	 * 
	 * @param rawData
	 * @return a left trimmed string
	 */
	public static String trimLeft(final String rawData) {
		String ret = null;
		if (rawData != null) {
			for (int i = 0; i < rawData.length(); i++) {
				if (!Character.isWhitespace(rawData.charAt(i))) {
					if (i == 0) {
						ret = rawData;
					} else {
						int endIndex = rawData.length();
						ret = rawData.substring(i, endIndex);
					}
					break;
				}
				// ret = "";
			}
		}
		if (ret == null && rawData != null) {
			ret = "";
		}

		if (logger.isDebugEnabled()) {
			logger.debug("input: '" + rawData + "' leftTrimmed '" + ret + "'");
		}
		return ret;
	}

	public static String fromCamelCaseToWords(final String camel,
			final char wordSeparator) {
		final StringBuilder b = new StringBuilder();
		final char[] chars = camel.toCharArray();
		for (final char c : chars) {
			if (Character.isLetter(c)) {
				if (Character.isUpperCase(c) && b.length() != 0) {
					b.append(wordSeparator);
				}
				b.append(Character.toLowerCase(c));
			} else {
				b.append(c);
			}
		}
		return b.toString();
	}

	@SuppressWarnings("unchecked")
	public static String asString(final Map map) {
		return asString(map, null);
	}

	@SuppressWarnings("unchecked")
	public static String asString(final Map map, final Integer maxKeys) {
		final StringBuilder buffy = new StringBuilder();
		Iterator<Object> iterator = map.keySet().iterator();
		buffy.append("{");
		int printedCount = 0;
		while (iterator != null && iterator.hasNext()) {
			if (maxKeys == null || printedCount < maxKeys) {
				if (printedCount != 0) {
					buffy.append(", ");
				}
				final Object key = iterator.next();
				buffy.append(key);
				buffy.append(": ");
				final Object value = map.get(key);
				buffy.append(value);
				printedCount++;
			} else {
				buffy.append(", ..." + (map.size() - maxKeys.intValue())
						+ " more, " + map.size() + " total");
				iterator = null;
			}
		}
		buffy.append("}");
		return buffy.toString();
	}

	/**
	 * Cuts all ascii characters from the string that are not printable.
	 * 
	 * @param rawData
	 * @return an ascii printable string
	 */
	public static String getPrintableAscii(final String rawData) {
		String ret = null;
		if (rawData != null) {
			final StringBuilder bob = new StringBuilder(rawData);
			for (int i = 0; i < bob.length(); i++) {
				if (!CharUtils.isAsciiPrintable(bob.charAt(i))) {
					bob.setCharAt(i, SPACE_CHAR);
				}
			}
			ret = bob.toString();
		}
		return ret;
	}

	static String lpad(final String text, final int minLength,
			final String pad, final boolean trim) {
		if (pad == null) {
			throw new IllegalArgumentException("padding character is null");
		}
		if (pad.length() != 1) {
			throw new IllegalArgumentException(
					"padding character is not a single character");
		}
		if (minLength < 1) {
			throw new IllegalArgumentException(
					"minimum characters must be greater than 0");
		}
		/*
		 * In oracle the maximum length of the string is padded length (Here it
		 * is minlength). syntax LPAD( char1, padlength, char2) eg1 LPAD
		 * ('12345678', 5, '0') ==> '12345' eg2 LPAD ('55', 10, '0') ==>
		 * '0000000055'
		 */
		String retval = null;
		int padLength;
		final StringBuilder sb = new StringBuilder();
		if (text == null) {
			retval = text;
			padLength = minLength;
		} else {
			padLength = minLength - text.length();
			if (padLength < 0) {
				padLength = 0;
			}
		}

		if (text != null && text.length() == minLength) {
			retval = text;
		} else if (trim && text != null && text.length() > minLength) {
			retval = text.substring(0, minLength);
		} else if (text != null && text.length() < minLength) {
			while (padLength-- > 0) {
				sb.append(pad);
			}
			sb.append(text.toString());
			retval = sb.toString();
		}
		return retval;
	}

	/**
	 * @deprecated use the csv marshaller and related methods
	 * @param array
	 * @return
	 */
	@Deprecated
	public static String asCSV(final short[] array) {
		final StringBuilder sb = new StringBuilder();
		for (final short element : array) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(element);
		}
		return sb.toString();
	}

	/**
	 * @deprecated use the csv marshaller and related methods
	 * @param array
	 * @return
	 */
	@Deprecated
	public static String asCSV(final long[] array) {
		/**
		 * @deprecated use the csv marshaller and related methods
		 * @param array
		 * @return
		 */
		final StringBuilder sb = new StringBuilder();
		for (final long element : array) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(element);
		}
		return sb.toString();
	}

	public static String rightPad(final String val, final int length) {

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

	/**
	 * Truncates a stringto the specified length.
	 * 
	 * If the input is null, a zero length String is returned.
	 * 
	 * The toString method is called on the object to get the String
	 * representation. Lengths must be non negative.
	 * 
	 * @param in
	 * @param length
	 * @return
	 */

	public static String rightTrim(final Object in, final int length) {
		if (length < 0) {
			throw new IllegalArgumentException("negative length " + length);
		}
		String txt = in == null ? "" : in.toString();
		if (txt.length() > length) {
			txt = txt.substring(0, length);
		}
		return txt;
	}

	public static String toJavaString(Collection<String> strings,
			String leading, boolean withNewLines) {
		StringBuilder sb = new StringBuilder(4096);
		boolean needsPlus = false;
		for (String string : strings) {
			if (needsPlus) {
				sb.append(" + //\n");
			}

			needsPlus = true;
			if (leading != null) {
				sb.append(leading);
			}
			sb.append("\"");
			sb.append(string);
			if (withNewLines) {
				sb.append("\\n");
			}
			sb.append("\"");
		}
		return sb.toString();
	}
}
