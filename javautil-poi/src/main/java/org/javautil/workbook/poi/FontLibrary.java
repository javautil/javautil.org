package org.javautil.workbook.poi;

import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public abstract class FontLibrary {
	public static final int eightPoint = 8;
	public static final int tenPoint = 10;
	public static final int twelvePoint = 12;
	public static final int fourteenPoint = 14;
	// todo cam finish this off
	public static final short WHITE = HSSFColor.WHITE.index;
	public static final short BLUE_GREY = HSSFColor.BLUE_GREY.index;
	public static final short BLUE = HSSFColor.BLUE.index;
	public static final short BLACK = HSSFColor.BLACK.index;

	private final Map<String, HSSFFont> fonts = new TreeMap<String, HSSFFont>();

	/**
	 * Arial font
	 */

	public final static String ARIAL = HSSFFont.FONT_ARIAL;

	/**
	 * Normal boldness (not bold)
	 */

	public final static short BOLDWEIGHT_NORMAL = HSSFFont.BOLDWEIGHT_NORMAL;

	/**
	 * Bold boldness (bold)
	 */

	public final static short BOLDWEIGHT_BOLD = HSSFFont.BOLDWEIGHT_BOLD;

	/**
	 * normal type of black color.
	 */

	public final static short COLOR_NORMAL = HSSFFont.COLOR_NORMAL;
	/**
	 * Dark Red color
	 */

	public final static short COLOR_RED = HSSFFont.COLOR_RED;

	/**
	 * no type offsetting (not super or subscript)
	 */

	public final static short NO_SUPER_SUB_SCRIPT = HSSFFont.SS_NONE;

	/**
	 * superscript
	 */

	public final static short SUPERSCRIPT = HSSFFont.SS_SUPER;

	/**
	 * subscript
	 */

	public final static short SUBSCRIPT = HSSFFont.SS_SUB;

	/**
	 * not underlined
	 */

	public final static byte UNDERLINE_NONE = HSSFFont.U_NONE;

	/**
	 * single (normal) underline
	 */

	public final static byte UNDERLINE_SINGLE = HSSFFont.U_SINGLE;

	/**
	 * double underlined
	 */

	public final static byte UNDERLINE_DOUBLE = HSSFFont.U_DOUBLE;

	/**
	 * accounting style single underline
	 */

	public final static byte UNDERLINE_SINGLE_ACCCOUNTING = HSSFFont.U_SINGLE_ACCOUNTING;

	/**
	 * accounting style double underline
	 */

	public final static byte UNDERLINE_DOUBLE_ACCOUNTING = HSSFFont.U_DOUBLE_ACCOUNTING;

	public HSSFFont createFont(final HSSFWorkbook workbook, final int fontSize,
			final short color, final short boldness, final byte underline) {
		final HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) fontSize);
		font.setColor(color);
		font.setBoldweight(boldness);
		font.setUnderline(underline);
		return font;
	}

	public abstract Map<String, HSSFFont> getFonts(HSSFWorkbook workbook);

	public void addFont(final String fontName, final HSSFFont font) {
		fonts.put(fontName, font);
	}

	public HSSFFont getFont(final String fontName) {
		final HSSFFont font = fonts.get(fontName);
		if (font == null) {
			throw new IllegalArgumentException("no such font '" + fontName
					+ "'");
		}
		return font;
	}

	protected Map<String, HSSFFont> getFonts() {
		return fonts;
	}
}
