package org.javautil.jfm.mains.mvc;

import org.javautil.jfm.JFMGenerator;
import org.javautil.jfm.mains.FreeMarkerGenerator;

public class SpringFTLFormGenerator extends FreeMarkerGenerator {

	public static void main(final String[] args) throws Exception {
		final SpringFTLFormGeneratorArguments arguments = new SpringFTLFormGeneratorArguments();
		arguments.processArgs(args);
		final JFMGenerator instance = getInstance(SpringFTLFormGenerator.class,
				arguments);
		FreeMarkerGenerator.generateToOutputFileArgument(instance, arguments);
	}
}
