package org.javautil.document.style;

import java.awt.Color;
import java.awt.Font;

/**
 * An abstraction of a cell style.
 * 
 * Initially designed as an abstract represention of the presentation
 * information for the content of a table data <td>or workbook cell. -- jjs
 * 
 * 
 * A Style containing java.awt and non-document specific representations of
 * style components. The java.awt.Color and java.awt.Font objects are used
 * frequently because of their good out of the box support.
 * 
 * @author bcm
 */
public interface Style {

	/**
	 * Returns the name of the style, should be unique across other style
	 * instances when contained in a DocumentStyle
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Sets the name of the style
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * Returns the platform's native font name that is used
	 * 
	 * @return
	 */
	public Font getFont();

	/**
	 * Sets the name of the native font to be used
	 * 
	 * @param font
	 */
	public void setFont(Font font);

	/**
	 * Returns a set of attributes to be used in conjuction with the font and
	 * fontColor when rendering the font
	 * 
	 * @return
	 */
	public FontAttributes getFontAttributes();

	/**
	 * Sets the fontAttributes
	 * 
	 * @param fontAttributes
	 */
	public void setFontAttributes(FontAttributes fontAttributes);

	/**
	 * Returns the color to be used when rendering the font
	 * 
	 * @return
	 */
	public Color getFontColor();

	/**
	 * Sets the color to be used when rendering the font
	 * 
	 * @param fontColor
	 */
	public void setFontColor(Color fontColor);

	/**
	 * Returns the vertical alignment
	 * 
	 * @return
	 */
	public VerticalAlignment getVerticalAlignment();

	/**
	 * Sets the vertical alignment
	 * 
	 * @param verticalAlignment
	 */
	public void setVerticalAlignment(VerticalAlignment verticalAlignment);

	/**
	 * Gets the horizontal alignment
	 * 
	 * @return
	 */
	public HorizontalAlignment getHorizontalAlignment();

	/**
	 * Sets the horizontal alignment
	 * 
	 * @param horizontalAlignment
	 */
	public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment);

	/**
	 * Gets the borders
	 * 
	 * @return
	 */
	public Borders getBorders();

	/**
	 * Sets the borders
	 * 
	 * @param borders
	 */
	public void setBorders(Borders borders);

	/**
	 * Gets the background color
	 * 
	 * @return
	 */
	public Color getBackgroundColor();

	/**
	 * Sets the background color
	 * 
	 * @param color
	 */
	public void setBackgroundColor(Color color);

	/**
	 * Gets the format mask to be usd when rendering
	 * 
	 * @return
	 */
	public String getFormatMask();

	/**
	 * Sets the format mask to be used when rendering
	 * 
	 * @param formatMask
	 */
	public void setFormatMask(String formatMask);

	/**
	 * Produces a new copy of the style, that return true for the equals method
	 * 
	 * @return
	 */
	public Style copyOf();

	public void applyAttributes(Style style);

}
