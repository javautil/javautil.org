package org.javautil.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides a static instance of an H2 database for the current jvm.
 * 
 * @author bcm
 */
public class H2SingletonInstance {

	private static Log logger = LogFactory.getLog(H2SingletonInstance.class);

	private static Connection connection = null;

	private static final String H2_DRIVER_CLASSNAME = "org.h2.Driver";

	private static final String IN_MEMORY_URL_ARGS = "mem:";

	private static String jdbcUrlArgs = IN_MEMORY_URL_ARGS;

	public static void loadH2Driver() throws Exception {
		Class.forName(H2_DRIVER_CLASSNAME);
	}

	public static void startup() throws Exception {
		H2SingletonInstance.loadH2Driver();
		final String url = "jdbc:h2:" + getJdbcUrlArgs();
		logger.info("H2 jdbc url is " + url);
		connection = DriverManager.getConnection(url, "h2", "h2 h2");
	}

	public static void shutdown() throws Exception {
		connection.close();
		connection = null;
	}

	public static String getSchemaName() {
		return "PUBLIC"; // this is the h2 default schema
	}

	public static String getCatalogName() {
		return "UNNAMED"; // this is the h2 default catalog
	}

	public static Connection getConnection() {
		if (connection == null) {
			try {
				H2SingletonInstance.startup();
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}
		return new CloseProtectedConnection(connection);
	}

	public static String getJdbcUrlArgs() {
		return jdbcUrlArgs;
	}

	public static void setJdbcUrlArgs(final String _jdbcUrlArgs) {
		jdbcUrlArgs = _jdbcUrlArgs;
	}

}
