package org.javautil.document.style;

import java.awt.Color;

/**
 * Provides methods for manipulating java.awt.Color objects from string and
 * numeric representations.
 * 
 * @author bcm
 * 
 */
public abstract class ColorUtil {

	/**
	 * A color having no opacity, useful when a background color is needed but
	 * not specified.
	 */
	public static Color TRANSPARENT = new Color(255, 255, 255, 0);

	/**
	 * Returns a CSS friendly color in #RRGGBB format, where RR, GG, and BB are
	 * the red, green, and blue values of the color in hex. A leading hash sign
	 * is always the first character.
	 * 
	 * @param color
	 * @return
	 */
	public static String toRgbHex(final Color color) {
		final StringBuffer hexString = new StringBuffer();
		hexString.append("#");
		hexString.append(Integer.toHexString(color.getRed()));
		if (hexString.length() < 3) {
			hexString.append("0");
		}
		hexString.append(Integer.toHexString(color.getGreen()));
		if (hexString.length() < 5) {
			hexString.append("0");
		}
		hexString.append(Integer.toHexString(color.getBlue()));
		if (hexString.length() < 7) {
			hexString.append("0");
		}

		return hexString.toString();
	}

	/**
	 * Returns a java.awt.Color by reading the RGB values from a hexadecimal
	 * string of format #RRGGBB (the same format used by toRgbHex). An
	 * IllegalArgumentException is thrown if the string format is not valid.
	 * 
	 * @param rgbHexString
	 * @return
	 */
	public static Color parseColorFromRgbHexString(final String rgbHexString) {
		String t = rgbHexString;
		Color ret = null;
		if (t.charAt(0) != '#') {
			throw new IllegalArgumentException(
					"color RGB value must begin with hash (#) mark");
		}
		if (t.length() == 4) {
			// convert three hex value to six hex value
			t = new String(new char[] { t.charAt(0), t.charAt(1), t.charAt(1),
					t.charAt(2), t.charAt(2), t.charAt(3), t.charAt(3) });
		}
		if (t.length() != 7) {
			throw new IllegalArgumentException("format expected to be "
					+ "in the format #RGB or #RRGGBB; " + "format was "
					+ rgbHexString);
		}
		// parse as six hex value
		int rValue, gValue, bValue = 0;
		try {
			rValue = Integer.parseInt(t.substring(1, 3), 16);
			gValue = Integer.parseInt(t.substring(3, 5), 16);
			bValue = Integer.parseInt(t.substring(5, 7), 16);
			ret = new Color(rValue, gValue, bValue);
		} catch (final Exception e) {
			throw new IllegalArgumentException("bad RGB hex format");
		}
		return ret;
	}

	/**
	 * Convenience method for reading a string as a java.awt.Color.
	 * 
	 * If the color starts with hash sign, the color is parsed using the method
	 * parseColorFromRgbHexString,
	 * 
	 * todo jjs where is this suitable color name lookup? otherwise a suitable
	 * color name lookup is used.
	 * 
	 * @param color
	 * @return
	 */
	public static Color parseColor(final String color) {
		Color ret = null;
		if (color.toLowerCase().equals("transparent")) {
			ret = TRANSPARENT;
		} else if (color.charAt(0) == '#') {
			ret = parseColorFromRgbHexString(color);
		} else {
			throw new IllegalArgumentException("unknown color string: " + color);
		}
		return ret;
	}
}
