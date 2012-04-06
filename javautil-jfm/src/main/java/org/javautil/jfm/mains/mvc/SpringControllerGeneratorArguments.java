package org.javautil.jfm.mains.mvc;

public class SpringControllerGeneratorArguments extends
		JavaClassGenerationArguments {

	public static final String CONTROLLER_TEMPLATE_RESOURCE_NAME = "spring/reportandform_controller.ftl";

	/**
	 * The default value for templateResourceName is set to @see
	 * {@link #CONTROLLER_TEMPLATE_RESOURCE_NAME }
	 */
	public SpringControllerGeneratorArguments() {
		setTemplateResourceName(CONTROLLER_TEMPLATE_RESOURCE_NAME);
	}

}
