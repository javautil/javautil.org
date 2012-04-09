package org.javautil.document.style;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HorizontalAlignmentTest {

	@Test
	public void test() {
		assertEquals(HorizontalAlignment.LEFT,
				HorizontalAlignment.getFromAbbreviation("L"));
		assertEquals(HorizontalAlignment.RIGHT,
				HorizontalAlignment.getFromAbbreviation("R"));
		assertEquals(HorizontalAlignment.CENTER,
				HorizontalAlignment.getFromAbbreviation("C"));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testInvalid() {
		HorizontalAlignment.getFromAbbreviation("l");
	}

}
