package org.javautil.logging;

/**
 * Sets events from the System property "EVENT_NAMES" as a comma separated list.
 * 
 * @see EnvironmentVariableEventSet
 * 
 *      TODO jjs this cut and paste is inelegant seems like this and
 *      EnvironmentVariableEventSet should be merged and support a Environment
 *      then Property mechanism.
 * @author bcm
 */
public class SystemPropertyEventSet extends AbstractEventsSet {

	private static final long serialVersionUID = 2L;

	private static final String PROPERTY_EVENT_NAMES = "EVENT_NAMES";

	public SystemPropertyEventSet() {
		final String _eventNames = System.getProperty(PROPERTY_EVENT_NAMES);
		if (_eventNames == null) {
			throw new IllegalStateException("Required system property "
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
