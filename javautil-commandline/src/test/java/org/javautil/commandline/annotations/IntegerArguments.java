package org.javautil.commandline.annotations;

import org.javautil.commandline.BaseArgumentBean;

public class IntegerArguments extends BaseArgumentBean {

	@Optional
	private Integer intValue;

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(final Integer intValue) {
		this.intValue = intValue;
	}

}
