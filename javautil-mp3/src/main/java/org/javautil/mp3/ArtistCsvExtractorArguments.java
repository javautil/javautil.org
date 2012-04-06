package org.javautil.mp3;

import java.io.File;

import org.javautil.commandline.annotations.Required;

public class ArtistCsvExtractorArguments {
	@Required
	File outputFile;

	@Required
	String datasourceName;

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
	 * @return the datasourceName
	 */
	public String getDatasourceName() {
		return datasourceName;
	}

	/**
	 * @param datasourceName
	 *            the datasourceName to set
	 */
	public void setDatasourceName(final String datasourceName) {
		this.datasourceName = datasourceName;
	}

}
