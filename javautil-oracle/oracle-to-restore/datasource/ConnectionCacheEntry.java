package com.dbexperts.oracle.datasource;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
public class ConnectionCacheEntry {

	private Connection conn;
	private long nanoTimeLeased;

	private int expectedLeaseTime;
	private long	nanoTimeReturned;
	private long returnCount;
	private final StackTraceElement[] callStack;
	private static Logger logger = Logger.getLogger(ConnectionCacheEntry.class.getName());
	private final DataSource dataSource;

	//private Class leaseeClass;

//	private ConnectionCacheEntry() {
//
//	}

	/**
	 * todo should consider
	 * @param connectionNumber
	 * @param conn
	 * @param nanoTimeLeased
	 * @param leasee
	 * @param expectedLeaseTime
	 * @param stack
	 */
	public ConnectionCacheEntry(final Connection conn, final DataSource ds, final int expectedLeaseTime, final StackTraceElement[] stack) {
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
	//	this.leaseeClass = leasee.getClass();
		this.expectedLeaseTime = expectedLeaseTime;
		this.callStack = stack;
	}

	public int getExpectedLeaseTime() {
		return expectedLeaseTime;
	}
	public void setExpectedLeaseTime(final int expectedLeaseTime) {
		this.expectedLeaseTime = expectedLeaseTime;
	}
	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}
	/**
	 * @param conn the conn to set
	 */
	public void setConn(final Connection conn) {
		this.conn = conn;
	}
	/**
	 * @return the leaseTime
	 */
	public long getNanoTimeLeased() {
		return nanoTimeLeased;
	}
	/**
	 * @param leaseTime the leaseTime to set
	 * todo set in constructor and hide
	 */
	public void setNanoTimeLeased(final long leaseTime) {
		this.nanoTimeLeased = leaseTime;
	}



	public long getNanoTimeReturned() {
		return nanoTimeReturned;
	}
	public void setNanoTimeReturned(final long l) {
		nanoTimeReturned = l;
		returnCount++;

	}
//
//	public Class getLeaseeClass() {
//		return leaseeClass;
//	}
//
//	public void setLeaseeClass(Class leaseeClass) {
//		this.leaseeClass = leaseeClass;
//	}

	public long getReturnCount() {
		return returnCount;
	}



	public StackTraceElement[] getCallStack() {
		return callStack;
	}

	public javax.sql.DataSource getDataSource() {

		return dataSource;
	}
}
