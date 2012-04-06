package com.dbexperts.oracle.datasource;

import oracle.jdbc.pool.OracleConnectionCacheCallback;

public interface OracleDataSourceWrapperValuesSourceInterface {

	public abstract void setAbandonedConnectionCallback(OracleConnectionCacheCallback callback);

	/**
	 * @return the abandonedConnectionTimeout
	 */
	public abstract Integer getAbandonedConnectionTimeout();

	/**
	 * @return the initialLimit
	 */
	public abstract Integer getInitialLimit();

	/**
	 * @return the maxLimit
	 */
	public abstract Integer getMaxLimit();

	/**
	 * @return the minLimit
	 */
	public abstract Integer getMinLimit();

	/**
	 * @return the maxStatementsLimit
	 */
	public abstract Integer getMaxStatementsLimit();

	/**
	 * @return the inactivityTimeout
	 */
	public abstract Integer getInactivityTimeout();

	/**
	 * @return the propertyCheckInterval
	 */
	public abstract Integer getPropertyCheckInterval();

	/**
	 * @return the timeToLiveTimeout
	 */
	public abstract Integer getTimeToLiveTimeout();

	/**
	 * @return the connectionCacheName
	 */
	public abstract String getConnectionCacheName();

	/**
	 * @return the serverName
	 */
	public abstract String getServerName();

	/**
	 * @return the portNumber
	 */
	public abstract Integer getPortNumber();

	/**
	 * @return the user
	 *
	 * The name of the database user for which connections are created.
	 */
	public abstract String getUser();

	/**
	 * @return the serviceName
	 */
	public abstract String getServiceName();


	/**
	 * @return the loginTimeout
	 */
	public abstract Integer getLoginTimeout();

	public abstract boolean getUseThinConnectionType();

	public abstract boolean getConnectionCachingEnabled();

	public abstract Integer getLowerThresholdLimit();

	public abstract String getValuesText();

}