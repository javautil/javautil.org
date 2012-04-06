package org.javautil.commandline.annotations;

import java.io.File;

public class InheritenceArguments extends IntegerArguments {

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
