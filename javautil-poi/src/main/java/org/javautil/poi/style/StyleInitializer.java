package org.javautil.poi.style;

import java.awt.Color;
import java.awt.Font;

import org.javautil.document.style.ColorUtil;
import org.javautil.document.style.HorizontalAlignment;
import org.javautil.document.style.Style;
import org.javautil.document.style.StyleImpl;

public class StyleInitializer {

	public Style baseHeadingStyle;
	public Style headingStyleLeft;
	public Style headingStyleRight;
	public Style baseDataStyle;
	public Style dataStyleLeft;
	public Style dataStyleRight;
	public Style dataStyleDate;
	public Style groupStyleInner;
	public Style groupStyleOuter;

	public void initialize(final HSSFCellStyleFactory styles) {
		final Color c = ColorUtil.parseColor("#6996AD");
		final Color c1 = ColorUtil.parseColor("#FFFFFF");
		final Style headingStyle = new StyleImpl();
		headingStyle.setBackgroundColor(c);
		headingStyle.setFontColor(c1);
		final Font font8 = new Font("Arial", Font.PLAIN, 8);
		final Font font10 = new Font("Arial", Font.PLAIN, 10);
		final Font font12 = new Font("Arial", Font.PLAIN, 12);
		final Style dataStyle = new StyleImpl();
		styles.setBaseDataStyle(dataStyle);
		styles.setBaseHeaderStyle(headingStyle);
		styles.setBaseFooterStyle(headingStyle);

		baseHeadingStyle = styles.getBaseHeaderStyle();
		baseHeadingStyle.setBackgroundColor(c);
		baseHeadingStyle.setFontColor(c1);
		baseHeadingStyle.setFont(font8);
		baseHeadingStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

		headingStyleLeft = baseHeadingStyle.copyOf();
		headingStyleLeft.setHorizontalAlignment(HorizontalAlignment.LEFT);
		headingStyleLeft.setFont(font10);

		headingStyleRight = baseHeadingStyle.copyOf();
		headingStyleRight.setHorizontalAlignment(HorizontalAlignment.RIGHT);

		baseDataStyle = styles.getBaseDataStyle();
		baseDataStyle.setFont(font8);
		baseDataStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

		dataStyleLeft = baseDataStyle.copyOf();
		dataStyleLeft.setHorizontalAlignment(HorizontalAlignment.LEFT);

		dataStyleRight = baseDataStyle.copyOf();
		dataStyleRight.setHorizontalAlignment(HorizontalAlignment.RIGHT);

		dataStyleDate = baseDataStyle.copyOf();
		dataStyleDate.setHorizontalAlignment(HorizontalAlignment.RIGHT);

		groupStyleInner = baseDataStyle.copyOf();
		groupStyleInner.setHorizontalAlignment(HorizontalAlignment.LEFT);
		groupStyleInner.setFont(font10);

		groupStyleOuter = groupStyleInner.copyOf();
		groupStyleOuter.setFont(font12);

	}
}
