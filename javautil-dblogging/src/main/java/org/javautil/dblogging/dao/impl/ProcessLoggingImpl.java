/**
 * 
 */
package org.javautil.dblogging.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.javautil.dblogging.dao.ProcessLogLevel;
import org.javautil.dblogging.dao.ProcessLogging;
import org.javautil.dblogging.dto.UtProcessLog;
import org.javautil.dblogging.dto.UtProcessLogId;
import org.javautil.dblogging.dto.UtProcessStatus;
import org.javautil.lang.ThreadHelper;

/**
 * @author siyer
 * 
 */
public class ProcessLoggingImpl implements ProcessLogging {

	private final Logger logger = Logger.getLogger(getClass());
	private UtProcessStatus utProcessStatus;
	private int logSeqNbr = 1;
	private long startTime;
	private final Map<Thread, Long> threadMaps = new HashMap<Thread, Long>();
	private final Configuration cfg = new Configuration();
	private SessionFactory sessionFactory = null;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(final SessionFactory sf) {
		this.sessionFactory = sf;
	}

	private void checkSessionFactory() {
		if (sessionFactory == null) {
			throw new IllegalStateException("Session Factory is null");
		}
	}

	// public ProcessLoggingImpl() {
	// SessionFactory sf = cfg.configure().buildSessionFactory();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#beginJob(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public UtProcessStatus beginJob(final String processName,
			final String schemaName, final Integer sid,
			final Integer serialNumber) {
		checkSessionFactory();
		final Session session = sessionFactory.openSession();
		final Transaction t = session.beginTransaction();
		utProcessStatus = new UtProcessStatus();
		utProcessStatus.setProcessNm(processName);
		utProcessStatus.setSchemaNm(schemaName);
		utProcessStatus.setThreadNm(Thread.currentThread().getName());
		utProcessStatus.setSid(sid);
		utProcessStatus.setSerialNbr(serialNumber);
		utProcessStatus.setStatusId("I");
		startTime = System.currentTimeMillis();
		utProcessStatus.setStatusTs(new Timestamp(startTime));

		session.saveOrUpdate(utProcessStatus);
		session.flush();
		t.commit();
		session.close();
		return utProcessStatus;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#endJob()
	 */
	@Override
	public void endJob() {
		checkSessionFactory();
		final Session session = sessionFactory.openSession();
		final Transaction t = session.beginTransaction();
		final long endTime = System.currentTimeMillis();
		final long elapsedTime = endTime - startTime;
		utProcessStatus.setSerialNbr(null);
		utProcessStatus.setSid(null);
		utProcessStatus.setStatusId("C");
		utProcessStatus.setStatusTs(new Timestamp(endTime));
		double elapsedSeconds = elapsedTime / 1000;
		utProcessStatus.setTotalElapsedSeconds(new BigDecimal(elapsedSeconds));// (TimeUnit.MILLISECONDS.toMillis(elapsedTime));
		session.saveOrUpdate(utProcessStatus);
		session.flush();
		t.commit();
		session.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#abortJob()
	 */
	@Override
	public void abortJob() {
		checkSessionFactory();
		final Session session = sessionFactory.openSession();
		final Transaction t = session.beginTransaction();
		final long endTime = System.currentTimeMillis();
		final long elapsedTime = endTime - startTime;
		utProcessStatus.setSerialNbr(null);
		utProcessStatus.setSid(null);
		utProcessStatus.setStatusId("A");
		utProcessStatus.setStatusMsg("ABORT");
		utProcessStatus.setStatusTs(new Timestamp(endTime));
		double totalElapsedSeconds = elapsedTime / 1000;
		utProcessStatus.setTotalElapsedSeconds(new BigDecimal(
				totalElapsedSeconds));
		// utProcessStatus.setTotalElapsedSeconds(TimeUnit.MILLISECONDS
		// .toMillis(elapsedTime));

		session.saveOrUpdate(utProcessStatus);
		session.flush();
		t.commit();
		session.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#severe(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void severe(final String logMsgId, final String logMsg) {
		createProcessLog(logMsgId, logMsg, ProcessLogLevel.SEVERE.valueOf());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#warning(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void warning(final String logMsgId, final String logMsg) {
		createProcessLog(logMsgId, logMsg, ProcessLogLevel.WARNING.valueOf());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#info(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void info(final String logMsgId, final String logMsg) {
		createProcessLog(logMsgId, logMsg, ProcessLogLevel.INFO.valueOf());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#action(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void action(final String logMsgId, final String logMsg) {
		createProcessLog(logMsgId, logMsg, ProcessLogLevel.ACTION.valueOf());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#entering(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void entering(final String logMsgId, final String logMsg) {
		createProcessLog(logMsgId, logMsg, ProcessLogLevel.ENTERING.valueOf());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#exiting(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void exiting(final String logMsgId, final String logMsg) {
		createProcessLog(logMsgId, logMsg, ProcessLogLevel.EXITING.valueOf());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#fine(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void fine(final String logMsgId, final String logMsg) {
		createProcessLog(logMsgId, logMsg, ProcessLogLevel.FINE.valueOf());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#finer(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void finer(final String logMsgId, final String logMsg) {
		createProcessLog(logMsgId, logMsg, ProcessLogLevel.FINER.valueOf());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#finest(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void finest(final String logMsgId, final String logMsg) {
		createProcessLog(logMsgId, logMsg, ProcessLogLevel.FINEST.valueOf());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.dblogging.dao.ProcessLogging#none(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void none(final String logMsgId, final String logMsg) {
		createProcessLog(logMsgId, logMsg, ProcessLogLevel.NONE.valueOf());
	}

	private void createProcessLog(final String logMsgId, final String logMsg,
			final int logLevel) {

		checkSessionFactory();
		final Session session = sessionFactory.openSession();
		final Transaction transaction = session.beginTransaction();

		long elapsedTime;
		final long logTime = System.currentTimeMillis();
		final String threadName = Thread.currentThread().getName();
		final StackTraceElement el = Thread.currentThread().getStackTrace()[2];

		final Thread t = Thread.currentThread();
		if (threadMaps.get(t) != null) {
			elapsedTime = logTime - threadMaps.get(t).longValue();
		} else {
			elapsedTime = logTime - startTime;
		}
		threadMaps.put(t, new Long(logTime));
		final UtProcessLog utProcessLog = new UtProcessLog();
		final UtProcessLogId id = new UtProcessLogId();
		utProcessLog.setUtProcessStatus(utProcessStatus);
		id.setUtProcessStatusNbr(utProcessStatus.getUtProcessStatusNbr());
		id.setLogSeqNbr(logSeqNbr++);
		utProcessLog.setId(id);

		utProcessLog.setLogMsgId(logMsgId);
		utProcessLog.setLogMsg(logMsg);
		utProcessLog.setCallerName(threadName);
		utProcessLog.setLineNbr(el.getLineNumber());
		if (logLevel == ProcessLogLevel.SEVERE.valueOf()) {
			utProcessLog.setCallStack(ThreadHelper.getStackTraceAsString());
		}
		utProcessLog.setLogLevel((byte) logLevel);
		utProcessLog.setLogMsgTs(new Timestamp(logTime));
		double elapsedSeconds = elapsedTime / 1000;
		utProcessLog.setElapsedSeconds(new BigDecimal(elapsedSeconds));
		session.save(utProcessLog);
		session.flush();
		transaction.commit();
		session.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.dblogging.dao.ProcessLogging#snapStats(java.lang.String)
	 */
	@Override
	public void snapStats(final String snapName) {
		// TODO implement
	}
}
