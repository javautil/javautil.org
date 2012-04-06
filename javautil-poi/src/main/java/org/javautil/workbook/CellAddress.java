package org.javautil.workbook;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class CellAddress {
	private static final char A = 'A';
	private static final String[] columnNames = new String[256];

	public static String getCellAddress(final int rowIndex,
			final int columnIndex) {
		return getColumnName(columnIndex) + (rowIndex + 1);
	}

	static {
		for (int i = 0; i < 256; i++) {
			columnNames[i] = computeColumnName(i);
		}
	}

	public static String getColumnName(final int columnIndex) {
		if (columnIndex < 0 || columnIndex > 255) {
			throw new IllegalArgumentException("out of range [0-255] "
					+ columnIndex);
		}
		return columnNames[columnIndex];
	}

	public static String computeColumnName(final int columnIndex) {

		String retval = null;
		if (columnIndex < 26) {
			final char[] chars = new char[1];
			chars[0] = letterByIndex(columnIndex);
			retval = new String(chars);
		} else {
			final char[] chars = new char[2];
			chars[1] = letterByIndex(columnIndex % 26);
			chars[0] = letterByIndex(columnIndex / 26 - 1);
			retval = new String(chars);
		}
		return retval;

	}

	public static char letterByIndex(final int ind) {
		if (ind > 25) {
			throw new IllegalArgumentException("index out of bounds 0-25 : "
					+ ind);
		}
		final char index = (char) ind;
		final char units = (char) (A + (index));
		return units;
	}

	// public static void main(final String args[]) {
	// for (int i = 0; i < 256; i++) {
	// System.out.println(i + " " + getColumnName(i));
	// }
	// }
}
