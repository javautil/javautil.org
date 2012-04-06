package org.javautil.oracle.metadata;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.dataset.ColumnAttributes;
import org.javautil.jdbc.metadata.DatabaseObject;
import org.javautil.jdbc.metadata.DatabaseObjectType;
import org.javautil.jdbc.metadata.Messages;
import org.javautil.jdbc.metadata.NonexistantTableException;
import org.javautil.jdbc.metadata.PrimaryKey;
import org.javautil.jdbc.metadata.TableImpl;
import org.javautil.jdbc.metadata.containers.ForeignKeys;
import org.javautil.oracle.dao.OracleForeignKeyDAO;
import org.javautil.oracle.dao.OracleTableColumnDAO;
import org.javautil.oracle.dto.OracleTableColumn;
import org.javautil.oracle.metadata.dao.ColumnComments;
import org.javautil.oracle.metadata.dao.OracleConstraintsDAO;
import org.javautil.oracle.metadata.dao.OraclePrimaryKeys;

public class OracleTable extends TableImpl {

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private static final String newline = System.getProperty("line.separator");
	private List<OracleConstraint> constraints;

	protected OracleTable() {

	}

	public OracleTable(DatabaseObject databaseObject) {
		setSchemaName(databaseObject.getOwner());
		setTableName(databaseObject.getName());
		if (!databaseObject.getDatabaseObjectType().equals(
				DatabaseObjectType.TABLE)) {
			throw new IllegalArgumentException("not a table");
		}
		setTableType(databaseObject.getDatabaseObjectType().toString());
	}

	public OracleTable(final Connection conn,
			final DatabaseObject databaseObject) throws SQLException,
			NonexistantTableException {
		this(databaseObject);
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}

		// setSchemaName(databaseObject.getOwner());
		// setTableName(databaseObject.getName());
		// if
		// (!databaseObject.getDatabaseObjectType().equals(DatabaseObjectType.TABLE))
		// {
		// throw new IllegalArgumentException("not a table");
		// }
		// setTableType(databaseObject.getDatabaseObjectType().toString());
		populate(conn);
	}

	public OracleTable(final String schemaName, final String tableName,
			final String remarks, final String tableType) {
		setSchemaName(schemaName);
		setTableName(tableName);
		setRemarks(remarks);
		setTableType(tableType);
	}

	public OracleTable(final Connection conn, final String schemaName,
			final String tableName, final String remarks, final String tableType)
			throws SQLException, NonexistantTableException {
		if (logger.isDebugEnabled()) {
			logger.debug("processing schema: " + schemaName + " tableName: "
					+ tableName + " remarks: " + remarks + " tableType "
					+ tableType);
		}

		setMetaDataRetrieveStart(System.nanoTime());
		setTableName(tableName);
		setSchemaName(schemaName);
		setTableType(tableType);
		populate(conn);

	}

	void populate(Connection conn) throws SQLException,
			NonexistantTableException {
		constraints = OracleConstraintsDAO.getForTable(conn, getSchemaName(),
				getTableName(), false, true);
		final String tablePattern = getTableName().toUpperCase();
		Messages.getString("Table.tableMask");
		logger.debug("getting information on table: " + getSchemaName() + "."
				+ getTableName());
		// boolean tableFound = false;
		if (tablePattern.startsWith("BIN$")) {
			logger.debug(Messages.getString("Table.recycleBinObjectMessage")
					+ tablePattern);
		} else {
			getColumns(conn);
			if ("TABLE".equals(getTableType())) {
				getExportedKeys(conn);
				setExportedKeysDone(System.nanoTime());
				getImportedKeys(conn);
				setImportedKeysDone(System.nanoTime());
				// TODO restore
				// getIndexColumns(conn);
				setIndexInfosDone(System.nanoTime());
				getPrimaryKey(conn);
				setPrimaryKeyDone(System.nanoTime());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<OracleTableColumn> getOracleColumns() {
		List<? extends ColumnAttributes> columns = getColumns();
		return (List<OracleTableColumn>) columns;

	}

	@SuppressWarnings("unchecked")
	public List<OracleTableColumn> getColumns(Connection conn)
			throws SQLException {
		List<? extends ColumnAttributes> columns = null;
		columns = OracleTableColumnDAO.getForTable(conn, getSchemaName(),
				getTableName(), false, true);
		setMetaDataColumnsDone(System.nanoTime());
		super.setColumns((List<ColumnAttributes>) columns);
		return (List<OracleTableColumn>) columns;
	}

	public List<ColumnComment> getColumnComments(Connection conn)
			throws SQLException {
		List<ColumnComment> comments = ColumnComments.getForTable(conn, this);
		for (ColumnComment comment : comments) {
			String columnName = comment.getColumnName();
			ColumnAttributes c = getColumn(columnName);
			if (c == null) {
				logger.warn("comment found for column " + columnName
						+ " but no such column in table");
			} else {
				c.setComments(comment.getComments());
			}
		}
		return comments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getExportedKeys(java.sql.
	 * DatabaseMetaData)
	 */
	public ForeignKeys getExportedKeys(final Connection conn)
			throws SQLException {
		if (getExportedKeys() == null) {
			OracleForeignKeyDAO exportedKeys = new OracleForeignKeyDAO(conn,
					getSchemaName(), getTableName(), constraints);

			setExportedKeys(exportedKeys);
		}
		return getExportedKeys();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getImportedKeys(java.sql.
	 * DatabaseMetaData)
	 */
	public ForeignKeys getImportedKeys(final Connection conn)
			throws SQLException {
		if (getImportedKeys() == null) {
			setImportedKeys(new OracleForeignKeyDAO(conn, getSchemaName(),
					getTableName(), constraints));
		}
		return getImportedKeys();
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// com.dbexperts.jdbc.DatabaseMetaData.Table#getIndexInfos(java.sql.DatabaseMetaData)
	// */
	// public IndexColumns getIndexColumns(final Connection conn) throws
	// SQLException {
	// if (getIndexInfos() == null) {
	// setIndexInfos(new OracleIndexColumns(conn, this ));
	// }
	// return getIndexInfos();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getPrimaryKey(java.sql.
	 * DatabaseMetaData)
	 */
	public PrimaryKey getPrimaryKey(final Connection conn) throws SQLException {
		if (getPrimaryKey() == null) {
			setPrimaryKey(OraclePrimaryKeys.getPrimaryKey(conn,
					getSchemaName(), getTableName()));
		}
		return getPrimaryKey();
	}
	//
	// @Override
	// public IndexColumns getIndexInfos(Connection meta) throws SQLException {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public String[] getSelectStatementLines() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// public DDL getDDL() {
	// if (ddl == null) {
	//
	// StringBuilder b = new StringBuilder();
	// b.append(getCreateStatement());
	// b.append(newline);
	//
	// final List<String> imported = getCreateImportedForeignKeys();
	// for (final String imp : imported) {
	// b.append(imp);
	// b.append(";");
	// b.append(newline);
	// b.append(newline);
	// }
	// String s = b.toString();
	//
	// ddl = new DDL(this,s);
	// }
	// return ddl;
	// }
}
