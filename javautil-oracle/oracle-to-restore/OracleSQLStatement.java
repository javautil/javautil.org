package com.dbexperts.oracle;

import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.dbexperts.persistence.PersistenceException;

public class OracleSQLStatement {
	private final String sqlText;
	private final Level failLogLevel;
	private final boolean throwException;
	private int sid;
	private final Logger logger = Logger.getLogger(getClass());
	public OracleSQLStatement(String sqlText, Level failLogLevel, boolean throwException) {
		if (sqlText == null) {
			throw new IllegalArgumentException("sqlText is null");
		}
		if (failLogLevel == null) {
			throw new IllegalArgumentException("failLogLevel is null");
		}
		this.sqlText = sqlText;
		this.failLogLevel = failLogLevel;
		this.throwException = throwException;

	}
	
	public void process(OracleConnection conn) throws  PersistenceException, SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
			WrappedOracleConnection woc = WrappedOracleConnection.getInstance(conn);
			sid = woc.getSid();
		
		Statement statement = conn.createStatement();
		try {
			statement.execute(sqlText);
		} catch (SQLException sqe) {
			Level l = logger.getLevel();
			Priority p = failLogLevel.toPriority(failLogLevel.toInt());
			if (logger.isEnabledFor(p)) {
				logger.log(p, sqe.getMessage());
			}
			if (throwException) {
				throw new PersistenceException(sqe,sqlText);
			}
		}
	}
	
}
