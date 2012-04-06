package org.javautil.poi.style;

import java.awt.Color;
import java.awt.Font;
import java.awt.Transparency;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.record.PaletteRecord;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.javautil.document.style.FontAttributes;
import org.javautil.document.style.FontUnderlineStyle;
import org.javautil.document.style.HorizontalAlignment;
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
public class HSSFCellStyleFactory {
	private final String newline = System.getProperty("line.separator");
	private int nextHSSFPaletteColorIndex = PaletteRecord.FIRST_COLOR_INDEX; // 0x8

	private final Integer maximumColorIndex = PaletteRecord.FIRST_COLOR_INDEX
			+ PaletteRecord.STANDARD_PALETTE_SIZE; // 0x40

	private final HSSFWorkbook workbook;

	private final HSSFPalette palette;

	private final MultiKeyMap fontCache = new MultiKeyMap();

	private final Map<Color, HSSFColor> colorCache = new LinkedHashMap<Color, HSSFColor>();

	private final Map<Style, HSSFCellStyle> stylesCache = new LinkedHashMap<Style, HSSFCellStyle>();

	private final Set<Integer> reservedColorIndexes = new HashSet<Integer>();

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
	public HSSFCellStyleFactory(final HSSFWorkbook workbook) {
		if (workbook == null) {
			throw new IllegalArgumentException("workbook is null");
		}
		this.workbook = workbook;
		palette = workbook.getCustomPalette();
	}

	protected int getNextHSSFPaletteColorIndex() {
		if (maximumColorIndex != null
				&& nextHSSFPaletteColorIndex > maximumColorIndex) {
			throw new IllegalStateException("too many custom colors in palette");
		}
		Integer ret = null;
		while (ret == null || reservedColorIndexes.contains(ret)) {
			ret = nextHSSFPaletteColorIndex++;
		}
		// todo jjs boxing and unboxing what was the intent and where is the
		// test?
		// todo jjs this not possible
		// if (ret == null) {
		// throw new IllegalStateException("too many custom colors in palette");
		// }
		return ret.intValue();
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	protected HSSFPalette getPalette() {
		return palette;
	}

	public HSSFCellStyle getHSSFCellStyle(final Style style) {
		HSSFCellStyle s = getCachedHSSFCellStyle(style);
		if (s == null) {
			s = createHSSFCellStyle(style);
		}
		return s;
	}

	private HSSFCellStyle getCachedHSSFCellStyle(final Style style) {
		HSSFCellStyle retval = stylesCache.get(style);
		if (retval == null) {
			createHSSFCellStyle(style);
			retval = stylesCache.get(style);
		}
		if (retval == null) {
			throw new IllegalStateException("failed to find or create style "
					+ style.toString());
		}
		return retval;
	}

	public HSSFCellStyle getHSSFCellStyle(final Style baseStyle,
			final String excelFormat,
			final HorizontalAlignment horizontalAlignment) {
		final Style s = baseStyle.copyOf();
		s.setHorizontalAlignment(horizontalAlignment);
		s.setFormatMask(excelFormat);
		final HSSFCellStyle retval = getCachedHSSFCellStyle(s);
		return retval;
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
			final HSSFColor hssfColor = getHSSFColor(style.getBackgroundColor());
			if (hssfColor != null) {
				s.setFillPattern(CellStyle.SOLID_FOREGROUND);
				s.setFillForegroundColor(hssfColor.getIndex());
			}
		}
		final HSSFFont hssfFont = getHSSFFont(style.getFont(),
				style.getFontAttributes(), style.getFontColor());
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
			applyWrapStyle(style.getFontAttributes(), s);
		}
		stylesCache.put(style, s);

		return s;
	}

	public static Map<String, HSSFCellStyle> getHSSFCellStyles(
			final HSSFWorkbook workbook, final Collection<Style> styles) {
		final HSSFCellStyleFactory factory = new HSSFCellStyleFactory(workbook);
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

	public HSSFFont getHSSFFont(final Font font, final FontAttributes attr,
			final Color color) {
		HSSFFont f = getCachedHSSFFont(font, attr, color);
		if (f == null) {
			f = createHSSFFont(font, attr, color);
		}
		return f;
	}

	private HSSFFont getCachedHSSFFont(final Font font,
			final FontAttributes attr, final Color color) {
		final MultiKey key = new MultiKey(font, attr, color);
		return (HSSFFont) fontCache.get(key);
	}

	private HSSFFont createHSSFFont(final Font font, final FontAttributes attr,
			final Color color) {
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
		if (attr != null) {
			applyUnderlineStyle(attr, hssfFont);
		}
		if (font != null && color != null) {
			final MultiKey key = new MultiKey(font, attr, color);
			fontCache.put(key, hssfFont);
		}
		return hssfFont;
	}

	private void applyWrapStyle(final FontAttributes attr,
			final HSSFCellStyle cellStyle) {
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

	public HSSFColor getHSSFColor(final Color color) {
		HSSFColor c = getCachedHSSFColor(color);
		if (c == null) {
			c = createHSSFColor(color);
		}
		return c;
	}

	private HSSFColor getCachedHSSFColor(final Color color) {
		return colorCache.get(color);
	}

	private HSSFColor createHSSFColor(final Color color) {
		final HSSFPalette palette = getPalette();
		HSSFColor hssfColor = null;
		final byte r = (byte) color.getRed();
		final byte g = (byte) color.getGreen();
		final byte b = (byte) color.getBlue();
		hssfColor = palette.findColor(r, g, b);
		if (hssfColor == null) {
			try {
				final int index = getNextHSSFPaletteColorIndex();
				palette.setColorAtIndex((short) index, (byte) color.getRed(),
						(byte) color.getGreen(), (byte) color.getBlue());
				hssfColor = palette.getColor(index);
				if (hssfColor == null) {
					throw new IllegalStateException(
							"invalid color created at index " + index);
				}
			} catch (final Exception e) {
				hssfColor = palette.findSimilarColor(r, g, b);
				logger.warn("error after adding " + colorCache.size()
						+ " colors", e);
			}
		} else {
			reservedColorIndexes.add((int) hssfColor.getIndex());
		}
		colorCache.put(color, hssfColor);
		return hssfColor;
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
