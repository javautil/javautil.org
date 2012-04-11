package org.javautil.jfm.mains.mvc;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SpringFTLFormGeneratorExample {

	@BeforeClass
	public static void prepareDirectories() {
		new File("target/examples/mvc").mkdirs();
	}

	// TODO fix this test
	@Ignore
	@Test
	public void createReportTest() throws Exception {
		createReport();
	}

	public File createReport() throws Exception {
		final String argsString = "-outputFile=target/examples/mvc/person_form.ftl" //
				+ " -htmlFormAction=/myContext/myServlet/myController/myView.html" //
				+ " -javaClassFile=src/main/resources/Person.class"; //
		final String[] args = argsString.split(" ");
		SpringFTLFormGenerator.main(args);
		return new File("target/examples/mvc/person_form.ftl");
	}

}
