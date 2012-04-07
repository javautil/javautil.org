package org.javautil.logging;

/**
 * todo jjs what is this? It is never referenced in this projec
 * 
 * @author bcm
 */
public class AllEventsFilter implements EventFilter {

	@Override
	public boolean isEventRegistered(final String eventName) {
		return true;
	}

}
