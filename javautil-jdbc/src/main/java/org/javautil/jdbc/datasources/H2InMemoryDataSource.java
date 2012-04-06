package org.javautil.jdbc.datasources;

import java.sql.Connection;
import java.sql.SQLException;

import org.javautil.jdbc.H2SingletonInstance;

/**
 * Gives access to an in memory singleton H2 database instance.
 * 
 * @author cvw
 * 
 */
public class H2InMemoryDataSource extends AbstractDataSource {

	@Override
	protected void initialize() {
		// nothing needs to be initialized here
	}

	@Override
	public Connection getConnection() throws SQLException {
		return H2SingletonInstance.getConnection();
	}

	@Override
	protected void assertRequiredProperties() {
		// No properties required here
	}

}
