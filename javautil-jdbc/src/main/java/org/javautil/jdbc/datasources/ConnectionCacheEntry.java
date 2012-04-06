package org.javautil.jdbc.datasources;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class ConnectionCacheEntry {

	private static Logger logger = Logger.getLogger(ConnectionCacheEntry.class
			.getName());
	private Connection conn;

	private long nanoTimeLeased;
	private int expectedLeaseTime;
	private long nanoTimeReturned;
	private long returnCount;
	private final StackTraceElement[] callStack;
	private final DataSource dataSource;

	/**
	 * todo should consider
	 * 
	 * @param connectionNumber
	 * @param conn
	 * @param nanoTimeLeased
	 * @param leasee
	 * @param expectedLeaseTime
	 * @param stack
	 */
	public ConnectionCacheEntry(final Connection conn, final DataSource ds,
			final int expectedLeaseTime, final StackTraceElement[] stack) {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		if (ds == null) {
			throw new IllegalArgumentException("ds is null");
		}
		if (stack == null) {
			throw new IllegalArgumentException("stack is null");
		}
		this.conn = conn;
		this.nanoTimeLeased = System.nanoTime();
		this.dataSource = ds;
		// this.leaseeClass = leasee.getClass();
		this.expectedLeaseTime = expectedLeaseTime;
		this.callStack = stack;
	}

	public StackTraceElement[] getCallStack() {
		return callStack;
	}

	/**
	 * @return the Connection
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * 
	 * @return The datasource from which this Connection was obtained.
	 */
	public javax.sql.DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * tood jjs really? are we still using this
	 * 
	 * @return
	 */
	public int getExpectedLeaseTime() {
		return expectedLeaseTime;
	}

	/**
	 * @return the leaseTime
	 */
	public long getNanoTimeLeased() {
		return nanoTimeLeased;
	}

	public long getNanoTimeReturned() {
		return nanoTimeReturned;
	}

	public long getReturnCount() {
		return returnCount;
	}

	/**
	 * @param conn
	 *            the conn to set
	 * 
	 *            todo jjs if the connection has been set this should be illegal
	 */
	public void setConn(final Connection conn) {
		this.conn = conn;
	}

	public void setExpectedLeaseTime(final int expectedLeaseTime) {
		this.expectedLeaseTime = expectedLeaseTime;
	}

	/**
	 * @param leaseTime
	 *            the leaseTime to set todo set in constructor and hide
	 */
	public void setNanoTimeLeased(final long leaseTime) {
		this.nanoTimeLeased = leaseTime;
	}

	public void setNanoTimeReturned(final long l) {
		nanoTimeReturned = l;
		returnCount++;

	}

}
