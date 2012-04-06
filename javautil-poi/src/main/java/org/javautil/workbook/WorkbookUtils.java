package org.javautil.workbook;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author bcm
 */
public abstract class WorkbookUtils {

	public static final String GENERAL_FORMAT_MASK = "GENERAL";

	public static final char LEFT_BRACKET = '[';

	public static final char RIGHT_BRACKET = ']';

	public static final char QUESTION_MARK = '?';

	public static final String POUND_STRING = "#";

	private static Map<String, DecimalFormat> numberFormats = new HashMap<String, DecimalFormat>();
	static {

		// taken from jexcelapi, derived from built-in number formats in excel

		numberFormats.put("", new DecimalFormat("#"));
		numberFormats.put("0", new DecimalFormat("0"));
		numberFormats.put("0.00", new DecimalFormat("0.00"));
		numberFormats.put("#,##0", new DecimalFormat("#,##0"));
		numberFormats.put("#,##0.00", new DecimalFormat("#,##0.00"));
		numberFormats.put("($#,##0_);($#,##0)", new DecimalFormat(
				"$#,##0;($#,##0)"));
		numberFormats.put("($#,##0_);[RED]($#,##0)", new DecimalFormat(
				"$#,##0;($#,##0)"));
		numberFormats.put("($#,##0_);[RED]($#,##0)", new DecimalFormat(
				"$#,##0;($#,##0)"));
		numberFormats.put("($#,##0.00_);[RED]($#,##0.00)", new DecimalFormat(
				"$#,##0;($#,##0)"));
		numberFormats.put("0%", new DecimalFormat("0%"));
		numberFormats.put("0.00%", new DecimalFormat("0.00%"));
		numberFormats.put("0.00E+00", new DecimalFormat("0.00E00"));
		numberFormats.put("# ?/?", new DecimalFormat("?/?"));
		numberFormats.put("# ??/??", new DecimalFormat("??/??"));
		numberFormats.put("(#,##0_);(#,##0)",
				new DecimalFormat("#,##0;(#,##0)"));
		numberFormats.put("(#,##0_);[RED](#,##0)", new DecimalFormat(
				"#,##0;(#,##0)"));
		numberFormats.put("(#,##0.00_);(#,##0.00)", new DecimalFormat(
				"#,##0.00;(#,##0.00)"));
		numberFormats.put("(#,##0.00_);[RED](#,##0.00)", new DecimalFormat(
				"#,##0.00;(#,##0.00)"));
		numberFormats.put("_(*#,##0_);_(*(#,##0);_(*\"-\"_);(@_)",
				new DecimalFormat("#,##0;(#,##0)"));
		numberFormats.put("_($*#,##0_);_($*(#,##0);_($*\"-\"_);(@_)",
				new DecimalFormat("$#,##0;($#,##0)"));
		numberFormats.put("_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);(@_)",
				new DecimalFormat("#,##0.00;(#,##0.00)"));
		numberFormats.put(
				"_($* #,##0.00_);_($* (#,##0.00);_($* \"-\"??_);(@_)",
				new DecimalFormat("$#,##0.00;($#,##0.00)"));
		numberFormats.put("##0.0E+0", new DecimalFormat("##0.0E0"));

		// added by bcm to handle common decimal formats
		numberFormats.put("#", new DecimalFormat("#"));
		numberFormats.put("#.0", new DecimalFormat("#.0"));
		numberFormats.put("#.00", new DecimalFormat("#.00"));
		numberFormats.put("#.000", new DecimalFormat("#.000"));
		numberFormats.put("#.0000", new DecimalFormat("#.0000"));
		numberFormats.put("#.00000", new DecimalFormat("#.00000"));
		numberFormats.put("#.000000", new DecimalFormat("#.000000"));

	}

	/**
	 * Converts column number to Excel column characters name, e.g.: 43 => AQ
	 * 
	 * @deprecated use CellAddress.getColumnName
	 */
	@Deprecated
	public static String getColumnId(final int columnIndex) {
		throw new UnsupportedOperationException(
				"Use CellAddress.getColumnName()");
		// if (columnIndex < 0) {
		// throw new IllegalArgumentException(
		// "Converted number must be greater than or equal to zero.");
		// }
		// final int iBase = 'Z' - 'A'; // + 1; // TODO jjs locale independent
		// ????
		// if (iBase > Character.MAX_RADIX) {
		// throw new UnsupportedOperationException(
		// "This JRE can't convert to radix greater than "
		// + Character.MAX_RADIX);
		// }
		// final String convert = Integer.toString(columnIndex - 1, iBase)
		// .toUpperCase();
		// // TODO this is too expensive to do on the fly should be in static
		// // initializer
		// final char[] ac = convert.toCharArray();
		// for (int x = 0; x < ac.length; x++) {
		// final int r = ac.length - x - 1;
		// final char c = ac[x];
		// ac[x] = (char) ('A' - r + Character.digit(c, iBase));
		// }
		// // TODO examine
		// return String.copyValueOf(ac);
	}

	/**
	 * Gets the column number of the string cell reference
	 * 
	 * @param columnId
	 *            the string to parse
	 * @return the column index
	 * @deprecated TODO nuke
	 */
	@Deprecated
	public static int getColumnIndex(final String columnId) {
		throw new UnsupportedOperationException("Use CellAddress method");
		// if (columnId == null) {
		// throw new NullPointerException("columnId is null");
		// }
		// // return CellReference.convertColStringToIndex(columnId);
		// int colnum = 0;
		// final String s2 = columnId.toUpperCase();
		// final int endPos = columnId.length();
		// for (int i = 0; i < endPos; i++) {
		// if (i != 0) {
		// colnum = (colnum + 1) * 26;
		// }
		// colnum += s2.charAt(i) - 'A';
		// }
		// return colnum + 1;
	}

	public static String cleanFormatString(final String formatString) {
		final StringBuilder buff = new StringBuilder();
		boolean upper = false;
		int questionMarkCount = 0;
		for (final char c : formatString.toCharArray()) {
			switch (c) {
			case QUESTION_MARK:
				questionMarkCount++;
				break;
			case LEFT_BRACKET:
				upper = true;
				break;
			case RIGHT_BRACKET:
				upper = false;
			}
			if (upper) {
				buff.append(Character.toUpperCase(c));
			} else {
				buff.append(c);
			}
		}
		String ret = null;
		if (questionMarkCount == buff.length()) {
			ret = POUND_STRING;
		} else {
			ret = buff.toString();
		}
		return ret;
	}

	public static boolean isKnownNumberFormat(final String formatString) {
		return numberFormats.get(formatString) != null;
	}

	public static Number readNumber(final String value,
			final String formatString) throws ParseException {
		Number ret = null;
		final DecimalFormat numberFormat = numberFormats.get(formatString);
		if (numberFormat != null) {
			ret = numberFormat.parse(value);
		} else { // also handles GENERAL_FORMAT_MASK case
			try {
				ret = Double.parseDouble(value);
				if (ret != null && ret.doubleValue() % 1.0 == 0.0) {
					ret = new Long(ret.longValue());
				}
			} catch (final Exception e) {
				if (e instanceof NumberFormatException) {
					throw new ParseException(e.getMessage(), 0);
				} else if (e instanceof NullPointerException) {
					throw new ParseException(e.getMessage(), 0);
				} else {
					throw new ParseException("unexpected exception", 0);
				}
			}
		}
		return ret;
	}

	public static String formatNumber(final Double _value,
			final String formatString) throws ParseException {
		String ret = null;
		Number value = _value;
		// when the number has no fractional part, convert it to a long, so
		// that no trailing zeroes remain unless specified in the format
		if (_value != null && _value.doubleValue() % 1.0 == 0.0) {
			value = new Long(_value.longValue());
		}
		if (formatString.equals(GENERAL_FORMAT_MASK)) {
			ret = String.valueOf(value);
		} else {
			DecimalFormat numberFormat = numberFormats.get(formatString);
			if (numberFormat == null) {
				numberFormat = new DecimalFormat(formatString);
			}
			try {
				ret = numberFormat.format(value);
			} catch (final Exception e) {
				if (!numberFormats.containsKey(formatString)) {
					throw new ParseException(
							"unable to format number using excel proprietary format mask: "
									+ formatString, 0);
				} else if (e instanceof IllegalArgumentException) {
					throw new ParseException(e.getMessage(), 0);
				} else if (e instanceof NullPointerException) {
					throw new ParseException(e.getMessage(), 0);
				} else {
					throw new ParseException("unexpected exception", 0);
				}
			}
		}
		return ret;
	}
}
