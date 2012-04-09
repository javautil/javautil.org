package org.javautil.logging;

import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author bcm
 */
public class EventsUtil {

	private static Log logger = LogFactory.getLog(EventsUtil.class);

	private static EventsAnnounceThread announceThread = null;

	public static void announceDeregisteredEvents() {
		final StringBuilder buffy = new StringBuilder();
		buffy.append("some logging events are disabled; event names:");
		for (final String eventName : Events.deregisteredEventNames) {
			buffy.append("\n\t" + eventName);
		}
		logger.info(buffy.toString());
	}

	public static void announceUnregisteredEvents(final int seconds) {
		if (seconds < 1) {
			throw new IllegalArgumentException("seconds is less than one");
		}
		synchronized (Boolean.TRUE) {
			if (announceThread != null) {
				announceThread.die();
			}
			announceThread = new EventsAnnounceThread(new TreeSet<String>(
					Events.deregisteredEventNames),
					"new unregistered event names available", seconds, false);
			announceThread.start();
		}
	}

}
