package org.javautil.sales.populate;

import java.io.File;

import org.javautil.commandline.annotations.FileExists;
import org.javautil.commandline.annotations.FileReadable;
import org.javautil.commandline.annotations.Required;

public class DataPopulatorCliArguments {
	@Required
	@FileExists
	@FileReadable
	private File applicationContextFile;

	/**
	 * @return the applicationContextFile
	 */
	public File getApplicationContextFile() {
		return applicationContextFile;
	}

	/**
	 * @param applicationContextFile
	 *            the applicationContextFile to set
	 */
	public void setApplicationContextFile(final File applicationContextFile) {
		this.applicationContextFile = applicationContextFile;
	}

}
