package org.javautil.commandline.annotations;

import org.javautil.commandline.BaseArgumentBean;

public class RequiresArguments extends BaseArgumentBean {

	@Optional
	private Integer numberOfWarts;

	@Requires(property = "numberOfWarts")
	@Optional
	private String toad;

	public String getToad() {
		return toad;
	}

	public void setToad(final String toad) {
		this.toad = toad;
	}

	public Integer getNumberOfWarts() {
		return numberOfWarts;
	}

	public void setNumberOfWarts(final Integer numberOfWarts) {
		this.numberOfWarts = numberOfWarts;
	}
}
