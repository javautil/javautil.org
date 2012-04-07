package org.javautil.logging;

/**
 * Provides a ThreadLocal stored instance of a LoggingEventBuffer for use with
 * BufferedLog instances. Not typically used by application code, instead see
 * ThreadLogFactory.
 * 
 * @author bcm
 */
public class ThreadLogUtil {

	private static ThreadLocal<LoggingEventBuffer> threadLocal = new ThreadLocal<LoggingEventBuffer>();

	public static LoggingEventBuffer getLoggingEventBuffer() {
		LoggingEventBuffer buffer = threadLocal.get();
		if (buffer == null) {
			buffer = new LoggingEventBuffer();
			threadLocal.set(buffer);
		}
		return buffer;
	}

}
