package org.javautil.jdbc.datasources;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;
import org.javautil.oracle.datasources.ODSWrapperInterface;

//import com.dbexperts.oracle.datasource.ODSWrapperInterface;

// todo what is this
public class OracleDataSourceBigInit {
	//

	// private String cacheName = "test";

	// if this is not set we get invalid URL, need to
	private String serviceName;

	private String serverName;

	// private boolean useThinConnectionType = true;

	private int portNumber;

	private String user;

	private String password;

	// private int loginTimeout = 10;

	// private int lowerThresholdLimit = 1;

	// private int connectionWaitTimeout = 0;

	// private boolean connectionCachingEnabled = true;

	private Integer maxLimit = 20;

	// private static final int ConnectionLimit = 30;

	private Integer initialLimit = 1;

	private OracleDataSource ods;

	private final LinkedList<Connection> connections = new LinkedList<Connection>();

	// private TreeMap<Integer,Connection> connections;
	// private static final int MaxConnections = 5;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * We will stop creating connections at Connection Limit despite anything
	 * the ds allows
	 */
	// private static final int ConnectionLimit = 30;
	public OracleDataSourceBigInit() {
		configureLogging();
	}

	private void configureLogging() {

		// SimpleProcessFormatter spf = new SimpleProcessFormatter();
		// spf.setPrintSourceClassName(false);
		// spf.setPrintDate(false);
		// ConsoleHandler ch = new ConsoleHandler();
		// ch.setFormatter(spf);
		// logger.addHandler(ch);
		// Handler[] handlers = logger.getHandlers();
		// for (Handler handler : handlers) {
		// logger.removeHandler(handler);
		// }
		// logger.addHandler(ch);
		// logger.setUseParentHandlers(false);
		// logger.setLevel(Level.FINEST);
		// ch.setLevel(Level.FINEST);
	}

	private void connectFromEnvironment() throws SQLException {
		ods = new OracleDataSource();
		serverName = getMandatoryEnv("ODS_SERVER_NAME");
		portNumber = getMandatoryEnvInt("ODS_PORT_NUMBER");
		serviceName = getMandatoryEnv("ODS_SERVICE_NAME");
		user = getMandatoryEnv("ODS_USER");
		password = getMandatoryEnv("ODS_PASSWORD");
		initialLimit = getEnvInt("ODS_INITIAL_LIMIT");
		maxLimit = getEnvInt("ODS_MAX_LIMIT");

		ods.setServerName(serverName);
		ods.setPortNumber(portNumber);
		ods.setServiceName(serviceName);
		ods.setUser(user);
		ods.setPassword(password);
		ods.setDriverType("thin");
		final Properties connectionCacheProperties = new Properties();
		if (initialLimit != null) {
			connectionCacheProperties.setProperty(
					ODSWrapperInterface.InitialLimit,
					Integer.toString(initialLimit));
		}
		if (maxLimit != null) {
			connectionCacheProperties.setProperty(ODSWrapperInterface.MaxLimit,
					Integer.toString(maxLimit));
		}
		ods.setConnectionCacheProperties(connectionCacheProperties);
	}

	private Integer getEnvInt(final String varName) {
		final String x = System.getenv(varName);
		Integer retval = null;
		if (x == null) {
			logger.warn(varName + " is not set");
		} else {
			try {
				retval = new Integer(x);
			} catch (final NumberFormatException nfe) {
				throw new IllegalArgumentException("environment variable "
						+ varName + " must be an integer");
			}
		}
		return retval;
	}

	private int getMandatoryEnvInt(final String varName) {
		final String x = System.getenv(varName);
		if (x == null) {
			throw new IllegalArgumentException("environment variable "
					+ varName + " is null");
		}
		int retval;

		try {
			retval = Integer.parseInt(x);
		} catch (final NumberFormatException nfe) {
			throw new IllegalArgumentException("environment variable "
					+ varName + " must be an integer");
		}
		return retval;
	}

	private String getMandatoryEnv(final String varName) {
		final String x = System.getenv(varName);
		if (x == null) {
			throw new IllegalArgumentException("environment variable "
					+ varName + "is null");
		}
		return x;
	}

	public void testConnection(final Connection conn) throws SQLException {
		assertNotNull(conn);

		final PreparedStatement ps = conn.prepareCall("select 'y' from dual");
		final ResultSet rs = ps.executeQuery();
		rs.next();
		final String txt = rs.getString(1);
		conn.close();
		System.out.println(txt);
		assertTrue("y".equals(txt));
		// logger.exiting(this.getClass().getName(), "testConnection");

	}

	private void getConnections(final int count) {
		long ela;
		for (int i = 1; i <= count; i++) {
			Connection conn;
			try {
				final long a = System.currentTimeMillis();
				conn = ods.getConnection();
				final long b = System.currentTimeMillis();
				ela = b - a;
			} catch (final SQLException sqe) {
				sqe.printStackTrace();
				break;
			}
			if (conn == null) {
				logger.info("stopped at " + i);
				break;
			}

			connections.add(conn);
			logger.warn("open connections " + connections.size()
					+ " elapsed millis last connection " + ela);
		}

	}

	private void getConnectionsSleep(final int connectionCount,
			final int sleepSeconds) throws SQLException {
		getConnections(connectionCount);
		logger.warn("sleeping for " + sleepSeconds);
		try {
			Thread.sleep(sleepSeconds * 1000);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 1 minute
	}

	private void closeConnections() throws SQLException {
		logger.warn("closing connections");
		for (final Connection conn : connections) {
			conn.close();
		}
	}

	private void getShitLoad() throws SQLException {

		connectFromEnvironment();
		getConnectionsSleep(150, 60);
		getConnectionsSleep(150, 60);
		closeConnections();
		ods.close();
	}

	public static void main(final String args[]) {
		final OracleDataSourceBigInit ods = new OracleDataSourceBigInit();
		try {
			ods.getShitLoad();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
