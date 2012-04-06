package com.dbexperts.oracle.datasource;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.WeakHashMap;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.sql.DataSource;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleConnectionCacheCallback;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;

import com.dbexperts.datasources.DataSourceInstantiationException;
import com.dbexperts.jdbc.ConnectionCacheEntry;
import com.dbexperts.jdbc.DataSourceWrapper;
import com.dbexperts.jdbc.InvalidDataSourceException;
import com.dbexperts.misc.Named;
import com.dbexperts.misc.string.StringBuilderHelper;
import com.dbexperts.oracle.WrappedOracleConnection;
import com.dbexperts.properties.PropertiesHelper;
import com.dbexperts.security.AuthenticationException;

/**
 // todo implmement
 7.4.3.1 AttributeWeights

 See "AttributeWeights" .
 7.4.3.2 ClosestConnectionMatch

 See "ClosestConnectionMatch" .
 7.4.3.3 ConnectionWaitTimeout

 Specifies cache behavior when a connection is requested and there are already MaxLimit connections active. If ConnectionWaitTimeout is greater than zero (0), each connection request waits for the specified number of seconds, or until a connection is returned to the cache. If no connection is returned to the cache before the timeout elapses, the connection request returns null.

 Default: 0 (no timeout)
 7.4.3.4 LowerThresholdLimit

 Sets the lower threshold limit on the cache. The default is 20% of the MaxLimit on the connection cache. This property is used whenever a releaseConnection() cache callback method is registered. For details, see "Connection Cache Callbacks" .
 7.4.3.5 ValidateConnection

 Setting ValidateConnection to true causes the connection cache to test every connection it retrieves against the underlying database.

 Default: false
 */

/**
 * @author jjs@dbexperts.com
 *
 */
@SuppressWarnings("boxing")
public class OracleDataSourceWrapper implements DataSourceWrapper, ODSWrapperInterface,  DataSource, Named {
	private static String newline = System.getProperty("line.separator");

	public static final String INVALID_AUTHENTICATION = "attempt to get connection with invalidAuthenticationString";

	public static final String TICKET_REQUIRED = "you must provide an authentication ticket";
	// private Properties properties = new Properties();

	private static final TreeMap<String, Boolean> cachePropertyType = new TreeMap<String, Boolean>();

	static {
		cachePropertyType.put(AbandonedConnectionTimeout, true);
		cachePropertyType.put(ConnectionWaitTimeout, true);
		cachePropertyType.put(InitialLimit, true);
		cachePropertyType.put(InactivityTimeout, true);
		cachePropertyType.put(LowerThresholdLimit, true);
		cachePropertyType.put(MaxLimit, true);
		cachePropertyType.put(MaxStatementsLimit, true);
		cachePropertyType.put(MinLimit, true);
		cachePropertyType.put(PropertyCheckInterval, true);
		cachePropertyType.put(TimeToLiveTimeout, true);
		cachePropertyType.put(ValidateConnection, true);
		cachePropertyType.put(ServerName, false);
		cachePropertyType.put(PortNumber, false);
		cachePropertyType.put(User, false);
		cachePropertyType.put(ConnectionCachingEnabled, false);
		cachePropertyType.put(ServiceName, false);
		cachePropertyType.put(LoginTimeout, true);
	}
	@SuppressWarnings("boxing")
	// todo document every one of these fields in one location
	
	private final Logger logger = Logger.getLogger(getClass());

	private OracleDataSource ds;

	private final WeakHashMap<String, String> useridByTicket = new WeakHashMap<String, String>();

	private Object lock = null;
	private final Properties connectionProperties = new Properties();

	private final Properties cacheProperties = new Properties();

	private Class<OracleConnectionCacheCallback> callback;

	private boolean allowUnauthenticatedConnectionRequests = true;

	private String name;

	private File configurationFile;

	public OracleDataSourceWrapper() throws SQLException {
		ds = new OracleDataSource();
		ds.setDriverType("thin");
		ds.setConnectionProperties(connectionProperties);
	}

	public ArrayList<String> getValidateMessages() {
		ArrayList<String> messages = new ArrayList<String>();
		String driverType = ds.getDriverType();
		if (! ("thin".equals(driverType) || "oci".equals(driverType))) {
			messages.add("driverType: '" + driverType + "' is not 'thin' or 'oci'");
		}
		if (! (ds.getPortNumber() == 1521 || ds.getPortNumber() == 1526)) {
			messages.add("non standard port (1521,1526) : " + ds.getPortNumber());
		}
		if (ds.getServiceName() == null) {
			messages.add("serviceName is null");
		}
		if (ds.getTNSEntryName() == null && "oci".equals(driverType)) {
			messages.add("driverType is 'oci' and TNSEntryName is null");
		}
		if (ds.getServerName() == null && ds.getTNSEntryName() == null) {
			messages.add("serverName is null " + " for driverType " + driverType);
		}
		return messages;
		
	}
	
	public String authenticate(final String userName, final String password) throws AuthenticationException {
		final String sql = "? := web_authentication.is_user(?,?); end;";
		String isAuthenticated = "false";
		WrappedOracleConnection wconn = null;

		try {
			wconn = getWrappedConnection();
		} catch (final SQLException e) {
			throw new AuthenticationException(e);

		}

		try {
			final OracleCallableStatement ocs = (OracleCallableStatement) getWrappedConnection().prepareCall(sql);
			ocs.registerOutParameter(1, java.sql.Types.VARCHAR);
			ocs.setString(2, userName);
			ocs.setString(3, password);
			ocs.executeUpdate();
			isAuthenticated = ocs.getString(1);
			ocs.close();
		} catch (final SQLException sqe) {
			try {
				wconn.close();
			} catch (final SQLException e) {
				logger.error(e.getMessage());
			}
			throw new AuthenticationException(sqe);
		}

		if ("true".equals(isAuthenticated)) {
			final long now = System.nanoTime();
			String ticket = Long.toHexString(now) + "-" + userName;
			ticket = Integer.toHexString(ticket.hashCode());
			useridByTicket.put(ticket, userName);
			return ticket;
		}
		throw new AuthenticationException("nope");
	}

	/**
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getAbandonedConnectionTimeout()
	 */
	public int getAbandonedConnectionTimeout() throws SQLException {

		return getIntProperty(AbandonedConnectionTimeout);

	}

	public OracleConnectionCacheCallback getCallback() {
		OracleConnectionCacheCallback retval = null;
		try {
			retval = callback.newInstance();
		} catch (final Exception e) {
			throw new IllegalStateException(e.getClass().getName() + " " + e.getMessage());
		}
		return retval;
	}

	/**
	 * @return the configurationFile
	 */
	public File getConfigurationFile() {
		return configurationFile;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getConnection()
	 */
	public WrappedOracleConnection getConnection() throws SQLException {
		if (!allowUnauthenticatedConnectionRequests) {
			throw new UnsupportedOperationException(TICKET_REQUIRED);

		}
		return getWrappedConnection();
	}

	public WrappedOracleConnection getConnection(final String authenticationString) throws SQLException, AuthenticationException {
		final String userName = useridByTicket.get(authenticationString);
		if (userName == null) {
			final String message = INVALID_AUTHENTICATION + authenticationString;
			// logger.error(message + new CallStack().toString());
			throw new AuthenticationException(message);
		}
		final WrappedOracleConnection oconn = getConnection();
		oconn.setSecurity(lock, userName);
		return getConnection();
	}

	public Connection getConnection(final String username, final String password) {
		throw new UnsupportedOperationException();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getConnectionCacheName()
	 */
	public String getConnectionCacheName() throws SQLException {
		return ds.getConnectionCacheName();
	}

	public Properties getConnectionCacheProperties() throws SQLException {
		return ds.getConnectionCacheProperties();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getPropertiesText()
	 */
	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
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

	public String getConnectionType() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getDataSource()
	 */
	public OracleDataSource getDataSource() {
		return ds;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getConnectionType()
	 */
	public String getDriverType() {
		return ds.getDriverType();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getInactivityTimeout()
	 */
	public int getInactivityTimeout() throws SQLException {
		return getIntProperty(InactivityTimeout);
	}

	/**
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getInitialLimit()
	 */
	public int getInitialLimit() throws SQLException {
		return getIntProperty(InitialLimit);

	}

	private Integer getIntProperty(final String propertyName) throws SQLException {
		final Boolean isCacheProperty = cachePropertyType.get(propertyName);
		if (isCacheProperty == null) {
			throw new IllegalArgumentException("unmapped property type " + propertyName);
		}
		final Properties p = isCacheProperty ? ds.getConnectionCacheProperties() : ds.getConnectionProperties();

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
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getLoginTimeout()
	 */
	public int getLoginTimeout() {
		return ds.getLoginTimeout();
	}

	public PrintWriter getLogWriter() {
		return ds.getLogWriter();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getLowerThresholdLimit()
	 */
	public Integer getLowerThresholdLimit() throws SQLException {
		return getIntProperty(LowerThresholdLimit);

	}

	/**
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getMaxLimit()
	 */
	public int getMaxLimit() throws SQLException {
		return getIntProperty(MaxLimit);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getMaxStatementsLimit()
	 */
	public int getMaxStatementsLimit() throws SQLException {
		return getIntProperty(MaxStatementsLimit);
	}

	/**
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getMinLimit()
	 */
	public int getMinLimit() throws SQLException {
		return getIntProperty("MinLimit");
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	// 7.6.2 Connection Cache Callbacks
	//
	// The implicit connection cache offers a way for the application to specify
	// callbacks to be invoked by the connection cache. Callback methods are
	// supported with the OracleConnectionCacheCallback interface. This callback
	// mechanism is useful to take advantage of the application's special
	// knowledge of particular connections, supplementing the default behavior
	// when handling abandoned connections or when the cache is empty.
	//
	// OracleConnectionCacheCallback is an interface that must be implemented by
	// the user and registered with OracleConnection. The registration API is:
	//
	// public void
	// registerConnectionCacheCallback(
	// OracleConnectionCacheCallback cbk, Object usrObj, int cbkflag);
	//
	//
	// In this interface, cbk is the user's implementation of the
	// OracleConnectionCacheCallback interface.
	/*
	 * The usrObj parameter contains any parameters that the user wants
	 * supplied. This user object is passed back, unmodified, when the callback
	 * method is invoked. The cbkflag parameter specifies which callback method
	 * should be invoked. It must be one of the following values:
	 */
	//
	// *
	//
	// OracleConnection.ABANDONED_CONNECTION_CALLBACK
	// *
	//
	// OracleConnection.RELEASE_CONNECTION_CALLBACK
	// *
	//
	// OracleConnection.ALL_CALLBACKS
	//
	// When ALL_CALLBACKS is set, all the connection cache callback methods are
	// invoked. For example,
	//
	// // register callback, to invoke all callback methods
	// ((OracleConnection)conn).registerConnectionCacheCallback( new
	// UserConnectionCacheCallback(),
	// new SomeUserObject(),
	// OracleConnection.ALL_CALLBACKS);
	//
	//
	// An application can register a ConnectionCacheCallback on an
	// OracleConnection. When a callback is registered, the connection cache
	// calls the callback's handleAbandonedConnection() before reclaiming the
	// connection. If the callback returns true, the connection is reclaimed. If
	// the callback returns false, the connection remains active. For details,
	// see "Connection Cache Manager API" .
	//
	// The UserConnectionCacheCallback interface supports two callback methods
	// to be implemented by the user, releaseConnection() and
	// handleAbandonedConnection(). For details on these methods, see the
	// Javadoc.
	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getOds()
	 */
	public DataSource getOds() {
		return ds;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getOracleDataSource()
	 */
	public DataSource getOracleDataSource() {
		return ds;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getPortNumber()
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
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getPropertyCheckInterval()
	 */
	public int getPropertyCheckInterval() throws SQLException {
		return getIntProperty(PropertyCheckInterval);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getServerName()
	 */
	public String getServerName() {
		return ds.getServerName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getServiceName()
	 */
	public String getServiceName() {
		return ds.getServiceName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getTimeToLiveTimeout()
	 */
	public int getTimeToLiveTimeout() throws SQLException {
		return getIntProperty(TimeToLiveTimeout);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getUser()
	 */
	public String getUser() {
		return ds.getUser();
	}

	/**
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#getConnection()
	 */
	private WrappedOracleConnection getWrappedConnection() throws SQLException, InvalidDataSourceException {

		OracleConnection oconn = null;
		try {
			oconn = (OracleConnection) ds.getConnection();
		} catch (final SQLException sqe) {
			ArrayList<String> messages = getValidateMessages();
			StringBuilder sb = new StringBuilder();
			for (String message : getValidateMessages()) {
				sb.append(message);
				sb.append(newline);
			}
			sb.append(getUrl(ds));
//			sb.append("name: " + name + newline);
//			sb.append("configurationFile: " + configurationFile + newline);
//			sb.append(asString(ds));
//			throw new InvalidDataSourceException(sqe,"name is '" + name + "'" + newline +
// 					"' configuration file : '" + configurationFile + "'" + newline +
//					asString(ds));
			throw new InvalidDataSourceException(sqe,newline + sb.toString());

		}
		final WrappedOracleConnection woc = WrappedOracleConnection.getInstance(oconn);
		if (callback != null) {
			final long now = System.nanoTime();
			final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			final ConnectionCacheEntry cce = new ConnectionCacheEntry(woc, this, 5000, stack);
			final OracleConnectionCacheCallback occ = getCallback();
			oconn.registerConnectionCacheCallback(occ, cce, OracleConnection.ABANDONED_CONNECTION_CALLBACK);
		}

		return woc;
	}

	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return ds.isWrapperFor(iface);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setAbandonedConnectionCallback(oracle.jdbc.pool.OracleConnectionCacheCallback)
	 */
	public void setAbandonedConnectionCallback(final OracleConnectionCacheCallback callback) {
		
	}

	public void setAbandonedConnectionTimeout(final Integer abandonedConnectionTimeout) throws SQLException {
		if (abandonedConnectionTimeout == null) {
			logger.warn("abandonedConnectionTimeout was null");
		} else {
			setProperty(AbandonedConnectionTimeout, abandonedConnectionTimeout.toString());
		}
	}

	public void setAllowUnauthenticatedConnectionRequests(final Object lockKey, final boolean value) {
		validateLockKey(lockKey);
		allowUnauthenticatedConnectionRequests = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setValidateConnection(boolean)
	 */

	@SuppressWarnings("unchecked")
	public void setCallback(final String connectionCallbackClassname) throws DataSourceInstantiationException {
		final Class<OracleConnectionCacheCallback> callbackClass = null;
		try {
			final Class c = Class.forName(connectionCallbackClassname);
			final Object o = c.newInstance();
			if (!(o instanceof OracleConnectionCacheCallback)) {
				throw new DataSourceInstantiationException(o.getClass().getName()
						+ " is not an instance of oracle.jdbc.pool.OracleConnectionCacheCallback");
			}
			callback = callbackClass;
		} catch (final ClassNotFoundException e) {
			throw new DataSourceInstantiationException("cannot locate class " + connectionCallbackClassname);
		} catch (final Exception e) {
			throw new DataSourceInstantiationException(e.getMessage() + " on class " + connectionCallbackClassname);
		}

	}

	/**
	 * @param configurationFile the configurationFile to set
	 */
	public void setConfigurationFile(final File configurationFile) {
		this.configurationFile = configurationFile;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setConnectionCacheName(java.lang.String)
	 */
	public void setConnectionCacheName(final String connectionCacheName) throws SQLException {
		ds.setConnectionCacheName(connectionCacheName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setConnectionCachingEnabled(boolean)
	 */
	public void setConnectionCachingEnabled(final boolean connectionCachingEnabled) throws SQLException {
		ds.setConnectionCachingEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setConnectionWaitTimeout(int)
	 */
	public void setConnectionWaitTimeout(final Integer connectionWaitTimeout) throws SQLException {
		setProperty(ConnectionWaitTimeout, connectionWaitTimeout);
		// TODO Auto-generated method stub

	}

	public void setInactivityTimeout(final Integer inactivityTimeout) throws SQLException {
		setPropertyGreaterThanOrEqualToOne(InactivityTimeout, inactivityTimeout);

	}

	/**
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setInitialLimit(java.lang.Integer)
	 */
	public void setInitialLimit(final Integer initialLimit) throws SQLException {
		if (initialLimit != null) {
			final int lim = initialLimit.intValue();
			if (lim < 0) {
				throw new IllegalArgumentException("initialLimit must be >= 0 not: " + lim);
			}
			setProperty(InitialLimit, lim);

		}
	}

	public void setLock(final Object _lock) {
		if (_lock == null) {
			throw new IllegalArgumentException("_lock is null");
		}
		if (lock != null && _lock != lock) {
			throw new IllegalArgumentException("lock is already set invalid key passed " + _lock + " expected " + lock);
		}
		lock = _lock;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setLoginTimeout(int)
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
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setAbandonedConnectionTimeout(int)
	 */

	public void setLogWriter(final PrintWriter out) {
		ds.setLogWriter(out);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setLowerThresholdLimit(int)
	 */
	public void setLowerThresholdLimit(final int lowerLimit) throws SQLException {
		if (lowerLimit < 0) {
			throw new IllegalArgumentException("lowerThresholdLimit must be >0 not: " + lowerLimit);
		}
		setProperty(LowerThresholdLimit, lowerLimit);
	}

	public void setLowerThresholdLimit(final Integer lowerLimit) throws SQLException {
		setProperty(LowerThresholdLimit, lowerLimit);

	}

	public void setMaxLimit(final Integer maxLimit) throws SQLException {
		setPropertyGreaterThanOrEqualToOne(MaxLimit, maxLimit);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setMaxStatementsLimit(java.lang.Integer)
	 */
	public void setMaxStatementsLimit(final Integer maxStatementsLimit) throws SQLException {
		if (maxStatementsLimit != null) {
			final int val = maxStatementsLimit.intValue();
			if (val < 1) {
				throw new IllegalArgumentException("maxStatementLimit must be >= 1 not: " + val);
			}
			setProperty(MaxStatementsLimit, maxStatementsLimit);
		}

	}

	/**
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setMinLimit(int)
	 */
	public void setMinLimit(final Integer minLimit) throws SQLException {
		if (minLimit != null) {
			final int val = minLimit.intValue();

			if (val < 0) {
				throw new IllegalArgumentException("minLimit must be >= 0 not: " + val);
			}
			setProperty(MinLimit, val);
		}
	}

	public void setName(final String dsName) {
		this.name = dsName;

	}

	/**
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setOds(oracle.jdbc.pool.OracleDataSource)
	 */
	public void setOds(final OracleDataSource ods) {
		this.ds = ods;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setPassword(java.lang.String)
	 */
	public void setPassword(final String password) {
		ds.setPassword(password);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setPortNumber(int)
	 */
	public void setPortNumber(final int portNumber) {
		ds.setPortNumber(portNumber);
	}

	public void setPortNumber(final Integer portNumber) {
		if (portNumber == null) {
			throw new IllegalArgumentException("portNumber may not be null");
		}
		ds.setPortNumber(portNumber.intValue());

	}

	private void setProperty(final String propertyName, final boolean value) throws SQLException {
		setProperty(propertyName, Boolean.toString(value));

	}

	private void setProperty(final String propertyName, final int value) throws SQLException {
		setProperty(propertyName, Integer.toString(value));

	}

	private void setProperty(final String propertyName, final Integer value) throws SQLException {
		if (value != null) {
			setProperty(propertyName, value.toString());
		}

	}

	private void setProperty(final String propertyName, final String value) throws SQLException {
		final Boolean isCacheProperty = cachePropertyType.get(propertyName);
		if (isCacheProperty == null) {
			throw new IllegalArgumentException("unmapped property type " + propertyName);
		}
		if (value != null) {

			if (isCacheProperty.booleanValue()) {
				cacheProperties.put(propertyName, value);
				ds.setConnectionCacheProperties(cacheProperties);
				logger.debug("setting connectionCacheProperty " + propertyName + " to " + value);
			} else {
				connectionProperties.put(propertyName, value);
				ds.setConnectionProperties(connectionProperties);
				logger.debug("setting connectionProperty " + propertyName + " to " + value);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setPropertyCheckInterval(int)
	 */
	public void setPropertyCheckInterval(final Integer propertyCheckInterval) throws SQLException {
		setProperty(PropertyCheckInterval, propertyCheckInterval);
	}

	public void setPropertyGreaterThanOrEqualToOne(final String propertyName, final Integer value) throws SQLException {
		if (value != null) {
			final int val = value.intValue();
			if (val < 1) {
				throw new IllegalArgumentException("value must be >= 1 not: " + val);
			}
			setProperty(propertyName, value);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setServerName(java.lang.String)
	 */
	public void setServerName(final String serverName) {
		ds.setServerName(serverName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setServiceName(java.lang.String)
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
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setUser(java.lang.String)
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


	public void setValidateConnection(final boolean validate) throws SQLException {
		setProperty(ValidateConnection, validate);
	}


	/*
	 * (non-Javadoc) @todo remove tests for null
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setValues(com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface)
	 */
	public void setValues(final OracleDataSourceWrapperValuesSourceInterface values) throws SQLException {
		final ArrayList<String> messages = new ArrayList<String>();
		logger.info("setting values to " + values.getValuesText());
		setAbandonedConnectionTimeout(values.getAbandonedConnectionTimeout());
		setInitialLimit(values.getInitialLimit().intValue());
		setMaxLimit(values.getMaxLimit().intValue());
		setMinLimit(values.getMinLimit().intValue());
		setMaxStatementsLimit(values.getMaxStatementsLimit().intValue());
		setInactivityTimeout(values.getInactivityTimeout().intValue());
		setPropertyCheckInterval(values.getPropertyCheckInterval().intValue());
		setTimeToLiveTimeout(values.getTimeToLiveTimeout().intValue());
		setConnectionCacheName(values.getConnectionCacheName());
		setServerName(values.getServerName());
		setPortNumber(values.getPortNumber().intValue());
		setUser(values.getUser());
		setServiceName(values.getServiceName());
		setUseThinConnectionType(values.getUseThinConnectionType());
		setLoginTimeout(values.getLoginTimeout());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setValuesAndTest(com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface)
	 */
	public void setValuesAndTest(final OracleDataSourceWrapperValuesSourceInterface values) throws SQLException {
		try {
			setValues(values);

		} catch (final SQLException sqe) {
			logger.error(sqe.getMessage());
			throw sqe;
		}

	}

	public <T> T unwrap(final Class<T> iface) throws SQLException {
		return ds.unwrap(iface);

	}

	private void validateLockKey(final Object key) {
		if (key == null) {
			throw new IllegalArgumentException("key is null");
		}
		if (lock == null) {
			throw new UnsupportedOperationException("not locked must be locked with call to setLock previously");
		}
		if (lock != key) {
			throw new IllegalArgumentException("key does not match lock");
		}
	}
	
	public String getUrl() throws NamingException {
		return getUrl(ds);
	}
	
	public static String getUrl(final OracleDataSource ds)  {
		Reference reference;
		String retval ;
		try {
			reference = ds.getReference();
			retval = reference.get("url").toString();
		} catch (NamingException e) {
			retval = e.getMessage();
		}
	
		return retval;

	}
	
	public static String asString(final OracleDataSource ds) throws SQLException {
		final StringBuilder b = new StringBuilder();
		final StringBuilderHelper sbh = new StringBuilderHelper(b);
		//
		//
		b.append("Connection Values" + newline);
		sbh.addNameValue("DatabaseName", ds.getDatabaseName());
		sbh.addNameValue("DataSourceName", ds.getDataSourceName());
		sbh.addNameValue("DriverType", ds.getDriverType());
		sbh.addNameValue("ExplicitCachingEnabled", ds.getExplicitCachingEnabled());
		sbh.addNameValue("ImplicitCachingEnabled", ds.getImplicitCachingEnabled());
		sbh.addNameValue("MaxStatements", ds.getMaxStatements());
		sbh.addNameValue("NetworkProtocol", ds.getNetworkProtocol());
		sbh.addNameValue("PortNumber", ds.getPortNumber());
		sbh.addNameValue("User", ds.getUser());
		sbh.addNameValue("ServerName", ds.getServerName());
		sbh.addNameValue("TNSEntryName", ds.getTNSEntryName());
//		String url = null;
//		try {
//			url = ds.getURL();
//		} catch (final Exception e) {
//			url = e.getMessage();
//		}
//		sbh.addNameValue("URL", url);
		//
		//
		b.append("Cache Values" + newline);
		sbh.addNameValue("ConnectionCacheName", ds.getConnectionCacheName());
		sbh.addNameValue("ConnectionCachingEnabled", ds.getConnectionCachingEnabled());
		sbh.addNameValue("Description", ds.getDescription());
		sbh.addNameValue("LogWriter", ds.getLogWriter() == null);
		sbh.addNameValue("ONSConfiguration", ds.getONSConfiguration());

		b.append("url: " + getUrl(ds));
	
//		b.append("== Reference ==" + newline);
//		try {
//			final Reference reference = ds.getReference();
//			b.append("url: '" + reference.get("url") + "'" + newline);
//			sbh.addNameValue("Reference", reference);
//		} catch (final Exception e) {
//			final String reference = e.getMessage();
//			sbh.addNameValue("Reference", reference);
//		}
//		b.append("== End Reference ==" + newline);
		sbh.addNameValue("FastConnectionFailoverEnabled", ds.getFastConnectionFailoverEnabled());
		sbh.addNameValue("LoginTimeout", ds.getLoginTimeout());
		b.append(PropertiesHelper.asText("CacheProperties", ds.getConnectionCacheProperties()));
		b.append(PropertiesHelper.asText("ConnectionProperties", ds.getConnectionProperties()));
		return b.toString();

	}

	public void close() throws SQLException {
		ds.close();
		
	}
}
