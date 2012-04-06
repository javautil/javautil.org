package org.javautil.jfm.mains.mvc;

import org.javautil.commandline.annotations.Optional;
import org.javautil.jfm.mains.FreeMarkerGeneratorArguments;
import org.javautil.jfm.model.JFMModel;

@JFMModel
public class SpringFTLReportGeneratorArguments extends
		FreeMarkerGeneratorArguments {

	public static final String REPORT_TEMPLATE_RESOURCE_NAME = "spring/report.ftl";

	/**
	 * Represents the HTML table tag attribute "class".
	 */
	@Optional
	private String htmlTableClass;

	/**
	 * The default value for templateResourceName is set to @see
	 * {@link #REPORT_TEMPLATE_RESOURCE_NAME }
	 */
	public SpringFTLReportGeneratorArguments() {
		setTemplateResourceName(REPORT_TEMPLATE_RESOURCE_NAME);
	}

	public String getHtmlTableClass() {
		return htmlTableClass;
	}

	public void setHtmlTableClass(final String htmlTableClass) {
		this.htmlTableClass = htmlTableClass;
	}
}
