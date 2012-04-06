package org.javautil.hibernate;

import java.io.File;

import org.javautil.commandline.annotations.DirectoryReadable;
import org.javautil.commandline.annotations.FileReadable;
import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.Required;
import org.javautil.commandline.annotations.RequiredUnless;

public class CreateDatabaseSchemaArgs {

	/**
	 * The file to which the SQL scripts to create the database objects should
	 * be written.
	 */
	@Required
	private File outputFile;

	@Required
	@FileReadable
	private File configurationFile;

	@RequiredUnless(property = "outputFile")
	private boolean createDatabaseObjects;

	@DirectoryReadable
	@Required
	private File mappingsDirectory;

	/**
	 * SQL statement delimiter for generated output
	 */
	@Optional
	private String delimiter = ";";

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(final String delimiter) {
		this.delimiter = delimiter;
	}

	public File getMappingsDirectory() {
		return mappingsDirectory;
	}

	public void setMappingsDirectory(final File mappingsDirectory) {
		this.mappingsDirectory = mappingsDirectory;
	}

	public boolean isCreateDatabaseObjects() {
		return createDatabaseObjects;
	}

	public void setCreateDatabaseObjects(final boolean createDatabaseObjects) {
		this.createDatabaseObjects = createDatabaseObjects;
	}

	public File getConfigurationFile() {
		return configurationFile;
	}

	public void setConfigurationFile(final File configurationFile) {
		this.configurationFile = configurationFile;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(final File outputFile) {
		this.outputFile = outputFile;
	}

}
