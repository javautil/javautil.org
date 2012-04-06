package org.javautil.jdbc.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.dataset.ColumnAttributes;
import org.javautil.jdbc.metadata.containers.ForeignKeys;

/**
 * TODO replace with DAO and bean todo create OracleTable that extends this.
 */

public class JDBCTable extends TableImpl {

	public static final String TYPE_IS_TABLE = "TABLE";
	public static final String TYPE_IS_VIEW = "VIEW";

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private Connection connection;
	private DatabaseMetaData databaseMetaData;

	// private DatabaseMetaData databaseMetaData;

	protected JDBCTable() {

	}

	public JDBCTable(final Connection conn, final String schemaName,
			final String catalogName, final String tableName)
			throws SQLException {

		setSchemaName(schemaName);
		setCatalogName(catalogName);
		setTableName(tableName);
		this.connection = conn;
		this.databaseMetaData = conn.getMetaData();
		// setRemarks(remarks);
		// setTableType(tableType);
	}

	public JDBCTable(final String schemaName, final String catalogName,
			final String tableName, final String remarks, final String tableType) {
		setSchemaName(schemaName);
		setCatalogName(catalogName);
		setTableName(tableName);
		setRemarks(remarks);
		setTableType(tableType);
	}

	public void init() throws SQLException {
		init(databaseMetaData, connection);
	}

	public void init(final DatabaseMetaData meta, final Connection conn)
			throws SQLException {

		final String tablePattern = getTableName().toUpperCase();
		final String columnNamePattern = Messages.getString("Table.tableMask");
		boolean tableFound = false;
		if (tablePattern.startsWith("BIN$")) {
			logger.debug(Messages.getString("Table.recycleBinObjectMessage")
					+ tablePattern);
		} else {
			logger.debug("tablePattern '" + tablePattern + "' "
					+ " columnPattern '" + columnNamePattern + "'");
			final ResultSet rs = meta.getColumns(getCatalogName(),
					getSchemaName(), tablePattern, columnNamePattern);
			while (rs.next()) {
				tableFound = true;
				final Column column = new Column(rs);
				if (isDumpColumns() && logger.isDebugEnabled()) {
					logger.debug(column.toString());
				}
				addColumn(column);
			}
			setMetaDataColumnsDone(System.nanoTime());
			rs.close();
			if (!tableFound) {

				throw new NonexistantTableException(
						"no such table as '"
								+ getTableName()
								+ "' in schema "
								+ "'"
								+ getSchemaName()
								+ "' or it is not available to current user. \n"
								+ "No case conversion is done. \n"
								+ "If you did not provide in upper case, and this was not"
								+ " created using quoted names, try using upper case.");
			}

			if (getTableType().equals("TABLE")) {
				getExportedKeys(meta);
				setExportedKeysDone(System.nanoTime());
				getImportedKeys(meta);
				setImportedKeysDone(System.nanoTime());
				// getIndexInfos(conn);
				setIndexInfosDone(System.nanoTime());
				getPrimaryKey(meta);
				setPrimaryKeyDone(System.nanoTime());
			}
		}
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
		if (getExportedKeys() == null) {
			setExportedKeys(new JDBCForeignKeys(meta, getCatalogName(),
					getSchemaName(), getTableName(), true));
		}
		return getExportedKeys();
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
		if (getImportedKeys() == null) {
			setImportedKeys(new JDBCForeignKeys(meta, getCatalogName(),
					getSchemaName(), getTableName(), false));
		}
		return getImportedKeys();
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
		if (getPrimaryKey() == null) {
			setPrimaryKey(PrimaryKeys.getPrimaryKey(meta, getCatalogName(),
					getSchemaName(), getTableName()));
		}
		return getPrimaryKey();
	}

	public boolean isPrimaryKeyColumn(final String columnName) {
		return getPrimaryKey() == null ? false : getPrimaryKey()
				.getColumnNames().contains(columnName);
	}

	public String[] getSelectStatementLines() {
		return getSelectStatement().split(newline);
	}

	public List<ColumnAttributes> getPrimaryKeyColumns(
			final DatabaseMetaData meta) throws SQLException {
		final PrimaryKey pk = getPrimaryKey(meta);
		final ArrayList<ColumnAttributes> pkColumns = new ArrayList<ColumnAttributes>();
		final Collection<String> columnNames = (pk == null) ? new ArrayList<String>()
				: pk.getColumnNames();
		if (columnNames != null) {
			for (final String columnName : columnNames) {
				pkColumns.add(getColumn(columnName));

			}
		}
		return pkColumns;
	}

}
