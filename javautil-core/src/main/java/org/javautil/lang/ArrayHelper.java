package org.javautil.lang;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class ArrayHelper {
	private static String newline = System.getProperty("line.separator");

	/**
	 * Index of last last not null object.
	 * 
	 * If objects is null or all objects values are null returns -1;
	 * 
	 * @param objects
	 * @return
	 */
	public static int lastNonNullValueIndex(final Object[] objects) {

		int i = -1;
		if (objects != null) {
			for (i = objects.length - 1; i >= 0; i--) {
				if (objects[i] != null) {
					break;
				}
			}
		}
		return i;
	}

	public static String asString(final Object[][] matrix) {
		final StringBuilder b = new StringBuilder();
		for (final Object[] o : matrix) {
			b.append(asString(o));
			b.append(newline);
		}
		return b.toString();
	}

	public static String asString(final Object[] vector) {
		final StringBuilder b = new StringBuilder();
		String retval = "null";
		if (vector == null) {
			retval = "null";
		} else {
			for (int i = 0; i < vector.length; i++) {
				if (i > 0) {
					b.append(" ");
				}
				final Object obj = vector[i];
				b.append(obj == null ? "null" : "'" + obj.toString() + "'\t");

			}
			retval = b.toString();
		}
		return retval;
	}
}
