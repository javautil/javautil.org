package org.javautil.jdbc.metadata;

import java.sql.ResultSet;

import org.javautil.dataset.ColumnAttributes;
import org.javautil.jdbc.metadata.renderer.XmlMeta;

//import org.dom4j.Element;

// TODO replace with ColumnMetadata eliminate the commented code
/**
 * getColumns
 * 
 * <pre>
 *  public synchronized ResultSet getColumns(String catalog,
 *                       String schemaPattern,
 *                       String tableNamePattern,
 *                       String columnNamePattern) throws SQLException
 * Get a description of table columns available in a catalog.
 * Only column descriptions matching the catalog, schema, table and column name criteria are returned. They are ordered by TABLE_SCHEM, TABLE_NAME and ORDINAL_POSITION.
 * Each column description has the following columns:
 * TABLE_CAT String =&gt; table catalog (may be null)
 * TABLE_SCHEM String =&gt; table schema (may be null)
 * TABLE_NAME String =&gt; table name
 * COLUMN_NAME String =&gt; column name
 * DATA_TYPE short =&gt; SQL type from java.sql.Types
 * TYPE_NAME String =&gt; Data source dependent type name
 * COLUMN_SIZE int =&gt; column size. For char or date types this is the maximum number of characters, for numeric or decimal types this is precision.
 * BUFFER_LENGTH is not used.
 * DECIMAL_DIGITS int =&gt; the number of fractional digits
 * NUM_PREC_RADIX int =&gt; Radix (typically either 10 or 2)
 * NULLABLE int =&gt; is NULL allowed?
 * columnNoNulls - might not allow NULL values
 * columnNullable - definitely allows NULL values
 * columnNullableUnknown - nullability unknown
 * REMARKS String =&gt; comment describing column (may be null)
 * COLUMN_DEF String =&gt; default value (may be null)
 * SQL_DATA_TYPE int =&gt; unused
 * SQL_DATETIME_SUB int =&gt; unused
 * CHAR_OCTET_LENGTH int =&gt; for char types the maximum number of bytes in the column
 * ORDINAL_POSITION int =&gt; index of column in table (starting at 1)
 * IS_NULLABLE String =&gt; &quot;NO&quot; means column definitely does not allow NULL values; &quot;YES&quot; means the column might allow NULL values. An empty string means nobody knows.
 * Parameters:
 * catalog - a catalog name; &quot;&quot; retrieves those without a catalog; null means drop catalog name from the selection criteria
 * schemaPattern - a schema name pattern; &quot;&quot; retrieves those without a schema
 * tableNamePattern - a table name pattern
 * columnNamePattern - a column name pattern
 * </pre>
 */
public class Column implements ColumnAttributes {
	/**
	 * 
	 */

	private String schema;
	private String tableName;
	private Integer columnIndex;
	/*
	 * The COLUMN_NAME from DatabaseMetaData
	 */
	private String columnName;
	/**
	 * from java.sql.Types
	 */
	private Integer dataType;
	/**
	 * TODO document
	 */
	private String columnTypeName;
	/**
	 * For char or date types this is the maximum number of characters<br/>
	 * for numeric or decimal types this is precision.
	 * 
	 * Populated from DatabaseMetaData.getColumns()
	 */
	private Integer columnSize;

	/**
	 * Number of fractional digits if the number is positive
	 */
	private int scale;
	/** the number of fractional digits */
	// private int decimalDigits;
	/** Radix (typically either 10 or 2) */
	private int numberPrecisionRadix;
	private int javaType = -1;
	private final String javaObjectType = null;

	/**
	 * is NULL allowed? Assumable values
	 * <ul>
	 * <li>0 - java.sql.DatabaseMetaData.columnNoNulls - might not allow NULL
	 * values</li>
	 * <li>1 - java.sql.DatabaseMetaData.columnNullable - definitely allows NULL
	 * values</li>
	 * <li>2 - java.sql.DatabaseMetaData.columnNullableUnknown - nullability
	 * unknown</li>
	 * </ul>
	 */
	private Boolean nullable;

	// private int nullable;
	/*
	 * comment describing column (may be null)
	 * 
	 * TODO this is also comments;
	 */
	private String remarks;
	/**
	 * default value (may be null)
	 */
	private String defaultValue;

	/**
	 * For char types the maximum number of bytes in the column.
	 */
	private int charOctetLength;
	/**
	 * index of column in table (starting at 1).
	 */
	private int ordinalPosition;
	// IS_NULLABLE String => "NO" means column definitely does not allow NULL
	// values; "YES" means the column might allow NULL values. An empty string
	// means nobody knows.

	/**
	 * Java acceptable name for an attribute for this columnName.
	 */
	private String attributeName = null;
	/**
	 * Same as attributeName with first letter capitalized.
	 * 
	 * Used for example in "get" + getAttributeNameInitCap() in generating
	 * accessors.
	 */
	private final String attributeNameInitCap = null;

	private String sqlType = null;
	/**
	 * Database catalog.
	 */
	private String catalog;

	public Column(final ResultSet rs) throws java.sql.SQLException {
		schema = rs.getString("TABLE_SCHEM");
		tableName = rs.getString("TABLE_NAME");
		columnName = rs.getString("COLUMN_NAME");
		dataType = rs.getInt("DATA_TYPE");
		columnTypeName = rs.getString("TYPE_NAME");
		columnSize = rs.getInt("COLUMN_SIZE");
		// decimalDigits = rs.getInt("DECIMAL_DIGITS");
		scale = rs.getInt("DECIMAL_DIGITS");
		numberPrecisionRadix = rs.getInt("NUM_PREC_RADIX");
		final int nullable = rs.getInt("NULLABLE");
		setNullable(nullable);
		remarks = rs.getString("REMARKS");
		defaultValue = rs.getString("COLUMN_DEF");
		charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");
		ordinalPosition = rs.getInt("ORDINAL_POSITION");

	}

	public Column() {
	}

	/**
	 * is NULL allowed? Assumable values
	 * <ul>
	 * <li>0 - java.sql.DatabaseMetaData.columnNoNulls - might not allow NULL
	 * values</li>
	 * <li>1 - java.sql.DatabaseMetaData.columnNullable - definitely allows NULL
	 * values</li>
	 * <li>2 - java.sql.DatabaseMetaData.columnNullableUnknown - nullability
	 * unknown</li>
	 * </ul>
	 */
	public void setNullable(final int val) {
		switch (val) {
		case 0:
			nullable = Boolean.FALSE;
			break;
		case 1:
			nullable = Boolean.TRUE;
			break;
		case 2:
			nullable = null;
			break;
		default:
			throw new IllegalArgumentException(
					"must be 0,1 or 2 see jdbc MetaData spec");
		}
	}

	public void setNullable(final String val) {
		if ("N".equals(val)) {
			nullable = Boolean.FALSE;
		} else if ("Y".equals(val)) {
			nullable = Boolean.TRUE;
		} else if (XmlMeta.NULL_ALLOWED.equals(val)) {
			nullable = Boolean.TRUE;
		} else if (XmlMeta.NOT_NULL.equals(val)) {
			nullable = Boolean.FALSE;
		} else if (val == null) {
			nullable = null;
		} else {
			throw new IllegalArgumentException("expected '"
					+ XmlMeta.NULL_ALLOWED + "' |  '" + XmlMeta.NOT_NULL
					+ "' | null | 'Y' | 'N' but got '" + val + "'");
		}
	}

	@Override
	public Boolean isDefinitelyNullable() {
		final boolean retval = nullable == null ? false : nullable
				.booleanValue();

		return retval;
	}

	@Override
	public Boolean isNotNullable() {
		final boolean retval = nullable == null ? false : !nullable
				.booleanValue();

		return retval;
	}

	@Override
	public Boolean isUnknownNullable() {
		return nullable == null;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public void setColumnTypeName(String typeName) {
		this.columnTypeName = typeName;
	}

	@Override
	public Integer getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(Integer columnSize) {
		this.columnSize = columnSize;
	}

	@Override
	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public int getNumberPrecisionRadix() {
		return numberPrecisionRadix;
	}

	public void setNumberPrecisionRadix(int numberPrecisionRadix) {
		this.numberPrecisionRadix = numberPrecisionRadix;
	}

	public int getJavaType() {
		return javaType;
	}

	public void setJavaType(int javaType) {
		this.javaType = javaType;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getCharOctetLength() {
		return charOctetLength;
	}

	public void setCharOctetLength(int charOctetLength) {
		this.charOctetLength = charOctetLength;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getJavaObjectType() {
		return javaObjectType;
	}

	public String getAttributeNameInitCap() {
		return attributeNameInitCap;
	}

	@Override
	public String getColumnTypeName() {
		return columnTypeName;
	}

	@Override
	public Integer getColumnType() {
		return dataType;
	}

	@Override
	public String getComments() {
		return remarks;
	}

	@Override
	public Boolean isNullable() {
		return nullable;
	}

	@Override
	public void setComments(String comments) {
		this.remarks = comments;

	}

	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return "Column [schema=" + schema + ", tableName=" + tableName
				+ ", columnIndex=" + columnIndex + ", columnName=" + columnName
				+ ", dataType=" + dataType + ", typeName=" + columnTypeName
				+ ", columnSize=" + columnSize + ", scale=" + scale
				+ ", numberPrecisionRadix=" + numberPrecisionRadix
				+ ", javaType=" + javaType + ", javaObjectType="
				+ javaObjectType + ", nullable=" + nullable + ", remarks="
				+ remarks + ", defaultValue=" + defaultValue
				+ ", charOctetLength=" + charOctetLength + ", ordinalPosition="
				+ ordinalPosition + ", attributeName=" + attributeName
				+ ", attributeNameInitCap=" + attributeNameInitCap
				+ ", sqlType=" + sqlType + ", catalog=" + catalog + "]";
	}

}
