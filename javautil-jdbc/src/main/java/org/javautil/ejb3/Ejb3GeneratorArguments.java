package org.javautil.ejb3;

import java.io.File;

import org.javautil.commandline.CommandLineHandler;
import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.Required;

public class Ejb3GeneratorArguments {

	@Required
	private File templateFile;

	@Required
	private String packageName;

	@Required
	private File sourceDirectory = new File("src/main/java");

	@Optional
	private String catalogName;

	/**
	 * The database schema in which the table is located
	 */
	@Required
	private String schemaName;

	@Required
	private String tableName;

	@Optional
	private String tableType = "TABLE";

	@Required
	private String dataSourceName;

	public void processArguments(final String[] args) {
		final CommandLineHandler clh = new CommandLineHandler(this);
		clh.evaluateArguments(args);
	}

	public File getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(final File templateFile) {
		this.templateFile = templateFile;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(final String packageName) {
		this.packageName = packageName;
	}

	public File getSourceDirectory() {
		return sourceDirectory;
	}

	public void setSourceDirectory(final File sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(final String catalogName) {
		this.catalogName = catalogName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(final String schemaName) {
		this.schemaName = schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(final String tableType) {
		this.tableType = tableType;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(final String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
}
