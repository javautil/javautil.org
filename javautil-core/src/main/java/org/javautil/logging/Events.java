package org.javautil.logging;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides a simple static resource for checking if the client has requested a
 * particular "event". In most cases, the "event" can be anything; but
 * programmers should consider only using generic names in code that is common.
 * Application specific code should use very specific event names, like
 * "MyApplication_MyApplicationModule_MyApplicationFeature".
 * 
 * @author bcm, jjs
 */
public class Events {

	public static String PROPERTY_EVENTSET_CLASSNAME = "EVENTSET_CLASSNAME";

	private static Log logger = LogFactory.getLog(Events.class);

	static ArrayList<String> registeredEventNames = new ArrayList<String>();

	static ArrayList<String> deregisteredEventNames = new ArrayList<String>();

	static EventFilter eventFilter = null;

	static {
		String className = System.getProperty(PROPERTY_EVENTSET_CLASSNAME);
		if (className == null) {
			className = System.getenv(PROPERTY_EVENTSET_CLASSNAME);
		}
		if (className != null) {
			Class<? extends Object> clazz = null;
			try {
				clazz = Class.forName(className);
				if (EventFilter.class.isAssignableFrom(clazz)) {
					final EventFilter eventFilter = (EventFilter) clazz
							.newInstance();
					logger.info("registering EventFilter " + eventFilter);
					registerEventFilter(eventFilter);
				} else if (EventSet.class.isAssignableFrom(clazz)) {
					final EventSet eventSet = (EventSet) clazz.newInstance();
					logger.info("registering EventSet " + eventSet);
					registerEventSet(eventSet);
				} else {
					throw new IllegalArgumentException("class \"" + className
							+ "\"" + " does not implement the interfaces "
							+ EventFilter.class.getName() + " or "
							+ EventSet.class.getName());
				}

			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			logger.warn("Events was not initialized; event logging is "
					+ "either off or not configured");
		}
	}

	public Collection<String> getRegisteredEventNames() {
		final Collection<String> ret = new ArrayList<String>();
		if (eventFilter != null) {
			ret.addAll(registeredEventNames);
		}
		return ret;
	}

	public Collection<String> getDeregisteredEventNames() {
		final Collection<String> ret = new ArrayList<String>();
		if (eventFilter != null) {
			ret.addAll(deregisteredEventNames);
		}
		return ret;
	}

	public static void registerEventName(final String eventName) {
		if (eventFilter != null) {
			throw new IllegalArgumentException("cannot deregister events; "
					+ PROPERTY_EVENTSET_CLASSNAME + " was specified");
		}
		if (deregisteredEventNames.contains(eventName)) {
			deregisteredEventNames.remove(eventName);
		}
		if (!registeredEventNames.contains(eventName)) {
			registeredEventNames.add(eventName);
		}
	}

	public static void registerEventSet(final EventSet eventSet) {
		if (eventSet == null) {
			throw new IllegalArgumentException("eventSet is null");
		}
		for (final String eventName : eventSet.getEventNames()) {
			registerEventName(eventName);
		}
	}

	public static void registerEventFilter(final EventFilter _eventFilter) {
		eventFilter = _eventFilter;
	}

	public static void deregisterEventName(final String eventName) {
		if (eventFilter != null) {
			throw new IllegalArgumentException("cannot deregister events; "
					+ PROPERTY_EVENTSET_CLASSNAME + " was specified");
		}
		if (!deregisteredEventNames.contains(eventName)) {
			deregisteredEventNames.add(eventName);
		}
		if (registeredEventNames.contains(eventName)) {
			registeredEventNames.remove(eventName);
		}
	}

	public static void deregisterEventSet(final EventSet eventSet) {
		if (eventSet == null) {
			throw new IllegalArgumentException("eventSet is null");
		}
		for (final String eventName : eventSet.getEventNames()) {
			deregisterEventName(eventName);
		}
	}

	public static boolean isRegistered(final String eventName) {
		boolean ret = false;
		if (eventFilter != null) {
			ret = eventFilter.isEventRegistered(eventName);
		} else if (registeredEventNames.contains(eventName)) {
			ret = true;
		} else if (!deregisteredEventNames.contains(eventName)) {
			deregisteredEventNames.add(eventName);
		}
		return ret;
	}

}
