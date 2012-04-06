package org.javautil.jdbc;

/**
 * 
 * @author jjs TODO create interface
 */
public class JdbcJavaNameImpl {

	/**
	 * Converts a sql object name to a java identifier.
	 * 
	 * The identifier is formed by stripping all "_" characters and converting
	 * all characters following an "_" to upper case. The '#' character, if
	 * encountered is replaced with "Number" The '$' character, is replaced with
	 * "Dollar"
	 * 
	 * Leading #, $ is a special case as it is an illegal start of a sql
	 * identifier we don't bother converting to lower case No checking is done
	 * to ensure that column name is a valid non quoted sql identifier.
	 * 
	 */

	public String getAttributeName(final String columnName) {
		final StringBuffer buff = new StringBuffer(columnName.length());
		int i = 0;
		String work = columnName;

		work = work.replace("#", "_NUMBER_");
		work = work.replace("$", "_DOLLAR_");

		while (i < work.length()) {
			final char c = work.charAt(i);
			char appendChar = 0;
			if (c == '_') {
				if (i < work.length() - 1) {
					appendChar = Character.toUpperCase(work.charAt(++i));
				}
			} else {
				appendChar = Character.toLowerCase(c);
			}
			if (appendChar > 0) {
				buff.append(appendChar);
			}
			i++;
		}
		return new String(buff);
	}

	public String getSetter(final String columnName) {
		return "set" + attributeNameInitCap(columnName);
	}

	public String getGetter(final String columnName) {
		return "get" + attributeNameInitCap(columnName);
	}

	public String attributeNameInitCap(final String name) {
		if (name == null || name.length() == 0) {
			throw new java.lang.IllegalArgumentException(
					"name is null or empty");
		}
		final String temp = getAttributeName(name);
		final String returnValue = temp.substring(0, 1).toUpperCase()
				+ temp.substring(1);
		return returnValue;
	}

}
