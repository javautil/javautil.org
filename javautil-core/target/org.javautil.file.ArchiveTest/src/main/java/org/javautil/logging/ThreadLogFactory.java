package org.javautil.logging;

import org.apache.log4j.Logger;

/**
 * Provides a Log instance that will only print log messages when the
 * flushLogBuffer method or trace("flushLogBuffer") is called.
 * 
 * @author bcm
 */
public class ThreadLogFactory {

	public BufferedLog getLogger(final Class<? extends Object> clazz) {
		final Logger logger = Logger.getLogger(clazz);
		final LoggingEventBuffer buffer = ThreadLogUtil.getLoggingEventBuffer();
		final BufferedLog bufferedLog = new BufferedLog(logger, buffer);
		return bufferedLog;
	}

}
