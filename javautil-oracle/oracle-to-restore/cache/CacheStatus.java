package com.dbexperts.oracle.cache;

import org.apache.log4j.Logger;


/**
Version:   $Id: CacheStatus.java,v 1.1 2011/08/31 21:09:57 jjs Exp $
*/
public class CacheStatus {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(CacheStatus.class.getName());
	private java.util.Date lastRefreshTimeStart = null;
	private java.util.Date lastRefreshTimeEnd = null;
	private int refreshCount;
	private int cacheChangeNbr = 0;
    private long changeTime;

	private String objectName = null;
    private boolean uncommittedChanges = true;

	public CacheStatus(final String objectName) {

			this.objectName = objectName;


	}

	public int getCacheChangeNbr()
	{
		return cacheChangeNbr;
	}

	/**
	 * @return Returns the changeTime.
	 */
	public long getChangeTime() {
		return changeTime;
	}

	public String getObjectName() {
		return objectName;
	}

	public int getRefreshCount()
	{
		return refreshCount;
	}

	public long getRefreshDuration() {
		return lastRefreshTimeEnd.getTime() - lastRefreshTimeStart.getTime();
	}

	public java.util.Date getRefreshEndTime() {
		return lastRefreshTimeEnd;
	}
public java.util.Date getRefreshStartTime() {
		return lastRefreshTimeStart;
	}

	/*
	public long getCacheReferenceCount() {

	}
*/
	public void incrementReferenceCount() {
	}

	public boolean isUncommittedChanges() {
		return uncommittedChanges;
	}

	public void refreshEnds() {
		this.lastRefreshTimeEnd = new java.util.Date();
		this.refreshCount++;
	}



	public void refreshStarts() {
		this.lastRefreshTimeStart = new java.util.Date();
		this.changeTime = lastRefreshTimeStart.getTime();
	}

	public void setCacheChangeNbr( final int cacheChangeNbr)
	{
		this.cacheChangeNbr = cacheChangeNbr;
	}

	public void setUncommittedChanges(final boolean val) {
		uncommittedChanges = val ;
	}
}

