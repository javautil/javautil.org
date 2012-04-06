package org.javautil.sql;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Stores information about the bind variable state of a query. Typically, a new
 * instance of this class should be created for every query that is run. By
 * design, the map holding the bind values cannot be manipulated directly.
 * 
 * @author bcm
 */
public class SQLBindValues {

	/**
	 * When true, setting an unknown bind variable fails fast.
	 */
	private boolean errorOnUnknownBind = true;

	/**
	 * Stores information about the query that can be derived from the sqlText.
	 */
	private final SqlStatementBindMeta bindMeta;

	/**
	 * Stores bind variable names and values that have been set.
	 */
	private Map<String, Object> bindValues = new LinkedHashMap<String, Object>();

	/**
	 * Creates an instance using a specified name QueryInfo (name and sqlText).
	 * 
	 * @param query
	 */
	public SQLBindValues(final SqlStatementBindMeta query) {
		this.bindMeta = query;
	}

	/**
	 * Creates an instance using a specified name QueryInfo (name and sqlText)
	 * and adds the specified bind values;
	 * 
	 * @param query
	 */
	public SQLBindValues(final SqlStatementBindMeta query,
			final Map<String, Object> bindValues) {
		this.bindMeta = query;
		setBinds(bindValues);
	}

	/**
	 * Sets the named bind variable as bound to a specified date.
	 * 
	 * @throws IllegalArgumentException
	 *             when errorOnUnknownBind is true
	 * @param bindName
	 * @param bindValue
	 */
	public void setDate(final String bindName, final Date bindValue) {
		checkNotNullBind(bindName, bindValue);
		final String bind = bindName.toLowerCase();
		checkValidBind(bind);
		bindValues.put(bind, bindValue);
	}

	/**
	 * Sets the named bind variable as bound to a specified string.
	 * 
	 * @param bindName
	 * @param bindValue
	 */
	public void setString(final String bindName, final String bindValue) {
		checkNotNullBind(bindName, bindValue);
		final String bind = bindName.toLowerCase();
		checkValidBind(bind);
		bindValues.put(bind, bindValue);
	}

	/**
	 * Sets the named bind variable as bound to a specified number.
	 * 
	 * @param bindName
	 * @param bindValue
	 */
	public void setNumber(final String bindName, final Number bindValue) {
		checkNotNullBind(bindName, bindValue);
		final String bind = bindName.toLowerCase();
		checkValidBind(bind);
		bindValues.put(bind, bindValue);
	}

	/**
	 * Sets the named bind variable as bound to a specified object.
	 * 
	 * @param bindName
	 * @param bindValue
	 */
	public void setObject(final String bindName, final Object bindValue) {
		checkNotNullBind(bindName, bindValue);
		final String bind = bindName.toLowerCase();
		checkValidBind(bind);
		bindValues.put(bind, bindValue);
	}

	/**
	 * Clears all bind variables that have been set.
	 */
	public void clear() {
		bindValues.clear();
	}

	/**
	 * Internal method to ensure not-null bind names/values.
	 * 
	 * @param bindName
	 * @param bindValue
	 */
	protected void checkNotNullBind(final String bindName,
			final Object bindValue) {
		if (bindName == null) {
			throw new IllegalArgumentException("bindName is null");
		}
		if (bindValue == null) {
			throw new IllegalArgumentException("bindValue is null");
		}
	}

	/**
	 * Internal method to validate bind names before they are set. Throws an
	 * IncorrectSQLBindException when an unknown bind variable name is used.
	 * 
	 * @throws SQLBindException
	 * @param bindName
	 */
	protected void checkValidBind(final String bindName) {
		if (isErrorOnUnknownBind() && !bindMeta.getBindNames().contains(bindName)) {
			throw new SQLBindException("query " + bindMeta.getStatementName()
					+ " has no bind variable named " + bindName + ";"
					+ " valid bind variables are: "
					+ Arrays.toString(bindMeta.getBindNames().toArray()));
		}
	}

	/**
	 * Sets the named bind variables as bound to NULL.
	 * 
	 * @param bindName
	 */
	public void setNull(final String bindName) {
		bindValues.put(bindName.toLowerCase(), null);
	}

	/**
	 * A string representation of the query and its bound variables, suitable
	 * for a multiline debug message.
	 */
	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder();
		s.append(bindMeta.toString());
		s.append("\n-- Provided binds: ");
		if (bindValues != null) {
			s.append(bindValues == null ? "none" : Arrays.toString(bindValues
					.keySet().toArray()));
		} else {
			s.append("\n--\tnone");
		}
		s.append("\n-- Bind values: ");
		if (bindValues != null) {
			for (final String bind : getBindValues().keySet()) {
				final Object value = bindValues.get(bind);
				final String clazz = value == null ? null : value.getClass()
						.getName();
				s.append("\n--\t" + bind + "=" + value + "\t"
						+ (clazz != null ? "(" + clazz + ")" : ""));
			}
		} else {
			s.append("\n--\tnone");
		}
		return s.toString();
	}

	/**
	 * Returns a Map of the current bind variable values. This map is
	 * unmodifiable to prevent confusion with how binds can be set. This
	 * intentionally prevents bind variables from being set through
	 * modifications to the map returned by this method.
	 * 
	 * @return bindValues
	 */
	public Map<String, Object> getBindValues() {
		return Collections.unmodifiableMap(bindValues);
	}

	/**
	 * Adds binds variables values for all the bind names in the map. If the
	 * value is null, the sql statement will be created with a NULL bind.
	 * 
	 * @param bindVariables
	 */
	public void setBinds(final Map<String, Object> bindVariables) {
		if (bindVariables == null) {
			throw new IllegalArgumentException("bindVariables is null");
		}
		for (final String bindName : bindVariables.keySet()) {
			if (bindName == null) {
				throw new IllegalArgumentException(
						"one or more bind names is null");
			}
			final String bind = bindName.toLowerCase();
			checkValidBind(bind);
			final Object bindValue = bindVariables.get(bindName);
			this.bindValues.put(bind, bindValue);
		}
		this.bindValues = bindVariables;
	}

	/**
	 * Gets the associated QueryInfo (query name and sqlText).
	 * 
	 * @return query
	 */
	public SqlStatementBindMeta getQuery() {
		return bindMeta;
	}

	/**
	 * When true, setting an unknown bind variable fails fast. Defaults to true.
	 * 
	 * @return errorOnUnknownBind
	 */
	public boolean isErrorOnUnknownBind() {
		return errorOnUnknownBind;
	}

	/**
	 * When true, setting an unknown bind variable fails fast. Defaults to true.
	 * 
	 * @param errorOnUnknownBind
	 */
	public void setErrorOnUnknownBind(final boolean errorOnUnknownBind) {
		this.errorOnUnknownBind = errorOnUnknownBind;
	}

}
