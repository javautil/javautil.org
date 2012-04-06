package org.javautil.commandline.annotations;

import java.io.File;

import org.javautil.commandline.BaseArgumentBean;

public class FileExistsArguments extends BaseArgumentBean {

	@FileExists
	@Required
	private File outputFile;

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(final File outputFile) {
		this.outputFile = outputFile;
	}

}
