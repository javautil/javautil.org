package org.javautil.document.style;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

public class SimpleBorderTest {

	@Test
	public void testBorders() {
		final SimpleBorder sb = new SimpleBorder();
		sb.setTopLineWidth(1);
		assertEquals(1, sb.getTopLineWidth());
		sb.setBottomLineWidth(4);
		assertEquals(4, sb.getBottomLineWidth());
		sb.setLeftLineWidth(3);
		assertEquals(3, sb.getLeftLineWidth());
		sb.setRightLineWidth(2);
		assertEquals(2, sb.getRightLineWidth());
	}

	public void testColors() {
		final Color red = new Color(255, 0, 0);
		final Color green = new Color(0, 255, 0);
		final Color blue = new Color(0, 0, 255);
		final Color white = new Color(255, 255, 255);
		final SimpleBorder sb = new SimpleBorder();
		sb.setLeftLineColor(red);
		assertEquals(red, sb.getLeftLineColor());
		sb.setRightLineColor(green);
		assertEquals(green, sb.getRightLineColor());
		sb.setTopLineColor(blue);
		assertEquals(blue, sb.getTopLineColor());
		sb.setBottomLineColor(white);
		assertEquals(white, sb.getBottomLineColor());

	}
}
