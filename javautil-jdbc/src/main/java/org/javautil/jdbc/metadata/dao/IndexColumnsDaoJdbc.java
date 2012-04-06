/*
 * Exportedinfos.java
 *
 * Created on December 23, 2005, 11:57 AM
 *
 * To change this template, choose Tools | Template Manager and open the
 * template in the editor.
 */

package org.javautil.jdbc.metadata.dao;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.jdbc.metadata.IndexColumn;


/**
 * 
 * TODO write a work around - oracle doesn't close the cursor on this call
 * 
 *       Nasty hack to record exceptions and passback because oracle was
 *       throwing exceptions when statistics were locked on the table or index.
 */
public class IndexColumnsDaoJdbc  {

	private String catalog;

	private String schemaPattern;

	private String tableName;

	private boolean unique;

	private boolean approximate;
	
	private DatabaseMetaData metadata = null;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private List<IndexColumn> indexColumns = new ArrayList<IndexColumn>();

	private final List<Exception> exceptions = new ArrayList<Exception>();

	public IndexColumnsDaoJdbc() {
	}

	public IndexColumnsDaoJdbc(final DatabaseMetaData metadata, final String catalog,
			final String schemaPattern, final String tableName,
			final boolean unique, final boolean approximate

	) throws SQLException {
		setMetadata(metadata);
		this.catalog = catalog;
		this.schemaPattern = schemaPattern;
		this.tableName = tableName;
		this.unique = unique;
		this.approximate = approximate;
	}

	

	private void process() throws SQLException {
		ResultSet rs = getMetadata().getIndexInfo(catalog, schemaPattern,
				tableName, unique, approximate);
		// TODO automatically get correct type but this would create a circular dependency
		logger.warn("If this is an oracle connection the performance bites should have used OracleDatabaseMetaDataHelper");

		

		try {
			int rowcount = 0;
			if (rs != null) {
				while (rs.next()) {
					rowcount++;
					final IndexColumn info = new IndexColumn();
					info.setTableCat(rs.getString("table_cat"));
					info.setTableSchem(rs.getString("table_schem"));
					info.setTableName(rs.getString("table_name"));
					info.setNonUnique(rs.getBoolean("non_unique"));
					info.setIndexQualifier(rs.getString("index_qualifier"));
					info.setIndexName(rs.getString("index_name"));
					info.setType(rs.getShort("type"));
					info.setOrdinalPosition(rs.getShort("ordinal_position"));
					info.setColumnName(rs.getString("column_name"));
					info.setAscOrDesc(rs.getString("asc_or_desc"));
					info.setCardinality(rs.getInt("cardinality"));
					info.setPages(rs.getInt("pages"));
					info.setFilterCondition(rs.getString("filter_condition"));
					if (info.getIndexName() != null) {
						if (logger.isDebugEnabled()) {
						logger.debug("adding information for index "
								+ info.getIndexName());
						}
						indexColumns.add(info);
					} else {
						logger.warn("discarding non column information for "
								+ info.getIndexName());
					}
				}
				logger.info("rowcount " + rowcount);
				rs.close();
			}
		} catch (final SQLException e) {
			final String msg = "while getting index information :\n" + catalog
					+ " schemaPattern " + schemaPattern + " tableName "
					+ tableName + " unique " + unique + " approximate "
					+ approximate + " ";
			final SQLException s = new SQLException(msg + e.getMessage());
			logger.error(msg + " " + e.getMessage());
			exceptions.add(s);
			logger.error(msg + e.getMessage());

		}

	}

	public List<IndexColumn> getIndexColumns() {
		return indexColumns;
	}


	public List<Exception> getExceptions() {
		return exceptions;
	}

	public String getSchemaPattern() {
		return schemaPattern;
	}

	public void setSchemaPattern(final String schemaPattern) {
		this.schemaPattern = schemaPattern;
	}

	/**
	 * @return the metadata
	 */
	public DatabaseMetaData getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata the metadata to set
	 */
	public void setMetadata(DatabaseMetaData metadata) {
		this.metadata = metadata;
	}


}
