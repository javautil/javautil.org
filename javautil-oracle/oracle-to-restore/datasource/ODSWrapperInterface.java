package com.dbexperts.oracle.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleConnectionCacheCallback;
import oracle.jdbc.pool.OracleDataSource;

public interface ODSWrapperInterface {

	public static final String	AbandonedConnectionTimeout	= "AbandonedConnectionTimeout";

	public static final String	ConnectionWaitTimeout		= "ConnectionWaitTimeout";

	public static final String	InitialLimit				= "InitialLimit";

	public static final String	InactivityTimeout			= "InactivityTimeout";

	public static final String	LowerThresholdLimit			= "LowerThresholdLimit";

	public static final String	MaxLimit					= "MaxLimit";

	public static final String	MaxStatementsLimit			= "MaxStatementsLimit";

	public static final String	MinLimit					= "MinLimit";

	public static final String	PropertyCheckInterval		= "PropertyCheckInterval";

	public static final String	TimeToLiveTimeout			= "TimeToLiveTimeout";

	public static final String	ValidateConnection			= "ValidateConnection";

	// following properties are set through setters and getters
	public static final String	ServerName					= "ServerName";

	public static final String	PortNumber					= "PortNumber";

	public static final String	User						= "User";

	public static final String	ConnectionCachingEnabled	= "ConnectionCachingEnabled";

	public static final String	ServiceName					= "ServiceName";

	public static final String	LoginTimeout				= "LoginTimeout";

	public abstract DataSource getOracleDataSource();

	public abstract OracleConnection getConnection() throws SQLException;

	public abstract void setAbandonedConnectionCallback(OracleConnectionCacheCallback callback);


	/**
	 * @return the ods
	 */
	public abstract DataSource getOds();

	/**
	 * @param ods
	 *            the ods to set
	 */
	public abstract void setOds(OracleDataSource ods);

	/**
	 * @return the abandonedConnectionTimeout
	 * @throws SQLException
	 */
	public abstract int getAbandonedConnectionTimeout() throws SQLException;

	/**
	 * @param abandonedConnectionTimeout
	 *            the abandonedConnectionTimeout to set
	 * @throws SQLException
	 */
	public abstract void setAbandonedConnectionTimeout(Integer abandonedConnectionTimeout) throws SQLException;

	/**
	 * @return the value set by call to setInitialLimit
	 * @see #setInitialLimit(Integer)
	 * @throws SQLException
	 */
	public abstract int getInitialLimit() throws SQLException;

	/**
	 *
	 * Sets how many connections are created in the cache when it is created or
	 * reinitialized.
	 *
	 * When this property is set to an integer value greater than 0, creating or
	 * reinitializing the cache automatically creates the specified number of
	 * connections, filling the cache in advance of need.
	 *
	 * @param initialLimit
	 *            the initialLimit to set
	 *

	 *
	 * Default: 0
	 * @throws SQLException
	 */
	public abstract void setInitialLimit(Integer initialLimit) throws SQLException;

	/**
	 * @return the maxLimit
	 * @throws SQLException
	 */
	public abstract int getMaxLimit() throws SQLException;

	/**
	 * Sets the maximum number of cached Connections.
	 *
	 * The default value is Integer.MAX_VALUE, meaning that there is no limit
	 * enforced by the connection cache, so that the number of connections is
	 * limited only by the number of database sessions configured for the
	 * database.
	 *
	 * @param maxLimit
	 *            the maxLimit to set
	 * @throws SQLException
	 */
	public abstract void setMaxLimit(Integer maxLimit) throws SQLException;

	/**
	 * @return the minLimit
	 * @throws SQLException
	 */
	public abstract int getMinLimit() throws SQLException;

	/**
	 * @param minLimit
	 *            the minLimit to set
	 * @throws SQLException
	 */
	public abstract void setMinLimit(Integer minLimit) throws SQLException;

	/**
	 * @return the maxStatementsLimit
	 * @throws SQLException
	 */
	public abstract int getMaxStatementsLimit() throws SQLException;

	/* (non-Javadoc)
	 * @see com.dbexperts.oracle.datasource.ODSWrapperInterface#setAbandonedConnectionTimeout(int)
	 */
	/**
	 * @param maxStatementsLimit
	 *            the maxStatementsLimit to set
	 * @throws SQLException
	 */
	public abstract void setMaxStatementsLimit(Integer maxStatementsLimit) throws SQLException;

	/**
	 * @return the inactivityTimeout
	 * @throws SQLException
	 */
	public abstract int getInactivityTimeout() throws SQLException;

	/**
	 * @param inactivityTimeout
	 *            the inactivityTimeout to set
	 * @throws SQLException
	 */
	public abstract void setInactivityTimeout(Integer inactivityTimeout) throws SQLException;

	/**
	 * @return the propertyCheckInterval
	 * @throws SQLException
	 */
	public abstract int getPropertyCheckInterval() throws SQLException;

	/**
	 * @param propertyCheckInterval
	 *            the propertyCheckInterval to set
	 * @throws SQLException
	 */
	public abstract void setPropertyCheckInterval(Integer propertyCheckInterval) throws SQLException;

	/**
	 * @return the timeToLiveTimeout
	 * @throws SQLException
	 */
	public abstract int getTimeToLiveTimeout() throws SQLException;

	/**
	 * @return the connectionCacheName
	 * @throws SQLException
	 */
	public abstract String getConnectionCacheName() throws SQLException;

	/**
	 * @param connectionCacheName
	 *            the connectionCacheName to set
	 * @throws SQLException
	 */
	public abstract void setConnectionCacheName(String connectionCacheName) throws SQLException;

	/**
	 * @return the serverName
	 */
	public abstract String getServerName();

	/**
	 * @param serverName
	 *            the serverName to set
	 */
	public abstract void setServerName(String serverName);

	/**
	 * @return the portNumber
	 */
	public abstract int getPortNumber();

	/**
	 * @param portNumber
	 *            the portNumber to set
	 *
	 * This is the IP port on which the database is listening for connections.
	 */
	public abstract void setPortNumber(Integer portNumber);

	/**
	 * @param password
	 *            the password to set
	 *
	 * The password associated with the specified user for the database.
	 */
	public abstract void setPassword(String password);

	/**
	 * @param connectionCachingEnabled
	 *            the connectionCachingEnabled to set
	 * @throws SQLException
	 */
	public abstract void setConnectionCachingEnabled(boolean connectionCachingEnabled) throws SQLException;

	/**
	 * @return the user
	 *
	 * The name of the database user for which connections are created.
	 */
	public abstract String getUser();

	/**
	 * @param user
	 *            the user to set
	 */
	public abstract void setUser(String user);

	/**
	 * @return the serviceName
	 */
	public abstract String getServiceName();

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	public abstract void setServiceName(String serviceName);

	/**
	 * @return the connnectionType
	 */
	public abstract String getConnectionType();

	/**
	 * @param val
	 *            true - use thin connection false use oci
	 */
	public abstract void setUseThinConnectionType(boolean val);

	/**
	 * @return the loginTimeout
	 */
	public abstract int getLoginTimeout();

	/**
	 * @param loginTimeout
	 *            the loginTimeout to set
	 * @throws SQLException
	 */
	public abstract void setLoginTimeout(Integer loginTimeout) throws SQLException;

	/**
	 * Set ValidateConnection.
	 *
	 * A value of rue causes the connection cache to test every connection it
	 * retrieves against the underlying database.
	 * @throws SQLException
	 */

	public abstract void setValidateConnection(boolean validate) throws SQLException;

	/**
	 * A value of -1 means that the property was not set
	 * todo return Integer so that null can indicate not set
	 * @return lowerThresholdLimit
	 * @throws SQLException
	 */
	public abstract Integer getLowerThresholdLimit() throws SQLException;

	public abstract void setLowerThresholdLimit(Integer lowerLimit) throws SQLException;

	public abstract OracleDataSource getDataSource();

	public abstract void setConnectionWaitTimeout(Integer connectionWaitTimeout) throws SQLException;

	public abstract void setValuesAndTest(OracleDataSourceWrapperValuesSourceInterface values) throws SQLException;

	public abstract void setValues(OracleDataSourceWrapperValuesSourceInterface values) throws SQLException;

	public abstract String getConnectionCachePropertiesText() throws SQLException;

	public abstract void setTimeToLiveTimeout(Integer value) throws SQLException;



}