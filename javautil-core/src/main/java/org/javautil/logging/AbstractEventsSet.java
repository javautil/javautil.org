package org.javautil.logging;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author bcm
 * 
 *         TODO jjs, how is this a Set if it is backed by a List? and why is it
 *         called Abrstract when it is not?
 */
public class AbstractEventsSet extends ArrayList<String> implements EventSet {

	public AbstractEventsSet() {
		super();
	}

	public AbstractEventsSet(final Collection<? extends String> arg0) {
		super(arg0);
	}

	public AbstractEventsSet(final int arg0) {
		super(arg0);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Collection<String> getEventNames() {
		return this;
	}

}
