package com.dbexperts.oracle;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.TimeZone;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleOCIFailover;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleSavepoint;
import oracle.jdbc.OracleConnection.CommitOption;
import oracle.jdbc.aq.AQDequeueOptions;
import oracle.jdbc.aq.AQEnqueueOptions;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQNotificationRegistration;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.pool.OracleConnectionCacheCallback;
import oracle.sql.ARRAY;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.CLOB;
import oracle.sql.DATE;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;
import oracle.sql.TypeDescriptor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.dbexperts.oracle.dao.OracleSessionStats;
import com.dbexperts.oracle.dao.OracleSessionWaits;
import com.dbexperts.oracle.dao.StatNameS;
import com.dbexperts.oracle.dto.OracleSessionWait;
import com.dbexperts.persistence.PersistenceException;
public class OracleConnectionHelper extends com.dbexperts.jdbc.WrappedConnection 
		 {
	private static final Logger logger = Logger.getLogger(Connection.class.getName());

	private static final String startTraceUnlimited = "begin \n" + "   alter session set timed_statistics=true;\n"
			+ "   alter session set max_dump_file_size = unlimited; \n"
			+ "   alter session set event '10046 trace name context forever, level 12';\n" + "end;";

	/**
	 * @todo implement or remove
	 */
	@SuppressWarnings("unused")
	private static final String startTraceLimited = 
		"begin \n" + "   alter session set timed_statistics=true;\n" + //
		 "   alter session set max_dump_file_size = :max_size; \n" + //
		 "   alter session set event '10046 trace name context forever, level 12';\n" + //
		 "end;";

	/**
	 * @todo implement
	 */
	@SuppressWarnings("unused")
	private static final String stopTrace = "   alter session set events '10046 trace name context off'";

	private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

	private boolean oracleConnection;

	private CallableStatement sqlAction = null;

	private CallableStatement sqlModule = null;

	private CallableStatement sqlClientInfo = null;

	private int instanceNumber;

	private String instanceName;

	private String hostName;

	private int sid;

	private String module;

	private int serial;

	private int spid;

	private int pid;

	private boolean haveSessionInfo = false;

	private boolean throwSysExceptions = false;
	
	private OracleConnection conn;
//


	/*
	 * time checked out nano time
	 */
	private long leaseStartTime;

	/**
	 * most recent return time nanos
	 */
	private long latestLeaseReturnTime;

	private final String commitBatchNoWaitText = "commit work write batch nowait";

	private PreparedStatement commitBatchNoWaitStatement = null;

	private Object lock;

	private UserEnv userEnv;

	private StatNames  statNames;

	private OracleConnectionHelper() {
		
	}
	
	public UserEnv getUserEnv() throws SQLException {
		if (userEnv == null) {
			try {
				userEnv = new UserEnv(conn);
			} catch (PersistenceException e) {
				throw new SQLException(e.getMessage());
			}
		}
		return userEnv;
	}

	public String getInstanceName() throws SQLException {
		return getUserEnv().getInstanceName();
	}
	
	public static void enableTraceDetail(final Connection conn, final int megabytes) throws SQLException {
		final CallableStatement beginTraceStmt = conn.prepareCall(startTraceUnlimited);
		final int meg = megabytes * 1024 * 1024;
		beginTraceStmt.setInt(1, meg);
		beginTraceStmt.executeUpdate();
		beginTraceStmt.close();
	}

	/**
	 * http://download-east.oracle.com/docs/cd/A97335_02/apps.102/a83724/basic1.htm
	 *
	 * @param password
	 * @param hostName
	 * @param port
	 * @param SID
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	// clean up
	public static OracleConnection getConnectionToSys(final String password, final String hostName, final String port, final String SID)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		final Driver driver = (Driver) Class.forName(ORACLE_DRIVER).newInstance();
		DriverManager.registerDriver(driver);
		final String url = getURL(hostName, port, SID);
		final Properties props = new Properties();
		props.put("user", "sys");
		props.put("password", password);
		props.put("internal_logon", "sysdba");
		final OracleConnection rawConn = (OracleConnection) DriverManager.getConnection(url, props);
		return rawConn;
	}
	public static OracleConnectionHelper getInstance(final OracleConnection conn) throws SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		if (conn instanceof OracleConnectionHelper) {
			return (OracleConnectionHelper) conn;
		}
		final OracleConnectionHelper woc = new OracleConnectionHelper();

		conn.setAutoCommit(false);
		conn.setImplicitCachingEnabled(true);
		conn.setDefaultRowPrefetch(100);
		woc.setConn(conn);
		return woc;
	}
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * http://www.oracle.com/technology/sample_code/tech/java/codesnippet/jdbc/clob10g/handlingclobsinoraclejdbc10g.html
	 *
	 * @throws SQLException
	 */
	/*
	 * public static OracleConnection getSmartConnection() throws SQLException {
	 *//**
	 * Close any open SQL statements.
	 */

	/*
	 * <url>jdbc:oracle:thin:@localhost:1521:XE</url>
	 */
	public static String getURL(final String hostName, final String port, final String SID) {
		if (hostName == null) {
			throw new IllegalArgumentException("hostName is null");
		}
		if (port == null) {
			throw new IllegalArgumentException("port is null");
		}
		if (SID == null) {
			throw new IllegalArgumentException("SID is null");
		}
		int portNumber;
		try {
			portNumber = Integer.parseInt(port);
		} catch (final NumberFormatException nfe) {
			throw new IllegalArgumentException("port '" + port + "' is not a number");
		}
		if (portNumber < 1 || portNumber > 65534) {
			throw new IllegalArgumentException("port: '" + port + "' is out of range 1-65534");
		}
		return "jdbc:oracle:thin:@" + hostName + ":" + Integer.toString(portNumber) + ":" + SID;
	}

	public static String metaDataToHtml(final ResultSetMetaData meta) {
		final StringBuffer buff = new StringBuffer(2048);
		int columnCount;
		buff.append("<tr>");
		try {
			columnCount = meta.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				buff.append("<th>");
				buff.append(meta.getColumnLabel(column));
				buff.append("</th>");
			}
		} catch (final java.sql.SQLException s) {
			throw new java.lang.IllegalStateException(
					"JDBC driver is defective, reported column count not returned on ResultSetMetaData");
		}
		buff.append("</tr>\n");
		return new String(buff);
	}

	public static String metaDataToString(final ResultSetMetaData meta) throws java.sql.SQLException {
		final StringBuffer buff = new StringBuffer(2048);
		final int columnCount = meta.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			buff.append("\"");
			buff.append(meta.getColumnLabel(column));
			buff.append("\"");
			if (column < columnCount) {
				buff.append(",");
			}
		}
		return new String(buff);
	}


	/*
	public static WrappedOracleConnection getInstance(final OracleConnection conn) throws SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		if (conn instanceof WrappedOracleConnection) {
			return (WrappedOracleConnection) conn;
		}
		//		if (!(conn instanceof OracleConnection)) {
		//			throw new IllegalArgumentException("conn is instance of "
		//					+ conn.getClass().getName()
		//					+ " must be oracle.jdbc.OracleConnection");
		//		}
		final WrappedOracleConnection woc = new WrappedOracleConnection();

		conn.setAutoCommit(false);
		conn.setImplicitCachingEnabled(true);
		conn.setDefaultRowPrefetch(100);
		woc.setConn(conn);
		return woc;
	}
	*/
	
	public OracleConnectionHelper(final String module) {
		this.module = module;
	}


	public Connection _getPC() {
		return getConn()._getPC();
	}

	/**
	 * change the state of an oracle database tablespace to backup moe
	 *
	 * @param dbc
	 * @param tablespace
	 * @exception java.sql.SQLException
	 */
	public void alterTablespaceBackupBegin(final String tablespace) throws java.sql.SQLException {
		logger.info("beginning backup on '" + tablespace + "'");
		// String sqlText = "alter tablespace ? begin backup";
		final String sqlText = "alter tablespace " + tablespace + " begin backup";
		final PreparedStatement stmt = prepareStatement(sqlText);
		// stmt.setString(1,tablespace);
		stmt.executeUpdate();
		stmt.close();
	}

	public void alterTablespaceBackupEnd(final String tablespace) throws java.sql.SQLException {
		final String sqlText = "alter tablespace ? end backup";
		final PreparedStatement stmt = prepareStatement(sqlText);
		stmt.setString(1, tablespace);
		stmt.executeUpdate();
		stmt.close();
	}

	public void applyConnectionAttributes(final Properties arg0) throws SQLException {
		getConn().applyConnectionAttributes(arg0);
	}

	@SuppressWarnings("deprecation")
	public void archive(final int arg0, final int arg1, final String arg2) throws SQLException {
		getConn().archive(arg0, arg1, arg2);
	}

	// public void close() throws SQLException {
	//
	// // logger.info("connectionPool: " + connectionPool + " dataSource: " +
	// // dataSource);
	//
	// if (dataSource != null) {
	// dataSource.returnConnection(this);
	// } else {
	// conn.close();
	// }
	// }

	@Override
	public void close() throws SQLException {
		//logger.warn("closing connection ensure just returned to datasource");
		getConn().close();
		setConn(null);
	}

	public void close(final int arg0) throws SQLException {
		getConn().close(arg0);
	}

	public void close(final Properties arg0) throws SQLException {
		getConn().close(arg0);
	}

	public void commit(final boolean asynchronous) throws SQLException {
		if (asynchronous == false) {
			getConn().commit();
		} else {
			if (commitBatchNoWaitStatement == null) {
				commitBatchNoWaitStatement = getConn().prepareStatement(commitBatchNoWaitText);
			}
			commitBatchNoWaitStatement.executeUpdate();
		}
	}

	public void controlFileBackup(final String controlFileBackup) throws java.sql.SQLException {
		final String sqlText = "alter database backup controlfile to ?";
		final PreparedStatement stmt = prepareStatement(sqlText);
		stmt.setString(1, controlFileBackup);
		stmt.executeUpdate();
	}

	public BLOB createBLOB() throws SQLException {
		final BLOB blob = oracle.sql.BLOB.createTemporary(getConn(), false, oracle.sql.BLOB.DURATION_SESSION);
		return blob;
	}

	@Override
	public Clob createClob() throws SQLException {
		final Clob clob = oracle.sql.CLOB.createTemporary(getConn(), false, oracle.sql.CLOB.DURATION_SESSION);
		return clob;
	}

	public void disableTrace() throws SQLException {
		final CallableStatement stmt = getConn().prepareCall(stopTrace);
		stmt.executeUpdate();
		stmt.close();
	}

	public void dispose() throws SQLException {
		getConn().close();
	}

	public boolean enableTraceDetailUnlimited() throws SQLException {
		boolean success = true;
		try {
			CallableStatement stmt = getConn().prepareCall("alter session set timed_statistics = true");
			stmt.executeUpdate();
			stmt.close();
			stmt = getConn().prepareCall("alter session set max_dump_file_size = unlimited");
			stmt.executeUpdate();
			stmt.close();
			stmt = getConn().prepareCall("alter session set sql_trace = true");
			stmt.executeUpdate();
			stmt.close();
			stmt = getConn().prepareCall("alter session set events '10046 trace name context forever, level 12'");
			stmt.executeUpdate();
			stmt.close();
	
		} catch (final SQLException e) {
			if (e.getErrorCode() == 1031) {
				success = false;
				logger.warn("no alter session permission 1031" + e.getMessage());
			} else if (e.getErrorCode() == -1031) {
				success = false;
				logger.warn("no alter session -1031 " + e.getMessage());

			} else {
				logger.error(e);
				e.printStackTrace();
				throw e;
			}

		}
		return success;
	}

	/**
	 * @todo implement
	 * @param dbc
	 * @throws java.sql.SQLException
	 */
	public void forceLogSwitch() throws java.sql.SQLException {
		final String sqlText = "alter system switch logfile";
		final PreparedStatement stmt = getConn().prepareStatement(sqlText);
		stmt.executeUpdate();
		stmt.close();
	}

	public void freeBLOB(final BLOB blob) throws SQLException {
		blob.freeTemporary();
	}

	// @todo quick and dirty paste from super class to resolve conn
	// disambiguation fix someday, yeah, right

	@Override
	public void freeClob(final Clob clob) throws SQLException {
		if (clob == null) {
			throw new IllegalArgumentException("clob is null");
		}
		if (!(clob instanceof CLOB)) {
			throw new IllegalArgumentException("the provided CLOB is not an Oracle CLOB");
		}
		final CLOB oclob = (CLOB) clob;
		oclob.freeTemporary();

	}

	public String getArchiveDestinationDirectory() throws java.sql.SQLException {
		return getInstanceValue("log_archive_dest");
	}

	public boolean getAutoClose() throws SQLException {
		return getConn().getAutoClose();
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return getConn().getAutoCommit();
	}

	public CallableStatement getCallWithKey(final String arg0) throws SQLException {
		return getConn().getCallWithKey(arg0);
	}

	@Override
	public String getCatalog() throws SQLException {
		return getConn().getCatalog();
	}

	/*
	 * public java.sql.Connection getConn() { return conn; }
	 */

	/*
	 * public OracleConnection getConnection() throws SQLException {
	 * OracleConnection conn = getConnection(false); return conn; }
	 */

	public Properties getConnectionAttributes() throws SQLException {
		return getConnectionAttributes();
	}

	public int getConnectionReleasePriority() throws SQLException {
		return getConn().getConnectionReleasePriority();
	}

	/**
	 * need to validate that files are readable and that we have properly parsed
	 * the output
	 *
	 * @return
	 * @exception java.sql.SQLException
	 */
	public ArrayList<String> getControlFiles() throws java.sql.SQLException {
		return getInstanceValues("control_files");
	}

	public boolean getCreateStatementAsRefCursor() {
		return getConn().getCreateStatementAsRefCursor();
	}

	public int getDefaultExecuteBatch() {
		return getConn().getDefaultExecuteBatch();
	}

	public int getDefaultRowPrefetch() {
		return getConn().getDefaultRowPrefetch();
	}

	public Object getDescriptor(final String arg0) {
		return getConn().getDescriptor(arg0);
	}

	@Override
	public String getDriverName() {
		return ORACLE_DRIVER;
	}

	public short getEndToEndECIDSequenceNumber() throws SQLException {
		return getConn().getEndToEndECIDSequenceNumber();
	}

	public String[] getEndToEndMetrics() throws SQLException {
		return getConn().getEndToEndMetrics();
	}

	public boolean getExplicitCachingEnabled() throws SQLException {
		return getConn().getExplicitCachingEnabled();
	}

	public String getHostName() {
		return hostName;
	}

	public int getIdleTimeInSeconds() {
		return (int) (getIdleTimeNano() / 1e9);
	}

	public boolean getImplicitCachingEnabled() throws SQLException {
		return getConn().getImplicitCachingEnabled();
	}

	public boolean getIncludeSynonyms() {
		return getConn().getIncludeSynonyms();
	}

//	public String getInstanceName() throws SQLException {
//		if (instanceName == null) {
//			final Statement stmt = getConn().createStatement();
//			final String text = "SELECT sys_context('USERENV', 'DB_NAME') FROM dual";
//			//final String text = "select instance_number, instance_name, host_name from sys.v_$instance";
//			ResultSet rset;
//			try {
//				rset = stmt.executeQuery(text);
//				rset.next();
//				//instanceNumber = rset.getInt("instance_Number");
//				instanceName = rset.getString("instance_name");
//				//hostName = rset.getString("host_name");
//				rset.close();
//			} catch (final SQLException s) {
//				final String message = "while executing " + text + " " + s.getMessage();
//				logger.error(message);
//				throw new SQLException(message);
//			}
//		}
//		return instanceName;
//	}

	public int getInstanceNumber() {
		return instanceNumber;
	}

	@SuppressWarnings("deprecation")
	public Object getJavaObject(final String arg0) throws SQLException {
		return getConn().getJavaObject(arg0);
	}

	public long getLeasedDuration() {
		return leaseStartTime == -1 ? leaseStartTime : System.nanoTime() - leaseStartTime;
	}

	/**
	 */
	@Override
	public int getLeaseDuration() {
		return getLeaseDuration();
	}

	//	public PrintWriter getLogWriter() throws SQLException {
	//		return conn.getLogWriter();
	//		throw new SQLException("getLogWriter is not implemented by this class");
	//	}

	public String getModule() {
		return module;
	}

	// todo document
	public int getPid() {
		return pid;
	}

	public Properties getProperties() {
		return getConn().getProperties();
	}

	public boolean getRemarksReporting() {
		return getConn().getRemarksReporting();
	}

	public boolean getRestrictGetTables() {
		return getConn().getRestrictGetTables();
	}

	public int getSerial() {
		return serial;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.ConnectionWrapper#getSessionInfo()
	 */
	public void getSessionInfo() throws SQLException {
		if (!haveSessionInfo) {
			final String text = "select s.sid, s.serial#, p.spid, p.pid from v$session s, v$process p "
					+ " where s.audsid=userenv('sessionid') and p.addr = s.paddr";
			Statement stmt;
			try {
				stmt = getConn().createStatement();
				final ResultSet rset = stmt.executeQuery(text);
				rset.next();
				sid = rset.getInt("sid");
				serial = rset.getInt("serial#");
				spid = rset.getInt("spid");
				pid = rset.getInt("pid");
				logger.info("sid " + sid + " serial " + serial + " spid " + spid + " pid " + pid);
				haveSessionInfo = true;
			} catch (final SQLException e) {
				final String message = "while processing " + text + " " + e.getMessage();
				if (throwSysExceptions) {
					throw new SQLException(message);
				}
				logger.error(message);

			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.ConnectionWrapper#getSessionStats()
	 */
	public OracleSessionStatSet getSessionStats() throws SQLException {
		return OracleSessionStats.getForMySession(this.conn);
	}

	public OracleSessionStatSet getSessionStats(int sid) throws SQLException  {
		return OracleSessionStats.getForSession(this,sid);
	}
	public String getSessionTimeZone() {
		return getConn().getSessionTimeZone();
	}

	public ArrayList<OracleSessionWait> getSessionWaits() throws SQLException {
		return OracleSessionWaits.getForMySession(this.conn);
	}

	public static int getSid(Connection conn) throws SQLException {
		
			final Statement stmt = conn.createStatement();
			final String text = "select sid from v$mystat where rownum = 1";
			final ResultSet rset = stmt.executeQuery(text);
			rset.next();
			int sid = rset.getInt("sid");
			rset.close();
		
		return sid;
	}

	public int getSid() throws SQLException {
		int retval = sid;
		if (sid == 0) {
			sid = retval =   getSid(getConn());
			
		}
		return retval;
	}

	public int getSpid() {
		return spid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.ConnectionWrapper#getSqlAction()
	 */
	public CallableStatement getSqlAction() {
		return sqlAction;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.ConnectionWrapper#getSqlClientInfo()
	 */
	public CallableStatement getSqlClientInfo() {
		return sqlClientInfo;
	}

	public CallableStatement getSqlModule() {
		return sqlModule;
	}

	@SuppressWarnings("deprecation")
	public String getSQLType(final Object arg0) throws SQLException {
		return getConn().getSQLType(arg0);
	}

	@SuppressWarnings("deprecation")
	public int getStatementCacheSize() {
		return getConn().getStmtCacheSize();
	}

	public PreparedStatement getStatementWithKey(final String arg0) throws SQLException {
		return getConn().getStatementWithKey(arg0);
	}

	@SuppressWarnings("deprecation")
	public int getStmtCacheSize() {
		return getConn().getStmtCacheSize();
	}

	public short getStructAttrCsId() throws SQLException {
		return getConn().getStructAttrCsId();
	}

	public ArrayList<String> getTablespaceNames() throws java.sql.SQLException {
		final ArrayList<String> responses = new ArrayList<String>();
		final String sqlText = "select tablespace_name " + "from dba_tablespaces";
		final PreparedStatement stmt = prepareStatement(sqlText);
		final ResultSet rset = stmt.executeQuery(sqlText);
		while (rset.next()) {
			responses.add(rset.getString("tablespace_name"));
		}
		stmt.close();
		return responses;
	}

	public String getUdumpDest() throws SQLException {
		String dest = null;
		final String text = "select value from v$parameter where name = 'user_dump_dest'";
		try {
			final Statement stmt = getConn().createStatement();
			final ResultSet rset = stmt.executeQuery(text);
			rset.next();
			dest = rset.getString(1);
			stmt.close();
		} catch (final SQLException e) {
			final String message = "while processing " + text + " " + e.getMessage();
			if (throwSysExceptions) {
				throw new SQLException(message);
			}
			logger.error(e);
		}
		return dest;
	}

	public Properties getUnMatchedConnectionAttributes() throws SQLException {
		return getConn().getUnMatchedConnectionAttributes();
	}

	public String getUserName() throws SQLException {
		return getConn().getUserName();
	}

	@SuppressWarnings("deprecation")
	public boolean getUsingXAFlag() {
		return getConn().getUsingXAFlag();
	}

	@SuppressWarnings("deprecation")
	public boolean getXAErrorFlag() {
		return getConn().getXAErrorFlag();
	}

	// /**
	// * @todo backup somewhere else
	// * @param properties
	// * @throws java.sql.SQLException
	// * @throws java.io.IOException
	// */
	// public void hotBackup(PropertyManagement properties) throws
	// java.sql.SQLException {
	// ArrayList<String> tablespaceNames = getTablespaceNames();
	// for (String tablespace : tablespaceNames) {
	// backupTableSpace(tablespace, "/tmp");
	// logger.info(tablespace);
	// }
	// }

	// public String getUserName() throws SQLException {
	// return conn.getUserName();
	// }
	//
	public boolean isDatabaseUnavailableException(final SQLException sqe) {
		final int errNbr = sqe.getErrorCode();
		boolean returnValue = false;
		switch (errNbr) {
		case 1034: // oracle not available database not running
		case 3135: // ORA-03135: connection lost contact somebody shutdown the
			// database ?
		case 12500: // TNS:listener failed to start a dedicated server process
		case 12541: // no listener - this is from the connection pool
			returnValue = true;
			break;
		}
		return returnValue;
	}

	public boolean isHaveSessionInfo() {
		return haveSessionInfo;
	}

	public boolean isLogicalConnection() {
		return getConn().isLogicalConnection();
	}

	public boolean isOracleConnection() {
		return oracleConnection;
	}

	public boolean isProxySession() {
		return getConn().isProxySession();
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return getConn().isReadOnly();
	}

	/**
	 * @return Returns the throwSysExceptions.
	 */
	public boolean isThrowSysExceptions() {
		return throwSysExceptions;
	}

	public void openProxySession(final int arg0, final Properties arg1) throws SQLException {
		getConn().openProxySession(arg0, arg1);
	}

	public void oracleReleaseSavepoint(final OracleSavepoint arg0) throws SQLException {
		getConn().oracleReleaseSavepoint(arg0);
	}

	public void oracleRollback(final OracleSavepoint arg0) throws SQLException {
		getConn().oracleRollback(arg0);
	}

	public OracleSavepoint oracleSetSavepoint() throws SQLException {
		return getConn().oracleSetSavepoint();
	}

	public OracleSavepoint oracleSetSavepoint(final String arg0) throws SQLException {
		return getConn().oracleSetSavepoint(arg0);
	}

	public oracle.jdbc.internal.OracleConnection physicalConnectionWithin() {
		return getConn().physicalConnectionWithin();
	}

	public int pingDatabase(final int arg0) throws SQLException {
		return getConn().pingDatabase(arg0);
	}

	@SuppressWarnings("deprecation")
	public CallableStatement prepareCallWithKey(final String arg0) throws SQLException {
		return getConn().prepareCallWithKey(arg0);
	}

	@Override
	public OraclePreparedStatement prepareStatement(final String sql) throws SQLException {
		if (getConn() == null) {
			throw new IllegalStateException("conn is null");
		}
		return (OraclePreparedStatement) getConn().prepareStatement(sql);
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys) throws SQLException {
		final PreparedStatement returnValue = getConn().prepareStatement(sql, autoGeneratedKeys);
		return returnValue;
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency)
			throws SQLException {
		return getConn().prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency,
			final int resultSetHoldability) throws SQLException {
		final PreparedStatement returnValue = getConn().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		return returnValue;
	}

	@SuppressWarnings("deprecation")
	public PreparedStatement prepareStatementWithKey(final String arg0) throws SQLException {
		return getConn().prepareStatementWithKey(arg0);
	}

	public void purgeExplicitCache() throws SQLException {
		getConn().purgeExplicitCache();
	}

	public void purgeImplicitCache() throws SQLException {
		getConn().purgeImplicitCache();
	}

	public void putDescriptor(final String arg0, final Object arg1) throws SQLException {
		getConn().putDescriptor(arg0, arg1);
	}

	public void registerConnectionCacheCallback(final OracleConnectionCacheCallback arg0, final Object arg1, final int arg2)
			throws SQLException {
		getConn().registerConnectionCacheCallback(arg0, arg1, arg2);
	}

	@SuppressWarnings( { "deprecation", "unchecked" })
	public void registerSQLType(final String arg0, final Class arg1) throws SQLException {
		getConn().registerSQLType(arg0, arg1);
	}

	@SuppressWarnings("deprecation")
	public void registerSQLType(final String arg0, final String arg1) throws SQLException {
		getConn().registerSQLType(arg0, arg1);
	}

	public void registerTAFCallback(final OracleOCIFailover arg0, final Object arg1) throws SQLException {
		getConn().registerTAFCallback(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.ConnectionWrapper#setAction(java.lang.String)
	 */
	public void setAction(final String action) throws java.sql.SQLException {
		String text = action;
		if (text.length() > 32) {
			text = action.substring(0, 31);
		}
		if (sqlAction == null) {
			sqlAction = getConn().prepareCall("{call dbms_application_info.set_action(?)}");
		}
		sqlAction.setString(1, text);
		sqlAction.executeUpdate();
		if (logger.isDebugEnabled()) {
			logger.debug("action set to " + action);
		}
	}

	
	public static void setTraceIdentifierNoFail(Connection conn, String id) {
		try {
			setTraceIdentifier(conn,id);
		} catch (SQLException e) {
			logger.warn(e);
		}
	}
	/**
	 * 
	 * Doesn't throw a sqlexception in case you don't have alter session priviliges no reason 
	 * to hose you.
	 * @param conn
	 * @param id
	 * @throws SQLException 
	 */
	public static void setTraceIdentifier(Connection conn, String id) throws SQLException {

	    if (conn == null) {
	    	throw new IllegalArgumentException("conn is null");
	    }
	    if (id == null) {
	    	throw new IllegalArgumentException("id is null");
	    }
		String text = "alter session set tracefile_identifier='" + id + "'";
		Statement stmt = null;
	    try {
	    	stmt = conn.createStatement();
			stmt.execute(text); 
	    } catch(SQLException sqe) {
	    	throw new SQLException(" while processing " + text + " " + sqe.getMessage());	 
		} finally {
			stmt.close();
		}

	
	}
	
	public void setAutoClose(final boolean arg0) throws SQLException {
		getConn().setAutoClose(arg0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.ConnectionWrapper#setClientInfo(java.lang.String)
	 */
	public void setClientInfo(final String info) throws java.sql.SQLException {
		String text = info;
		if (text.length() > 32) {
			text = info.substring(0, 31);
		}
		if (sqlClientInfo == null) {
			sqlClientInfo = getConn().prepareCall("{call dbms_application_info.set_client_info(:txt)}");
		}
		sqlClientInfo.setString(1, text);
		sqlClientInfo.executeUpdate();
	}

	public void setConnectionReleasePriority(final int arg0) throws SQLException {
		getConn().setConnectionReleasePriority(arg0);
	}

	public void setCreateStatementAsRefCursor(final boolean arg0) {
		getConn().setCreateStatementAsRefCursor(arg0);
	}

	public void setDefaultExecuteBatch(final int arg0) throws SQLException {
		getConn().setDefaultExecuteBatch(arg0);
	}

	public void setDefaultRowPrefetch(final int arg0) throws SQLException {
		getConn().setDefaultRowPrefetch(arg0);
	}

	public void setEndToEndMetrics(final String[] arg0, final short arg1) throws SQLException {
		getConn().setEndToEndMetrics(arg0, arg1);
	}

	public void setExplicitCachingEnabled(final boolean arg0) throws SQLException {
		getConn().setExplicitCachingEnabled(arg0);
	}

	public void setImplicitCachingEnabled(final boolean arg0) throws SQLException {
		getConn().setImplicitCachingEnabled(arg0);
	}

	public void setIncludeSynonyms(final boolean arg0) {
		getConn().setIncludeSynonyms(arg0);
	}

	// public void setLogWriter(@SuppressWarnings("unused")
	// PrintWriter out) throws SQLException {
	// super.setLogWriter(out);
	// }

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.ConnectionWrapper#setModule(java.lang.String)
	 */
	@Override
	public void setModule(final String module) throws java.sql.SQLException {
		String text = module;
		if (text.length() > 48) {
			final int end = text.length() - 1;
			final int begin = end - 48;
			text = module.substring(begin, end);
		}
		try {
			if (sqlModule == null) {
				final String txt = "{call dbms_application_info.set_module(?,?)}";
				sqlModule = getConn().prepareCall(txt);
			}
			sqlModule.setString(1, text);
			sqlModule.setString(2, "");
			sqlModule.execute();
		} catch (final java.lang.NullPointerException n) {
			if (getConn() == null) {
				throw new java.lang.IllegalStateException("conn is null");
			}
			throw n;
		}
	}

	public void setPlsqlWarnings(final String arg0) throws SQLException {
		getConn().setPlsqlWarnings(arg0);
	}

	public void setRemarksReporting(final boolean arg0) {
		getConn().setRemarksReporting(arg0);
	}

	public void setRestrictGetTables(final boolean arg0) {
		getConn().setRestrictGetTables(arg0);
	}

	public void setSessionTimeZone(final String arg0) throws SQLException {
		getConn().setSessionTimeZone(arg0);
	}

	public void setStatementCacheSize(final int arg0) throws SQLException {
		getConn().setStatementCacheSize(arg0);
	}

	@SuppressWarnings("deprecation")
	public void setStmtCacheSize(final int arg0) throws SQLException {
		getConn().setStmtCacheSize(arg0);
	}

	@SuppressWarnings("deprecation")
	public void setStmtCacheSize(final int arg0, final boolean arg1) throws SQLException {
		getConn().setStmtCacheSize(arg0, arg1);
	}

	/**
	 *
	 * @param throwSysExceptions
	 *            The throwSysExceptions to set.
	 */
	public void setThrowSysExceptions(final boolean throwSysExceptions) {
		this.throwSysExceptions = throwSysExceptions;
	}

	/**
	 *
	 */
	public String setTraceFileIdentifier(final String id) throws SQLException {
		getSessionInfo();
		// String text = "alter session set tracefile_identifier = :id";
		final String text = "alter session set tracefile_identifier = '" + id + "'";
		final PreparedStatement stmt = getConn().prepareStatement(text);
		// stmt.setString(1,id);
		try {
			stmt.executeUpdate();
		} catch (final SQLException s) {
			logger.error("while trying to set identifier to '" + id + "'" + s.getMessage());
		}
		stmt.close();
		final String instanceName = getUserEnv().getInstanceName();
		// @todo depends on init parameters
		final String retval = instanceName.toLowerCase() + "_ora_" + spid + "_" + id + ".trc";
		return retval;
	}

	@SuppressWarnings("deprecation")
	public void setUsingXAFlag(final boolean arg0) {
		getConn().setUsingXAFlag(arg0);
	}

	public void setWrapper(final oracle.jdbc.OracleConnection arg0) {
		getConn().setWrapper(arg0);
	}

	@SuppressWarnings("deprecation")
	public void setXAErrorFlag(final boolean arg0) {
		getConn().setXAErrorFlag(arg0);
	}


	public oracle.jdbc.OracleConnection unwrap() {
		return getConn().unwrap();
	}


	private ArrayList<String> getDataFileNamesForTablespace(final String tablespace) throws java.sql.SQLException {
		final ArrayList<String> responses = new ArrayList<String>();
		final String sqlText = "select file_name " + "from dba_data_files \n " + "where tablespace_name = ?";
		final PreparedStatement stmt = prepareStatement(sqlText);
		stmt.setString(1, tablespace);
		final ResultSet rset = stmt.executeQuery(sqlText);
		while (rset.next()) {
			responses.add(rset.getString("file_name"));
		}
		stmt.close();

		return responses;
	}

	/*
	 * private void archiveBusy(String filename) { "/usr/bin/fuser filename"; }
	 */
	private String getInstanceValue(final String name) throws java.sql.SQLException {
		final ArrayList<String> values = getInstanceValues(name);
		return values.get(0);
	}

	private ArrayList<String> getInstanceValues(final String name) throws SQLException {
		final ArrayList<String> values = new ArrayList<String>();
		final ArrayList<String> responses = new ArrayList<String>();
		final String sqlText = "select value " + "from v$parameter \n" + "where name = ?";
		final PreparedStatement stmt = prepareStatement(sqlText);
		stmt.setString(1, name);
		ResultSet rset;
		try {
			rset = stmt.executeQuery(sqlText);
			while (rset.next()) {
				responses.add(rset.getString("value"));
			}
			stmt.close();
			int index = 0;
			for (final Iterator<String> it = responses.iterator(); it.hasNext(); index++) {
				final String value = it.next();
				values.add(value);
			}
		} catch (final SQLException e) {
			final String message = "while processing " + sqlText + e.getMessage();
			if (throwSysExceptions) {
				throw new SQLException(message);
			}
			logger.error(message);
		}
		return values;
	}

	long getIdleTimeNano() {
		return System.nanoTime() - latestLeaseReturnTime;
	}

	/**
	 * @todo either close the open cursors, clear batches or report openCursors;
	 *
	 */
	void inspectReturnState() {
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return getConn().getClientInfo();

	}

	@Override
	public String getClientInfo(final String name) throws SQLException {
		return getConn().getClientInfo(name);
	}

	@Override
	public boolean isValid(final int timeout) throws SQLException {
		return getConn().isValid(timeout);
	}

	@Override
	public void setClientInfo(final Properties properties) throws SQLClientInfoException {
		getConn().setClientInfo(properties);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.ConnectionWrapper#setClientInfo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
		getConn().setClientInfo(name, value);

	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) {
		return getConn().getClass().equals(iface);
	}

	@Override
	public <T> T unwrap(final Class<T> iface) {
		throw new java.lang.UnsupportedOperationException();

	}

	public void lock(final Object lock) {
		if (lock == null) {
			throw new IllegalArgumentException("lock is null");
		}
		this.lock = lock;

	}

	public void abort() throws SQLException {
		getConn().abort();

	}

	public void cancel() throws SQLException {

		getConn().cancel();
	}

	public BINARY_DOUBLE createBINARY_DOUBLE(final double arg0) throws SQLException {
		return getConn().createBINARY_DOUBLE(arg0);

	}

	public BINARY_FLOAT createBINARY_FLOAT(final float arg0) throws SQLException {
		return getConn().createBINARY_FLOAT(arg0);
	}

	public DATE createDATE(final Date arg0) throws SQLException {
		return getConn().createDATE(arg0);
	}

	public DATE createDATE(final Time arg0) throws SQLException {
		return getConn().createDATE(arg0);
	}

	public DATE createDATE(final Timestamp arg0) throws SQLException {
		return getConn().createDATE(arg0);

	}

	public DATE createDATE(final String arg0) throws SQLException {
		return getConn().createDATE(arg0);

	}

	public DATE createDATE(final Date arg0, final Calendar arg1) throws SQLException {
		return getConn().createDATE(arg0, arg1);

	}

	public DATE createDATE(final Time arg0, final Calendar arg1) throws SQLException {
		return getConn().createDATE(arg0, arg1);

	}

	public DATE createDATE(final Timestamp arg0, final Calendar arg1) throws SQLException {
		return getConn().createDATE(arg0, arg1);
	}

	public INTERVALDS createINTERVALDS(final String arg0) throws SQLException {
		return getConn().createINTERVALDS(arg0);
	}

	public INTERVALYM createINTERVALYM(final String arg0) throws SQLException {
		return getConn().createINTERVALYM(arg0);
	}

	public NUMBER createNUMBER(final boolean arg0) throws SQLException {
		return getConn().createNUMBER(arg0);
	}

	public NUMBER createNUMBER(final byte arg0) throws SQLException {
		return getConn().createNUMBER(arg0);
	}

	public NUMBER createNUMBER(final short arg0) throws SQLException {
		return getConn().createNUMBER(arg0);
	}

	public NUMBER createNUMBER(final int arg0) throws SQLException {
		return getConn().createNUMBER(arg0);

	}

	public NUMBER createNUMBER(final long arg0) throws SQLException {
		return getConn().createNUMBER(arg0);

	}

	public NUMBER createNUMBER(final float arg0) throws SQLException {
		return getConn().createNUMBER(arg0);

	}

	public NUMBER createNUMBER(final double arg0) throws SQLException {
		return getConn().createNUMBER(arg0);
	}

	public NUMBER createNUMBER(final BigDecimal arg0) throws SQLException {
		return getConn().createNUMBER(arg0);
	}

	public NUMBER createNUMBER(final BigInteger arg0) throws SQLException {
		return getConn().createNUMBER(arg0);

	}

	public NUMBER createNUMBER(final String arg0, final int arg1) throws SQLException {
		return getConn().createNUMBER(arg0, arg1);
	}

	public TIMESTAMP createTIMESTAMP(final Date arg0) throws SQLException {
		return getConn().createTIMESTAMP(arg0);
	}

	public TIMESTAMP createTIMESTAMP(final DATE arg0) throws SQLException {
		return getConn().createTIMESTAMP(arg0);

	}

	public TIMESTAMP createTIMESTAMP(final Time arg0) throws SQLException {
		return getConn().createTIMESTAMP(arg0);
	}

	public TIMESTAMP createTIMESTAMP(final Timestamp arg0) throws SQLException {
		return getConn().createTIMESTAMP(arg0);
	}

	public TIMESTAMP createTIMESTAMP(final String arg0) throws SQLException {
		return getConn().createTIMESTAMP(arg0);
	}

	public TIMESTAMPLTZ createTIMESTAMPLTZ(final Date arg0, final Calendar arg1) throws SQLException {
		return getConn().createTIMESTAMPLTZ(arg0, arg1);

	}

	public TIMESTAMPLTZ createTIMESTAMPLTZ(final Time arg0, final Calendar arg1) throws SQLException {
		return getConn().createTIMESTAMPLTZ(arg0, arg1);
	}

	public TIMESTAMPLTZ createTIMESTAMPLTZ(final Timestamp arg0, final Calendar arg1) throws SQLException {
		return getConn().createTIMESTAMPLTZ(arg0, arg1);
	}

	public TIMESTAMPLTZ createTIMESTAMPLTZ(final String arg0, final Calendar arg1) throws SQLException {
		return getConn().createTIMESTAMPLTZ(arg0, arg1);
	}

	public TIMESTAMPLTZ createTIMESTAMPLTZ(final DATE arg0, final Calendar arg1) throws SQLException {
		return getConn().createTIMESTAMPLTZ(arg0, arg1);

	}

	public TIMESTAMPTZ createTIMESTAMPTZ(final Date arg0) throws SQLException {
		return getConn().createTIMESTAMPTZ(arg0);

	}

	public TIMESTAMPTZ createTIMESTAMPTZ(final Time arg0) throws SQLException {
		return getConn().createTIMESTAMPTZ(arg0);
	}

	public TIMESTAMPTZ createTIMESTAMPTZ(final Timestamp arg0) throws SQLException {
		return getConn().createTIMESTAMPTZ(arg0);
	}

	public TIMESTAMPTZ createTIMESTAMPTZ(final String arg0) throws SQLException {
		return getConn().createTIMESTAMPTZ(arg0);

	}

	public TIMESTAMPTZ createTIMESTAMPTZ(final DATE arg0) throws SQLException {
		return getConn().createTIMESTAMPTZ(arg0);

	}

	public TIMESTAMPTZ createTIMESTAMPTZ(final Date arg0, final Calendar arg1)

	throws SQLException {
		return getConn().createTIMESTAMPTZ(arg0, arg1);

	}

	public TIMESTAMPTZ createTIMESTAMPTZ(final Time arg0, final Calendar arg1) throws SQLException {
		return getConn().createTIMESTAMPTZ(arg0, arg1);

	}

	public TIMESTAMPTZ createTIMESTAMPTZ(final Timestamp arg0, final Calendar arg1) throws SQLException {
		return getConn().createTIMESTAMPTZ(arg0, arg1);
	}

	public TIMESTAMPTZ createTIMESTAMPTZ(final String arg0, final Calendar arg1) throws SQLException {
		return getConn().createTIMESTAMPTZ(arg0, arg1);
	}

	public TypeDescriptor[] getAllTypeDescriptorsInCurrentSchema() throws SQLException {
		return getConn().getAllTypeDescriptorsInCurrentSchema();
	}

	public String getAuthenticationAdaptorName() throws SQLException {
		return getConn().getAuthenticationAdaptorName();

	}

	public String getCurrentSchema() throws SQLException {
		return getConn().getCurrentSchema();
	}

	public String getDataIntegrityAlgorithmName() throws SQLException {
		return getConn().getDataIntegrityAlgorithmName();
	}

	public TimeZone getDefaultTimeZone() throws SQLException {
		return getConn().getDefaultTimeZone();
	}

	public String getEncryptionAlgorithmName() throws SQLException {
		return getConn().getEncryptionAlgorithmName();
	}

	public String getSessionTimeZoneOffset() throws SQLException {
		return getConn().getSessionTimeZoneOffset();
	}

	public TypeDescriptor[] getTypeDescriptorsFromList(final String[][] arg0) throws SQLException {
		return getConn().getTypeDescriptorsFromList(arg0);
	}

	public TypeDescriptor[] getTypeDescriptorsFromListInCurrentSchema(final String[] arg0) throws SQLException {
		return getConn().getTypeDescriptorsFromListInCurrentSchema(arg0);
	}

	public boolean isUsable() {
		return getConn().isUsable();
	}

	public int pingDatabase() throws SQLException {
		return getConn().pingDatabase();
	}

	public void setDefaultTimeZone(final TimeZone arg0) throws SQLException {
		getConn().setDefaultTimeZone(arg0);

	}

	public void unregisterDatabaseChangeNotification(final int arg0) throws SQLException {
		getConn().unregisterDatabaseChangeNotification(arg0);

	}

	public void unregisterDatabaseChangeNotification(final int arg0, final String arg1, final int arg2) throws SQLException {
		getConn().unregisterDatabaseChangeNotification(arg0, arg1, arg2);

	}

	public void commit(final EnumSet<CommitOption> arg0) throws SQLException {
		getConn().commit(arg0);

	}

	public ARRAY createARRAY(final String arg0, final Object arg1) throws SQLException {
		return getConn().createARRAY(arg0, arg1);
	}

	public AQMessage dequeue(final String arg0, final AQDequeueOptions arg1, final byte[] arg2) throws SQLException {
		return getConn().dequeue(arg0, arg1, arg2);

	}

	public AQMessage dequeue(final String arg0, final AQDequeueOptions arg1, final String arg2) throws SQLException {
		return getConn().dequeue(arg0, arg1, arg2);
	}

	public void enqueue(final String arg0, final AQEnqueueOptions arg1, final AQMessage arg2) throws SQLException {
		getConn().enqueue(arg0, arg1, arg2);

	}

	public DatabaseChangeRegistration getDatabaseChangeRegistration(final int arg0) throws SQLException {
		return getConn().getDatabaseChangeRegistration(arg0);
	}

	public AQNotificationRegistration[] registerAQNotification(final String[] arg0, final Properties[] arg1, final Properties arg2)
			throws SQLException {
		return getConn().registerAQNotification(arg0, arg1, arg2);
	}

	public DatabaseChangeRegistration registerDatabaseChangeNotification(final Properties arg0) throws SQLException {
		return getConn().registerDatabaseChangeNotification(arg0);
	}


	public void unregisterAQNotification(final AQNotificationRegistration arg0) throws SQLException {
		getConn().unregisterAQNotification(arg0);

	}

	public void unregisterDatabaseChangeNotification(final DatabaseChangeRegistration arg0) throws SQLException {
		getConn().unregisterDatabaseChangeNotification(arg0);

	}

	@Override
	public Connection getConnection() {
		return getConn();
	}

	// todo document
	public final void setSecurity(final Object key, final String userName) throws SQLException {
		validateLockKey(key);
		final String sqlText = "begin set_security_for_userid(:userid); end;";
		final CallableStatement cs = getConn().prepareCall(sqlText);
		cs.setString(1, userName);
		cs.execute();
	}

	public void setLock(final Object _lock) {
		if (_lock == null) {
			throw new IllegalArgumentException("_lock is null");
		}
		if (lock != null) {
			throw new IllegalArgumentException("lock is already set");
		}
		lock = _lock;
	}

	public void validateLockKey(final Object key) {
		if (key == null) {
			throw new IllegalArgumentException("key is null");
		}
		if (lock == null) {
			throw new UnsupportedOperationException("not locked must be locked with call to setLock previously");
		}
		if (lock != key) {
			throw new IllegalArgumentException("key does not match lock");
		}
	}

	// @todo make sure that OracleConnectionWrapper restores the state
	public void setAsynchCommit(final boolean val, final boolean throwExceptionOnExecuteFailure) throws SQLException {
		// if (val) {

		//		}
		final Statement stmt = getConn().createStatement();
		String alter = null;
		try {
			if (val) {
				alter = "alter session set commit_write=batch,nowait";

				stmt.execute(alter);
			}

			else {
				// todo
				throw new UnsupportedOperationException();
			}
		} catch (final SQLException e) {
			if (throwExceptionOnExecuteFailure) {
				throw new SQLException("while attempting " + alter + " " + e.getMessage());
			}
			logger.error(e);
		}

	}

	@Override
	public void commit() throws SQLException {
		getConn().commit();
	}

	private void setConn(final oracle.jdbc.OracleConnection conn) {
		super.setConn(conn);
	}

	@Override
	// todo use
	public oracle.jdbc.OracleConnection getConn() {
		return (OracleConnection) super.getConn();
	}
	public void runStatementNoException(String sqlText, Level failLogLevel, Level successLogLevel) throws SQLException, PersistenceException {
		runStatement(sqlText,failLogLevel,successLogLevel,false);
	}
	
	public void runStatement(String sqlText, Level failLogLevel, Level successLogLevel) throws SQLException, PersistenceException {
		runStatement(sqlText,failLogLevel,successLogLevel,true);
	}
	
	public void runStatement(String sqlText, Level failLogLevel, Level successLogLevel, boolean throwException) throws SQLException, PersistenceException {
		if (sqlText == null) {
			throw new IllegalArgumentException("sqlText is null");
		}
		if (failLogLevel == null) {
			throw new IllegalArgumentException("failLogLevel is null");
		}
	
		
		Statement statement = createStatement();
		try {
			long before = System.currentTimeMillis();
			statement.execute(sqlText);
			long after = System.currentTimeMillis();
			long elapsed = after - before;
		//	Level l = logger.getLevel();
			Priority p = successLogLevel.toPriority(successLogLevel.toInt());
			// todo write an xml logger
			if (logger.isEnabledFor(p)) {
				logger.log(p, "elapsed Millis: " + elapsed + " '" +  sqlText + "'");
			}
		} catch (SQLException sqe) {
	//		Level l = logger.getLevel();
			Priority p = failLogLevel.toPriority(failLogLevel.toInt());
			if (logger.isEnabledFor(p)) {
				logger.log(p, sqe.getMessage());
			}
			if (throwException) {
				throw new PersistenceException(sqe,sqlText);
			}
		}
	}

	public StatNames getStatNames() throws SQLException {
		if (statNames == null) {
			statNames = StatNameS.getStatNames(this.conn);
		}
		return statNames;
	}
}