package org.javautil.commandline.annotations;

import java.util.Collection;

import org.javautil.commandline.BaseArgumentBean;
import org.javautil.commandline.ParamType;

public class MultiValueBean extends BaseArgumentBean {

	@Optional
	@MultiValue(type = ParamType.STRING)
	private Collection<String> words;

	public Collection<String> getWords() {
		return words;
	}

	public void setWords(final Collection<String> words) {
		this.words = words;
	}

}
