package org.javautil;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.javautil.java.JavaFile;
import org.javautil.lang.SystemProperties;
import org.junit.BeforeClass;
import org.junit.Test;

public class JavaFileTest {
	private final Logger logger = Logger.getLogger(getClass());
	private static final String directorySeparator = SystemProperties
			.getFileSeparator();

	@BeforeClass
	public static void beforeClass() {
		BasicConfigurator.configure();
	}

	@Test
	public void test1() {
		final JavaFile javaFile = new JavaFile();
		final File f = javaFile
				.getJavaFile("/tmp", "org.javautil.test", "Test");
		final String filePath = f.getAbsolutePath();
		String expectedPath = null;
		logger.debug("File Separator is " + SystemProperties.getFileSeparator());
		if (SystemProperties.getFileSeparator().equals("/")) {
			expectedPath = "/tmp/org/javautil/test/Test.java";
			assertEquals(expectedPath, filePath);
		}
		if (SystemProperties.getFileSeparator().equals("\\")) {
			expectedPath = "\\tmp\\org\\javautil\\test\\Test.java";
			assertEquals(expectedPath, filePath.substring(2));
		}
		logger.info("Expected Path is : " + expectedPath);
		// expectedPath = expectedPath.replace("/", directorySeparator);

	}
}
