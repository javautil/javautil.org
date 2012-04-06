package org.javautil.jfm.mains.mvc;

import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.StringSet;
import org.javautil.jfm.mains.FreeMarkerGeneratorArguments;
import org.javautil.jfm.model.JFMModel;

@JFMModel
public class SpringFTLFormGeneratorArguments extends
		FreeMarkerGeneratorArguments {

	public static final String FORM_TEMPLATE_RESOURCE_NAME = "spring/form.ftl";

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

	/**
	 * The default value for templateResourceName is set to @see
	 * {@link #FORM_TEMPLATE_RESOURCE_NAME }
	 */
	public SpringFTLFormGeneratorArguments() {
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

}
