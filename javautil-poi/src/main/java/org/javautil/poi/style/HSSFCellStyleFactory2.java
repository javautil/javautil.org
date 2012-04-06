package org.javautil.poi.style;

import java.awt.Color;
import java.awt.Transparency;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.javautil.document.style.Style;

/**
 * Provides methods for converting a Style to a HSSFCellStyle. Interning of
 * references to fonts, styles, and colors is handled automatically. If the
 * maximum number of allowed spreadsheet formats/colors/fonts is exceeded, the
 * style conversion process will fail. Sufficient knowledge of the limits of the
 * Microsoft Excel 97 format is recommended before using this class.
 * 
 * 
 * // comments jjs as this is tied to poi it should be called
 * HSSFCellStyleFactory
 * 
 * @author bcm
 * 
 */

public class HSSFCellStyleFactory2 {
	private final String newline = System.getProperty("line.separator");

	private final HSSFWorkbook workbook;

	private PoiColor colors;

	private PoiFont fonts;

	private final Map<Style, HSSFCellStyle> stylesCache = new LinkedHashMap<Style, HSSFCellStyle>();

	private final Log logger = LogFactory.getLog(getClass());

	private Style baseHeaderStyle;

	private Style baseDataStyle;

	private Style baseFooterStyle;

	/**
	 * Constructor handling interning of styles, fonts, and colors. Reference to
	 * workbook is required to manage custom palette, styles, and fonts.
	 * 
	 * @param workbook
	 */
	public HSSFCellStyleFactory2(final HSSFWorkbook workbook) {
		if (workbook == null) {
			throw new IllegalArgumentException("workbook is null");
		}
		this.workbook = workbook;

	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public HSSFCellStyle getHSSFCellStyle(final Style style) {
		HSSFCellStyle s = getCachedHSSFCellStyle(style);
		if (s == null) {
			s = createHSSFCellStyle(style);
		}
		return s;
	}

	// public HSSFCellStyle getHSSFCellStyleBold(Style style) {
	// HSSFCellStyle hcs = getHSSFCellStyle(style);
	// HSSFFont hf = hcs.getFont(workbook);
	// String fontName = hf.getFontName();
	// int fontHeight = hf.getFontHeight();
	// int color = hf.getColor();
	// int boldweight = hf.getBoldweight();
	// getCachedHSSFFont
	// }

	private HSSFCellStyle getCachedHSSFCellStyle(final Style style) {
		return stylesCache.get(style);
	}

	private HSSFCellStyle createHSSFCellStyle(final Style style) {
		if (style == null) {
			throw new IllegalArgumentException("style is null");
		}

		final HSSFCellStyle s = getWorkbook().createCellStyle();
		final Color bg = style.getBackgroundColor();
		final boolean notTransparent = bg != null
				&& (bg.getTransparency() == Transparency.OPAQUE || (bg
						.getTransparency() == Transparency.TRANSLUCENT && bg
						.getAlpha() != 255));
		if (notTransparent) {
			final HSSFColor hssfColor = colors.getHSSFColor(style
					.getBackgroundColor());
			if (hssfColor != null) {
				s.setFillPattern(CellStyle.SOLID_FOREGROUND);
				s.setFillForegroundColor(hssfColor.getIndex());
			}
		}
		final HSSFFont hssfFont = fonts.getHSSFFont(style.getFont(),
				style.getFontAttributes(), style.getFontColor(), null);
		s.setFont(hssfFont);
		if (style.getHorizontalAlignment() != null) {
			switch (style.getHorizontalAlignment()) {
			case LEFT:
				s.setAlignment(CellStyle.ALIGN_LEFT);
				break;
			case CENTER:
				s.setAlignment(CellStyle.ALIGN_CENTER);
				break;
			case RIGHT:
				s.setAlignment(CellStyle.ALIGN_RIGHT);
				break;
			default:
				throw new IllegalStateException("unimplemented halign case");
			}
		}
		if (style.getVerticalAlignment() != null) {
			switch (style.getVerticalAlignment()) {
			case TOP:
				s.setVerticalAlignment(CellStyle.VERTICAL_TOP);
				break;
			case MIDDLE:
				s.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				break;
			case BOTTOM:
				s.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
				break;
			default:
				throw new IllegalStateException("unimplemented valign case");
			}
		}
		if (style.getFormatMask() != null) {
			s.setDataFormat(getFormat(style.getFormatMask()));
		}
		if (style.getFontAttributes() != null) {
			fonts.applyWrapStyle(style.getFontAttributes(), s);
		}
		stylesCache.put(style, s);

		return s;
	}

	public static Map<String, HSSFCellStyle> getHSSFCellStyles(
			final HSSFWorkbook workbook, final Collection<Style> styles) {
		final HSSFCellStyleFactory2 factory = new HSSFCellStyleFactory2(
				workbook);
		final Map<String, HSSFCellStyle> hssfCellStyles = new LinkedHashMap<String, HSSFCellStyle>();
		for (final Style style : styles) {
			if (style == null) {
				throw new IllegalStateException("style is null");
			}
			final HSSFCellStyle hssfCellStyle = factory.getHSSFCellStyle(style);
			final String styleName = style.getName();
			hssfCellStyles.put(styleName, hssfCellStyle);
		}
		return hssfCellStyles;
	}

	private short getFormat(final String formatMask) {
		final HSSFDataFormat format = workbook.createDataFormat();
		final short formatIndex = format.getFormat(formatMask);
		return formatIndex;
	}

	public void setBaseHeaderStyle(final Style baseHeaderStyle) {
		this.baseHeaderStyle = baseHeaderStyle;
	}

	public Style getBaseHeaderStyle() {
		if (baseHeaderStyle == null) {
			throw new IllegalStateException(
					"baseHeaderStyle has not been set yet");
		}
		return baseHeaderStyle;
	}

	public void setBaseDataStyle(final Style baseDataStyle) {
		this.baseDataStyle = baseDataStyle;
	}

	public Style getBaseDataStyle() {
		return baseDataStyle;
	}

	public void setBaseFooterStyle(final Style baseFooterStyle) {
		this.baseFooterStyle = baseFooterStyle;
	}

	public Style getBaseFooterStyle() {
		return baseFooterStyle;
	}
}
