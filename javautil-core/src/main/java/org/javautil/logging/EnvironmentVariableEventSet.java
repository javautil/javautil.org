package org.javautil.logging;

/**
 * Sets events from the environment variable "EVENT_NAMES" as a comma separated
 * list.
 * 
 * @author bcm
 */
public class EnvironmentVariableEventSet extends AbstractEventsSet {

	private static final long serialVersionUID = 2L;

	private static final String PROPERTY_EVENT_NAMES = "EVENT_NAMES";

	public EnvironmentVariableEventSet() {
		final String _eventNames = System.getenv(PROPERTY_EVENT_NAMES);
		if (_eventNames == null) {
			throw new IllegalStateException("Required environment variable "
					+ PROPERTY_EVENT_NAMES
					+ " was not set; set it to a comma separated list"
					+ " of event names");
		}
		final String[] eventNames = _eventNames.split(",");
		for (final String eventName : eventNames) {
			add(eventName.trim());
		}
	}

}
