package org.javautil.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides query bind validation and prepared statement binding. Additional
 * parameters can be set to ignore any extra bind variables found in the binds,
 * or to automatically set remaining binds to null.
 * 
 * @author bcm
 */
public class SQLBindsProcessor {

	private final Log logger = LogFactory.getLog(getClass());

	private boolean setRemainingBindsToNull = false;

	private boolean allowExtraBindVariables = false;

	public Map<String, Object> getBindMap(final SQLBindValues queryBinds) {
		final Set<String> bindNames = queryBinds.getQuery().getBindNames();
		// build the actual binds, adding nulls if requested
		final Map<String, Object> bindMap = new LinkedHashMap<String, Object>();
		if (setRemainingBindsToNull) {
			for (final String bindName : bindNames) {
				bindMap.put(bindName, null);
			}
		}
		bindMap.putAll(queryBinds.getBindValues());
		return bindMap;
	}

	/**
	 * Binds a prepared statement. Throws an IncorrectSQLBindException when a
	 * bind is specified that is not known in the query's sqlText.
	 * 
	 * @throws SQLBindException
	 * 
	 * @param stmt
	 * @param queryBinds
	 * @throws SQLException
	 */
	public void bind(final PreparedStatement stmt,
			final SQLBindValues queryBinds) throws SQLException {
		final SqlStatementBindMeta query = queryBinds.getQuery();
		final Map<String, Object> bindMap = getBindMap(queryBinds);
		final Map<String, List<Integer>> bindIndexes = query.getBindIndexes();
		for (final String bindName : query.getBindNames()) {
			if (!bindMap.containsKey(bindName)) {
				throw new SQLBindException("no bind for bind variable: "
						+ bindName);
			}
			final Object bindValue = bindMap.get(bindName);
			final List<Integer> indexes = bindIndexes.get(bindName);
			for (final Integer index : indexes) {
				stmt.setObject(index, bindValue);
				if (logger.isDebugEnabled()) {
					logger.debug("bound index " + index + " to value "
							+ bindValue);
				}
			}
		}
	}

	/**
	 * Validates that the SQLBindValues can be used to bind the query. 
	 * If not a SQLBindException will be thrown.
	 * 
	 * @throws SQLBindException
	 * @param queryBinds
	 */
	public void validate(final SQLBindValues queryBinds) {
		final Map<String, Object> actualBinds = queryBinds.getBindValues();
		final SqlStatementBindMeta query = queryBinds.getQuery();
		final Set<String> bindNames = query.getBindNames();
		// ensure no unset binds exist unless we are going to null them
		if (!setRemainingBindsToNull && bindNames.size() > 0) {
			if (!actualBinds.keySet().containsAll(bindNames)) {
				final TreeSet<String> unset = new TreeSet<String>(bindNames);
				unset.removeAll(actualBinds.keySet());
				final String error = "query " + query.getStatementName()
						+ " had unset bind variables: "
						+ Arrays.toString(unset.toArray());
				throw new SQLBindException(error);
			}
		}
		// ensure no extra binds exist unless they are allowed
		if (!allowExtraBindVariables && actualBinds.size() > 0) {
			final TreeSet<String> extra = new TreeSet<String>(
					actualBinds.keySet());
			extra.removeAll(bindNames);
			if (extra.size() > 0) {
				final String error = "query " + query.getStatementName()
						+ " had unexpected bind variables: "
						+ Arrays.toString(bindNames.toArray());
				throw new SQLBindException(error);
			}
		}
	}

	public PreparedStatement getPreparedStatement(final SqlStatementBindMeta query,
			final Connection connection) throws SQLException {
		final String sql = SQLBindUtils.asPreparableString(query.getSqlText());
		final PreparedStatement statement = connection.prepareStatement(sql);
		return statement;
	}

	public boolean isSetRemainingBindsToNull() {
		return setRemainingBindsToNull;
	}

	public void setSetRemainingBindsToNull(final boolean setRemainingBindsToNull) {
		this.setRemainingBindsToNull = setRemainingBindsToNull;
	}

	public boolean isAllowExtraBindVariables() {
		return allowExtraBindVariables;
	}

	public void setAllowExtraBindVariables(final boolean allowExtraBindVariables) {
		this.allowExtraBindVariables = allowExtraBindVariables;
	}

}
