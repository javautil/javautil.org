package org.javautil.poi.style;

import java.awt.Color;
import java.awt.Font;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.javautil.document.style.BoldWeight;
import org.javautil.document.style.FontAttributes;
import org.javautil.document.style.FontUnderlineStyle;

public class PoiFont {
	private HSSFWorkbook workbook;
	private final MultiKeyMap fontCache = new MultiKeyMap();

	public PoiFont(final HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public HSSFFont getHSSFFont(final Font font, final FontAttributes attr,
			final Color color, final BoldWeight boldWeight) {
		HSSFFont f = getCachedHSSFFont(font, attr, color, boldWeight);
		if (f == null) {
			f = createHSSFFont(font, attr, color, boldWeight);
		}
		return f;
	}

	private HSSFFont getCachedHSSFFont(final Font font,
			final FontAttributes attr, final Color color,
			final BoldWeight boldWeight) {
		final MultiKey key = new MultiKey(font, attr, color, boldWeight);
		return (HSSFFont) fontCache.get(key);
	}

	private HSSFFont createHSSFFont(final Font font, final FontAttributes attr,
			final Color color, final BoldWeight boldWeight) {
		final HSSFFont hssfFont = getWorkbook().createFont();

		if (color != null) {
			final HSSFColor hssfColor = getHSSFColor(color);
			if (hssfColor != null) {
				hssfFont.setColor(hssfColor.getIndex());
			}
		}
		if (font != null) {
			// todo jjs where did these constants come from
			hssfFont.setBoldweight((short) (font.isBold() ? 0x2bc : 0x190));
			hssfFont.setItalic(font.isItalic());
			final int fontOneTwentiethPoints = font.getSize() * 20;
			hssfFont.setFontHeight((short) fontOneTwentiethPoints);
			hssfFont.setFontName(font.getName());
		}
		if (boldWeight != null) {
			switch (boldWeight) {
			case NORMAL:
				hssfFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_NORMAL);
				break;
			case BOLD:
				hssfFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
			}
		}
		if (attr != null) {
			applyUnderlineStyle(attr, hssfFont);
		}
		if (font != null && color != null) {
			final MultiKey key = new MultiKey(font, attr, color);
			fontCache.put(key, hssfFont);
		}
		return hssfFont;
	}

	private HSSFColor getHSSFColor(final Color color) {
		return null;
	}

	void applyWrapStyle(final FontAttributes attr, final HSSFCellStyle cellStyle) {
		final String _wrapText = attr.get(FontAttributes.KEY_WORD_WRAP);
		if (_wrapText != null) {
			final boolean wrapText = Boolean.valueOf(_wrapText);
			cellStyle.setWrapText(wrapText);
		}
	}

	private void applyUnderlineStyle(final FontAttributes attr,
			final HSSFFont hssfFont) {
		final String _underlineStyle = attr
				.get(FontAttributes.KEY_UNDERLINE_STYLE);
		if (_underlineStyle != null) {
			byte hssfUnderline = org.apache.poi.ss.usermodel.Font.U_NONE;
			final FontUnderlineStyle underlineStyle = FontUnderlineStyle
					.valueOf(_underlineStyle);
			switch (underlineStyle) {
			case DOUBLE:
				hssfUnderline = org.apache.poi.ss.usermodel.Font.U_DOUBLE;
				break;
			case SINGLE:
				hssfUnderline = org.apache.poi.ss.usermodel.Font.U_SINGLE;
				break;
			case NONE:
				hssfUnderline = org.apache.poi.ss.usermodel.Font.U_NONE;
				break;
			default:
				throw new IllegalStateException("unimplemented underline case "
						+ underlineStyle.toString());
			}
			hssfFont.setUnderline(hssfUnderline);
		}
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(final HSSFWorkbook workbook) {
		this.workbook = workbook;
	}
}
