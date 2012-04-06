package org.javautil.commandline.annotations;

import java.io.File;

import org.javautil.commandline.BaseArgumentBean;

public class FileReadableArguments extends BaseArgumentBean {

	@FileReadable
	@Required
	private File outputFile;

	public File getoutputFile() {
		return outputFile;
	}

	public void setoutputFile(final File outputFile) {
		this.outputFile = outputFile;
	}

}
