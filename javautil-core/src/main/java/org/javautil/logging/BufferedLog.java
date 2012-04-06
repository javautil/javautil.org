package org.javautil.logging;

import org.apache.commons.logging.Log;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

/**
 * An implementation of Log that writes to a LoggingEventBuffer to prevent log
 * writers from receiving log messages immediately. Typically, this mechanism
 * would be used to collate log messages together in the case of a
 * multi-threaded application. To print log messages, either the flushLogBuffer
 * method must be called (not part of the commons.logging.Log interface) or the
 * trace method must be called with the argument "flushLogBuffer".
 * 
 * todo needs tests and examples
 * 
 * @author bcm
 * 
 */
public class BufferedLog implements Log {

	private Logger wrappedLog = null;

	private LoggingEventBuffer buffer = null;

	public BufferedLog(final Logger wrappedLog, final LoggingEventBuffer buffer) {
		this.wrappedLog = wrappedLog;
		this.buffer = buffer;
	}

	public static final String FLUSH_LOG_BUFFER = "flushLogBuffer";

	@Override
	public void debug(final Object message) {
		processLogEvent(Level.DEBUG, message);
	}

	@Override
	public void debug(final Object message, final Throwable exception) {
		processLogEvent(Level.DEBUG, message, exception);
	}

	@Override
	public void error(final Object message) {
		processLogEvent(Level.ERROR, message);
	}

	@Override
	public void error(final Object message, final Throwable exception) {
		processLogEvent(Level.ERROR, message, exception);
	}

	@Override
	public void fatal(final Object message) {
		processLogEvent(Level.FATAL, message);
	}

	@Override
	public void fatal(final Object message, final Throwable exception) {
		processLogEvent(Level.FATAL, message, exception);
	}

	@Override
	public void info(final Object message) {
		processLogEvent(Level.INFO, message);
	}

	@Override
	public void info(final Object message, final Throwable exception) {
		processLogEvent(Level.INFO, message, exception);
	}

	@Override
	public boolean isDebugEnabled() {
		return wrappedLog.isEnabledFor(Level.DEBUG);
	}

	@Override
	public boolean isErrorEnabled() {
		return wrappedLog.isEnabledFor(Level.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return wrappedLog.isEnabledFor(Level.FATAL);
	}

	@Override
	public boolean isInfoEnabled() {
		return wrappedLog.isEnabledFor(Level.INFO);
	}

	@Override
	public boolean isTraceEnabled() {
		return wrappedLog.isEnabledFor(Level.TRACE);
	}

	@Override
	public boolean isWarnEnabled() {
		return wrappedLog.isEnabledFor(Level.WARN);
	}

	@Override
	public void trace(final Object message) {
		if (message.equals(FLUSH_LOG_BUFFER)) {
			flushLogBuffer();
		} else {
			processLogEvent(Level.TRACE, message);
		}
	}

	@Override
	public void trace(final Object message, final Throwable exception) {
		if (message.equals(FLUSH_LOG_BUFFER)) {
			flushLogBuffer();
		} else {
			processLogEvent(Level.TRACE, message, exception);
		}
	}

	@Override
	public void warn(final Object message) {
		processLogEvent(Level.WARN, message);
	}

	@Override
	public void warn(final Object message, final Throwable exception) {
		processLogEvent(Level.WARN, message, exception);
	}

	protected void processLogEvent(final Level level, final Object message) {
		processLogEvent(level, message, null);
	}

	/**
	 * Should only be called from any of the methods implementing a commons
	 * logging Log event because of the assumed depth of the stack being
	 * fetched.
	 * 
	 * @param level
	 * @param message
	 * @param exception
	 */
	protected void processLogEvent(final Priority level, final Object message,
			final Throwable exception) {
		if (wrappedLog.isEnabledFor(level)) {
			final LoggingEvent event = new LoggingEvent(wrappedLog.getName(),
					wrappedLog, level, message, exception);
			buffer.put(event, wrappedLog);
		}
	}

	public void flushLogBuffer() {
		buffer.flush();
	}

}
