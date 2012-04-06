package org.javautil.poi;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.document.style.HorizontalAlignment;
import org.javautil.document.style.Style;
import org.javautil.document.style.StyleImpl;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.javautil.workbook.BaseSheet;
import org.javautil.workbook.CellFormula;

/**
 * 
 * @author jjs
 * 
 */
public class AbstractSheetRenderer implements BaseSheet {

	protected HSSFWorkbook workbook;

	protected HSSFSheet sheet;

	private final Logger logger = Logger.getLogger(getClass());

	private short rowIndex;

	private short columnIndex;

	private HSSFRow row;

	private final String dateStyleFormat = "mm/dd/yyyy";

	private HSSFCellStyleFactory styleFactory;

	private HSSFCellStyle dateStyle;

	private SheetStyleHelper sheetHelper;

	public AbstractSheetRenderer() {

	}

	public AbstractSheetRenderer(final HSSFWorkbook workbook) {
		if (workbook == null) {
			throw new IllegalArgumentException("workbook is null");
		}
		setWorkbook(workbook);
		dateStyle = createStyle(getDateStyleFormat());
		styleFactory = new HSSFCellStyleFactory(workbook);

		// generalStyle = createStyle("general");
	}

	// public AbstractSheetRenderer(HSSFWorkbook workbook,
	// WorkbookCellStyleFactory styleFactory) {
	// this(workbook);
	// if (workbook == null) {
	// throw new IllegalArgumentException("workbook is null");
	// }
	// setWorkbook(workbook);
	// dateStyle = createStyle(getDateStyleFormat());
	// this.styleFactory = styleFactory;
	// // generalStyle = createStyle("general");
	// }

	// protected HSSFCellStyle getStyle(String styleName) {
	// Style style = documentStyles.get(styleName);
	// if (styleFactory == null) {
	// styleFactory = new HSSFCellStyleFactory(workbook);
	// }
	// return styleFactory.getHSSFCellStyle(style);
	// }
	//
	// protected HSSFCellStyle getStyle(String styleName, String formatMask) {
	// Style style = documentStyles.get(styleName);
	// Style copiedStyle = style.copyOf();
	// copiedStyle.setFormatMask(formatMask);
	// HSSFCellStyle cellStyle = styleFactory.getHSSFCellStyle(copiedStyle);
	// return cellStyle;
	// }

	protected HSSFSheet createSheet(final String sheetName) {
		sheet = workbook.createSheet(sheetName);
		rowIndex = 0;
		columnIndex = 0;
		row = null;
		sheetHelper = new SheetStyleHelper(sheet, styleFactory);
		return sheet;

	}

	protected HSSFSheet createSheet(final String sheetName,
			final HSSFCellStyleFactory styles) {
		createSheet(sheetName);
		sheetHelper = new SheetStyleHelper(sheet, styleFactory);
		return sheet;

	}

	protected void addRow(final int rowCount) {
		for (int i = 0; i < rowCount; i++) {
			addRow();
		}
	}

	@Override
	public HSSFRow addRow() {
		if (sheet == null) {
			throw new IllegalStateException("call createSheet first");
		}
		row = sheet.createRow(rowIndex);
		rowIndex++;
		columnIndex = 0;
		return row;
	}

	@Override
	public void setRowHeight(final int height) {
		row.setHeight((short) height);
	}

	@Override
	public void setColumnWidth(final int columnIndex, final int characterWidth) {
		final int unitsPerChar = 300;
		final int funkyWidth = characterWidth * unitsPerChar;
		final short shortFunkyWidth = funkyWidth < Short.MAX_VALUE ? (short) funkyWidth
				: Short.MAX_VALUE;
		sheet.setColumnWidth((short) columnIndex, shortFunkyWidth);
	}

	@Override
	public void setColumnWidth(final int characterWidth) {
		setColumnWidth(columnIndex - 1, (short) characterWidth);
	}

	private HSSFCellStyle createStyle(final String styleFormat) {
		final HSSFCellStyle style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat(styleFormat));
		return style;
	}

	/**
	 */
	@Override
	public String getDateStyleFormat() {
		return dateStyleFormat;
	}

	/**
	 */
	@Override
	public void setPreviousColumnWidth(final int i) {
		setColumnWidth((short) (columnIndex - 1), (short) i);

	}

	@Override
	public void createFreezePaneRow() {
		/**
		 * Creates a split (freezepane).
		 * 
		 * @param colSplit
		 *            Horizontal position of split.
		 * @param rowSplit
		 *            Vertical position of split.
		 * @param topRow
		 *            Top row visible in bottom pane
		 * @param leftmostColumn
		 *            Left column visible in right pane.
		 */
		sheet.createFreezePane(0, rowIndex, 0, rowIndex);

	}

	@Override
	public HSSFWorkbook getWorkbook() {
		if (workbook == null) {
			throw new IllegalStateException("workbook has not been set");
		}
		return workbook;
	}

	@Override
	public void addCell(final Object data, final HSSFCellStyle cellStyle) {
		if (row == null) {
			throw new IllegalStateException("call addRow first");
		}
		final HSSFCell cell = row.createCell(columnIndex);
		setCellData(cell, data);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		columnIndex++;
	}

	@Override
	public void addCell(final Object data, final int charWidth,
			final HSSFCellStyle cellStyle) {
		addCell(data, cellStyle);
		setPreviousColumnWidth(charWidth);
	}

	private void setCellData(final HSSFCell cell, final Object data) {
		if (data == null) {
			// logger.debug("null data " + rowIndex + " " + columnIndex);
			// do nothing.
		} else

		if (data instanceof String) {
			final String string = (String) data;
			cell.setCellValue(string);
			// new HSSFRichTextString((String) data)
			// );
		} else if (data instanceof Number) {
			cell.setCellValue(((Number) data).doubleValue());
		} else if (data instanceof Date) {
			cell.setCellValue((Date) data);
			// cellStyle = getDateStyle();
			// cell.setCellStyle(dateStyle);
		} else {
			throw new IllegalArgumentException("unknown type");
		}
	}

	public void setWorkbook(final HSSFWorkbook workbook) {
		this.workbook = workbook;
		// styleFactory = new WorkbookCellStyleFactory(workbook);
	}

	@Override
	public short getRowIndex() {
		return rowIndex;
	}

	@Override
	public short getColumnIndex() {
		return columnIndex;
	}

	@Override
	public void addFormulaCell(final String formula,
			final HSSFCellStyle cellStyle) {

		final HSSFCell cell = row.createCell(columnIndex);
		cell.setCellFormula(formula);
		logger.debug("setting cell to style " + cellStyle);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		columnIndex++;

	}

	/**
	 * @deprecated use SheetStyleHelper.getStyle (SheetStyleHelper is deprecated
	 *             as well...)
	 * @param baseStyle
	 * @param excelFormat
	 * @param horizontalAlignment
	 * @return
	 */
	@Deprecated
	public Style getStyle(final Style baseStyle, final String excelFormat,
			final String horizontalAlignment) {

		final StyleImpl s = new StyleImpl();
		s.setFormatMask(excelFormat);
		final HorizontalAlignment ha = HorizontalAlignment
				.getFromAbbreviation(horizontalAlignment);
		s.setHorizontalAlignment(ha);
		final Style cellStyle = s.getStyle(baseStyle, s);
		return cellStyle;
	}

	/**
	 * @deprecated use SheetStyleHelper.getStyle (SheetStyleHelper is deprecated
	 *             as well...)
	 * @param baseStyle
	 * @param excelFormat
	 * @param horizontalAlignment
	 * @return
	 */
	@Deprecated
	public Style getStyle(final Style baseStyle, final String excelFormat,
			final HorizontalAlignment horizontalAlignment) {

		final StyleImpl s = new StyleImpl();
		s.setFormatMask(excelFormat);
		s.setHorizontalAlignment(horizontalAlignment);
		final Style cellStyle = s.getStyle(baseStyle, s);
		return cellStyle;
	}

	protected void emitHeaders(final List<ColumnMetadata> columns,
			final Style baseStyle) {

		// int currentRowIndex = getRowIndex();
		int currentColumnIndex = 0;
		for (final ColumnMetadata column : columns) {

			final Style cellStyle = getStyle(baseStyle, null,
					column.getHorizontalAlignment());
			sheetHelper.addCell(rowIndex, currentColumnIndex,
					column.getHeading(), cellStyle);
			if (column.getColumnDisplaySize() != null) {
				setColumnWidth(currentColumnIndex, column
						.getColumnDisplaySize().intValue());
			}
			currentColumnIndex++;
		}

	}

	/**
	 * @return the dateStyle
	 */
	public HSSFCellStyle getDateStyle() {
		return dateStyle;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void addMergedRegion(final CellRangeAddress cellRangeAddress) {
		sheet.addMergedRegion(cellRangeAddress);

	}

	public void setStyleFactory(final HSSFCellStyleFactory styles) {
		this.styleFactory = styles;

	}

	/**
	 * @deprecated use SheetStyleHelper.addFormulaCell todo cam document
	 * @param template
	 * @param columnIndexByName
	 * @param columnName
	 * @return
	 */
	@Deprecated
	public HSSFCell addFormulaCell(final String template,
			final Map<String, Integer> columnIndexByName,
			final String columnName, final int rowIndex, final int columnIndex) {
		if (template == null || columnIndexByName == null) {
			throw new IllegalArgumentException(); // / todo cam clean up
		}

		logger.debug("template : " + template + " columnIndexByName "
				+ columnIndexByName + " " + columnName + " " + rowIndex + " "
				+ columnIndex);

		final String formula = CellFormula.getAsFormula(template,
				columnIndexByName, rowIndex);
		logger.debug("formula is " + formula);
		final HSSFCell cell = sheetHelper.getCell(rowIndex, columnIndex);
		logger.debug("cell is " + cell.getRowIndex() + " "
				+ cell.getColumnIndex());
		try {
			cell.setCellFormula(formula);
		} catch (final Exception wtf) {
			final StringBuilder sb = new StringBuilder();
			sb.append("this is really goofy\n");
			sb.append("but go ahead and replace the semicolons with commas and poi will change them back to ';' \n");
			sb.append("exception was " + wtf.getClass().getName() + "\n");
			sb.append(wtf.getMessage());
			throw new IllegalArgumentException(sb.toString());
		}

		return cell;
	}

	@Override
	public HSSFCellStyleFactory getStyleFactory() {
		if (styleFactory == null) {
			throw new IllegalStateException("styleFactory has not yet been set");
		}
		return styleFactory;
	}

	public SheetStyleHelper getSheetHelper() {
		return sheetHelper;
	}

	public void setSheetHelper(final SheetStyleHelper sheetHelper) {
		this.sheetHelper = sheetHelper;
	}

}
