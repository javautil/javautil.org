package org.javautil.jfm.mains;

import java.io.File;

import org.javautil.commandline.CommandLineHandler;
import org.javautil.commandline.annotations.Exclusive;
import org.javautil.commandline.annotations.FileReadable;
import org.javautil.commandline.annotations.FileWritable;
import org.javautil.commandline.annotations.Optional;

/**
 * Command line arguments and processing configuration for invoking freemarker
 * template generation with pojos.
 * 
 * @author bcm
 */
public class FreeMarkerGeneratorArguments {

	@FileWritable
	@Optional
	private File outputFile;

	@Optional
	@FileReadable
	@Exclusive(property = "templateResourceName")
	private File templateFile;

	@Optional
	@Exclusive(property = "templateFile")
	private String templateResourceName;

	@Optional
	@FileReadable
	@Exclusive(property = "javaClassName")
	private File javaClassFile;

	@Optional
	@Exclusive(property = "javaClassFile")
	private String javaClassName;

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(final File outputFile) {
		this.outputFile = outputFile;
	}

	public File getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(final File templateFile) {
		this.templateFile = templateFile;
	}

	public File getJavaClassFile() {
		return javaClassFile;
	}

	public void setJavaClassFile(final File javaClassFile) {
		this.javaClassFile = javaClassFile;
	}

	public void processArgs(final String[] args) throws Exception {
		final CommandLineHandler command = new CommandLineHandler(this);
		command.evaluateArguments(args);
	}

	public String getTemplateResourceName() {
		return templateResourceName;
	}

	public void setTemplateResourceName(final String templateResourceName) {
		this.templateResourceName = templateResourceName;
	}

	public String getJavaClassName() {
		return javaClassName;
	}

	public void setJavaClassName(final String javaClassName) {
		this.javaClassName = javaClassName;
	}
}
