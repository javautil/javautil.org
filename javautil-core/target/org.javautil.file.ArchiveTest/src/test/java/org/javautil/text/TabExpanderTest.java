package org.javautil.text;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TabExpanderTest {

	@Test
	public void test1() {
		final TabExpander te = new TabExpander();
		final String in = "a\tb";
		// "01234567"
		final String expectedOut = "a       b";
		final String actualOut = te.expand(in, 8);
		assertEquals(expectedOut, actualOut);

	}

	@Test
	public void test2() {
		final TabExpander te = new TabExpander();
		final String in = "a  \tb";
		// "01234567"
		final String expectedOut = "a       b";
		final String actualOut = te.expand(in, 8);
		assertEquals(expectedOut, actualOut);

	}

	@Test
	public void test3() {
		final TabExpander te = new TabExpander();
		final String actualOut = te.expand(null, 8);
		assertEquals(null, actualOut);

	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void test4() {
		final TabExpander te = new TabExpander();
		te.expand("a", -1);
	}

}
