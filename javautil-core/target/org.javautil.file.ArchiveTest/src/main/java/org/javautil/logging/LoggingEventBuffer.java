package org.javautil.logging;

import java.util.Enumeration;
import java.util.LinkedHashMap;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Stores LoggingEvent entries in a LinkedHashMap until the flush method is
 * called. To be used in conjunction with a BufferedLog.
 * 
 * @author bcm
 */
public class LoggingEventBuffer extends LinkedHashMap<LoggingEvent, Logger> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void flush() {
		for (final LoggingEvent loggingEvent : keySet()) {
			final Logger logger = get(loggingEvent);
			final Enumeration<Appender> appenders = logger.getAllAppenders();
			while (appenders.hasMoreElements()) {
				final Appender appender = appenders.nextElement();
				appender.doAppend(loggingEvent);
			}
		}
	}

}
