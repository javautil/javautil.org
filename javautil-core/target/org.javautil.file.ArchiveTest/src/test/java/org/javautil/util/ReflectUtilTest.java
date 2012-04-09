package org.javautil.util;

import junit.framework.Assert;

import org.javautil.lang.reflect.ReflectUtils;
import org.junit.Test;

public class ReflectUtilTest {
	@Test
	public void testListPropertiesAsString() {
		final Object pawn = new TestClass();
		final String props = ReflectUtils.listPropertiesAsString(pawn);
		// UnitTests.getLogger(this.getClass()).debug(props);
		Assert.assertTrue(props.contains(String.valueOf(21)));
		Assert.assertTrue(props.contains("javaPropertyValue"));
		Assert.assertTrue(props.contains("integerProperty"));
		Assert.assertTrue(props.contains("stringProperty"));
	}

	class TestClass {
		int integerProperty = 21;
		String stringProperty = "javaPropertyValue";
	}
}
