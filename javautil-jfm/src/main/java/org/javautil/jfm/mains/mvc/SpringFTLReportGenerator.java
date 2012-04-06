package org.javautil.jfm.mains.mvc;

import org.javautil.jfm.JFMGenerator;
import org.javautil.jfm.mains.FreeMarkerGenerator;

public class SpringFTLReportGenerator extends FreeMarkerGenerator {

	public static void main(final String[] args) throws Exception {
		final SpringFTLReportGeneratorArguments arguments = new SpringFTLReportGeneratorArguments();
		arguments.processArgs(args);
		final JFMGenerator instance = getInstance(
				SpringFTLReportGenerator.class, arguments);
		FreeMarkerGenerator.generateToOutputFileArgument(instance, arguments);
	}
}
