package org.javautil.jdbc.datasources;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Generic class for obtaining a connection, requires a user, password, driver,
 * and a url to be defined within the datasource properties.
 * 
 * todo jjs this is not a pooled datasource
 */
public class NonConnectionPooledDatasource extends AbstractDataSource {
	public static final String USER = "user";

	public static final String PASSWORD = "password";

	public static final String URL = "url";

	public static final String DRIVER = "driver";

	private String url;

	private String user;

	private String password;

	private final Logger logger = Logger.getLogger(getClass());

	private String driverName;

	private final String newline = System.getProperty("line.separator");

	public NonConnectionPooledDatasource() {
		logger.warn("This DataSource is not backed by a connection pool");
	}

	@Override
	protected void assertRequiredProperties() {
		assertPropertyDefined(USER);
		assertPropertyDefined(PASSWORD);
		assertPropertyDefined(DRIVER);
		assertPropertyDefined(URL);
	}

	@Override
	protected void initialize()
	// throws Exception
	{
		user = getProperty(USER);
		password = getProperty(PASSWORD);
		url = getProperty(URL);

		driverName = getProperty(DRIVER);
		try {
			final Driver driver = (Driver) Class.forName(driverName)
					.newInstance();
			DriverManager.registerDriver(driver);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		final Connection conn = DriverManager
				.getConnection(url, user, password);
		return conn;
	}

	@Override
	public Connection getConnection(final String username, final String password)
			throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}
