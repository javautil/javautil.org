package org.javautil.commandline.annotations;

import org.javautil.commandline.BaseArgumentBean;

public class ExclusiveArguments extends BaseArgumentBean {

	@Exclusive(property = "frog")
	@Optional
	private String toad;

	@Optional
	private String frog;

	/**
	 * @return the toad
	 */
	public String getToad() {
		return toad;
	}

	/**
	 * @param toad
	 *            the toad to set
	 */
	public void setToad(final String toad) {
		this.toad = toad;
	}

	/**
	 * @return the frog
	 */
	public String getFrog() {
		return frog;
	}

	/**
	 * @param frog
	 *            the frog to set
	 */
	public void setFrog(final String frog) {
		this.frog = frog;
	}

}
