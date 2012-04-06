/**
 * 
 */
package org.javautil.exceptionprocessing.dao;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.javautil.exceptionprocessing.dto.UtProcessLog;
import org.javautil.exceptionprocessing.dto.UtProcessStatus;

/**
 * @author siyer
 * 
 */
public class ProcessLoggingImpl {

	private final Logger logger = Logger.getLogger(getClass());
	UtProcessStatus utProcessStatus;
	int logSeq = 1;
	long startTime;
	Configuration cfg = new Configuration();
	SessionFactory sf = null;

	public ProcessLoggingImpl() {
		cfg.configure().buildSessionFactory();
	}

	public UtProcessStatus beginJob(final String processName,
			final String schemaName, final String threadName,
			final Integer sid, final Integer serialNumber) {
		utProcessStatus = new UtProcessStatus();
		utProcessStatus.setProcessNm(processName);
		utProcessStatus.setSchemaNm(schemaName);
		utProcessStatus.setThreadNm(threadName);
		utProcessStatus.setSid(sid);
		utProcessStatus.setSerial(serialNumber);
		utProcessStatus.setStatusId("I");
		startTime = System.currentTimeMillis();
		// utProcessStatus.setStatusTs(new Timestamp(startTime));
		sf.getCurrentSession().save(utProcessStatus);
		return utProcessStatus;
	}

	public void endJob() {
		final long endTime = System.currentTimeMillis();
		utProcessStatus.setSerial(null);
		utProcessStatus.setSid(null);
		utProcessStatus.setStatusId("C");
		// utProcessStatus.setStatusTs(new Timestamp(endTime));
		// utProcessStatus.setTotalElapsed(new Timestamp(elapsedTime));
		sf.getCurrentSession().save(utProcessStatus);
	}

	public void abortJob() {
		final long endTime = System.currentTimeMillis();
		utProcessStatus.setSerial(null);
		utProcessStatus.setSid(null);
		utProcessStatus.setStatusId("A");
		utProcessStatus.setStatusMsg("ABORT");
		// utProcessStatus.setStatusTs(new Timestamp(endTime));
		// utProcessStatus.setTotalElapsed(new Timestamp(elapsedTime));
	}

	public void severe(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final String callStack) {
		createProcessLog(logMsgId, logMsg, logMsgClob, callerName, lineNumber,
				LogLevel.SEVERE, callStack);
	}

	public void warning(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final String callStack) {
		createProcessLog(logMsgId, logMsg, logMsgClob, callerName, lineNumber,
				LogLevel.WARNING, callStack);
	}

	public void info(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final String callStack) {
		createProcessLog(logMsgId, logMsg, logMsgClob, callerName, lineNumber,
				LogLevel.INFO, callStack);
	}

	public void action(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final String callStack) {
		createProcessLog(logMsgId, logMsg, logMsgClob, callerName, lineNumber,
				LogLevel.ACTION, callStack);
	}

	public void entering(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final String callStack) {
		createProcessLog(logMsgId, logMsg, logMsgClob, callerName, lineNumber,
				LogLevel.ENTERING, callStack);
	}

	public void exiting(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final String callStack) {
		createProcessLog(logMsgId, logMsg, logMsgClob, callerName, lineNumber,
				LogLevel.EXITING, callStack);
	}

	public void fine(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final String callStack) {
		createProcessLog(logMsgId, logMsg, logMsgClob, callerName, lineNumber,
				LogLevel.FINE, callStack);
	}

	public void finer(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final String callStack) {
		createProcessLog(logMsgId, logMsg, logMsgClob, callerName, lineNumber,
				LogLevel.FINER, callStack);
	}

	public void finest(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final String callStack) {
		createProcessLog(logMsgId, logMsg, logMsgClob, callerName, lineNumber,
				LogLevel.FINEST, callStack);
	}

	public void none(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final String callStack) {
		createProcessLog(logMsgId, logMsg, logMsgClob, callerName, lineNumber,
				LogLevel.NONE, callStack);
	}

	private void createProcessLog(final String logMsgId, final String logMsg,
			final String logMsgClob, final String callerName,
			final Integer lineNumber, final int logLevel, final String callStack) {

		new LogLevel();
		final long logTime = System.currentTimeMillis();
		final UtProcessLog utProcessLog = new UtProcessLog();
		utProcessLog.setUtProcessStatus(utProcessStatus);

		// TODO : what is the Primary Key for the Table?

		utProcessLog.setLogMsgId(logMsgId);
		utProcessLog.setLogMsg(logMsg);
		utProcessLog.setLogMsgClob(logMsgClob);
		utProcessLog.setCallerName(callerName);
		utProcessLog.setLineNbr(lineNumber);
		utProcessLog.setCallStack(callStack);
		utProcessLog.setLogLevel(logLevel);
		utProcessLog.setLogMsgTs(new Timestamp(logTime));
		// utProcessLog.setElapsedTime(new Timestamp(elapsedTime)); // TODO :
		// Fix the DB
		sf.getCurrentSession().save(utProcessLog);
	}

	public void snapStats(final String snapName) {

	}
}
