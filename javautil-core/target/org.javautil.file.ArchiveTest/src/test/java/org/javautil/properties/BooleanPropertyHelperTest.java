package org.javautil.properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

public class BooleanPropertyHelperTest {

	public static void beforeClass() {
		BasicConfigurator.configure();
	}

	@Test
	public void defaultBehaviorTest() {
		final BooleanPropertyHelper helper = new BooleanPropertyHelper();
		assertTrue(helper.parse("y"));
		assertTrue(helper.parse("1"));
		assertTrue(helper.parse("true"));
		assertTrue(helper.parse("yes"));
		assertFalse(helper.parse("n"));
		assertFalse(helper.parse("0"));
		assertFalse(helper.parse("no"));
		assertFalse(helper.parse("false"));
		assertTrue(helper.parse("Y"));
		assertTrue(helper.parse("TRUE"));
		assertTrue(helper.parse("YES"));
		assertFalse(helper.parse("N"));
		assertFalse(helper.parse("0"));
		assertFalse(helper.parse("No"));
		assertFalse(helper.parse("False"));
		assertFalse(helper.parse("FALSE"));
	}

	@Test(expected = org.javautil.properties.IllegalPropertyValueException.class)
	public void defaultBehaviorInvalidValueTest() {
		final BooleanPropertyHelper helper = new BooleanPropertyHelper();
		helper.parse("truth");

	}

	@Test
	public void customBehaviorValidValuesTest() {
		final BooleanPropertyHelper bph = new BooleanPropertyHelper(true,
				new String[] { "Y" }, new String[] { "N" });

		assertTrue(bph.parse("Y"));
		assertFalse(bph.parse("N"));
		try {
			bph.parse("true");
			assert (1 == 2);
		} catch (final IllegalPropertyValueException ipve) {
			// the expected result
		}
	}
}
