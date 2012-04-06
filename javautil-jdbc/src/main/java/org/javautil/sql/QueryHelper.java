package org.javautil.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;


/**
 * A helper for executing and binding jdbc statements. Supports bind of queries
 * using case-insensitive bind variables prefixed by a colon. Allows for reuse
 * of the same statement with subsequent binds. Implements DisposableBean for
 * closing of sql statements. The code invoking this class is resposible for
 * closing the ResultSets returned.
 * 
 * @author jjs@javautil.org, bcm
 */
public class QueryHelper implements DisposableBean {

	private final Log logger = LogFactory.getLog(getClass());

	private final Connection connection;

	private SqlStatementHelper helper;
	//private final SQLBindInfo query;

	private PreparedStatement statement;

	private final SQLBindValues binds;

	/**
	 * When false, unknown bind variables (that is, those not found while
	 * indexing the query text for named bind variables) cause an error to be
	 * thrown.
	 */
	private boolean allowExtraBindVariables = false;

	/**
	 * Simple constructor requiring a connection and sql text.
	 * 
	 * @param connection
	 * @param sqlText
	 */
	public QueryHelper(final Connection connection, final String sqlText) {
		this(null,connection,sqlText);
	}

	/**
	 * Constructor. Accepts a query name to associate with sqlText to make log
	 * messages more readable.
	 * 
	 * @param name
	 * @param connection
	 * @param sqlText
	 */
	public QueryHelper(final String name, final Connection connection,
			final String sqlText) {
		if (connection == null) {
			throw new IllegalArgumentException("connection is null");
		}
		if (sqlText == null) {
			throw new IllegalArgumentException("sqlText is null");
		}
		this.connection = connection;
		
		helper = new SqlStatementHelper(sqlText);
		// TODO should this be done in the constructor??? Probably not
		helper.process();
		// constructor will exception if parameters are null
		//this.query = new SQLBindInfo(name, sqlText);
		// by default, an error is thrown on an unknown bind
		this.binds = new SQLBindValues(this.helper);
	}

	/**
	 * Internal method using a binds processor to validate query binds, create a
	 * prepared statement, bind the parameters, and then execute the query.
	 * Remember to close the ResultSet!
	 * 
	 * @param processor
	 * @return ResultSet
	 * @throws SQLException
	 */
	protected ResultSet validateBindAndExecute(final SQLBindsProcessor processor)
			throws SQLException {
		processor.validate(binds);
		if (statement == null || statement.isClosed()) {
			statement = processor.getPreparedStatement(helper, connection);
		}
		if (helper.hasBindParameters()) {
			processor.bind(statement, binds);
		}
		if (logger.isTraceEnabled()) {
			logger.trace("executing query: " + helper.getSqlText());
		}
		final ResultSet ResultSet = statement.executeQuery();
		return ResultSet;
	}

	/**
	 * Internal method to construct a processor capable of preparing a query.
	 * 
	 * @param allowMissingBinds
	 * @return
	 */
	protected SQLBindsProcessor getProcessor(final boolean allowMissingBinds) {
		final SQLBindsProcessor processor = new SQLBindsProcessor();
		processor.setSetRemainingBindsToNull(allowMissingBinds);
		processor.setAllowExtraBindVariables(allowExtraBindVariables);
		return processor;
	}

	/**
	 * Executes a query and returns a ResultSet. If there are any missing binds,
	 * the query will fail. Remember to close the ResultSet!
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet executeQuery() throws SQLException {
		return validateBindAndExecute(getProcessor(false));
	}

	/**
	 * Executes a query, adding the specified binds, and returning a ResultSet.
	 * If there are any missing binds, the query will fail. Remember to close
	 * the ResultSet!
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet executeQuery(final Map<String, Object> bindMap)
			throws SQLException {
		setBinds(bindMap);
		final SQLBindsProcessor processor = getProcessor(false);
		return validateBindAndExecute(processor);
	}

	/**
	 * Executes a query, adding the specified binds, and returning a ResultSet.
	 * If there are any missing binds, and setRemainingBindsToNull is false, the
	 * query will fail. If there are any missing binds, and
	 * setRemainingBindsToNull is true, the query will be executed with the
	 * unspecified binds set to null. Remember to close the ResultSet!
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet executeQuery(final Map<String, Object> bindMap,
			final boolean setRemainingBindsToNull) throws SQLException {
		setBinds(bindMap);
		final SQLBindsProcessor processor = getProcessor(setRemainingBindsToNull);
		return validateBindAndExecute(processor);
	}

	/**
	 * Required method for DisposableBean. It is good practice to call this
	 * method to close the SQL statement after a query has been executed.
	 * Alternatively, the methods returning a ResultSet can close the statement
	 * as well, by first calling ResultSet.getStatement().close().
	 * 
	 * 
	 * @throws SQLException
	 */
	@Override
	public void destroy() throws SQLException {
		if (statement != null) {
			try {
				statement.close();
			} catch (final SQLException e) {
				logger.error(e);
			}
		}
		statement = null;
	}

	/**
	 * 
	 * @param bindMap
	 */
	public void setBinds(final Map<String, Object> bindMap) {
		binds.setBinds(bindMap);
	}

	/**
	 * 
	 * @param bindName
	 * @param bindValue
	 */
	public void setNumber(final String bindName, final Number bindValue) {
		binds.setNumber(bindName, bindValue);
	}

	/**
	 * @param bindName
	 * @param bindValue
	 */
	public void setDate(final String bindName, final Date bindValue) {
		binds.setDate(bindName, bindValue);
	}

	/**
	 * @param bindName
	 * @param bindValue
	 */
	public void setString(final String bindName, final String bindValue) {
		binds.setString(bindName, bindValue);
	}

	/**
	 * @param bindName
	 * @param bindValue
	 */
	public void setObject(final String bindName, final Object bindValue) {
		binds.setObject(bindName, bindValue);
	}

	/**
	 * @param bindName
	 */
	public void setNull(final String bindName) {
		binds.setNull(bindName);
	}

	/**
	 * Clear Binds
	 */
	public void clear() {
		binds.clear();
	}

	/**
	 * Setter for allowExtraBindVariables. When false, bind variables that were
	 * not found in the query sql text will cause an error to be thrown.
	 * 
	 * @return
	 */
	public boolean isAllowExtraBindVariables() {
		return allowExtraBindVariables;
	}

	/**
	 * Getter for allowExtraBindVariables.
	 * 
	 * When false, bind variables that were not found in the query sql text will
	 * cause an error to be thrown.
	 * 
	 * @param allowExtraBindVariables
	 */
	public void setAllowExtraBindVariables(final boolean allowExtraBindVariables) {
		this.allowExtraBindVariables = allowExtraBindVariables;
		binds.setErrorOnUnknownBind(!allowExtraBindVariables);
	}

	// TODO jjs implement maximum query seconds
	// public void setMaxQuerySec(Integer maxQuerySec) {
	// throw new UnsupportedOperationException();
	// }

}