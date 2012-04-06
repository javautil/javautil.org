package org.javautil.mp3;

import java.io.File;
import java.util.Collection;

import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.Required;

// TODO show extended 
public class Mp3Arguments {

	@Optional
	private Collection<String> events;

	@Optional
	private String dataSourceName;

	@Optional
	private File logDir;

	@Optional
	private int commitEveryNFiles = 100;

	@Optional
	private File errorLogFileName;

	@Optional
	protected File configFile;

	@Optional
	protected String logFileName;

	@Required
	protected Collection<File> directories;

	/**
	 * @return the events
	 */
	public Collection<String> getEvents() {
		return events;
	}

	/**
	 * @param events
	 *            the events to set
	 */
	public void setEvents(final Collection<String> events) {
		this.events = events;
	}

	/**
	 * @return the dataSourceName
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * @param dataSourceName
	 *            the dataSourceName to set
	 */
	public void setDataSourceName(final String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	/**
	 * @return the logDir
	 */
	public File getLogDir() {
		return logDir;
	}

	/**
	 * @param logDir
	 *            the logDir to set
	 */
	public void setLogDir(final File logDir) {
		this.logDir = logDir;
	}

	/**
	 * @return the commitEveryNFiles
	 */
	public int getCommitEveryNFiles() {
		return commitEveryNFiles;
	}

	/**
	 * @param commitEveryNFiles
	 *            the commitEveryNFiles to set
	 */
	public void setCommitEveryNFiles(final int commitEveryNFiles) {
		this.commitEveryNFiles = commitEveryNFiles;
	}

	/**
	 * @return the errorLogFileName
	 */
	public File getErrorLogFileName() {
		return errorLogFileName;
	}

	/**
	 * @param errorLogFileName
	 *            the errorLogFileName to set
	 */
	public void setErrorLogFileName(final File errorLogFileName) {
		this.errorLogFileName = errorLogFileName;
	}

	/**
	 * @return the configFile
	 */
	public File getConfigFile() {
		return configFile;
	}

	/**
	 * @param configFile
	 *            the configFile to set
	 */
	public void setConfigFile(final File configFile) {
		this.configFile = configFile;
	}

	/**
	 * @return the logFileName
	 */
	public String getLogFileName() {
		return logFileName;
	}

	/**
	 * @param logFileName
	 *            the logFileName to set
	 */
	public void setLogFileName(final String logFileName) {
		this.logFileName = logFileName;
	}

	/**
	 * @return the directories
	 */
	public Collection<File> getDirectories() {
		return directories;
	}

	/**
	 * @param directories
	 *            the directories to set
	 */
	public void setDirectories(final Collection<File> directories) {
		this.directories = directories;
	}

}
