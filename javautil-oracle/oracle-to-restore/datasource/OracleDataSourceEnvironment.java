package com.dbexperts.oracle.datasource;

import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;

// todo what is this and Why can't it go away?
public class OracleDataSourceEnvironment {
	//

	// if this is not set we get invalid URL, need to
	private String					serviceName;

	private String					serverName;

	private int						portNumber;

	private String					user;

	private String					password;

	private Integer					maxLimit					= 20;

	//private static final int		ConnectionLimit				= 30;

	private Integer 						initialLimit				= 1;

	private OracleDataSource		ods;

	//private LinkedList<Connection>	connections					= new LinkedList<Connection>();

	// private TreeMap<Integer,Connection> connections;
	//private static final int		MaxConnections				= 5;

	private final Logger					logger						= Logger.getLogger(this.getClass().getName());

	/**
	 * We will stop creating connections at Connection Limit despite anything
	 * the ds allows
	 * @throws SQLException
	 */
	// private static final int ConnectionLimit = 30;
	public OracleDataSourceEnvironment() throws SQLException {
		//configureLogging();
		connectFromEnvironment();
	}

//	private void configureLogging() {
//
//		SimpleProcessFormatter spf = new SimpleProcessFormatter();
//		spf.setPrintSourceClassName(false);
//		spf.setPrintDate(false);
//		ConsoleHandler ch = new ConsoleHandler();
//		ch.setFormatter(spf);
//		logger.addHandler(ch);
//		Handler[] handlers = logger.getHandlers();
//		for (Handler handler : handlers) {
//			logger.removeHandler(handler);
//		}
//		logger.addHandler(ch);
//		logger.setUseParentHandlers(false);
//		logger.setLevel(Level.FINEST);
//		ch.setLevel(Level.FINEST);
//	}

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
			connectionCacheProperties.setProperty(ODSWrapperInterface.InitialLimit, Integer.toString(initialLimit));
		}
		if (maxLimit != null) {
			connectionCacheProperties.setProperty(ODSWrapperInterface.MaxLimit, Integer.toString(maxLimit));
		}
		ods.setConnectionCacheProperties(connectionCacheProperties);
	}

	// todo move to common location
	private Integer getEnvInt(final String varName) {
		final String x = System.getenv(varName);
		Integer retval = null;
		if (x == null) {
			logger.warn(varName + " is not set");
		} else {
			try {
				retval = new Integer(x);
			} catch (final NumberFormatException nfe) {
				throw new IllegalArgumentException("environment variable " + varName + " must be an integer");
			}
		}
		return retval;
	}

//	private void configureLogging() {
//
//		SimpleProcessFormatter spf = new SimpleProcessFormatter();
//		spf.setPrintSourceClassName(false);
//		spf.setPrintDate(false);
//		ConsoleHandler ch = new ConsoleHandler();
//		ch.setFormatter(spf);
//		logger.addHandler(ch);
//		Handler[] handlers = logger.getHandlers();
//		for (Handler handler : handlers) {
//			logger.removeHandler(handler);
//		}
//		logger.addHandler(ch);
//		logger.setUseParentHandlers(false);
//		logger.setLevel(Level.FINEST);
//		ch.setLevel(Level.FINEST);
//

	// todo move to common location
	private int getMandatoryEnvInt(final String varName) {
		final String x = System.getenv(varName);
		if (x == null) {
			throw new IllegalArgumentException("environment variable " + varName + " is null");
		}
		int retval;

		try {
			retval = Integer.parseInt(x);
		} catch (final NumberFormatException nfe) {
			throw new IllegalArgumentException("environment variable " + varName + " must be an integer");
		}
		return retval;
	}

	private String getMandatoryEnv(final String varName) {
		final String x = System.getenv(varName);
		if (x == null) {
			throw new IllegalArgumentException("environment variable " + varName + " is null");
		}
		return x;
	}

	public OracleConnection getConnection() throws SQLException {
		return (OracleConnection) ods.getConnection();
	}

	public OracleDataSource getDataSource() {
		return ods;
	}
}
