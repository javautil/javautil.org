package org.javautil;

import java.io.FileInputStream;
import java.util.Properties;

import org.javautil.io.IOUtils;
import org.javautil.io.ResourceUtils;
import org.junit.Test;

public class SpringRunnerTest {

	public static final String CONTEXT = "file:src/test/resources/document-builder-test.xml";

	public static final String PROPERTIES = "file:src/test/resources/document-builder-test.properties";

	@Test
	public void test() throws Exception {
		SpringRunner.main(new String[] { CONTEXT, PROPERTIES });
		final Properties props = new Properties();
		props.load(ResourceUtils.asResource(PROPERTIES).getInputStream());
		final String outputFile = props.getProperty("outputFile");
		IOUtils.readStringFromStream(new FileInputStream(outputFile), true);
	}

}
