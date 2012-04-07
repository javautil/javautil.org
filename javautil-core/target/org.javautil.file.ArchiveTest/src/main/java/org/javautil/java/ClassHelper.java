package org.javautil.java;

// TODO jjs where did this come from and do we need it
public class ClassHelper {

	/**
	 * Returns the class name without the package qualifier.
	 * 
	 * @param fullName
	 * @return The class name without the package qualifier
	 */

	/**
	 * 
	 */
	public static String getUnqualifiedClassName(String className) {
		if (className == null) {
			throw new IllegalArgumentException("className is null");
		}
		int firstChar;
		firstChar = className.lastIndexOf('.') + 1;
		if (firstChar > 0) {
			className = className.substring(firstChar);
		}
		return className;
	}

	@SuppressWarnings("unchecked")
	public static String getPackageName(final Class clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("clazz is null");
		}
		String retval;
		final String fullyQualifiedName = clazz.getName();
		final int lastDot = fullyQualifiedName.lastIndexOf('.');
		if (lastDot > -1) {
			retval = fullyQualifiedName.substring(0, lastDot);
		} else {
			retval = "";
		}
		return retval;
	}

}
