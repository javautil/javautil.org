package org.javautil.poi.example1;

import java.awt.Color;

import org.javautil.document.style.ColorUtil;
import org.javautil.document.style.Style;
import org.javautil.document.style.StyleImpl;
import org.javautil.poi.style.HSSFCellStyleFactory;

public class ExampleWorkbook1 {
	// T
	protected void initializeStyles(final HSSFCellStyleFactory styleFactory) {
		final Color c = ColorUtil.parseColor("#6996AD");
		final Color c1 = ColorUtil.parseColor("#FFFFFF");
		final Style headingStyle = new StyleImpl();
		headingStyle.setBackgroundColor(c);
		headingStyle.setFontColor(c1);
		styleFactory.setBaseHeaderStyle(headingStyle);

		styleFactory.setBaseDataStyle(new StyleImpl());
		styleFactory.setBaseFooterStyle(new StyleImpl());
	}
}
