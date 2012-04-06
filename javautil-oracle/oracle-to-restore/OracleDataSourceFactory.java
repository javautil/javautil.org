package com.dbexperts.oracle;

import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

import com.dbexperts.oracle.datasource.OracleDataSourceEnvironment;

public class OracleDataSourceFactory {
	public static OracleDataSource newInstance() throws SQLException {
		final OracleDataSourceEnvironment e = new OracleDataSourceEnvironment();
		return e.getDataSource();

	}
}
