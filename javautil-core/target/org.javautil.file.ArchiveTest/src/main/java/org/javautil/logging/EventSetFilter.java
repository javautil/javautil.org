package org.javautil.logging;

import java.util.Collection;

/**
 * 
 * @author bcm
 */
public class EventSetFilter extends AbstractEventsSet implements EventFilter {

	private static final long serialVersionUID = -4221610891169287723L;

	public EventSetFilter() {
		super();
	}

	public EventSetFilter(final Collection<? extends String> arg0) {
		super(arg0);
	}

	public EventSetFilter(final int arg0) {
		super(arg0);
	}

	@Override
	public boolean isEventRegistered(final String eventName) {
		return this.contains(eventName);
	}

}
