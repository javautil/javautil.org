package org.javautil.poi.style;

import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.PaletteRecord;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class PoiColor {

	private final Map<Color, HSSFColor> colorCache = new LinkedHashMap<Color, HSSFColor>();

	private final HSSFPalette palette;
	private final Logger logger = Logger.getLogger(getClass());
	private final Set<Integer> reservedColorIndexes = new HashSet<Integer>();
	private int nextHSSFPaletteColorIndex = PaletteRecord.FIRST_COLOR_INDEX; // 0x8

	private final Integer maximumColorIndex = PaletteRecord.FIRST_COLOR_INDEX
			+ PaletteRecord.STANDARD_PALETTE_SIZE; // 0x40

	public PoiColor(final HSSFWorkbook workbook) {
		palette = workbook.getCustomPalette();
	}

	protected HSSFPalette getPalette() {
		return palette;
	}

	public HSSFColor getHSSFColor(final Color color) {
		HSSFColor c = getCachedHSSFColor(color);
		{
			if (c == null) {
				c = createHSSFColor(color);
			}
			return c;
		}
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

}