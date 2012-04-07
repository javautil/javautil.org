package org.javautil.document.style;

/**
 * A style represented in pure string format; more human readable. This class is
 * mostly useful when wiring up style definitions in a spring application
 * context for convenience by beginning java and report developers. See
 * StyleUtil and StyleParser for classes to help use of this class with spring.
 * 
 * @author bcm
 * 
 */
public class StyleDefinition implements Cloneable {

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	private String name;

	private String fontColor;

	private String fontFace;

	private String fontHeight;

	private String fontStyle;

	private String fontWeight;

	private String fontUnderlineStyle;

	private String wordWrap;

	private String verticalAlignment;

	private String horizontalAlignment;

	private String backgroundColor;

	private String formatMask;

	private String border;

	private String borderTop;

	private String borderRight;

	private String borderBottom;

	private String borderLeft;

	public String getWordWrap() {
		return wordWrap;
	}

	public void setWordWrap(final String wordWrap) {
		this.wordWrap = wordWrap;
	}

	public String getFontUnderlineStyle() {
		return fontUnderlineStyle;
	}

	public void setFontUnderlineStyle(final String fontUnderlineStyle) {
		this.fontUnderlineStyle = fontUnderlineStyle;
	}

	public String getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(final String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public String getFontWeight() {
		return fontWeight;
	}

	public void setFontWeight(final String fontWeight) {
		this.fontWeight = fontWeight;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(final String fontColor) {
		this.fontColor = fontColor;
	}

	public String getFontFace() {
		return fontFace;
	}

	public void setFontFace(final String fontFace) {
		this.fontFace = fontFace;
	}

	public String getFontHeight() {
		return fontHeight;
	}

	public void setFontHeight(final String fontHeight) {
		this.fontHeight = fontHeight;
	}

	public String getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(final String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public String getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(final String horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(final String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getFormatMask() {
		return formatMask;
	}

	public void setFormatMask(final String formatMask) {
		this.formatMask = formatMask;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(final String border) {
		this.border = border;
	}

	public String getBorderTop() {
		return borderTop;
	}

	public void setBorderTop(final String borderTop) {
		this.borderTop = borderTop;
	}

	public String getBorderRight() {
		return borderRight;
	}

	public void setBorderRight(final String borderRight) {
		this.borderRight = borderRight;
	}

	public String getBorderBottom() {
		return borderBottom;
	}

	public void setBorderBottom(final String borderBottom) {
		this.borderBottom = borderBottom;
	}

	public String getBorderLeft() {
		return borderLeft;
	}

	public void setBorderLeft(final String borderLeft) {
		this.borderLeft = borderLeft;
	}

}
