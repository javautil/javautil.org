package org.javautil.collections;

public class NullEquals {
	/**
	 * 
	 * @param a
	 * @param b
	 * @return true if both are null or a.equals(b)
	 */
	public static boolean equals(Object a, Object b) {
		boolean retval = false;
		if (a == null) {
			if (b == null) {
				retval = true;
			} else {
				retval = false;
			}
		} else {
			retval = a.equals(b);
			
		}
		return retval;
	}
}
