package org.javautil.properties;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class IntegerPropertyHelperTest {
	@Test
	public void test1() {
		final IntegerPropertyHelper t = new IntegerPropertyHelper("a", 0, 3);
		t.parse("0");
	}

	@Test(expected = IllegalPropertyValueException.class)
	public void testMinValue() {
		final IntegerPropertyHelper t = new IntegerPropertyHelper("a", 0, 3);
		t.parse("-1");
	}

	@Test(expected = IllegalPropertyValueException.class)
	public void testMaxValue() {
		final IntegerPropertyHelper t = new IntegerPropertyHelper("a", 0, 3);
		t.parse("-1");
	}

	@Test(expected = NumberFormatException.class)
	public void testNonInteger() {
		final IntegerPropertyHelper t = new IntegerPropertyHelper("a", 0, 3);
		t.parse("x32");
	}

	@Test
	public void testNull() {
		final IntegerPropertyHelper t = new IntegerPropertyHelper("a", 0, 3);
		final Integer val = t.parse(null);
		assertNull(val);
	}

	@Test
	public void testEmpty() {
		final IntegerPropertyHelper t = new IntegerPropertyHelper("a", 0, 3);
		final Integer val = t.parse("");
		assertNull(val);
	}
}
