package org.javautil.workbook.poi;

import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */

public abstract class StyleLibrary implements CellStyleLibrary {

	private final Map<String, HSSFCellStyle> styles = new TreeMap<String, HSSFCellStyle>();

	public static HSSFCellStyle createStyle(final HSSFWorkbook workbook,
			final Short alignment, final HSSFFont font,
			final String dataFormat, final Short fillPattern,
			final Short foregroundColor, final Short backgroundColor

	) {
		HSSFCellStyle style;
		style = workbook.createCellStyle();
		if (alignment != null) {
			style.setAlignment(alignment.shortValue());
		}
		if (font != null) {
			style.setFont(font);
		}
		if (dataFormat != null) {
			style.setDataFormat(workbook.createDataFormat().getFormat(
					dataFormat));
		}
		if (fillPattern != null) {
			style.setFillPattern(fillPattern.shortValue());
		}
		if (foregroundColor != null) {
			style.setFillForegroundColor(foregroundColor.shortValue());
		}
		if (backgroundColor != null) {
			style.setFillBackgroundColor(backgroundColor.shortValue());
		}

		return style;
	}

	// abstract public Map<STrin>

	@Override
	public Map<String, HSSFCellStyle> getStyles() {
		return styles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.poi.CellStyleLibrary#getStyle(java.lang.String)
	 */
	@Override
	public HSSFCellStyle getStyle(final String styleName) {
		return styles.get(styleName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.poi.CellStyleLibrary#addStyle(java.lang.String,
	 * org.apache.poi.hssf.usermodel.HSSFCellStyle)
	 */
	@Override
	public void addStyle(final String styleName, final HSSFCellStyle cellStyle) {
		styles.put(styleName, cellStyle);

	}

	@Override
	public abstract HSSFFont getFont(String fontName);

}
