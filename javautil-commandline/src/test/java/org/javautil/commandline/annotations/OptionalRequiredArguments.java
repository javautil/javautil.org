package org.javautil.commandline.annotations;

import java.io.File;

import org.javautil.commandline.BaseArgumentBean;

public class OptionalRequiredArguments extends BaseArgumentBean {

	@Optional
	@Required
	@DirectoryReadable
	private File requiredDirectory;

	public File getRequiredDirectory() {
		return requiredDirectory;
	}

	public void setRequiredDirectory(final File requiredDirectory) {
		this.requiredDirectory = requiredDirectory;
	}

}
