package org.javautil.document.style;

import java.awt.Color;

/**
 * 
 * @author jjs at dbexperts dot com
 * 
 */
public class StyleFactory {

	public static Style getStyle(final HorizontalAlignment horizontalAlignment,
			final String formatMask) {
		final Style s = new StyleImpl();
		s.setHorizontalAlignment(horizontalAlignment);
		s.setFormatMask(formatMask);
		return s;

	}

	public static Style getStyle(final HorizontalAlignment horizontalAlignment,
			final Color backgroundColor, final String formatMask) {
		final Style s = new StyleImpl();
		s.setHorizontalAlignment(horizontalAlignment);
		s.setBackgroundColor(backgroundColor);
		s.setFormatMask(formatMask);
		return s;

	}
}
