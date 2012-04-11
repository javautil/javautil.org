package org.javautil.jfm.mains;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class FreeMarkerGeneratorExample {

	public static void main(final String[] args) throws Exception {
		new FreeMarkerGeneratorExample().createReport();
	}

	@BeforeClass
	public static void prepareDirectories() {
		new File("target/examples").mkdir();
	}

	// TODO fix this test
	@Ignore
	@Test
	public void createReportTest() throws Exception {
		createReport();
	}

	public File createReport() throws Exception {
		final String argsString = "-outputFile=target/examples/person_example.ftl" //
				+ " -templateFile=src/main/resources/example.ftl" //
				+ " -javaClassFile=src/main/resources/Person.class"; //
		final String[] args = argsString.split(" ");
		FreeMarkerGenerator.main(args);
		return new File("target/examples/person_example.ftl");
	}
}
