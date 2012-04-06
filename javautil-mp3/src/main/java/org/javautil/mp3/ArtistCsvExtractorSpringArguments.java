package org.javautil.mp3;

import java.io.File;

import org.javautil.commandline.annotations.Required;

public class ArtistCsvExtractorSpringArguments {
	@Required
	private File outputFile;

	@Required
	private File applicationContextFile;

	/**
	 * @return the outputFile
	 */
	public File getOutputFile() {
		return outputFile;
	}

	/**
	 * @param outputFile
	 *            the outputFile to set
	 */
	public void setOutputFile(final File outputFile) {
		this.outputFile = outputFile;
	}

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
