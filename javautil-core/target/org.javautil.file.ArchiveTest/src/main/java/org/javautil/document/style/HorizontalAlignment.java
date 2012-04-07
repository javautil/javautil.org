package org.javautil.document.style;

/**
 * Possible values for horizontal alignment; not all values are necessarily
 * possible by the underlying document style.
 * 
 * @author bcm
 * 
 */
public enum HorizontalAlignment {

	/**
	 * Left alignment
	 */
	LEFT,

	/**
	 * Right alignment
	 */
	RIGHT,

	/**
	 * Centered alignment
	 */
	CENTER;

	/**
	 * Get a horizontal alignment based on an abbreviated version.
	 * 
	 * C - Center L - Left R - Right
	 * 
	 * @param abbrev
	 * @return
	 */
	public static HorizontalAlignment getFromAbbreviation(final String abbrev) {
		HorizontalAlignment ha = null;
		if ("R".equals(abbrev)) {
			ha = HorizontalAlignment.RIGHT;
		} else if ("C".equals(abbrev)) {
			ha = HorizontalAlignment.CENTER;
		} else if ("L".equals(abbrev)) {
			ha = HorizontalAlignment.LEFT;
		} else {
			throw new IllegalArgumentException("Invalid abbreviation '"
					+ abbrev + "'");
		}
		return ha;
	}

}
