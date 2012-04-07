package org.javautil.document.style;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class StyleParserImplTest extends StyleDefinitionsTest {

	private Map<String, Style> styles;

	@Before
	public void parse() {
		final StyleParser parser = new DefaultStyleParser();
		styles = StyleUtil.getStylesFromContext(appContext, parser);
	}

	@Test
	public void testCurrency() {
		final Style style = styles.get("currency");
		Assert.assertEquals(0, style.getFontColor().getRed());
		Assert.assertEquals(0, style.getFontColor().getGreen());
		Assert.assertEquals(0, style.getFontColor().getBlue());
		Assert.assertEquals("$###,###,###,###,##0.00", style.getFormatMask());
	}

	@Test
	public void testWrap() {
		final Style style = styles.get("wrap");
		Assert.assertEquals(Boolean.TRUE.toString(), style.getFontAttributes()
				.get(FontAttributes.KEY_WORD_WRAP));
	}

	@Test
	public void testSingleUnderline() {
		final Style style = styles.get("singleUnderline");
		Assert.assertEquals(FontUnderlineStyle.SINGLE.toString(), style
				.getFontAttributes().get(FontAttributes.KEY_UNDERLINE_STYLE));
	}

	@Test
	public void testDoubleUnderline() {
		final Style style = styles.get("doubleUnderline");
		Assert.assertEquals(FontUnderlineStyle.DOUBLE.toString(), style
				.getFontAttributes().get(FontAttributes.KEY_UNDERLINE_STYLE));
	}

	@Test
	public void testThreeDecimal() {
		final Style style = styles.get("threeDecimal");
		Assert.assertEquals(0, style.getFontColor().getRed());
		Assert.assertEquals(0, style.getFontColor().getGreen());
		Assert.assertEquals(0, style.getFontColor().getBlue());
		Assert.assertEquals("###,###,###,###,##0.000", style.getFormatMask());
	}

	@Test
	public void testIntegerBlueBg() {
		final Style style = styles.get("integerBlueBg");
		Assert.assertEquals(153, style.getBackgroundColor().getRed());
		Assert.assertEquals(153, style.getBackgroundColor().getGreen());
		Assert.assertEquals(204, style.getBackgroundColor().getBlue());
	}

	@Test
	public void testStandard() {
		final Style style = styles.get("standard");
		Assert.assertEquals(10, style.getFont().getSize());
		Assert.assertEquals("Arial", style.getFont().getName());
		Assert.assertFalse(style.getFont().isBold());
		Assert.assertFalse(style.getFont().isItalic());
	}

	@Test
	public void testBold() {
		final Style style = styles.get("bold");
		Assert.assertEquals(10, style.getFont().getSize());
		Assert.assertEquals("Arial", style.getFont().getName());
		Assert.assertTrue(style.getFont().isBold());
		Assert.assertFalse(style.getFont().isItalic());
	}

	@Test
	public void testItalic() {
		final Style style = styles.get("italic");
		Assert.assertEquals(10, style.getFont().getSize());
		Assert.assertEquals("Arial", style.getFont().getName());
		Assert.assertFalse(style.getFont().isBold());
		Assert.assertTrue(style.getFont().isItalic());
	}

	@Test
	public void testHugeFont() {
		final Style style = styles.get("hugeFont");
		Assert.assertEquals(20, style.getFont().getSize());
		Assert.assertEquals("Courier New", style.getFont().getName());
		Assert.assertTrue(style.getFont().isBold());
	}

	@Test
	public void testRedInteger() {
		final Style style = styles.get("redInteger");
		Assert.assertEquals(255, style.getFontColor().getRed());
		Assert.assertEquals(0, style.getFontColor().getGreen());
		Assert.assertEquals(0, style.getFontColor().getBlue());
	}

	@Test
	public void testInteger() {
		final Style style = styles.get("integer");
		Assert.assertEquals(0, style.getFontColor().getRed());
		Assert.assertEquals(0, style.getFontColor().getGreen());
		Assert.assertEquals(0, style.getFontColor().getBlue());
		Assert.assertEquals("###,###,###,###,##0", style.getFormatMask());
	}
}
