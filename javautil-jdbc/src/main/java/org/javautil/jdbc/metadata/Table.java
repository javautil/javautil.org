package org.javautil.jdbc.metadata;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javautil.dataset.ColumnAttributes;
import org.javautil.jdbc.metadata.containers.ForeignKeys;

public interface Table {

	public static final String TABLE = "TABLE";

	public abstract int getIndexInfosMillis();

	public abstract int getImportedKeysMillis();

	public abstract void addColumn(ColumnAttributes column);

	// TODO clean up
	// public abstract String getAccessors();

	// public abstract String getArrayTypeDDL();

	// public abstract String getAsJavaAttributes();

	public abstract ColumnAttributes getColumn(final String columnName);

	public abstract String getSQLColumnList();

	public abstract String getJavaColumnList();

	public abstract List<ColumnAttributes> getColumns();

	// public abstract List<String> getCreateImportedForeignKeys();

	// public abstract String getCreatePrimaryKey();

	// public abstract String getCreateStatement();

	public abstract ForeignKeys getExportedKeys();

	public abstract ForeignKeys getExportedKeys(final DatabaseMetaData meta)
			throws SQLException;

	/**
	 * @return the exportedKeysDone
	 */
	public abstract long getExportedKeysDone();

	public abstract ForeignKeys getImportedKeys();

	public abstract ForeignKeys getImportedKeys(final DatabaseMetaData meta)
			throws SQLException;

	/**
	 * @return the importedKeysDone
	 */
	public abstract long getImportedKeysDone();

	// public abstract IndexColumns getIndexInfos(final Connection meta)
	// throws SQLException;

	/**
	 * @return the indexInfosDone
	 */
	public abstract long getIndexInfosDone();

	/**
	 * @return the metaDataColumnsDone
	 */
	public abstract long getMetaDataColumnsDone();

	/**
	 * @return the metaDataRetrieveStart
	 */
	public abstract long getMetaDataRetrieveStart();

	public abstract PrimaryKey getPrimaryKey();

	public abstract PrimaryKey getPrimaryKey(final DatabaseMetaData meta)
			throws SQLException;

	// public abstract List<ColumnAttributes> getPrimaryKeyColumns();

	// /**
	// * @return the primaryKeyDone
	// */
	// public abstract long getPrimaryKeyDone();

	/**
	 * @return the referencedBy
	 */
	public abstract ArrayList<ForeignKeyImpl> getReferencedBy();

	/**
	 * @return the references
	 */
	public abstract ArrayList<ForeignKeyImpl> getReferences();

	/**
	 * @return the remarks
	 */
	public abstract String getRemarks();

	/**
	 * @return the schemaName
	 */
	public abstract String getSchemaName();

	public abstract String getJavaSelectStatement();

	public abstract String getSelectStatement();

	// public abstract String[] getSelectStatementLines();

	public abstract String getTableName();

	// public abstract String getTableNameAsAttributeName();

	public abstract String getTableType();

	public abstract Iterator<ColumnAttributes> iterator();

	// public abstract void setColumns(final List<ColumnAttributes> columns);

	public abstract void setColumns(final Collection<ColumnAttributes> columns);

	/**
	 * @param exportedKeys
	 *            the exportedKeys to set
	 */
	public abstract void setExportedKeys(final ForeignKeys exportedKeys);

	/**
	 * @param importedKeys
	 *            the importedKeys to set
	 */
	public abstract void setImportedKeys(final ForeignKeys importedKeys);

	/**
	 * @param primaryKey
	 *            the primaryKey to set
	 */
	public abstract void setPrimaryKey(final PrimaryKey primaryKey);

	/**
	 * @param references
	 *            the references to set
	 */
	public abstract void setReferences(
			final ArrayList<ForeignKeyImpl> references);

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public abstract void setRemarks(final String remarks);

	public abstract int size();

	public abstract List<String> getColumnNames();

	public abstract String getInsertText();

	public abstract List<ColumnAttributes> getCommonColumns(Table other);

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	@Override
	public abstract String toString();

	public Map<String, Index> getIndexesByName();
	// public abstract DDL getDDL();
}