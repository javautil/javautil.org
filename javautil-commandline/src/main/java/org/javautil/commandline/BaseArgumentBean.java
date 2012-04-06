package org.javautil.commandline;

import org.javautil.commandline.annotations.Optional;

public class BaseArgumentBean {
	/**
	 * The program should emit copious information regarding its activities.
	 */
	@Optional
	private boolean verbose;

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(final boolean verbose) {
		this.verbose = verbose;
	}

}
