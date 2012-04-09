package org.javautil.document.style;

import java.awt.Color;
import java.awt.Font;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class StyleImplTest {

	private StyleImpl style;

	@Before
	public void testConstructor() {
		style = new StyleImpl();
	}

	@Test
	public void testGettersAndSetters() {

		style.setBackgroundColor(Color.WHITE);
		Assert.assertEquals(Color.WHITE, style.getBackgroundColor());

		final Font monospaceFont = Font.getFont("Monospace");
		style.setFont(monospaceFont);
		Assert.assertEquals(monospaceFont, style.getFont());

		final FontAttributesImpl fontAttributesImpl = new FontAttributesImpl();
		style.setFontAttributes(fontAttributesImpl);
		Assert.assertEquals(fontAttributesImpl, style.getFontAttributes());

		style.setFontColor(Color.BLACK);
		Assert.assertEquals(Color.BLACK, style.getFontColor());

		final String formatMask = "###,###,###,##0.00";
		style.setFormatMask(formatMask);
		Assert.assertEquals(formatMask, style.getFormatMask());

		style.setHorizontalAlignment(HorizontalAlignment.LEFT);
		Assert.assertEquals(HorizontalAlignment.LEFT,
				style.getHorizontalAlignment());

		style.setName("dollars");
		Assert.assertEquals("dollars", style.getName());

		style.setVerticalAlignment(VerticalAlignment.MIDDLE);
		Assert.assertEquals(VerticalAlignment.MIDDLE,
				style.getVerticalAlignment());
	}
}
