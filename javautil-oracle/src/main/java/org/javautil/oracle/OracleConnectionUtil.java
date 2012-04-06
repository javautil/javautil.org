package org.javautil.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class OracleConnectionUtil {
	private static final Logger logger = Logger.getLogger(Connection.class
			.getName());

	// private static final String startTraceUnlimited = "begin \n" + " alter
	// session set timed_statistics=true;\n"
	// + " alter session set max_dump_file_size = unlimited; \n"
	// + " alter session set event '10046 trace name context forever, level
	// 12';\n" + "end;";

	/**
	 * @todo implement or remove
	 */
	@SuppressWarnings("unused")
	private static final String startTraceLimited = "begin \n"
			+ "   alter session set timed_statistics=true;\n"
			+ //
			"   alter session set max_dump_file_size = :max_size; \n"
			+ //
			"   alter session set event '10046 trace name context forever, level 12';\n"
			+ //
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

	private final boolean throwSysExceptions = false;

	private final Connection conn;

	private PreparedStatement commitBatchNoWaitStatement = null;

	public OracleConnectionUtil(final Connection conn) throws SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		this.conn = conn;
	}

	public void commit(final boolean asynchronous) throws SQLException {

		final String commitBatchNoWaitText = "commit work write batch nowait";
		if (asynchronous == false) {
			getConn().commit();
		} else {
			if (commitBatchNoWaitStatement == null) {
				commitBatchNoWaitStatement = getConn().prepareStatement(
						commitBatchNoWaitText);
			}
			commitBatchNoWaitStatement.executeUpdate();
		}
	}

	public boolean traceOff() throws SQLException {
		boolean success = true;
		Statement stmt = null;
		try {
			stmt = getConn().createStatement();
			stmt.execute("alter session set timed_statistics = false");
			stmt.execute("alter session set sql_trace = false");
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

		} finally {
			closeStatementNoFail(stmt);
		}
		return success;
	}

	public boolean traceOn(final String fileId) throws SQLException {
		boolean success = true;
		Statement stmt = null;
		try {
			stmt = getConn().createStatement();
			stmt.execute("alter session set timed_statistics = true");

			stmt.execute("alter session set max_dump_file_size = unlimited");
			stmt.execute("alter session set sql_trace = true");
			stmt.execute("alter session set events '10046 trace name context forever, level 12'");

			stmt.close();
			setTraceFileIdentifier(fileId);
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
			closeStatementNoFail(stmt);
		}
		return success;
	}

	protected java.sql.Connection getConn() {
		return conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.oracle.ConnectionWrapper#getSessionInfo()
	 */
	public void getSessionInfo() throws SQLException {
		if (!haveSessionInfo) {
			final String text = "select s.sid, s.serial#, p.spid, p.pid from v$session s, v$process p "
					+ " where s.audsid=userenv('sessionid') and p.addr = s.paddr";
			Statement stmt = null;
			ResultSet rset = null;
			try {
				stmt = getConn().createStatement();
				rset = stmt.executeQuery(text);
				rset.next();
				sid = rset.getInt("sid");
				serial = rset.getInt("serial#");
				spid = rset.getInt("spid");
				pid = rset.getInt("pid");
				logger.info("sid " + sid + " serial " + serial + " spid "
						+ spid + " pid " + pid);
				haveSessionInfo = true;
			} catch (final SQLException e) {
				final String message = "while processing " + text + " "
						+ e.getMessage();
				if (throwSysExceptions) {
					throw new SQLException(message);
				}
				logger.error(message);

			} finally {
				closeStatementNoFail(stmt);
				closeResultsetNoFail(rset);
			}
		}
	}

	public static int getSid(final Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rset = null;
		Integer sid = null;
		try {
			stmt = conn.createStatement();
			final String text = "select sid from v$mystat where rownum = 1";
			rset = stmt.executeQuery(text);
			rset.next();
			sid = rset.getInt("sid");
		} finally {
			closeStatementNoFail(stmt);
			closeResultsetNoFail(rset);
		}
		return sid;
	}

	public int getSpid() {
		return spid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.oracle.ConnectionWrapper#setAction(java.lang.String)
	 */
	public void setAction(final String action) throws java.sql.SQLException {
		String text = action;
		if (text.length() > 32) {
			text = action.substring(0, 31);
		}
		if (sqlAction == null) {
			sqlAction = getConn().prepareCall(
					"{call dbms_application_info.set_action(?)}");
		}
		sqlAction.setString(1, text);
		sqlAction.executeUpdate();
		if (logger.isDebugEnabled()) {
			logger.debug("action set to " + action);
		}
	}

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
	public void setTraceFileIdentifier(final String id) throws SQLException {
		// getSessionInfo();
		// String text = "alter session set tracefile_identifier = :id";
		final String text = "alter session set tracefile_identifier = '" + id
				+ "'";
		PreparedStatement stmt = null;
		try {
			stmt = getConn().prepareStatement(text);
			// stmt.setString(1,id);
			try {
				stmt.executeUpdate();
			} catch (final SQLException s) {
				logger.error("while trying to set identifier to '" + id + "'"
						+ s.getMessage());
			}
		} finally {
			closeStatementNoFail(stmt);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.ConnectionWrapper#setClientInfo(java.lang.String)
	 */
	public void setClientInfo(final String info) throws java.sql.SQLException {
		String text = info;
		if (text.length() > 32) {
			text = info.substring(0, 31);
		}
		if (sqlClientInfo == null) {
			sqlClientInfo = getConn().prepareCall(
					"{call dbms_application_info.set_client_info(:txt)}");
		}
		sqlClientInfo.setString(1, text);
		sqlClientInfo.executeUpdate();
	}

	private static void closeResultsetNoFail(final ResultSet r) {
		if (r != null) {
			try {
				r.close();
			} catch (final SQLException e) {
				logger.warn(e);

			}
		}
	}

	private static void closeStatementNoFail(final Statement s) {
		if (s != null) {
			try {
				s.close();
			} catch (final SQLException e) {
				logger.warn(e);

			}
		}
	}

	public void dispose() {
		closeStatementNoFail(sqlAction);
		closeStatementNoFail(sqlModule);
		closeStatementNoFail(sqlClientInfo);
	}

	// @todo make sure that OracleConnectionWrapper restores the state
	public void setAsynchCommit(final boolean val,
			final boolean throwExceptionOnExecuteFailure) throws SQLException {
		final Statement stmt = getConn().createStatement();
		String alter = null;
		try {
			if (val) {
				alter = "alter session set commit_write=batch,nowait";
				stmt.execute(alter);
			} else {
				// todo
				throw new UnsupportedOperationException();
			}
		} catch (final SQLException e) {
			if (throwExceptionOnExecuteFailure) {
				throw new SQLException("while attempting " + alter + " "
						+ e.getMessage());
			}
			logger.error(e);
		} finally {
			closeStatementNoFail(stmt);
		}

	}

	public boolean isDatabaseUnavailableException(final SQLException e) {
		// TODO check the oracle return code
		// TODO Auto-generated method stub
		return false;
	}

	
}