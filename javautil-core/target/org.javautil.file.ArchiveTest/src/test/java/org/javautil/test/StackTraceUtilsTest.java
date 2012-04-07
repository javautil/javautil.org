package org.javautil.test;

import static org.junit.Assert.assertEquals;

import org.javautil.lang.SystemProperties;
import org.junit.Test;

public class StackTraceUtilsTest {

	@Test
	public void test1() {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		final String path = StackTraceUtils.getResourceMethodPath(ste);
		if (SystemProperties.getFileSeparator().equals("/")) {
			assertEquals("org/javautil/test/StackTraceUtilsTest.test1", path);
		}
		if (SystemProperties.getFileSeparator().equals("\\")) {
			assertEquals("org\\javautil\\test\\StackTraceUtilsTest.test1", path);
		}
	}
}
