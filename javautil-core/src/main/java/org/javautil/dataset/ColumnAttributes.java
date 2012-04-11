package org.javautil.dataset;

//todo remove commented code
/**
 * jjs from dbexperts
 */
public interface ColumnAttributes {

	public abstract String getAttributeName();

	public abstract String getColumnName();

	/**
	 * @return the columnSize
	 */
	public abstract Integer getColumnSize();

	/**
	 * The database-specific type name.
	 * 
	 * Populate from ResultSetMetaData.getColumnTypeName.
	 */
	public abstract String getColumnTypeName();

	/**
	 * Domain is java.sql.Types Retrieves the designated column's SQL type.
	 * 
	 * Populated from ResultSetMetadata.getColumnType().
	 * 
	 * @return
	 */
	public abstract Integer getColumnType();

	public abstract Boolean isDefinitelyNullable();

	public abstract Boolean isNotNullable();

	public abstract Boolean isUnknownNullable();

	public String getComments();

	public abstract Boolean isNullable();

	/**
	 * Gets the designated column's number of digits to right of the decimal
	 * point. 0 is returned for data types where the scale is not applicable.
	 * 
	 * @return
	 */
	public Integer getScale();

	public abstract void setComments(String comments);
}