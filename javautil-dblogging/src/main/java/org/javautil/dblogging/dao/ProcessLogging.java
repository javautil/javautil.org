/**
 * 
 */
package org.javautil.dblogging.dao;

import org.javautil.dblogging.dto.UtProcessStatus;

/**
 * @author siyer
 * 
 */
public interface ProcessLogging {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#beginJob(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	public UtProcessStatus beginJob(String processName, String schemaName,
			Integer sid, Integer serialNumber);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#endJob()
	 */
	public void endJob();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#abortJob()
	 */
	public void abortJob();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#severe(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	public void severe(String logMsgId, String logMsg);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#warning(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	public void warning(String logMsgId, String logMsg);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#info(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	public void info(String logMsgId, String logMsg);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#action(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	public void action(String logMsgId, String logMsg);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#entering(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	public void entering(String logMsgId, String logMsg);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#exiting(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	public void exiting(String logMsgId, String logMsg);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#fine(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	public void fine(String logMsgId, String logMsg);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#finer(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	public void finer(String logMsgId, String logMsg);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#finest(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	public void finest(String logMsgId, String logMsg);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#none(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	public void none(String logMsgId, String logMsg);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.dblogging.dao.ProcessLogging#snapStats(java.lang.String)
	 */
	public void snapStats(String snapName);

}