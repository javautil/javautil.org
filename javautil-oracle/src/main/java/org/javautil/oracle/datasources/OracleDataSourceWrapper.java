package org.javautil.oracle.datasources;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleConnectionCacheCallback;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;
import org.javautil.jdbc.datasources.AbstractDataSource;
import org.javautil.jdbc.datasources.ConnectionCacheEntry;
import org.javautil.jdbc.datasources.DataSourceInstantiationException;
import org.javautil.jdbc.datasources.InvalidDataSourceException;
import org.javautil.oracle.datasources.ODSWrapperInterface;
import org.javautil.properties.PropertiesHelper;
import org.javautil.text.StringBuilderHelper;

/**
 * @author jjs@dbexperts.com
 * 
 *         todo jjs where did all of the unit tests go? todo create an
 *         OracleDataSourceWrapperArguments class
 * 
 *         look at package
 *         com.dbexperts.oracle.datasource.OracleDataSourceWrapperValues
 */
@SuppressWarnings("boxing")
public class OracleDataSourceWrapper extends AbstractDataSource implements
// DataSourceWrapper,
		ODSWrapperInterface, DataSource
// , Named
{
	private static String newline = System.getProperty("line.separator");

	private static final String PORT_NUMBER_MESSAGE = "Port Number must be a valid IP port, is usually 1521 or 1526";
	// todo create AuthenticatingDataSource
	// public static final String INVALID_AUTHENTICATION = "attempt to get
	// connection with invalidAuthenticationString";

	// public static final String TICKET_REQUIRED = "you must provide an
	// authentication ticket";

	private static final TreeMap<String, Boolean> cachePropertyType = new TreeMap<String, Boolean>() {
		{
			put(AbandonedConnectionTimeout, true);
			put(ConnectionWaitTimeout, true);
			put(InitialLimit, true);
			put(InactivityTimeout, true);
			put(LowerThresholdLimit, true);
			put(MaxLimit, true);
			put(MaxStatementsLimit, true);
			put(MinLimit, true);
			put(PropertyCheckInterval, true);
			put(TimeToLiveTimeout, true);
			put(ValidateConnection, true);
			put(ConnectionCachingEnabled, false);
			put(LoginTimeout, true);
		}
	};

	// todo document every one of these fields in one location
	private final Logger logger = Logger.getLogger(getClass());

	private OracleDataSource ds;

	// todo wrap this with an AuthenticatingDataSource
	// private final WeakHashMap<String, String> useridByTicket = new
	// WeakHashMap<String, String>();

	private Object lock = null;
	private final Properties connectionProperties = new Properties();

	private final Properties cacheProperties = new Properties();

	private Class<OracleConnectionCacheCallback> callback;

	// private boolean allowUnauthenticatedConnectionRequests = true;

	private String name;

	private File configurationFile;

	private OracleDataSourceWrapperArguments arguments;

	//
	//
	//

	/**
	 * 
	 * 
	 */

	public OracleDataSourceWrapper() throws SQLException {
		ds = new OracleDataSource();
		ds.setDriverType("thin");
		ds.setConnectionProperties(connectionProperties);
	}

	// TODO need the ability to validate a set Argument bean other than
	public void setArguments(OracleDataSourceWrapperArguments arguments)
			throws SQLException {
		this.arguments = arguments;
		setAbandonedConnectionTimeout(arguments.getAbandonedConnectionTimeout());
		setConnectionCacheName(arguments.getConnectionCacheName());
		setConnectionCachingEnabled(arguments.getConnectionCachingEnabled());
		setConnectionWaitTimeout(arguments.getConnectionWaitTimeout());
		setInactivityTimeout(arguments.getInactivityTimeout());
		if (arguments.getInitialLimit() != null) {
			setInitialLimit(arguments.getInitialLimit());
		}
		if (arguments.getLoginTimeout() != null) {
			setLoginTimeout(arguments.getLoginTimeout());
		}
		if (arguments.getLowerThresholdLimit() != null) {
			setLowerThresholdLimit(arguments.getLowerThresholdLimit());
		}
		if (arguments.getMaxLimit() != null) {
			setMaxLimit(arguments.getMaxLimit());
		}
		setMaxLimit(arguments.getMaxLimit());
		if (arguments.getMinLimit() != null) {
			setMinLimit(arguments.getMinLimit());
		}
		setPassword(arguments.getPassword());
		setPortNumber(arguments.getPortNumber());
		setServerName(arguments.getServerName());
		setServiceName(arguments.getServiceName());
		setTimeToLiveTimeout(arguments.getTimeToLiveTimeout());
		setUser(arguments.getUser());
	}

	public ArrayList<String> getValidateMessages() {
		final ArrayList<String> messages = new ArrayList<String>();
		final String driverType = ds.getDriverType();
		if (!("thin".equals(driverType) || "oci".equals(driverType))) {
			messages.add("driverType: '" + driverType
					+ "' is not 'thin' or 'oci'");
		}
		if (!(ds.getPortNumber() == 1521 || ds.getPortNumber() == 1526)) {
			messages.add("non standard port (1521,1526) : "
					+ ds.getPortNumber());
		}
		if (ds.getServiceName() == null) {
			messages.add("serviceName is null");
		}
		if (ds.getTNSEntryName() == null && "oci".equals(driverType)) {
			messages.add("driverType is 'oci' and TNSEntryName is null");
		}
		if (ds.getServerName() == null && ds.getTNSEntryName() == null) {
			messages.add("serverName is null " + " for driverType "
					+ driverType);
		}
		return messages;

	}

	/**
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#getAbandonedConnectionTimeout()
	 */
	@Override
	public int getAbandonedConnectionTimeout() throws SQLException {

		return getIntProperty(AbandonedConnectionTimeout);

	}

	public OracleConnectionCacheCallback getCallback() {
		OracleConnectionCacheCallback retval = null;
		try {
			retval = callback.newInstance();
		} catch (final Exception e) {
			throw new IllegalStateException(e.getClass().getName() + " "
					+ e.getMessage());
		}
		return retval;
	}

	/**
	 * @return the configurationFile
	 */
	public File getConfigurationFile() {
		return configurationFile;
	}

	@Override
	public Connection getConnection(final String username, final String password) {
		throw new UnsupportedOperationException();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#getConnectionCacheName
	 * ()
	 */
	@Override
	public String getConnectionCacheName() throws SQLException {
		return ds.getConnectionCacheName();
	}

	public Properties getConnectionCacheProperties() throws SQLException {
		return ds.getConnectionCacheProperties();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#getPropertiesText()
	 */
	@Override
	public String getConnectionCachePropertiesText() throws SQLException {
		final Properties p = ds.getConnectionCacheProperties();
		final StringBuilder sb = new StringBuilder();
		if (p != null) {
			for (final Map.Entry es : p.entrySet()) {
				sb.append(es.getKey());
				sb.append(":");
				sb.append(es.getValue());
				sb.append("\n");
			}
		} else {
			logger.warn("ds properties were null");
		}
		return sb.toString();
	}

	public Properties getConnectionProperties() throws SQLException {
		return ds.getConnectionProperties();

	}

	public String getConnectionPropertiesText() throws SQLException {
		final Properties p = ds.getConnectionProperties();
		final StringBuilder sb = new StringBuilder();
		if (p != null) {
			for (final Map.Entry es : p.entrySet()) {
				sb.append(es.getKey());
				sb.append(":");
				sb.append(es.getValue());
				sb.append("\n");
			}
		} else {
			logger.warn("ds properties were null");
		}
		return sb.toString();
	}

	@Override
	public String getConnectionType() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#getDataSource()
	 */
	@Override
	public OracleDataSource getDataSource() {
		return ds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#getConnectionType()
	 */
	public String getDriverType() {
		return ds.getDriverType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#getInactivityTimeout
	 * ()
	 */
	@Override
	public int getInactivityTimeout() throws SQLException {
		return getIntProperty(InactivityTimeout);
	}

	/**
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#getInitialLimit()
	 */
	@Override
	public int getInitialLimit() throws SQLException {
		return getIntProperty(InitialLimit);

	}

	public int getIntProperty(final String propertyName) throws SQLException {
		final Boolean isCacheProperty = cachePropertyType.get(propertyName);
		if (isCacheProperty == null) {
			throw new IllegalArgumentException("unknown  property  '"
					+ propertyName + "'");
			// todo add known property dump
		}
		final Properties p = isCacheProperty ? ds
				.getConnectionCacheProperties() : ds.getConnectionProperties();

		String val;
		Integer retval = null;
		if (p != null) {
			val = p.getProperty(propertyName);
			retval = Integer.parseInt(val);
		}

		return retval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#getLoginTimeout()
	 */
	@Override
	public int getLoginTimeout() {
		return ds.getLoginTimeout();
	}

	@Override
	public PrintWriter getLogWriter() {
		return ds.getLogWriter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#getLowerThresholdLimit
	 * ()
	 */
	@Override
	public Integer getLowerThresholdLimit() throws SQLException {
		return getIntProperty(LowerThresholdLimit);

	}

	/**
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#getMaxLimit()
	 */
	public int getMaxLimit() throws SQLException {
		return getIntProperty(MaxLimit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#getMaxStatementsLimit
	 * ()
	 */
	public int getMaxStatementsLimit() throws SQLException {
		return getIntProperty(MaxStatementsLimit);
	}

	/**
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#getMinLimit()
	 */
	public int getMinLimit() throws SQLException {
		return getIntProperty("MinLimit");
	}

	public DataSource getOds() {
		return ds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#getOracleDataSource()
	 */
	public DataSource getOracleDataSource() {
		return ds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#getPortNumber()
	 */
	public int getPortNumber() {
		return ds.getPortNumber();
	}

	public String getPropertiesText() throws SQLException {
		final StringBuilder b = new StringBuilder();
		b.append("Cache Properties \n");
		b.append(getConnectionCachePropertiesText());
		b.append("\nConnection Properties\n");
		b.append(getConnectionPropertiesText());
		return b.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#getPropertyCheckInterval
	 * ()
	 */
	public int getPropertyCheckInterval() throws SQLException {
		return getIntProperty(PropertyCheckInterval);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#getServerName()
	 */
	public String getServerName() {
		return ds.getServerName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#getServiceName()
	 */
	public String getServiceName() {
		return ds.getServiceName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#getTimeToLiveTimeout
	 * ()
	 */
	public int getTimeToLiveTimeout() throws SQLException {
		return getIntProperty(TimeToLiveTimeout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#getUser()
	 */
	public String getUser() {
		return ds.getUser();
	}

	public OracleConnection getConnection() throws SQLException {
		// , InvalidDataSourceException {
		//
		OracleConnection oconn = null;
		try {
			oconn = (OracleConnection) ds.getConnection();
		} catch (final SQLException sqe) {
			final ArrayList<String> messages = getValidateMessages();
			final StringBuilder sb = new StringBuilder();
			for (final String message : getValidateMessages()) {
				sb.append(message);
				sb.append(newline);
			}
			sb.append(getUrl(ds));
			sb.append("name: " + name + newline);
			sb.append("configurationFile: " + configurationFile + newline);
			sb.append(asString(ds));
			throw new InvalidDataSourceException(sqe, "name is '" + name + "'"
					+ newline + "' configuration file : '" + configurationFile
					+ "'" + newline + asString(ds));
			// throw new InvalidDataSourceException(sqe,newline +
			// sb.toString());

		}
		// final OracleConnection woc =
		// WrappedOracleConnection.getInstance(oconn);
		if (callback != null) {
			final long now = System.nanoTime();
			final StackTraceElement[] stack = Thread.currentThread()
					.getStackTrace();
			final ConnectionCacheEntry cce = new ConnectionCacheEntry(oconn,
					this, 5000, stack);
			final OracleConnectionCacheCallback occ = getCallback();
			oconn.registerConnectionCacheCallback(occ, cce,
					OracleConnection.ABANDONED_CONNECTION_CALLBACK);
		}

		return oconn;
	}

	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return ds.isWrapperFor(iface);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.javautil.oracle.datasource.ODSWrapperInterface#
	 * setAbandonedConnectionCallback
	 * (oracle.jdbc.pool.OracleConnectionCacheCallback)
	 */
	public void setAbandonedConnectionCallback(
			final OracleConnectionCacheCallback callback) {
		// todo
		throw new UnsupportedOperationException();
	}

	public void setAbandonedConnectionTimeout(
			final Integer abandonedConnectionTimeout) throws SQLException {
		if (abandonedConnectionTimeout == null) {
			logger.warn("abandonedConnectionTimeout was null");
		} else {
			setProperty(AbandonedConnectionTimeout,
					abandonedConnectionTimeout.toString());
		}
	}

	// public void setAllowUnauthenticatedConnectionRequests(final Object
	// lockKey, final boolean value) {
	// validateLockKey(lockKey);
	// allowUnauthenticatedConnectionRequests = value;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#setValidateConnection
	 * (boolean)
	 */

	public void setCallback(final String connectionCallbackClassname)
			throws DataSourceInstantiationException {
		final Class<OracleConnectionCacheCallback> callbackClass = null;
		try {
			final Class c = Class.forName(connectionCallbackClassname);
			final Object o = c.newInstance();
			if (!(o instanceof OracleConnectionCacheCallback)) {
				throw new DataSourceInstantiationException(
						o.getClass().getName()
								+ " is not an instance of oracle.jdbc.pool.OracleConnectionCacheCallback");
			}
			callback = callbackClass;
		} catch (final ClassNotFoundException e) {
			throw new DataSourceInstantiationException("cannot locate class "
					+ connectionCallbackClassname);
		} catch (final Exception e) {
			throw new DataSourceInstantiationException(e.getMessage()
					+ " on class " + connectionCallbackClassname);
		}

	}

	/**
	 * @param configurationFile
	 *            the configurationFile to set
	 */
	public void setConfigurationFile(final File configurationFile) {
		this.configurationFile = configurationFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#setConnectionCacheName
	 * (java.lang.String)
	 */
	public void setConnectionCacheName(final String connectionCacheName)
			throws SQLException {
		ds.setConnectionCacheName(connectionCacheName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.javautil.oracle.datasource.ODSWrapperInterface#
	 * setConnectionCachingEnabled(boolean) todo why is this here?
	 */
	public void setConnectionCachingEnabled(
			final boolean connectionCachingEnabled) throws SQLException {
		ds.setConnectionCachingEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#setConnectionWaitTimeout
	 * (int)
	 */
	public void setConnectionWaitTimeout(final Integer connectionWaitTimeout)
			throws SQLException {
		setProperty(ConnectionWaitTimeout, connectionWaitTimeout);
	}

	public void setInactivityTimeout(final Integer inactivityTimeout)
			throws SQLException {
		setPropertyGreaterThanOrEqualToOne(InactivityTimeout, inactivityTimeout);

	}

	/**
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#setInitialLimit(java.lang.Integer)
	 */
	public void setInitialLimit(final Integer initialLimit) throws SQLException {
		if (initialLimit != null) {
			final int lim = initialLimit.intValue();
			if (lim < 0) {
				throw new IllegalArgumentException(
						"initialLimit must be >= 0 not: " + lim);
			}
			setProperty(InitialLimit, lim);

		}
	}

	public void setLock(final Object _lock) {
		if (_lock == null) {
			throw new IllegalArgumentException("_lock is null");
		}
		if (lock != null && _lock != lock) {
			throw new IllegalArgumentException(
					"lock is already set invalid key passed " + _lock
							+ " expected " + lock);
		}
		lock = _lock;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#setLoginTimeout(int)
	 */
	public void setLoginTimeout(final int loginTimeout) {
		ds.setLoginTimeout(loginTimeout);
	}

	public void setLoginTimeout(final Integer loginTimeout) {
		ds.setLoginTimeout(loginTimeout);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.javautil.oracle.datasource.ODSWrapperInterface#
	 * setAbandonedConnectionTimeout(int)
	 */

	public void setLogWriter(final PrintWriter out) {
		ds.setLogWriter(out);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#setLowerThresholdLimit
	 * (int)
	 */
	public void setLowerThresholdLimit(final int lowerLimit)
			throws SQLException {
		if (lowerLimit < 0) {
			throw new IllegalArgumentException(
					"lowerThresholdLimit must be >0 not: " + lowerLimit);
		}
		setProperty(LowerThresholdLimit, lowerLimit);
	}

	public void setLowerThresholdLimit(final Integer lowerLimit)
			throws SQLException {
		setProperty(LowerThresholdLimit, lowerLimit);

	}

	public void setMaxLimit(final Integer maxLimit) throws SQLException {
		setPropertyGreaterThanOrEqualToOne(MaxLimit, maxLimit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#setMaxStatementsLimit
	 * (java.lang.Integer)
	 */
	public void setMaxStatementsLimit(final Integer maxStatementsLimit)
			throws SQLException {
		if (maxStatementsLimit != null) {
			final int val = maxStatementsLimit.intValue();
			if (val < 1) {
				throw new IllegalArgumentException(
						"maxStatementLimit must be >= 1 not: " + val);
			}
			setProperty(MaxStatementsLimit, maxStatementsLimit);
		}

	}

	/**
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#setMinLimit(int)
	 */
	public void setMinLimit(final Integer minLimit) throws SQLException {
		if (minLimit != null) {
			final int val = minLimit.intValue();

			if (val < 0) {
				throw new IllegalArgumentException(
						"minLimit must be >= 0 not: " + val);
			}
			setProperty(MinLimit, val);
		}
	}

	/**
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#setOds(oracle.jdbc.pool.OracleDataSource)
	 */
	public void setOds(final OracleDataSource ods) {
		this.ds = ods;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#setPassword(java.
	 * lang.String)
	 */
	public void setPassword(final String password) {
		ds.setPassword(password);
	}

	public void setPortNumber(final String portNumber) {
		int pn;

		try {
			pn = Integer.parseInt(portNumber);
		} catch (final NumberFormatException nfe) {
			throw new IllegalArgumentException(PORT_NUMBER_MESSAGE + " "
					+ portNumber);
		}
		setPortNumber(pn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#setPortNumber(int)
	 */
	public void setPortNumber(final int portNumber) {
		ds.setPortNumber(portNumber);
	}

	// public void setPortNumber(final Integer portNumber) {
	// if (portNumber == null) {
	// throw new IllegalArgumentException("portNumber may not be null");
	// }
	// ds.setPortNumber(portNumber.intValue());
	//
	// }

	private void setProperty(final String propertyName, final boolean value)
			throws SQLException {
		setProperty(propertyName, Boolean.toString(value));

	}

	private void setProperty(final String propertyName, final int value)
			throws SQLException {
		setProperty(propertyName, Integer.toString(value));

	}

	private void setProperty(final String propertyName, final Integer value)
			throws SQLException {
		if (value != null) {
			setProperty(propertyName, value.toString());
		}

	}

	private void setProperty(final String propertyName, final String value)
			throws SQLException {
		final Boolean isCacheProperty = cachePropertyType.get(propertyName);
		if (isCacheProperty == null) {
			throw new IllegalArgumentException("unmapped property type "
					+ propertyName);
		}
		if (value != null) {

			if (isCacheProperty.booleanValue()) {
				cacheProperties.put(propertyName, value);
				ds.setConnectionCacheProperties(cacheProperties);
				logger.debug("setting connectionCacheProperty " + propertyName
						+ " to " + value);
			} else {
				connectionProperties.put(propertyName, value);
				ds.setConnectionProperties(connectionProperties);
				logger.debug("setting connectionProperty " + propertyName
						+ " to " + value);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#setPropertyCheckInterval
	 * (int)
	 */
	public void setPropertyCheckInterval(final Integer propertyCheckInterval)
			throws SQLException {
		setProperty(PropertyCheckInterval, propertyCheckInterval);
	}

	public void setPropertyGreaterThanOrEqualToOne(final String propertyName,
			final Integer value) throws SQLException {
		if (value != null) {
			final int val = value.intValue();
			if (val < 1) {
				throw new IllegalArgumentException("value must be >= 1 not: "
						+ val);
			}
			setProperty(propertyName, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#setServerName(java
	 * .lang.String)
	 */
	public void setServerName(final String serverName) {
		ds.setServerName(serverName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.oracle.datasource.ODSWrapperInterface#setServiceName(java
	 * .lang.String)
	 */
	public void setServiceName(final String serviceName) {
		ds.setServiceName(serviceName);
	}

	public void setTimeToLiveTimeout(final Integer value) throws SQLException {
		setPropertyGreaterThanOrEqualToOne(TimeToLiveTimeout, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.oracle.datasource.ODSWrapperInterface#setUser(java.lang
	 * .String)
	 */
	public void setUser(final String user) {
		ds.setUser(user);
	}

	public void setUseThinConnectionType(final boolean val) {
		ds.setDriverType(val ? "thin" : "oci");
	}

	// /**
	// * No facility is provided for using any other procedure as this would
	// allow somebody to pass a parm
	// * and bypass the DBA procedure by writing their own that authenticates
	// anybody, or records passwords.
	// * @param userName
	// * @param password
	// * @return
	// * @throws SQLException
	// */
	// // public String authenticateUser(String userName, String password)
	// throws SQLException {
	//
	// }

	public void setValidateConnection(final boolean validate)
			throws SQLException {
		setProperty(ValidateConnection, validate);
	}

	public <T> T unwrap(final Class<T> iface) throws SQLException {
		return ds.unwrap(iface);

	}

	private void validateLockKey(final Object key) {
		if (key == null) {
			throw new IllegalArgumentException("key is null");
		}
		if (lock == null) {
			throw new UnsupportedOperationException(
					"not locked must be locked with call to setLock previously");
		}
		if (lock != key) {
			throw new IllegalArgumentException("key does not match lock");
		}
	}

	public String getUrl() throws NamingException {
		return getUrl(ds);
	}

	public static String getUrl(final OracleDataSource ds) {
		Reference reference;
		String retval;
		try {
			reference = ds.getReference();
			retval = reference.get("url").toString();
		} catch (final NamingException e) {
			retval = e.getMessage();
		}

		return retval;

	}

	public static String asString(final OracleDataSource ds)
			throws SQLException {
		final StringBuilder b = new StringBuilder();

		// todo should be text name value helper,where did I put that

		// todo reuse
		final StringBuilderHelper sbh = new StringBuilderHelper(b);
		b.append("Connection Values" + newline);
		sbh.addNameValue("DatabaseName", ds.getDatabaseName());
		sbh.addNameValue("DataSourceName", ds.getDataSourceName());
		sbh.addNameValue("DriverType", ds.getDriverType());
		sbh.addNameValue("ExplicitCachingEnabled",
				ds.getExplicitCachingEnabled());
		sbh.addNameValue("ImplicitCachingEnabled",
				ds.getImplicitCachingEnabled());
		sbh.addNameValue("MaxStatements", ds.getMaxStatements());
		sbh.addNameValue("NetworkProtocol", ds.getNetworkProtocol());
		sbh.addNameValue("PortNumber", ds.getPortNumber());
		sbh.addNameValue("User", ds.getUser());
		sbh.addNameValue("ServerName", ds.getServerName());
		sbh.addNameValue("TNSEntryName", ds.getTNSEntryName());
		b.append("Cache Values" + newline);
		sbh.addNameValue("ConnectionCacheName", ds.getConnectionCacheName());
		sbh.addNameValue("ConnectionCachingEnabled",
				ds.getConnectionCachingEnabled());
		sbh.addNameValue("Description", ds.getDescription());
		sbh.addNameValue("LogWriter", ds.getLogWriter() == null);
		sbh.addNameValue("ONSConfiguration", ds.getONSConfiguration());
		sbh.addNameValue("url: ", getUrl(ds));
		sbh.addNameValue("FastConnectionFailoverEnabled",
				ds.getFastConnectionFailoverEnabled());
		sbh.addNameValue("LoginTimeout", ds.getLoginTimeout());
		b.append(PropertiesHelper.asText("CacheProperties",
				ds.getConnectionCacheProperties()));
		b.append(PropertiesHelper.asText("ConnectionProperties",
				ds.getConnectionProperties()));
		return b.toString();

	}

	public void close() throws SQLException {
		ds.close();

	}

	@Override
	protected void assertRequiredProperties() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initialize() {
		final Map<String, String> props = super.getProperties();

		for (final String propertyName : props.keySet()) {
			final String value = props.get(propertyName);

			final Boolean isCacheProperty = cachePropertyType.get(propertyName);
			if ("password".equalsIgnoreCase(propertyName)) {
				ds.setPassword(value);
			} else if ("user".equalsIgnoreCase(propertyName)) {
				ds.setUser(value);
			} else if ("PortNumber".equalsIgnoreCase(propertyName)) {
				setPortNumber(value);
			} else if ("ServerName".equalsIgnoreCase(propertyName)) {
				setServerName(value);
			} else if ("ServiceName".equalsIgnoreCase(propertyName)) {
				setServiceName(value);
			}

			else if (isCacheProperty == null) {

				throw new IllegalArgumentException("unknown property type "
						+ propertyName);
			} else {
				if (value != null) {

					if (isCacheProperty.booleanValue()) {
						cacheProperties.put(propertyName, value);
						try {
							ds.setConnectionCacheProperties(cacheProperties);
						} catch (final SQLException e) {
							throw new RuntimeException(e);
						}
						// System.out.println("setting connectionCacheProperty "
						// + propertyName + " to " + value);
						logger.debug("setting connectionCacheProperty "
								+ propertyName + " to " + value);
					} else {
						connectionProperties.put(propertyName, value);
						try {
							ds.setConnectionProperties(connectionProperties);
						} catch (final SQLException e) {
							throw new RuntimeException(e);
						}
						// System.out.println("setting connectionProperty "
						// + propertyName + " to " + value);
						logger.debug("setting connectionProperty "
								+ propertyName + " to " + value);
					}
				} else {
					throw new IllegalArgumentException(
							"no value for property '" + propertyName + "'");
				}
			}
		}
	}

	public void processArguments(final OracleDataSourceWrapperArguments args)
			throws SQLException, DataSourceInstantiationException {
		// setAbandonedConnectionCallback(args.g)
		setAbandonedConnectionTimeout(args.getAbandonedConnectionTimeout());
		setCallback(args.getCallbackClassName());
		setConnectionCacheName(args.getDatasourceName());
		setConnectionCachingEnabled(true);
		setConnectionWaitTimeout(args.getConnectionWaitTimeout());
		setInactivityTimeout(args.getInactivityTimeout());
		setInitialLimit(args.getInitialLimit());
		setLock(args);
		setLoginTimeout(args.getLoginTimeout());
		setLogWriter(args.getLogWriter());
		setLowerThresholdLimit(args.getLowerThresholdLimit());
		setMaxLimit(args.getMaxLimit());
		setMaxStatementsLimit(args.getMaxStatementsLimit());
		setMinLimit(args.getMinLimit());
		setName(args.getDatasourceName());
		setPassword(args.getPassword());
		setPortNumber(args.getPortNumber());
		setServerName(args.getServiceName());
		setServiceName(args.getServiceName());

	}
}
