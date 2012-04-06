package org.javautil.lang;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ThreadHelperTest {

	// todo this could be helped by another
	@Test
	public void test() {
		assertEquals("ThreadHelperTest",
				ThreadHelper.getCallerSimpleClassName());
	}

	@Test
	public void test2() {
		assertEquals("org.javautil.lang.ThreadHelperTest",
				ThreadHelper.getCallerClassName());
	}
}
