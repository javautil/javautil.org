package org.javautil.workbook;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */

public class StandardWorkbookStyle extends AbstractWorkbookStyle implements
		WorkbookStyle {

	public StandardWorkbookStyle(final HSSFWorkbook workbook) {
		if (workbook == null) {
			throw new IllegalArgumentException("workbook is null");
		}
		setWorkbook(workbook);
		createFonts();
		setStyles();
	}

	HSSFCellStyle getHeaderStyle(final short alignment) {
		HSSFCellStyle style;
		style = getWorkbook().createCellStyle();
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(alignment);
		style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		style.setFont(getHdrFont());
		return style;
	}

	HSSFCellStyle getDataStyle(final short alignment) {
		final HSSFCellStyle style = getWorkbook().createCellStyle();
		style.setAlignment(alignment);
		style.setFont(getDataFont());

		return style;
	}

	// Initialization
	private void setStyles() {
		//
		// Header Styles
		//
		setLeftHeaderString(getHeaderStyle(CellStyle.ALIGN_LEFT));
		setCenterHeader(getHeaderStyle(CellStyle.ALIGN_CENTER));
		setRightHeaderString(getHeaderStyle(CellStyle.ALIGN_RIGHT));

		//
		// Data Styles
		//
		setLeftDataString(getDataStyle(CellStyle.ALIGN_LEFT));
		setCenterData(getDataStyle(CellStyle.ALIGN_CENTER));
		setRightDataNumber(getDataStyle(CellStyle.ALIGN_RIGHT));

		final HSSFCellStyle style = getDataStyle(CellStyle.ALIGN_LEFT);
		style.setFont(super.getBigBoldFont());
		super.setLeftBigBold(style);

		final HSSFCellStyle style2 = getDataStyle(CellStyle.ALIGN_LEFT);
		style2.setFont(super.getBigFont());
		super.setLeftBig(style2);

		// Workbook workbook = new Workbook();
		// HSSFDataFormat format = new HSSFDataFormat(workbook);
		//
		// format.setFormat("[$$-409]#,##0.00;-[$$-409]#,##0.00");

	}

	private void createFonts() {
		HSSFFont font;

		font = getWorkbook().createFont();
		font.setFontHeightInPoints((short) 8);
		font.setColor(HSSFColor.WHITE.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		setHdrFont(font);

		font = getWorkbook().createFont();
		font.setFontHeightInPoints((short) 8);
		font.setColor(HSSFColor.BLACK.index);
		setDataFont(font);

		font = getWorkbook().createFont();
		font.setFontHeightInPoints((short) 10);
		font.setColor(HSSFColor.BLACK.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		setBigFont(font);

		font = getWorkbook().createFont();
		font.setFontHeightInPoints((short) 12);
		font.setColor(HSSFColor.BLACK.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		setBigBoldFont(font);

	}

}
