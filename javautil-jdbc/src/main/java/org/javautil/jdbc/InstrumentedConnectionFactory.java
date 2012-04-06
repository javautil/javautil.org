package org.javautil.jdbc;

import java.sql.Connection;

public class InstrumentedConnectionFactory {

	/**
	 * Attempts to get an InstrumentedOracleConnection. If the setAction method
	 * fails throws an exception either because it is not an oracle database or
	 * no permissions on dbms_application_info a dummy implementation is
	 * returned.
	 * 
	 * @param connection
	 * @return
	 */
	public static InstrumentedConnection getInstance(final Connection connection) {
		if (connection == null) {
			throw new IllegalArgumentException("connection is null");
		}
		InstrumentedConnection retval = null;

		retval = new InstrumentedOracleConnection(connection);
		try {
			retval.setAction("");
		} catch (final RuntimeException re) {
			retval = new InstrumentedConnectionImpl();
		}

		return retval;
	}

}
