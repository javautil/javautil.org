package org.javautil.commandline.annotations;

import java.util.Collection;

import org.javautil.commandline.ParamType;

public class BooleanArguments {

	@Required
	private Boolean bean;

	@Optional
	@MultiValue(type = ParamType.BOOLEAN)
	private Collection<Boolean> flags;

	public Collection<Boolean> getFlags() {
		return flags;
	}

	public void setFlags(final Collection<Boolean> flags) {
		this.flags = flags;
	}

	public Boolean getBean() {
		return bean;
	}

	public void setBean(final Boolean beans) {
		this.bean = beans;
	}
}
