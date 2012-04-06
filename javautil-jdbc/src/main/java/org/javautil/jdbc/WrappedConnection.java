package org.javautil.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.sql.ConnectionEventListener;
import javax.sql.DataSource;
import javax.sql.StatementEventListener;

import org.apache.log4j.Logger;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class WrappedConnection implements java.sql.Connection,
		javax.sql.PooledConnection {
	private static final Logger logger = Logger
			.getLogger(WrappedConnection.class.getName());
	private java.sql.Connection conn;
	private int loginTimeout;

	private int leaseCount;
	private int leaseDuration;
	private Date leaseStartTime;

	private long latestLeaseReturnTime;

	/**
	 * Serial number per DataSource.
	 */
	private Integer connectionNumber;

	protected DataSource dataSource;

	protected WrappedConnection() {
	}

	public WrappedConnection(final java.sql.Connection conn,
			final DataSource jds) {
		this.conn = conn;
		this.dataSource = jds;
	}

	@Override
	public void addConnectionEventListener(
			final ConnectionEventListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addStatementEventListener(final StatementEventListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearWarnings() throws SQLException {
		conn.clearWarnings();
	}

	@Override
	public void close() throws SQLException {
		// if (dataSource == null) {
		// // if this came from an OracleDataSource then it doesn't matter
		// // todo worry about cleaning up these spurious messages
		// logger.warn("datasource is null, probably ok and came from OracleDataSourceWrapper");
		// conn.close();
		// //throw new IllegalStateException("dataSource is null");
		// }
		// dataSource.returnConnection(this);
		conn.close();
	}

	@Override
	public void commit() throws SQLException {
		conn.commit();
	}

	@Override
	public Array createArrayOf(final String typeName, final Object[] elements)
			throws SQLException {
		return conn.createArrayOf(typeName, elements);
	}

	@Override
	public Blob createBlob() throws SQLException {
		return conn.createBlob();
	}

	@Override
	public Clob createClob() throws SQLException {
		return conn.createClob();

	}

	@Override
	public NClob createNClob() throws SQLException {
		return conn.createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		return conn.createSQLXML();
	}

	@Override
	public Statement createStatement() throws SQLException {
		return conn.createStatement();
	}

	@Override
	public Statement createStatement(final int resultSetType,
			final int resultSetConcurrency) throws SQLException {
		return conn.createStatement(resultSetType, resultSetConcurrency);
	}

	@Override
	public Statement createStatement(final int resultSetType,
			final int resultSetConcurrency, final int resultSetHoldability)
			throws SQLException {
		final Statement returnValue = conn.createStatement(resultSetType,
				resultSetConcurrency, resultSetHoldability);
		return returnValue;
	}

	@Override
	public Struct createStruct(final String s, final Object[] o)
			throws SQLException {
		return conn.createStruct(s, o);
	}

	// @SuppressWarnings("unused")
	// public void freeClob(@SuppressWarnings("unused")
	// final (Clob clob) throws UnsupportedOperationException, SQLException {
	// throw new
	// UnsupportedOperationException("only supported in OracleConnection at this time");
	//
	// }

	@Override
	public boolean getAutoCommit() throws SQLException {
		return conn.getAutoCommit();
	}

	@Override
	public String getCatalog() throws SQLException {
		return conn.getCatalog();
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return conn.getClientInfo();
	}

	@Override
	public String getClientInfo(final String name) throws SQLException {
		return conn.getClientInfo(name);
	}

	// synchronized public String getLeaseeName() {
	// String rc = null;
	// if (getOccupiedBy() == null) {
	// rc = "vacant";
	// } else {
	// if (getOccupiedBy() instanceof String) {
	// rc = (String) getOccupiedBy();
	// } else {
	// rc = getOccupiedBy().getClass().getName();
	// }
	// }
	// return rc;
	// }

	@Override
	public java.sql.Connection getConnection() {
		return conn;
	}

	Integer getConnectionNumber() {
		return connectionNumber;
	}

	// public String getPreviousLeasee() {
	// return previousLeasee;
	// }

	public String getDriverInfo() throws SQLException {
		final DatabaseMetaData dm = getConnection().getMetaData();
		final StringBuilder b = new StringBuilder();
		b.append("Driver Name: '" + dm.getDriverName() + "' ");
		b.append("Driver Version: '" + dm.getDriverVersion() + "'");
		return b.toString();
	}

	public String getDriverName() throws SQLException {
		if (conn == null) {
			throw new IllegalStateException("conn is null");
		}
		final DatabaseMetaData dmd = conn.getMetaData();
		final String driverName = dmd.getDriverName();
		return driverName;
	}

	@Override
	public int getHoldability() throws SQLException {
		return conn.getHoldability();
	}

	public long getLatestLeaseReturnTime() {
		return latestLeaseReturnTime;
	}

	public int getLeaseDuration() {
		return leaseDuration;
	}

	Date getLeaseStartTime() {
		return leaseStartTime;
	}

	@SuppressWarnings("unused")
	public int getLoginTimeout() throws SQLException {
		return loginTimeout;
	}

	// public PrintWriter getLogWriter() throws SQLException {
	// throw new SQLException("getLogWriter is not implemented by this class");
	// }

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return conn.getMetaData();
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return conn.getTransactionIsolation();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return conn.getTypeMap();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return conn.getWarnings();
	}

	protected void incrementLeaseCount() {
		leaseCount++;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return conn.isClosed();
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return conn.isReadOnly();
	}

	@Override
	public boolean isValid(final int timeout) throws SQLException {

		return conn.isValid(timeout);
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String nativeSQL(final String sql) throws SQLException {
		return conn.nativeSQL(sql);
	}

	@Override
	public CallableStatement prepareCall(final String sql) throws SQLException {
		return conn.prepareCall(sql);
	}

	@Override
	public CallableStatement prepareCall(final String sql,
			final int resultSetType, final int resultSetConcurrency)
			throws SQLException {
		return conn.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public CallableStatement prepareCall(final String sql,
			final int resultSetType, final int resultSetConcurrency,
			final int resultSetHoldability) throws SQLException {
		final CallableStatement returnValue = conn.prepareCall(sql,
				resultSetType, resultSetHoldability);
		return returnValue;
	}

	@Override
	public PreparedStatement prepareStatement(final String sql)
			throws SQLException {
		return conn.prepareStatement(sql);
	}

	@Override
	public PreparedStatement prepareStatement(final String sql,
			final int autoGeneratedKeys) throws SQLException {
		final PreparedStatement returnValue = conn.prepareStatement(sql,
				autoGeneratedKeys);
		return returnValue;
	}

	@Override
	public PreparedStatement prepareStatement(final String sql,
			final int resultSetType, final int resultSetConcurrency)
			throws SQLException {
		return conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public PreparedStatement prepareStatement(final String sql,
			final int resultSetType, final int resultSetConcurrency,
			final int resultSetHoldability) throws SQLException {
		final PreparedStatement returnValue = conn.prepareStatement(sql,
				resultSetType, resultSetConcurrency, resultSetHoldability);
		return returnValue;
	}

	@Override
	public PreparedStatement prepareStatement(final String sql,
			final int[] columnIndexes) throws SQLException {
		final PreparedStatement returnValue = conn.prepareStatement(sql,
				columnIndexes);
		return returnValue;
	}

	/**
	 * @todo add to statement list
	 * @param sql
	 * @param columnNames
	 * @return
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement prepareStatement(final String sql,
			final String[] columnNames) throws SQLException {
		final PreparedStatement returnValue = conn.prepareStatement(sql,
				columnNames);
		return returnValue;
	}

	@Override
	public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
		conn.releaseSavepoint(savepoint);
	}

	@Override
	public void removeConnectionEventListener(
			final ConnectionEventListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeStatementEventListener(
			final StatementEventListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() throws SQLException {
		conn.rollback();
	}

	@Override
	public void rollback(final Savepoint savepoint) throws SQLException {
		conn.rollback();
	}

	@Override
	public void setAutoCommit(final boolean autoCommit) throws SQLException {
		conn.setAutoCommit(autoCommit);
	}

	@Override
	public void setCatalog(final String catalog) throws SQLException {
		conn.setCatalog(catalog);
	}

	// protected Object getOccupiedBy() {
	// return occupiedBy;
	// }

	@Override
	public void setClientInfo(final Properties properties)
			throws SQLClientInfoException {
		conn.setClientInfo(properties);

	}

	@Override
	public void setClientInfo(final String name, final String value)
			throws SQLClientInfoException {
		conn.setClientInfo(name, value);

	}

	// public Struct createStruct(String typeName, Object[] attributes) throws
	// SQLException {
	//
	// return conn.createStruct(typeName, attributes);
	// }

	// protected void setOccupiedBypreviousLeasee(Object occupiedBy) {
	// this.occupiedBy = occupiedBy;
	// }

	protected void setConn(final java.sql.Connection conn) {
		this.conn = conn;
	}

	void setConnectionNumber(final Integer connectionNumber) {
		this.connectionNumber = connectionNumber;
	}

	public void setDatasource(final DataSource ds) {
		this.dataSource = ds;
	}

	@Override
	public void setHoldability(final int holdability) throws SQLException {
		conn.setHoldability(holdability);
	}

	void setLeaseDuration(final int leaseDuration) {
		this.leaseDuration = leaseDuration;
	}

	void setLeaseStartTime(final Date leaseStartTime) {
		this.leaseStartTime = leaseStartTime;
	}

	@SuppressWarnings("unused")
	public void setLoginTimeout(final int seconds) throws SQLException {
		this.loginTimeout = seconds;
	}

	/**
	 * Does nothing as this is not an Oracle Connection
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public void setModule(@SuppressWarnings("unused") final String name)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReadOnly(final boolean readOnly) throws SQLException {
		conn.setReadOnly(readOnly);
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		return conn.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(final String name) throws SQLException {
		return conn.setSavepoint(name);
	}

	@Override
	public void setTransactionIsolation(final int level) throws SQLException {
		conn.setTransactionIsolation(level);
	}

	@Override
	public void setTypeMap(final Map<String, Class<?>> arg) throws SQLException {
		conn.setTypeMap(arg);
	}

	@Override
	public String toString() {
		return "connection# " + connectionNumber;
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		throw new SQLException("not supported");
	}

	// public void setDatasource(JdbcDataSource jdbcDataSource) {
	// // TODO Auto-generated method stub
	//
	// }

}