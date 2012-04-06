package org.javautil.document.style;

import java.awt.Color;

public class LineSettings {

	private Color lineColor;
	private Integer lineWidth;

	public LineSettings() {
	}

	public LineSettings(final Integer lineWidth, final Color lineColor) {
		setLineColor(lineColor);
		setLineWidth(lineWidth);
	}

	public Color getLineColor() {
		return lineColor;
	}

	public Integer getLineWidth() {
		return lineWidth;
	}

	public void setLineColor(final Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setLineWidth(final Integer lineWidth) {
		this.lineWidth = lineWidth;
	}

}