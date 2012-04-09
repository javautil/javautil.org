package org.javautil.logging;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author bcm
 */
public class EventsAnnounceThread extends Thread {

	private final Log logger = LogFactory.getLog(EventsUtil.class);

	private int seconds = 30;

	private boolean repeat = false;

	private boolean killMeNow = false;

	private String message = null;

	private Collection<String> eventNames = null;

	EventsAnnounceThread(final Collection<String> _eventNames,
			final String _message, final int _seconds, final boolean _repeat) {
		super("EventsAnnounceThread");
		eventNames = _eventNames;
		message = _message;
		seconds = _seconds;
		repeat = _repeat;
	}

	@Override
	public void run() {
		final HashSet<String> announcedNames = new HashSet<String>();
		boolean printMessage = false;
		final StringBuilder buffy = new StringBuilder();
		while (!killMeNow) {
			try {
				Thread.sleep(seconds * 1000);
			} catch (final InterruptedException e) {
				throw new RuntimeException(e);
			}
			if (!killMeNow) {
				buffy.append(message + "; eventNames:");
				for (final String eventName : eventNames) {
					if (!repeat && !announcedNames.contains(eventName)) {
						printMessage = true;
					} else if (repeat) {
						printMessage = true;
					}
					buffy.append("\n\t" + eventName);
					if (!repeat) {
						announcedNames.add(eventName);
					}
				}
				if (printMessage && logger != null && buffy.length() > 0) {
					logger.info(buffy.toString());
				}
				buffy.setLength(0);
				printMessage = false;
			}
		}
	}

	public void die() {
		killMeNow = true;
	}
}
