package org.javautil.document.style;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

public class ColorUtilTest {

	@Test
	public void test() {
		final Color red = new Color(255, 0, 0);
		final String rgbHex = ColorUtil.toRgbHex(red);
		assertEquals("#ff0000", rgbHex);

	}

	@Test
	public void testReveng() {
		final Color red = new Color(255, 0, 0);
		final Color reveng = ColorUtil.parseColorFromRgbHexString("#ff0000");
		assertEquals(red, reveng);
	}

}
