package org.javautil.jfm.mains.mvc;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SpringControllerGeneratorExample {

	@BeforeClass
	public static void prepareDirectories() {
		new File("target/examples/mvc").mkdirs();
	}

	// TODO fix this test
	@Ignore
	@Test
	public void createControllerTest() throws Exception {
		createController();
	}

	public File createController() throws Exception {
		final String argsString = "-outputFile=target/examples/mvc/PersonReportAndFormController.java" //
				+ " -packageName=com.mycompany.web.controller" //
				+ " -javaClassFile=src/main/resources/Person.class"; //
		final String[] args = argsString.split(" ");
		SpringControllerGenerator.main(args);
		return new File(
				"target/examples/mvc/PersonReportAndFormController.java");
	}

}
