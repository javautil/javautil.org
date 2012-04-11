package org.javautil;

import java.util.Properties;

import org.javautil.io.ResourceUtils;
import org.junit.Ignore;
import org.junit.Test;

public class SpringRunnerTest {

	public static final String CONTEXT = "file:src/test/resources/xls-document-builder-test.xml";

	public static final String PROPERTIES = "file:src/test/resources/xls-document-builder-test.properties";

	// TODO figure out what this is trying to do and fix or delete
	@Ignore
	@Test
	public void test() throws Exception {
		SpringRunner.main(new String[] { CONTEXT, PROPERTIES });
		final Properties props = new Properties();
		props.load(ResourceUtils.asResource(PROPERTIES).getInputStream());
		final String outputFile = props.getProperty("outputFile");
		Runtime.getRuntime().exec("ooffice " + outputFile);
	}
}
