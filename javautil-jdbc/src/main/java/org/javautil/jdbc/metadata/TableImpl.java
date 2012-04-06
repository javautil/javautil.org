package org.javautil.jdbc.metadata;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.javautil.dataset.ColumnAttributes;
import org.javautil.jdbc.metadata.containers.ForeignKeys;
import org.javautil.jdbc.metadata.containers.IndexGeneric;
import org.javautil.jdbc.metadata.dao.ForeignKeysDaoJdbc;
import org.javautil.jdbc.metadata.dao.PrimaryKeysJdbc;
import org.javautil.text.AsString;
import org.javautil.text.CommaFormatter;

/**
 * TODO create OracleTable that extends this.
 */

public  class TableImpl implements Iterable<ColumnAttributes>,
		Table {

	protected static final String newline = System
			.getProperty("line.separator");

	private List<ColumnAttributes> columns = new ArrayList<ColumnAttributes>();

	private Map<String, ColumnAttributes> columnByName = new TreeMap<String, ColumnAttributes>();

	private String schemaName;

	private String tableName;

	private String catalogName;

	private ForeignKeys exportedKeys;

	private ForeignKeys importedKeys;

	private ArrayList<ForeignKeyImpl> references;

	private Map<String, Index> indexesByIndexName = new TreeMap<String, Index>();

	/**
	 * 
	 */
	private final ArrayList<ForeignKeyImpl> referencedBy = new ArrayList<ForeignKeyImpl>();

	private IndexColumns indexColumns;

	private PrimaryKey primaryKey;

	private String tableType; // "table" or "view"

	private String remarks;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private long metaDataRetrieveStart;

	private long metaDataColumnsDone;

	private long exportedKeysDone;

	private long importedKeysDone;

	private long indexInfosDone;

	private long primaryKeyDone;

	private boolean dumpColumns = false;
	private static final String SQL_MARGIN = "    ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getIndexInfosMillis()
	 */
	@Override
	public int getIndexInfosMillis() {
		return (int) ((getIndexInfosDone() - getImportedKeysDone()) / 1000000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getImportedKeysMillis()
	 */
	@Override
	public int getImportedKeysMillis() {
		return (int) ((getIndexInfosDone() - getImportedKeysDone()) / 1000000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.Table#addColumn(com.dbexperts.jdbc
	 * .DatabaseMetaData.Column)
	 */
	@Override
	public void addColumn(final ColumnAttributes column) {
		if (column == null) {
			throw new IllegalArgumentException("column is null");
		}
		final ColumnAttributes oldCol = columnByName
				.get(column.getColumnName());
		if (oldCol != null) {
			throw new IllegalArgumentException("column already exists: "
					+ column.getColumnName() + " " + column.toString());
		}
		columnByName.put(column.getColumnName(), column);
		getColumns().add(column);

	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getAccessors()
	// */
	// public String getAccessors() {
	// final StringBuffer buff = new StringBuffer();
	// for (final ColumnAttributes column : getColumns()) {
	// buff.append(column.getAccessors());
	// buff.append("\n");
	// }
	// final String returnValue = buff.toString();
	// return returnValue;
	// }
	//
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getArrayTypeDDL()
	 */
	// @Override
	// public String getArrayTypeDDL() {
	// final TreeMap<String, String> types = new TreeMap<String, String>();
	// for (final ColumnAttributes column : getColumns()) {
	// types.put(column.getArrayTypeDDL(), null);
	// }
	// final StringBuffer buff = new StringBuffer();
	// for (final String typeDefinition : types.keySet()) {
	// buff.append(typeDefinition);
	// }
	// return buff.toString();
	// }

	// /*
	// * (non-Javadoc)
	// *
	// * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getAsJavaAttributes()
	// */
	// public String getAsJavaAttributes() {
	// final StringBuffer buff = new StringBuffer();
	// for (final ColumnAttributes column : getColumns()) {
	// final String columnAsAttribute = column.getAsJavaAttribute();
	// buff.append(columnAsAttribute);
	//			buff.append("\n"); //$NON-NLS-1$
	// }
	// final String returnValue = buff.toString();
	//
	// return returnValue;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.Table#getColumn(java.lang.String)
	 */
	@Override
	public ColumnAttributes getColumn(final String columnName) {
		return columnByName.get(columnName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getSQLColumnList()
	 */
	@Override
	public String getSQLColumnList() {
		final StringBuffer stmtText = new StringBuffer();

		boolean needsComma = false;
		for (final ColumnAttributes column : getColumns()) {
			if (needsComma) {
				stmtText.append("," + newline);
			}
			needsComma = true;
			final String label = column.getColumnName().toLowerCase();
			stmtText.append("     ");
			stmtText.append(label);
		}
		stmtText.append(newline);

		return stmtText.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getJavaColumnList()
	 */
	@Override
	public String getJavaColumnList() {
		final StringBuffer stmtText = new StringBuffer();

		boolean needsComma = false;
		for (final ColumnAttributes column : getColumns()) {
			if (needsComma) {
				stmtText.append(",\\n\" +\n");
			}
			needsComma = true;
			final String label = column.getColumnName().toLowerCase();
			stmtText.append("        \"    ");
			stmtText.append(label);
		}
		stmtText.append("\\n\" +\n");

		return stmtText.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getColumns()
	 */
	@Override
	public List<ColumnAttributes> getColumns() {
		return columns;
	}

	// public void addColumn(ColumnAttributes column) {
	// if (columns == null) {
	// columns = new ArrayList<ColumnAttributes>();
	// }
	// columns.add(column);
	// }

	// public ArrayList<String> getCreateImportedForeignKeys() {
	// ArrayList<String> retval = null;
	// if (importedKeys != null) {
	// retval = TableToSQL.getForeignKeysDDL(this,importedKeys.getValues());
	// } else {
	// retval = new ArrayList<String>();
	// }
	// return retval;
	// }
	//
	// public ArrayList<String> getCreateExportedForeignKeys() {
	// ArrayList<String> retval = null;
	// if (exportedKeys != null) {
	// retval = TableToSQL.getForeignKeysDDL(this,exportedKeys.getValues());
	// } else {
	// retval = new ArrayList<String>();
	// }
	// return retval;
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getExportedKeys()
	 */
	@Override
	public ForeignKeys getExportedKeys() {
		return exportedKeys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.dbexperts.jdbc.DatabaseMetaData.Table#getExportedKeys(java.sql.
	 * DatabaseMetaData)
	 */
	@Override
	public ForeignKeys getExportedKeys(final DatabaseMetaData meta)
			throws SQLException {
		if (exportedKeys == null) {
			exportedKeys = new ForeignKeysDaoJdbc(meta, catalogName,
					schemaName, tableName, true);
		}
		return exportedKeys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getExportedKeysDone()
	 */
	@Override
	public long getExportedKeysDone() {
		return exportedKeysDone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getImportedKeys()
	 */
	@Override
	public ForeignKeys getImportedKeys() {

		return importedKeys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.dbexperts.jdbc.DatabaseMetaData.Table#getImportedKeys(java.sql.
	 * DatabaseMetaData)
	 */
	@Override
	public ForeignKeys getImportedKeys(final DatabaseMetaData meta)
			throws SQLException {
		if (importedKeys == null) {
			importedKeys = new ForeignKeysDaoJdbc(meta, catalogName,
					schemaName, tableName, false);
		}
		return importedKeys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getImportedKeysDone()
	 */
	@Override
	public long getImportedKeysDone() {
		return importedKeysDone;
	}

	// TODO this should be in DAO
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.dbexperts.jdbc.DatabaseMetaData.Table#getIndexInfos(java.sql.
	 * DatabaseMetaData)
	 */

	// public IndexColumns getIndexInfos(final Connection conn)
	// throws SQLException {
	// if (indexColumns == null) {
	// indexColumns = new IndexColumnsDaoJdbc(conn, "", // catalog
	//					// //$NON-NLS-1$
	// this.schemaName, this.tableName, false, // unique indexes
	// // only?
	// true // approximate statistics
	// );
	//
	// }
	// return indexColumns;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getIndexInfosDone()
	 */
	@Override
	public long getIndexInfosDone() {
		return indexInfosDone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getMetaDataColumnsDone()
	 */
	@Override
	public long getMetaDataColumnsDone() {
		return metaDataColumnsDone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getMetaDataRetrieveStart()
	 */
	@Override
	public long getMetaDataRetrieveStart() {
		return metaDataRetrieveStart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getPrimaryKey()
	 */
	@Override
	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.dbexperts.jdbc.DatabaseMetaData.Table#getPrimaryKey(java.sql.
	 * DatabaseMetaData)
	 */
	@Override
	public PrimaryKey getPrimaryKey(final DatabaseMetaData meta)
			throws SQLException {
		if (primaryKey == null) {
			primaryKey = PrimaryKeysJdbc.getPrimaryKey(meta, catalogName,
					schemaName, tableName);
		}
		return primaryKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getPrimaryKeyDone()
	 */
	public long getPrimaryKeyDone() {
		return primaryKeyDone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getReferencedBy()
	 */
	@Override
	public ArrayList<ForeignKeyImpl> getReferencedBy() {
		return referencedBy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getReferences()
	 */
	@Override
	public ArrayList<ForeignKeyImpl> getReferences() {
		return references;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getRemarks()
	 */
	@Override
	public String getRemarks() {
		return remarks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getSchemaName()
	 */
	@Override
	public String getSchemaName() {
		return schemaName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getJavaSelectStatement()
	 */
	@Override
	public String getJavaSelectStatement() {
		final StringBuffer stmtText = new StringBuffer();
		stmtText.append("\"\" +\n");
		stmtText.append("         \"SELECT\\n\" +\n");
		stmtText.append(getJavaColumnList());
		stmtText.append("        \"FROM " + tableName + "\\n\";\n\n");
		return new String(stmtText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getSelectStatement()
	 */
	@Override
	public String getSelectStatement() {
		final StringBuffer stmtText = new StringBuffer();
		stmtText.append("SELECT " + newline);
		stmtText.append(CommaFormatter
				.formatWithCommas(getColumnNames(), 8, 80));
		stmtText.append("FROM " + tableName + newline);
		return new String(stmtText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getTableName()
	 */
	@Override
	public String getTableName() {
		return tableName;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// com.dbexperts.jdbc.DatabaseMetaData.Table#getTableNameAsAttributeName()
	// */
	// public String getTableNameAsAttributeName() {
	// return commaFormatter.attributeNameInitCap(getTableName());
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getTableType()
	 */
	@Override
	public String getTableType() {
		return tableType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#iterator()
	 */
	@Override
	public Iterator<ColumnAttributes> iterator() {
		return getColumns().iterator();
	}

	/**
	 * @param metaDataRetrieveStart
	 *            the metaDataRetrieveStart to set
	 */
	public void setMetaDataRetrieveStart(final long metaDataRetrieveStart) {
		this.metaDataRetrieveStart = metaDataRetrieveStart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.Table#setColumns(java.util.Collection
	 * )
	 */
	@Override
	public void setColumns(final Collection<ColumnAttributes> columns) {
		// List<ColumnAttributes> cols =new ArrayList<Column>();

		this.columns = new ArrayList<ColumnAttributes>();
		this.columnByName = new TreeMap<String, ColumnAttributes>();
		for (final ColumnAttributes column : columns) {
			addColumn(column);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.Table#setExportedKeys(com.dbexperts
	 * .jdbc.DatabaseMetaData.ForeignKeys)
	 */
	@Override
	public void setExportedKeys(final ForeignKeys exportedKeys) {
		this.exportedKeys = exportedKeys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.Table#setImportedKeys(com.dbexperts
	 * .jdbc.DatabaseMetaData.ForeignKeys)
	 */
	@Override
	public void setImportedKeys(final ForeignKeys importedKeys) {
		this.importedKeys = importedKeys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.Table#setPrimaryKey(com.dbexperts
	 * .jdbc.DatabaseMetaData.PrimaryKey)
	 */
	@Override
	public void setPrimaryKey(final PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.Table#setReferences(java.util.ArrayList
	 * )
	 */
	@Override
	public void setReferences(final ArrayList<ForeignKeyImpl> references) {
		this.references = references;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.Table#setRemarks(java.lang.String)
	 */
	@Override
	public void setRemarks(final String remarks) {
		this.remarks = remarks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#size()
	 */
	@Override
	public int size() {
		return getColumns().size();
	}

	protected void setSchemaName(final String schemaName) {

		this.schemaName = schemaName;
	}

	protected void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	protected void setTableType(final String type) {
		this.tableType = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getColumnNames()
	 */
	@Override
	public ArrayList<String> getColumnNames() {
		final ArrayList<String> columnNames = new ArrayList<String>();
		for (final ColumnAttributes c : getColumns()) {
			columnNames.add(c.getColumnName());
		}
		return columnNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getInsertText()
	 */
	@Override
	public String getInsertText() {

		final ArrayList<String> columnNames = getColumnNames();

		final StringBuilder text = new StringBuilder();
		// StringBuilder line = new StringBuilder();
		text.append("\n");
		text.append(SQL_MARGIN);
		text.append("insert into  ");
		text.append(tableName);
		text.append(" ( ");
		text.append(newline);
		text.append(CommaFormatter.formatWithCommas(columnNames, 8, 80));
		text.append(newline);

		text.append(" ) values (");
		for (int i = 0; i < getColumns().size() - 1; i++) {
			text.append("?,");
		}
		text.append("?)\n");
		final String retval = text.toString();
		System.out.println(retval);
		return text.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.Table#getCommonColumns(com.dbexperts
	 * .jdbc.DatabaseMetaData.Table)
	 */
	@Override
	public List<ColumnAttributes> getCommonColumns(final Table other) {
		final ArrayList<ColumnAttributes> retval = new ArrayList<ColumnAttributes>();
		for (final ColumnAttributes ocol : other.getColumns()) {
			if (columnByName.get(ocol.getColumnName()) != null) {
				retval.add(ocol);
			}
		}
		return retval;
	}

	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see com.dbexperts.jdbc.DatabaseMetaData.Table#toString()
	// */
	// @Override
	// public String toString() {
	// final String TAB = "\n\r";
	//
	// String retValue = "Table ( "
	// + super.toString()
	// + TAB
	// + "catalog = '"
	// + this.getCatalog()
	// + "'"
	// + TAB
	// // + "columnByName = '" + this.columnByName + "'" + TAB
	// + "columns = '" + this.getColumns() + "'" + TAB
	// + "exportedKeys = '" + this.exportedKeys + "'" + TAB
	// + "exportedKeysDone = '" + this.getExportedKeysDone() + "'"
	// + TAB + "importedKeys = '" + this.importedKeys + "'" + TAB
	// + "importedKeysDone = '" + this.getImportedKeysDone() + "'"
	// + TAB + "indexInfos = '" + this.getIndexInfos() + "'" + TAB
	// + "indexInfosDone = '" + this.getIndexInfosDone() + "'" + TAB
	// + "logger = '" + this.logger + "'" + TAB
	// + "metaDataColumnsDone = '" + this.getMetaDataColumnsDone()
	// + "'" + TAB + "metaDataRetrieveStart = '"
	// + this.getMetaDataRetrieveStart() + "'" + TAB
	// + "primaryKey = '" + this.primaryKey + "'" + TAB
	// + "primaryKeyDone = '" + this.getPrimaryKeyDone() + "'" + TAB
	// + "referencedBy = '" + this.referencedBy + "'" + TAB
	// + "references = '" + this.references + "'" + TAB
	// + "remarks = '" + this.remarks + "'" + TAB + "schemaName = '"
	// + this.schemaName + "'" + TAB + "tableName = '"
	// + this.tableName + "'" + TAB + "tableType = '" + this.tableType
	// + "'" + TAB + " )";
	//
	// return retValue;
	// }

	/**
	 * @param dumpColumns
	 *            the dumpColumns to set
	 */
	public void setDumpColumns(final boolean dumpColumns) {
		this.dumpColumns = dumpColumns;
	}

	/**
	 * @return the dumpColumns
	 */
	public boolean isDumpColumns() {
		return dumpColumns;
	}

	/**
	 * @param metaDataColumnsDone
	 *            the metaDataColumnsDone to set
	 */
	public void setMetaDataColumnsDone(final long metaDataColumnsDone) {
		this.metaDataColumnsDone = metaDataColumnsDone;
	}

	/**
	 * @param exportedKeysDone
	 *            the exportedKeysDone to set
	 */
	public void setExportedKeysDone(final long exportedKeysDone) {
		this.exportedKeysDone = exportedKeysDone;
	}

	/**
	 * @param importedKeysDone
	 *            the importedKeysDone to set
	 */
	public void setImportedKeysDone(final long importedKeysDone) {
		this.importedKeysDone = importedKeysDone;
	}

	/**
	 * @param indexInfosDone
	 *            the indexInfosDone to set
	 */
	public void setIndexInfosDone(final long indexInfosDone) {
		this.indexInfosDone = indexInfosDone;
	}

	/**
	 * @param primaryKeyDone
	 *            the primaryKeyDone to set
	 */
	public void setPrimaryKeyDone(final long primaryKeyDone) {
		this.primaryKeyDone = primaryKeyDone;
	}

	/**
	 * @param indexInfos
	 *            the indexInfos to set
	 */
	public void setIndexInfos(final IndexColumns indexInfos) {
		this.indexColumns = indexInfos;
	}

	/**
	 * @return the indexInfos
	 */
	public IndexColumns getIndexInfos() {
		return indexColumns;
	}

	@Override
	public Map<String, Index> getIndexesByName() {
		if (indexesByIndexName == null) {
			indexesByIndexName = new TreeMap<String, Index>();

		}
		if (indexColumns != null) {
			for (final IndexColumn column : indexColumns.getIndexColumns()) {
				final String indexName = column.getIndexName();
				if (indexName == null) {
					throw new IllegalStateException("index name is null "
							+ column.toString());
				}
				Index index = indexesByIndexName.get(indexName);
				if (index == null) {
					index = new IndexGeneric(column.getTableSchem(),
							column.getTableName(), column.getIndexName());
					indexesByIndexName.put(indexName, index);
				}
				index.addIndexColumn(column);
			}
		} else {
			logger.warn("no indexes found for " + this.toString());
		}
		return indexesByIndexName;
	}

	@Override
	public String toString() {
		final AsString as = new AsString();
		return as.toString(this);
	}

	/**
	 * 
	 * @return the catalog name
	 */
	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(final String catalogName) {
		this.catalogName = catalogName;
	}
}
