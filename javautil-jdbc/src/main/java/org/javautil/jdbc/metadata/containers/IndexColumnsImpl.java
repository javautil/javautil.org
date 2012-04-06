/*
 * Exportedinfos.java
 *
 * Created on December 23, 2005, 11:57 AM
 *
 * To change this template, choose Tools | Template Manager and open the
 * template in the editor.
 */

package org.javautil.jdbc.metadata.containers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.jdbc.metadata.IndexColumn;
import org.javautil.jdbc.metadata.IndexColumns;

//import com.dbexperts.oracle.OracleDatabaseMetaDataHelper;

/**
 * 
 * @todo write a work around - oracle doesn't close the cursor on this call
 * 
 *       Nasty hack to record exceptions and passback because oracle was
 *       throwing exceptions when statistics were locked on the table or index.
 */
// http://java.sun.com/j2se/1.4.2/docs/api/java/sql/DatabaseMetaData.html#getIndexInfo(java.lang.String,%20java.lang.String,%20java.lang.String,%20boolean,%20boolean)
// 1. TABLE_CAT String => table catalog (may be null)
// 2. TABLE_SCHEM String => table schema (may be null)
// 3. TABLE_NAME String => table name
// 4. NON_UNIQUE boolean => Can index values be non-unique. false when TYPE is
// tableIndexStatistic
// 5. INDEX_QUALIFIER String => index catalog (may be null); null when TYPE is
// tableIndexStatistic
// 6. INDEX_NAME String => index name; null when TYPE is tableIndexStatistic
// 7. TYPE short => index type:
// * tableIndexStatistic - this identifies table statistics that are returned in
// conjuction with a table's index descriptions
// * tableIndexClustered - this is a clustered index
// * tableIndexHashed - this is a hashed index
// * tableIndexOther - this is some other style of index
// 8. ORDINAL_POSITION short => column sequence number within index; zero when
// TYPE is tableIndexStatistic
// 9. COLUMN_NAME String => column name; null when TYPE is tableIndexStatistic
// 10. ASC_OR_DESC String => column sort sequence, "A" => ascending, "D" =>
// descending, may be null if sort sequence is not supported; null when TYPE is
// tableIndexStatistic
// 11. CARDINALITY int => When TYPE is tableIndexStatistic, then this is the
// number of rows in the table; otherwise, it is the number of unique values in
// the index.
// 12. PAGES int => When TYPE is tableIndexStatisic then this is the number of
// pages used for the table, otherwise it is the number of pages used for the
// current index.
// 13. FILTER_CONDITION String => Filter condition, if any. (may be null)
// TODO should not be a DAO and a container
public class IndexColumnsImpl implements IndexColumns {

	private String catalog;

	private String schemaPattern;

	private String tableName;

	private boolean unique;

	private boolean approximate;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private List<IndexColumn> infoList = new ArrayList<IndexColumn>();

	private final List<Exception> exceptions = new ArrayList<Exception>();

	public IndexColumnsImpl() {
	}



	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.IndexInfos#getIndexInfos()
	 */
	@Override
	public List<IndexColumn> getIndexColumns() {
		return infoList;
	}

	public void setIndexColumns(final List<IndexColumn> columns) {
		this.infoList = columns;
	}


	

	@Override
	public String getTableName() {
		return tableName;
	}

	public void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	@Override
	public boolean isUnique() {
		return unique;
	}

	public void setUnique(final boolean unique) {
		this.unique = unique;
	}

	@Override
	public String getSchemaName() {
		return schemaPattern;
	}
}
