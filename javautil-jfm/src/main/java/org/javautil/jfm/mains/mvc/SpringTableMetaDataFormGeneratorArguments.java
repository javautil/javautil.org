package org.javautil.jfm.mains.mvc;

import java.io.File;

import org.javautil.commandline.CommandLineHandler;
import org.javautil.commandline.annotations.Exclusive;
import org.javautil.commandline.annotations.FileReadable;
import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.Required;
import org.javautil.commandline.annotations.StringSet;
import org.javautil.jfm.model.JFMModel;

@JFMModel
public class SpringTableMetaDataFormGeneratorArguments
// extends
// FreeMarkerGeneratorArguments
{

	public static final String FORM_TEMPLATE_RESOURCE_NAME = "spring/form.ftl";

	@Required
	private File outputFile;

	@Optional
	@FileReadable
	@Exclusive(property = "templateResourceName")
	private File templateFile;

	@Required
	private String tableName;

	@Required
	private String datasourceName;

	@Required
	private String schemaName;

	@Optional
	private String catalogName;

	@Optional
	@Exclusive(property = "templateFile")
	private String templateResourceName;

	/**
	 * Represents the HTML form tag attribute "method". Only standard HTTP
	 * get/post methods are supported.
	 */
	@Optional
	@StringSet(allowableValues = { "get", "post" })
	private String htmlFormMethod = "post";

	/**
	 * Represents the HTML form tag attribute "action". When the form is
	 * submitted, the data from the fields will be submitted to the URL found at
	 * the action's location.
	 */
	@Optional
	private String htmlFormAction = "";

	@Optional
	private String htmlTableClass;

	public void processArguments(final String[] args) {
		final CommandLineHandler clh = new CommandLineHandler(this);
		clh.evaluateArguments(args);
	}

	/**
	 * The default value for templateResourceName is set to @see
	 * {@link #FORM_TEMPLATE_RESOURCE_NAME }
	 */
	public SpringTableMetaDataFormGeneratorArguments() {
		setTemplateResourceName(FORM_TEMPLATE_RESOURCE_NAME);
	}

	public String getHtmlFormMethod() {
		return htmlFormMethod;
	}

	public void setHtmlFormMethod(final String htmlFormMethod) {
		this.htmlFormMethod = htmlFormMethod;
	}

	public String getHtmlFormAction() {
		return htmlFormAction;
	}

	public void setHtmlFormAction(final String htmlFormAction) {
		this.htmlFormAction = htmlFormAction;
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

	/**
	 * @return the templateFile
	 */
	public File getTemplateFile() {
		return templateFile;
	}

	/**
	 * @param templateFile
	 *            the templateFile to set
	 */
	public void setTemplateFile(final File templateFile) {
		this.templateFile = templateFile;
	}

	/**
	 * @return the templateResourceName
	 */
	public String getTemplateResourceName() {
		return templateResourceName;
	}

	/**
	 * @param templateResourceName
	 *            the templateResourceName to set
	 */
	public void setTemplateResourceName(final String templateResourceName) {
		this.templateResourceName = templateResourceName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(final String tableName) {
		this.tableName = tableName;
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

	/**
	 * @return the schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * @param schemaName
	 *            the schemaName to set
	 */
	public void setSchemaName(final String schemaName) {
		this.schemaName = schemaName;
	}

	/**
	 * @return the catalogName
	 */
	public String getCatalogName() {
		return catalogName;
	}

	/**
	 * @param catalogName
	 *            the catalogName to set
	 */
	public void setCatalogName(final String catalogName) {
		this.catalogName = catalogName;
	}

	/**
	 * @return the htmlTableClass
	 */
	public String getHtmlTableClass() {
		return htmlTableClass;
	}

	/**
	 * @param htmlTableClass
	 *            the htmlTableClass to set
	 */
	public void setHtmlTableClass(final String htmlTableClass) {
		this.htmlTableClass = htmlTableClass;
	}

}
