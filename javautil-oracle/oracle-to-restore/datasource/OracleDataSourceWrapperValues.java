package com.dbexperts.oracle.datasource;

import java.sql.SQLException;

import oracle.jdbc.pool.OracleConnectionCacheCallback;

/**
 * A container for the canonical representation of information necessary to
 * properly initialize an OracleDataSourceWrapper.
 *
 * @author jjs@dbexperts.com
 *
 */
public class OracleDataSourceWrapperValues implements OracleDataSourceWrapperValuesSourceInterface {


	private final String			newline = System.getProperty("line.separator");
	private String				ConnectionCacheName;

	private String				serverName;

	private Integer				portNumber;

	private String				user;

	private String				password;

	/**
	 * Sets the lower threshold limit on the cache. The default is 20% of the
	 * MaxLimit on the connection cache. This property is used whenever a
	 * releaseConnection() cache callback method is registered. For details, see
	 * "Connection Cache Callbacks" .
	 */
	private boolean				connectionCachingEnabled;

	private String				serviceName;

	//private String				connectionType;

	/**
	 Sets the lower threshold limit on the cache.
	 The default is 20% of the MaxLimit on the connection cache.
	 This property is used whenever a releaseConnection() cache callback method is registered.
	 For details, see "Connection Cache Callbacks" .
	*/
	private Integer lowerThresholdLimit;

	// private int connectionTimeout;
	private Integer					loginTimeout;


	/**
	 *
	 *
	 * Sets the maximum time that a connection can remain unused before the
	 * connection is closed and returned to the cache. A connection is
	 * considered unused if it has not had SQL database activity.
	 *
	 * When AbandonedConnectionTimeout is set, JDBC monitors SQL database
	 * activity on each logical connection. For example, when stmt.execute() is
	 * invoked on the connection, a heartbeat is registered to convey that this
	 * connection is active. The heartbeats are set at each database execution.
	 * If a connection has been inactive for the specified amount of time, the
	 * underlying connection is reclaimed and returned to the cache for reuse.
	 *
	 * Default: 0 (no timeout in effect)
	 */
	private Integer					abandonedConnectionTimeout;

	/**
	 * Sets how many connections are created in the cache when it is created or
	 * reinitialized.
	 *
	 * When this property is set to an integer value greater than 0, creating or
	 * reinitializing the cache automatically creates the specified number of
	 * connections, filling the cache in advance of need.
	 *
	 * Default: 0
	 */
	private Integer					initialLimit;

	/**
	 * Sets the maximum number of connection instances the cache can hold.
	 *
	 * The default value is Integer.MAX_VALUE, meaning that there is no limit
	 * enforced by the connection cache, so that the number of connections is
	 * limited only by the number of database sessions configured for the
	 * database.
	 */
	private Integer					maxLimit;

	/**
	 * Sets the minimum number of connections the cache maintains.
	 *
	 * This guarantees that the cache will not shrink below this minimum limit.
	 * Setting the MinLimit property does not initialize the cache to contain
	 * the minimum number of connections. To do this, use the InitialLimit
	 * property
	 */
	private Integer					minLimit;

	/**
	 * Set the maximum number of statements that a connection keeps open.
	 *
	 * When a cache has this property set, reinitializing the cache or closing
	 * the datasource automatically closes all cursors beyond the specified
	 * MaxStatementsLimit.
	 *
	 * @todo demonstrate performance improvements with caching statements
	 * @todo show problems with tracing while caching statements Default: 0
	 */
	private Integer					maxStatementsLimit;

	/**
	 *
	 * Sets the maximum time a physical connection can remain idle in a
	 * connection cache. An idle connection is one that is not active and does
	 * not have a logical handle associated with it. When InactivityTimeout
	 * expires, the underlying physical connection is closed. However, the size
	 * of the cache is not allowed to shrink below minLimit, if has been set.
	 */
	private Integer					inactivityTimeout;

	/**
	 * Sets the time interval at which the cache manager inspects and enforces
	 * all specified cache properties. PropertyCheckInterval is set in seconds.
	 *
	 * Default: 900 seconds (15 minutes)
	 */
	private Integer					propertyCheckInterval;

	/**
	 * Sets the maximum time in seconds that a logical connection can remain
	 * open. When TimeToLiveTimeout expires, the logical connection is
	 * unconditionally closed, the relevant statement handles are canceled, and
	 * the underlying physical connection is returned to the cache for reuse.
	 */
	private Integer					timeToLiveTimeout;

	private boolean	validateConnection;

	private String	description;

	private OracleConnectionCacheCallback callback;

	private		boolean useThinConnection;

    private Integer connectionWaitTimeout;

	private boolean	useThinConnectionType;

    public OracleDataSourceWrapperValues() {

	}

	public OracleDataSourceWrapperValues (final OracleDataSourceWrapperValuesSourceInterface vals) {
		final OracleDataSourceWrapperValues retval = new OracleDataSourceWrapperValues();
		retval.setServerName(vals.getServerName());
		retval.setPortNumber(vals.getPortNumber());
		retval.setUser(vals.getUser());
		//vals.setPassword(getPassword());
		retval.setConnectionCachingEnabled(vals.getConnectionCachingEnabled());
		retval.setServiceName(vals.getServiceName());
		retval.setLowerThresholdLimit(vals.getLowerThresholdLimit());
		retval.setLoginTimeout(vals.getLoginTimeout());
		retval.setAbandonedConnectionTimeout(vals.getAbandonedConnectionTimeout());
		retval.setInitialLimit(vals.getInitialLimit());
		retval.setMaxLimit(vals.getMaxLimit());
	}
	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#setAbandonedConnectionCallback(oracle.jdbc.pool.OracleConnectionCacheCallback)
	 */
	public void setAbandonedConnectionCallback(final OracleConnectionCacheCallback callback) {
		this.callback = callback;
	}



	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getAbandonedConnectionTimeout()
	 */
	public Integer getAbandonedConnectionTimeout() {
		return abandonedConnectionTimeout;
	}

	/**
	 * @param abandonedConnectionTimeout
	 *            the abandonedConnectionTimeout to set
	 */
	public void setAbandonedConnectionTimeout(final int abandonedConnectionTimeout) {
		this.abandonedConnectionTimeout = abandonedConnectionTimeout;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getInitialLimit()
	 */
	public Integer getInitialLimit() {
		return initialLimit;
	}

	/**
	 * @param initialLimit
	 *            the initialLimit to set
	 *
	 * Sets how many connections are created in the cache when it is created or
	 * reinitialized.
	 *
	 * When this property is set to an integer value greater than 0, creating or
	 * reinitializing the cache automatically creates the specified number of
	 * connections, filling the cache in advance of need.
	 *
	 * Default: 0
	 */
	public void setInitialLimit(final int initialLimit) {
		if (initialLimit < 0) {
			throw new IllegalArgumentException("initialLimit must be >= 0 not: " + initialLimit);
		}

		this.initialLimit = initialLimit;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getMaxLimit()
	 */
	public Integer getMaxLimit() {
		return maxLimit;
	}

	/**
	 * @param maxLimit
	 *            the maxLimit to set * Sets the maximum number of connection
	 *            instances the cache can hold.
	 *
	 * The default value is Integer.MAX_VALUE, meaning that there is no limit
	 * enforced by the connection cache, so that the number of connections is
	 * limited only by the number of database sessions configured for the
	 * database.
	 */
	public void setMaxLimit(final int maxLimit) {
		if (maxLimit <= 1) {
			throw new IllegalArgumentException("maxLimit must be >= 1 not: " + maxLimit);
		}

		this.maxLimit = maxLimit;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getMinLimit()
	 */
	public Integer getMinLimit() {
		return minLimit;
	}

	/**
	 * @param minLimit
	 *            the minLimit to set
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapper#setMinLimit(Integer)
	 */
	public void setMinLimit(final int minLimit) {
		if (minLimit < 0) {
			throw new IllegalArgumentException("minLimit must be >= 0 not: " + minLimit );
		}

		this.minLimit = minLimit;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getMaxStatementsLimit()
	 */
	public Integer getMaxStatementsLimit() {
		return maxStatementsLimit;
	}

	/**
	 * @param maxStatementsLimit
	 *            the maxStatementsLimit to set
	 */
	public void setMaxStatementsLimit(final int maxStatementsLimit) {
		this.maxStatementsLimit = maxStatementsLimit;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getInactivityTimeout()
	 */
	public Integer getInactivityTimeout() {
		return inactivityTimeout;
	}

	/**
	 * @param inactivityTimeout
	 *            the inactivityTimeout to set
	 */
	public void setInactivityTimeout(final int inactivityTimeout) {
		if (inactivityTimeout < 0) {
			throw new IllegalArgumentException("inactivityTimeout must be >= 0 not: " + inactivityTimeout);
		}

		this.inactivityTimeout = inactivityTimeout;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getPropertyCheckInterval()
	 */
	public Integer getPropertyCheckInterval() {
		return propertyCheckInterval;
	}

	/**
	 * @param propertyCheckInterval
	 *            the propertyCheckInterval to set
	 */
	public void setPropertyCheckInterval(final int propertyCheckInterval) {
		this.propertyCheckInterval = propertyCheckInterval;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getTimeToLiveTimeout()
	 */
	public Integer getTimeToLiveTimeout() {
		return timeToLiveTimeout;
	}

	/**
	 * @param timeToLiveTimeout
	 *            the timeToLiveTimeout to set
	 */
	public void setTimeToLiveTimeout(final int timeToLiveTimeout) {
		this.timeToLiveTimeout = timeToLiveTimeout;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getConnectionCacheName()
	 */
	public String getConnectionCacheName() {
		return ConnectionCacheName;
	}

	/**
	 * @param connectionCacheName
	 *            the connectionCacheName to set
	 */
	public void setConnectionCacheName(final String connectionCacheName) {
		ConnectionCacheName = connectionCacheName;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getServerName()
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName
	 *            the serverName to set
	 */
	public void setServerName(final String serverName) {
		this.serverName = serverName;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getPortNumber()
	 */
	public Integer getPortNumber() {
		return portNumber;
	}

	/**
	 * @param portNumber
	 *            the portNumber to set
	 *
	 * This is the IP port on which the database is listening for connections.
	 */
	public void setPortNumber(final int portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * @param password
	 *            the password to set
	 *
	 * The password associated with the specified user for the database.
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	public boolean getConnectionCachingEnabled() {
		return connectionCachingEnabled;
	}
	/**
	 * @param connectionCachingEnabled
	 *            the connectionCachingEnabled to set
	 * @throws SQLException
	 */
	public void setConnectionCachingEnabled(final boolean connectionCachingEnabled)  {
		this.connectionCachingEnabled = connectionCachingEnabled;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getUser()
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(final String user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getServiceName()
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	public void setServiceName(final String serviceName) {
		this.serviceName = serviceName;
	}

//	/* (non-Javadoc)
//	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getConnectionType()
//	 */
//	public String getConnectionType() {
//		return connectionType;
//	}


	public void setUseThinConnectionType(final boolean val) {
		useThinConnectionType = val;
		//connectionType = val ? "thin" : "oci";
	}



	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.OracleDataSourceWrapperValuesSourceInterface#getLoginTimeout()
	 */
	public Integer getLoginTimeout() {
		return loginTimeout;
	}

	/**
	 * @param loginTimeout
	 *            the loginTimeout to set
	 * @throws SQLException
	 */
	public void setLoginTimeout(final int loginTimeout)  {
		this.loginTimeout = loginTimeout;

	}

	/**
	 * Set ValidateConnection.
	 *
	 * A value of rue causes the connection cache to test every connection it
	 * retrieves against the underlying database.
	 */

	public void setValidateConnection(final boolean validate) {
		this.validateConnection = validate;

	}

	public void setLowerThresholdLimit(final int lowerLimit) {
		if (lowerLimit < 0) {
			throw new IllegalArgumentException("lowerThresholdLimit must be >0 not: " + lowerLimit);
		}

	}


	public void setDescription(final String value) {
		description = value;

	}



	public boolean getUseThinConnection() {
		return useThinConnection;
	}



	public Integer getConnectionWaitTimeout() {
		return connectionWaitTimeout;
	}



	public void setConnectionWaitTimeout(final int connectionWaitTimeout) {
		this.connectionWaitTimeout = connectionWaitTimeout;
	}


	public boolean getUseThinConnectionType() {
		return useThinConnectionType;
	}




	public Integer getLowerThresholdLimit() {
		return lowerThresholdLimit;
	}

	public OracleConnectionCacheCallback getCallback() {
		return callback;
	}

	public void setCallback(final OracleConnectionCacheCallback callback) {
		this.callback = callback;
	}

	public String getPassword() {
		return password;
	}

	public boolean isValidateConnection() {
		return validateConnection;
	}

	public String getDescription() {
		return description;
	}

	public void setUseThinConnection(final boolean useThinConnection) {
		this.useThinConnection = useThinConnection;
	}


	public String getValuesText() {


		final StringBuilder b = new StringBuilder();
		b.append(ODSWrapperInterface.ServerName + ": " + getServerName() + newline);
		b.append(ODSWrapperInterface.PortNumber + ": " + getPortNumber() + newline);
		b.append(ODSWrapperInterface.User + ": " + getUser() + newline);
		b.append(ODSWrapperInterface.ConnectionCachingEnabled + ": " + getConnectionCachingEnabled() + newline);
		b.append(ODSWrapperInterface.ServiceName + ": " + getServiceName() + newline);
		b.append(ODSWrapperInterface.LowerThresholdLimit + ": " + getLowerThresholdLimit() + newline);
		b.append(ODSWrapperInterface.LoginTimeout + ": " + getLoginTimeout() + newline);
		b.append(ODSWrapperInterface.AbandonedConnectionTimeout + ": " + getAbandonedConnectionTimeout() + newline);
	    b.append(ODSWrapperInterface.InitialLimit + ": " + getInitialLimit() + newline);
		b.append(ODSWrapperInterface.MinLimit + ": " + getMinLimit() + newline);
		b.append(ODSWrapperInterface.MaxLimit + ": " + getMaxLimit() + newline);
		b.append(ODSWrapperInterface.InactivityTimeout + ": " + getInactivityTimeout() + newline);
		b.append(ODSWrapperInterface.InitialLimit + ": " + getInitialLimit() + newline);
		b.append(ODSWrapperInterface.MaxStatementsLimit + ": " + getMaxStatementsLimit() + newline);
		b.append(ODSWrapperInterface.PropertyCheckInterval + ": " + getPropertyCheckInterval() + newline);
		b.append(ODSWrapperInterface.ValidateConnection + ": " + isValidateConnection() + newline);
		b.append(ODSWrapperInterface.AbandonedConnectionTimeout + ": " + getAbandonedConnectionTimeout() + newline);
		return b.toString();
	}


}
