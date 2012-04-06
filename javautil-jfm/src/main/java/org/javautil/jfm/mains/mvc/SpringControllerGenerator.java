package org.javautil.jfm.mains.mvc;

import org.javautil.jfm.JFMGenerator;
import org.javautil.jfm.mains.FreeMarkerGenerator;

public class SpringControllerGenerator extends FreeMarkerGenerator {

	public static void main(final String[] args) throws Exception {
		final SpringControllerGeneratorArguments arguments = new SpringControllerGeneratorArguments();
		arguments.processArgs(args);
		final JFMGenerator instance = getInstance(
				SpringControllerGenerator.class, arguments);
		FreeMarkerGenerator.generateToOutputFileArgument(instance, arguments);
	}
}
