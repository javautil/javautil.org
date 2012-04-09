package org.javautil.document.style;

import java.awt.Color;

public class SimpleBorder implements Borders {

	public static final int TOP_LINE = 0;

	public static final int RIGHT_LINE = 1;

	public static final int BOTTOM_LINE = 2;

	public static final int LEFT_LINE = 3;

	private int topLineWidth = 0;

	private int rightLineWidth = 0;

	private int bottomLineWidth = 0;

	private int leftLineWidth = 0;

	private Color topLineColor;

	private Color rightLineColor;

	private Color bottomLineColor;

	private Color leftLineColor;

	public int getTopLineWidth() {
		return topLineWidth;
	}

	public void setTopLineWidth(final int topLineWidth) {
		this.topLineWidth = topLineWidth;
	}

	public int getRightLineWidth() {
		return rightLineWidth;
	}

	public void setRightLineWidth(final int rightLineWidth) {
		this.rightLineWidth = rightLineWidth;
	}

	public int getLeftLineWidth() {
		return leftLineWidth;
	}

	public void setLeftLineWidth(final int leftLineWidth) {
		this.leftLineWidth = leftLineWidth;
	}

	public int getBottomLineWidth() {
		return bottomLineWidth;
	}

	public void setBottomLineWidth(final int bottomLineWidth) {
		this.bottomLineWidth = bottomLineWidth;
	}

	public Color getTopLineColor() {
		return topLineColor;
	}

	public void setTopLineColor(final Color topLineColor) {
		this.topLineColor = topLineColor;
	}

	public Color getRightLineColor() {
		return rightLineColor;
	}

	public void setRightLineColor(final Color rightLineColor) {
		this.rightLineColor = rightLineColor;
	}

	public Color getLeftLineColor() {
		return leftLineColor;
	}

	public void setLeftLineColor(final Color leftLineColor) {
		this.leftLineColor = leftLineColor;
	}

	public Color getBottomLineColor() {
		return bottomLineColor;
	}

	public void setBottomLineColor(final Color bottomLineColor) {
		this.bottomLineColor = bottomLineColor;
	}

	public void setTop(final LineSettings lineSettings) {
		setTopLineColor(lineSettings.getLineColor());
		setTopLineWidth(lineSettings.getLineWidth());
	}

	public void setRight(final LineSettings lineSettings) {
		setRightLineColor(lineSettings.getLineColor());
		setRightLineWidth(lineSettings.getLineWidth());
	}

	public void setBottom(final LineSettings lineSettings) {
		setBottomLineColor(lineSettings.getLineColor());
		setBottomLineWidth(lineSettings.getLineWidth());
	}

	public void setLeft(final LineSettings lineSettings) {
		setLeftLineColor(lineSettings.getLineColor());
		setLeftLineWidth(lineSettings.getLineWidth());
	}

	public static SimpleBorder parse(final StyleDefinition style) {
		final SimpleBorder border = new SimpleBorder();
		final LineSettings[] settings = new LineSettings[4];
		LineSettings setting = parseLineSettings(style.getBorder());
		settings[0] = setting;
		settings[1] = setting;
		settings[2] = setting;
		settings[3] = setting;
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case TOP_LINE:
				setting = parseLineSettings(style.getBorderTop());
				border.setTop(setting);
				break;
			case RIGHT_LINE:
				setting = parseLineSettings(style.getBorderRight());
				border.setRight(setting);
				break;
			case BOTTOM_LINE:
				setting = parseLineSettings(style.getBorderBottom());
				border.setBottom(setting);
				break;
			case LEFT_LINE:
				setting = parseLineSettings(style.getBorderLeft());
				border.setLeft(setting);
				break;
			default:
				throw new IllegalStateException("unhandled case");
			}
		}
		return border;
	}

	public static LineSettings parseLineSettings(final String lineSettings) {
		String[] args = new String[0];
		if (lineSettings != null) {
			args = lineSettings.split(" ");
		}
		Color lineColor = null;
		Integer lineWidth = 0;
		try {
			if (args.length > 0) {
				lineWidth = Integer.parseInt(args[0].trim());
			}
			if (args.length > 1) {
				lineColor = ColorUtil.parseColor(args[1].trim());
			} else if (args.length > 0) {
				lineColor = Color.BLACK;
			}
		} catch (final Exception e) {
			throw new RuntimeException("unable to parse as simple border: "
					+ lineSettings, e);
		}
		return new LineSettings(lineWidth, lineColor);
	}

	public static SimpleBorder getInstance(final int lineWidth,
			final Color color) {
		return getInstance(new int[] { lineWidth, lineWidth, lineWidth,
				lineWidth }, new Color[] { color, color, color, color });
	}

	public static SimpleBorder getInstance(final LineSettings[] lines) {
		if (lines == null) {
			throw new IllegalArgumentException("lines is null");
		}
		if (lines.length != 4) {
			throw new IllegalArgumentException("lines length must be 4");
		}
		final int[] lineWidths = new int[4];
		final Color[] lineColors = new Color[4];
		for (int i = 0; i < lines.length; i++) {
			final Integer width = lines[i].getLineWidth();
			lineWidths[i] = width == null ? 0 : width.intValue();
			lineColors[i] = lines[i].getLineColor();
		}
		return getInstance(lineWidths, lineColors);
	}

	public static SimpleBorder getInstance(final int[] lineWidths,
			final Color[] colors) {
		final SimpleBorder border = new SimpleBorder();
		if (lineWidths == null) {
			throw new IllegalArgumentException("lineWidths is null");
		}
		if (colors == null) {
			throw new IllegalArgumentException("colors is null");
		}
		if (lineWidths.length != 4) {
			throw new IllegalArgumentException("lineWidths length must be 4");
		}
		if (colors.length != 4) {
			throw new IllegalArgumentException("colors length must be 4");
		}
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case TOP_LINE:
				border.setTopLineWidth(lineWidths[i]);
				border.setTopLineColor(colors[i]);
				break;
			case RIGHT_LINE:
				border.setRightLineWidth(lineWidths[i]);
				border.setRightLineColor(colors[i]);
				break;
			case BOTTOM_LINE:
				border.setBottomLineWidth(lineWidths[i]);
				border.setBottomLineColor(colors[i]);
				break;
			case LEFT_LINE:
				border.setLeftLineWidth(lineWidths[i]);
				border.setLeftLineColor(colors[i]);
				break;
			default:
				throw new IllegalStateException("unhandled case");
			}
		}
		return border;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	// this was generated by Eclipse
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((topLineColor == null) ? 0 : topLineColor.hashCode());
		result = prime * result
				+ ((rightLineColor == null) ? 0 : rightLineColor.hashCode());
		result = prime * result
				+ ((bottomLineColor == null) ? 0 : bottomLineColor.hashCode());
		result = prime * result
				+ ((leftLineColor == null) ? 0 : leftLineColor.hashCode());
		result = prime * result + (topLineWidth);
		result = prime * result + (rightLineWidth);
		result = prime * result + (bottomLineWidth);
		result = prime * result + (leftLineWidth);
		return result;
	}

	@Override
	public Borders copyOf() {
		final SimpleBorder copy = new SimpleBorder();
		copy.setBottomLineColor(bottomLineColor);
		copy.setBottomLineWidth(bottomLineWidth);
		copy.setLeftLineColor(leftLineColor);
		copy.setLeftLineWidth(leftLineWidth);
		copy.setRightLineColor(rightLineColor);
		copy.setRightLineWidth(rightLineWidth);
		copy.setTopLineColor(topLineColor);
		copy.setTopLineWidth(topLineWidth);
		return copy;
	}

}