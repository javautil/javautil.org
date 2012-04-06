package org.javautil.commandline.annotations;

import java.io.File;

import org.javautil.commandline.BaseArgumentBean;

public class DirectoryArguments extends BaseArgumentBean {

	@Required
	@DirectoryReadable
	private File requiredDirectory;

	public File getRequiredDirectory() {
		return requiredDirectory;
	}

	public void setRequiredDirectory(final File requiredDirectory) {
		this.requiredDirectory = requiredDirectory;
	}

	@Optional
	@DirectoryExists
	@DirectoryReadable
	private File optionalExistingReadableDirectory;

	@Optional
	@DirectoryReadable
	private File optionalReadableDirectory;

	public File getOptionalExistingReadableDirectory() {
		return optionalExistingReadableDirectory;
	}

	public void setOptionalExistingReadableDirectory(
			final File optionalExistingReadableDirectory) {
		this.optionalExistingReadableDirectory = optionalExistingReadableDirectory;
	}

	public File getOptionalReadableDirectory() {
		return optionalReadableDirectory;
	}

	public void setOptionalReadableDirectory(
			final File optionalReadableDirectory) {
		this.optionalReadableDirectory = optionalReadableDirectory;
	}

}
