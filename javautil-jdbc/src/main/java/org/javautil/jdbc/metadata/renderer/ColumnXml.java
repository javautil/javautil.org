package org.javautil.jdbc.metadata.renderer;

// todo nuke
/**
 * getColumns
 * 
 * <pre>
 * 
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
public class ColumnXml {

	// public static Column getColumn (final Element e) {
	// Column col = new Column();
	// col.setAttributeName(e.attributeValue(XmlMeta.FIELD_NAME));
	// col.setColumnName(e.attributeValue(XmlMeta.COLUMN_NAME));
	// col.setTypeName(e.attributeValue(XmlMeta.DATA_TYPE));
	// final String columnSizeText = e.attributeValue(XmlMeta.COLUMN_SIZE);
	// if (columnSizeText != null) {
	// col.setColumnSize(Integer.parseInt(columnSizeText));
	// }
	//
	// col.setNullable(e.attributeValue(XmlMeta.NULLABLE));
	// final String colSize = e.attributeValue(XmlMeta.COLUMN_SIZE);
	//
	// if (colSize != null) {
	// col.setColumnSize(new Integer(colSize));
	// }
	// col.setDataType(e.attributeValue(XmlMeta.DATA_TYPE));
	// return col;
	// }
	//
	// public static Element getAsElement(Column col, final boolean
	// emitCamelCase) {
	// final Element e = DocumentHelper.createElement(XmlMeta.FIELD);
	// if (emitCamelCase) {
	// e.addAttribute(XmlMeta.FIELD_NAME,StringHelper.attributeName(col.getColumnName()));
	// }
	// String xmlType;
	// // todo what is this
	// try {
	// xmlType = col.getXmlType();
	// } catch (final UnsupportedDataTypeException ude) {
	// xmlType = ude.getMessage();
	// }
	// e.addAttribute(XmlMeta.COLUMN_NAME,col.getColumnName());
	// e.addAttribute(XmlMeta.DATA_TYPE,col.getTypeName());
	// e.addAttribute(XmlMeta.SQL_TYPE,col.getSqlType());
	// if (col.getColumnSize() != null) {
	// e.addAttribute(XmlMeta.COLUMN_SIZE,col.getColumnSize().toString());
	// }
	// if (col.isNullable() != null) {
	// e.addAttribute(XmlMeta.NULLABLE,col.isNullable() ? "not null" : null);
	// }
	// return e;
	// }

}
