package com.dbexperts.oracle.jdbc;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class CallableStatementHelper {
	private final OracleCallableStatement stmt;
	private final OracleConnection conn;
	//private ArrayDescriptor numberDescriptor;

	public CallableStatementHelper(final OracleConnection conn,
			final OracleCallableStatement stmt) {
		if (stmt == null) {
			throw new IllegalArgumentException("stmt is null");
		}
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		this.stmt = stmt;
		this.conn = conn;
	}

	public Integer getInteger(final String bindName) throws SQLException {
		final int val = stmt.getInt(bindName);
		final Integer retval = stmt.wasNull() ? null : new Integer(val);
		return retval;
	}

	public Integer getInteger(final int n) throws SQLException {
		final int val = stmt.getInt(n);
		final Integer retval = stmt.wasNull() ? null : new Integer(n);
		return retval;
	}

	public void setInteger(final String bindName, final Integer val) throws SQLException {
		if (val == null) {
			stmt.setNullAtName(bindName, Types.NUMERIC);
		} else {
			stmt.setIntAtName(bindName, val);
		}
	}

	public void setBoolean(final String bindName, final boolean val) throws SQLException {
		// stmt.setBooleanAtName(bindName,new Boolean(val));
		stmt.setBooleanAtName(bindName, val);
	}

	public void setString(final String bindName, final String val) throws SQLException {
		// stmt.setBooleanAtName(bindName,new Boolean(val));
		stmt.setStringAtName(bindName, val);
	}

	public void setDate(final String bindName, final Date val) throws SQLException {
		// stmt.setBooleanAtName(bindName,new Boolean(val));
		stmt.setDateAtName(bindName, val);
	}

	public void setIntegerArray(final String bindName, final Integer[] val)
			throws SQLException {
		final ArrayDescriptor numberDescriptor = ArrayDescriptor.createDescriptor(
				"NUMBER_ARRAY", conn);
		final ARRAY itemArray = new ARRAY(numberDescriptor, conn, val);
		stmt.setARRAYAtName(bindName, itemArray);
	}

}
