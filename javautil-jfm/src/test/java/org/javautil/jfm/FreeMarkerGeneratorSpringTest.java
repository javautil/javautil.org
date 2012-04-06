package org.javautil.jfm;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class FreeMarkerGeneratorSpringTest {

	private static File springControllerClass;

	private static File springConfigurationFile;

	private final Log logger = LogFactory.getLog(getClass());

	@BeforeClass
	public static void setup() throws Exception {
		// formFreemarkerTemplate = test.createForm();
		// reportFreemarkerTemplate = test.createReport();
		// springControllerSource = test.createSpringController();
		// springConfigurationFile = test.createSpringConfiguration();
		// JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		// if (compiler.run(null, null, null, springControllerSource
		// .getAbsolutePath()) != 0) {
		// Assert.fail("compile of controller failed");
		// }
		// springControllerClass = new File(springControllerSource
		// .getAbsolutePath().replace(".java", ".class"));
	}

	@Before
	public void ensureFilesExist() {
		// Assert.assertTrue(formFreemarkerTemplate.exists());
		// Assert.assertTrue(reportFreemarkerTemplate.exists());
		// Assert.assertTrue(springControllerSource.exists());
		// Assert.assertTrue(springConfigurationFile.exists());
		// Assert.assertTrue(springControllerClass.exists());
	}

	// TODO fix this test
	@Ignore
	@Test
	public void testSpringContext() throws Exception {
		final String[] configFiles = new String[] { "file://"
				+ springConfigurationFile.getAbsolutePath() };
		final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
				configFiles);
		logger.debug("loading classes from "
				+ springControllerClass.getAbsolutePath());
		final URL[] urls = new URL[] { springControllerClass.toURI().toURL() };
		final URLClassLoader classLoader = new URLClassLoader(urls);
		context.setClassLoader(classLoader);
		context.start();
	}
}
