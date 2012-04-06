package org.javautil.jdbc;

import org.javautil.dataset.ColumnAttributes;

public interface JdbcToJavaMapper {

	String getJavaObjectType(ColumnAttributes mdata);

	String getGetter(ColumnAttributes column);

	/**
	 * 
	 * @param columnName
	 * @param typeName
	 * @param dataType
	 *            see #java.sql.Type
	 * @param nullable
	 * 
	 * 
	 * @param columnSize
	 * @param decimalDigits
	 * @return the java type the String representation of the class Name
	 */
	String getJavaObjectType(String columnName, String typeName,
			Integer dataType, Integer columnSize, Integer decimalDigits);

	String getImportClass(ColumnAttributes column);

	String getJdbcType(ColumnAttributes column);

}