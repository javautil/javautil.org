package org.javautil.reflect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SpringFieldHelperTest {

	@SuppressWarnings("serial")
	Map<String, String> values = new HashMap<String, String>();

	@Test
	public void testClass1() {
		values.clear();
		values.put("stringFieldWithSetter", "A");
		values.put("longNumberField", "32");

		Class1 cl = new Class1();
		SpringFieldHelper fh = new SpringFieldHelper();
		fh.set(cl, values);
		assertEquals("A", cl.getStringFieldWithSetter());
		// nosetter
		assertNull(cl.getStringFieldWithoutSetter());
		// no setter
		assertNull(cl.longNumberField);
	}

	public void test2() {
		values.clear();

		values.put("privateIntegerField", "64");
		values.put("stringFieldWithSetter", "B");
		Class1 cl = new Class1();
		SpringFieldHelper fh = new SpringFieldHelper();
		fh.set(cl, values);
		assertEquals("B", cl.getStringFieldWithSetter());
		assertEquals(new Integer(64), cl.getPrivateIntegerField());
		assertNull(cl.getStringFieldWithoutSetter());

	}
}
