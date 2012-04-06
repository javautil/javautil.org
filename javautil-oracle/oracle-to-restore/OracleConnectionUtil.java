package com.dbexperts.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Logger;

import com.dbexperts.oracle.dao.OracleSessionStats;
import com.dbexperts.oracle.dao.OracleSessionWaits;
import com.dbexperts.oracle.dao.StatNameS;
import com.dbexperts.oracle.dto.OracleSessionWait;
import com.dbexperts.persistence.PersistenceException;
public class OracleConnectionUtil extends com.dbexperts.jdbc.WrappedConnection 
		 {
	private static final Logger logger = Logger.getLogger(Connection.class.getName());

//	private static final String startTraceUnlimited = "begin \n" + "   alter session set timed_statistics=true;\n"
//			+ "   alter session set max_dump_file_size = unlimited; \n"
//			+ "   alter session set event '10046 trace name context forever, level 12';\n" + "end;";

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




	private CallableStatement sqlAction = null;

	private CallableStatement sqlModule = null;

	private CallableStatement sqlClientInfo = null;



	private int sid;



	private int serial;

	private int spid;

	private int pid;

	private boolean haveSessionInfo = false;

	private boolean throwSysExceptions = false;
	
	private OracleConnection conn;
//




	private PreparedStatement commitBatchNoWaitStatement = null;


	private UserEnv userEnv;

	private StatNames  statNames;

	private OracleConnectionUtil() {
		
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
	
//	public static void enableTraceDetail(final Connection conn, final int megabytes) throws SQLException {
//		final CallableStatement beginTraceStmt = conn.prepareCall(startTraceUnlimited);
//		final int meg = megabytes * 1024 * 1024;
//		beginTraceStmt.setInt(1, meg);
//		beginTraceStmt.executeUpdate();
//		beginTraceStmt.close();
//	}


	// move to
	public static OracleConnectionUtil getInstance(final OracleConnection conn) throws SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		if (conn instanceof OracleConnectionUtil) {
			return (OracleConnectionUtil) conn;
		}
		final OracleConnectionUtil woc = new OracleConnectionUtil();

		conn.setAutoCommit(false);
		conn.setImplicitCachingEnabled(true);
		conn.setDefaultRowPrefetch(100);
		woc.setConn(conn);
		return woc;
	}
	
	public void commit(final boolean asynchronous) throws SQLException {

		String commitBatchNoWaitText = "commit work write batch nowait";
		if (asynchronous == false) {
			getConn().commit();
		} else {
			if (commitBatchNoWaitStatement == null) {
				commitBatchNoWaitStatement = getConn().prepareStatement(commitBatchNoWaitText);
			}
			commitBatchNoWaitStatement.executeUpdate();
		}
	}

	

	public boolean traceOn(String fileId) throws SQLException {
		boolean success = true;
		Statement s = null;
		try {
			s = getConn().createStatement();
			s.execute("alter session set timed_statistics = true");
	
			s.execute("alter session set max_dump_file_size = unlimited");
			s.execute("alter session set sql_trace = true");
			s.execute("alter session set events '10046 trace name context forever, level 12'");
		
			s.close();
	
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

		} finally {
			if (s != null) {
				closeStatementNoFail(s);
			}
		}
		return success;
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

//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see com.dbexperts.oracle.ConnectionWrapper#getSqlAction()
//	 */
//	public CallableStatement getSqlAction() {
//		return sqlAction;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see com.dbexperts.oracle.ConnectionWrapper#getSqlClientInfo()
//	 */
//	public CallableStatement getSqlClientInfo() {
//		return sqlClientInfo;
//	}
//
//	public CallableStatement getSqlModule() {
//		return sqlModule;
//	}

	

	
	
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
	
	private void closeStatementNoFail(Statement s) {
		if (s != null) {
			try {
				s.close();
			} catch (SQLException e) {
				logger.warn(e);
				
			}
		}
	}

	public void dispose( ) {
		closeStatementNoFail(sqlAction);
		closeStatementNoFail(sqlModule);
		closeStatementNoFail(sqlClientInfo);
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

//	private void closeNoFail(Statement s) {
//		if (s != null) {
//			try {
//				s.close();
//			} catch (SQLException e) {
//				logger.warn(e);
//			}
//		}
//		
//	}

	public StatNames getStatNames() throws SQLException {
		if (statNames == null) {
			statNames = StatNameS.getStatNames(this.conn);
		}
		return statNames;
	}
}