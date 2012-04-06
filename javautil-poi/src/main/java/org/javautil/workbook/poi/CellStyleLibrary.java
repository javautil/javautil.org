package org.javautil.workbook.poi;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public interface CellStyleLibrary {

	public static final Short LEFT = new Short(HSSFCellStyle.ALIGN_LEFT);
	public static final Short CENTER = new Short(HSSFCellStyle.ALIGN_CENTER);
	public static final Short RIGHT = new Short(HSSFCellStyle.ALIGN_RIGHT);
	public static final Short SOLID_FOREGROUND = new Short(
			HSSFCellStyle.SOLID_FOREGROUND);

	public abstract HSSFCellStyle getStyle(String styleName);

	public abstract void addStyle(String styleName, HSSFCellStyle cellStyle);

	public abstract HSSFFont getFont(String fontName);

	public Map<String, HSSFCellStyle> getStyles();

	public final static short AQUA = HSSFColor.AQUA.index;

	public final static short BLACK = HSSFColor.BLACK.index;
	public final static short BLUE_GREY = HSSFColor.BLUE_GREY.index;
	public final static short BLUE = HSSFColor.BLUE.index;
	public final static short BRIGHT_GREEN = HSSFColor.BRIGHT_GREEN.index;
	public final static short BROWN = HSSFColor.BROWN.index;
	public final static short CORAL = HSSFColor.CORAL.index;
	public final static short CORNFLOWER_BLUE = HSSFColor.CORNFLOWER_BLUE.index;
	public final static short DARK_BLUE = HSSFColor.DARK_BLUE.index;
	public final static short DARK_GREEN = HSSFColor.DARK_GREEN.index;
	public final static short DARK_RED = HSSFColor.DARK_RED.index;
	public final static short DARK_TEAL = HSSFColor.DARK_TEAL.index;
	public final static short DARK_YELLOW = HSSFColor.DARK_YELLOW.index;
	public final static short GOLD = HSSFColor.GOLD.index;
	public final static short GREEN = HSSFColor.GREEN.index;
	public final static short GREY_25_PERCENT = HSSFColor.GREY_25_PERCENT.index;
	public final static short GREY_40_PERCENT = HSSFColor.GREY_40_PERCENT.index;
	public final static short GREY_50_PERCENT = HSSFColor.GREY_50_PERCENT.index;
	public final static short GREY_80_PERCENT = HSSFColor.GREY_80_PERCENT.index;
	public final static short INDIGO = HSSFColor.INDIGO.index;
	public final static short LAVENDER = HSSFColor.LAVENDER.index;
	public final static short LEMON_CHIFFON = HSSFColor.LEMON_CHIFFON.index;
	public final static short LIGHT_BLUE = HSSFColor.LIGHT_BLUE.index;
	public final static short LIGHT_CORNFLOWER_BLUE = HSSFColor.LIGHT_CORNFLOWER_BLUE.index;
	public final static short LIGHT_GREEN = HSSFColor.LIGHT_GREEN.index;
	public final static short LIGHT_ORANGE = HSSFColor.LIGHT_ORANGE.index;
	public final static short LIGHT_TURQUOISE = HSSFColor.LIGHT_TURQUOISE.index;
	public final static short LIGHT_YELLOW = HSSFColor.LIGHT_YELLOW.index;
	public final static short LIME = HSSFColor.LIME.index;
	public final static short MAROON = HSSFColor.MAROON.index;
	public final static short ORANGE = HSSFColor.ORANGE.index;
	public final static short ORCHID = HSSFColor.ORCHID.index;
	public final static short PALE_BLUE = HSSFColor.PALE_BLUE.index;
	public final static short PINK = HSSFColor.PINK.index;
	public final static short PLUM = HSSFColor.PLUM.index;
	public final static short RED = HSSFColor.RED.index;
	public final static short ROSE = HSSFColor.ROSE.index;
	public final static short ROYAL_BLUE = HSSFColor.ROYAL_BLUE.index;
	public final static short SEA_GREEN = HSSFColor.SEA_GREEN.index;
	public final static short SKY_BLUE = HSSFColor.SKY_BLUE.index;
	public final static short TAN = HSSFColor.TAN.index;
	public final static short TEAL = HSSFColor.TEAL.index;
	public final static short TURQUOISE = HSSFColor.TURQUOISE.index;
	public final static short VIOLET = HSSFColor.VIOLET.index;
	public final static short WHITE = HSSFColor.WHITE.index;
	public final static short YELLOW = HSSFColor.YELLOW.index;
	public final static short OLIVE_GREEN = HSSFColor.OLIVE_GREEN.index;

	public final static short BOLD = HSSFFont.BOLDWEIGHT_BOLD;
	public final static short NORMAL = HSSFFont.BOLDWEIGHT_NORMAL;

	public final static byte NONE = HSSFFont.U_NONE;
	public final static byte SINGLE = HSSFFont.U_SINGLE;
	public final static byte DOUBLE = HSSFFont.U_DOUBLE;

}