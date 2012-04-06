package org.javautil.mp3;

import java.io.File;
import java.util.Collection;

import org.javautil.commandline.ParamType;
import org.javautil.commandline.annotations.MultiValue;
import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.Required;

public class Mp3MetadataExtractorArguments {

	@Optional
	private File applicationContextFile;

	@Required
	private File outputFile;

	@Optional
	private File exceptionFile;

	@Required
	@MultiValue(type = ParamType.FILE)
	private Collection<File> directory;

	/**
	 * @return the directory
	 */
	public Collection<File> getDirectory() {
		return directory;
	}

	/**
	 * @param directory
	 *            the directory to set
	 */
	public void setDirectory(final Collection<File> directory) {
		this.directory = directory;
	}

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

	public void setApplicationContextFile(final File applicationContextFile) {
		this.applicationContextFile = applicationContextFile;
	}

	public File getApplicationContextFile() {
		return applicationContextFile;
	}

	/**
	 * @return the exceptionFile
	 */
	public File getExceptionFile() {
		return exceptionFile;
	}

	/**
	 * @param exceptionFile
	 *            the exceptionFile to set
	 */
	public void setExceptionFile(final File exceptionFile) {
		this.exceptionFile = exceptionFile;
	}
}
