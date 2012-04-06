package org.javautil.jfm.mains.mvc;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SpringFTLReportGeneratorExample {

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
		BasicConfigurator.configure();
		final String argsString = "-outputFile=target/examples/mvc/person_report.ftl" //
				+ " -htmlTableClass=pigglyWiggly" //
				+ " -javaClassFile=src/main/resources/Person.class"; //
		final String[] args = argsString.split(" ");
		SpringFTLReportGenerator.main(args);
		return new File("target/examples/mvc/person_report.ftl");
	}

	// TODO fix this test
	@Ignore
	@Test
	public void createAnimalReportTest() throws Exception {
		createAnimalReport();
	}

	public File createAnimalReport() throws Exception {
		BasicConfigurator.configure();
		final String argsString = "-outputFile=target/examples/mvc/animal_report.ftl" //
				+ " -htmlTableClass=hollywood" //
				+ " -javaClassFile=src/main/resources/Animal.class"; //
		final String[] args = argsString.split(" ");
		SpringFTLReportGenerator.main(args);
		return new File("target/examples/mvc/animal_report.ftl");
	}
}
