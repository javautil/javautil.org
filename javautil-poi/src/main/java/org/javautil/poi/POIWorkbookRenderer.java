package org.javautil.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.javautil.dataset.render.typewriter.TypewriterContent;
import org.javautil.dataset.render.typewriter.TypewriterContents;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.javautil.workbook.CellAddress;

public class POIWorkbookRenderer implements // WorkbookRenderer,
		TypewriterContents<HSSFRow, HSSFCell> {

	private final Logger logger = Logger.getLogger(getClass());

	protected HSSFWorkbook workbook;

	protected HSSFSheet sheet;

	private HSSFCellStyleFactory styleFactory;

	private final Map<String, HSSFSheet> sheetsByName = new LinkedHashMap<String, HSSFSheet>();

	private final Map<HSSFSheet, SheetHelper> sheetHelpers = new LinkedHashMap<HSSFSheet, SheetHelper>();

	public POIWorkbookRenderer() {
		setWorkbook(new HSSFWorkbook());
		if (logger.isDebugEnabled()) {
			logger.debug("default HSSFWorkbook " + getWorkbook()
					+ " was created");
		}
		addSheet("Untitled");
	}

	public POIWorkbookRenderer(final HSSFWorkbook workbook) {
		if (logger.isDebugEnabled()) {
			logger.debug("POIWorkbookRenderer instance created for workbook "
					+ workbook);
		}
		setWorkbook(workbook);
		checkWorkbook();
		addSheet("Untitled");
	}

	protected void checkWorkbook() {
		if (workbook == null) {
			throw new IllegalArgumentException("workbook is null");
		}
	}

	public SheetHelper getSheetHelper() {
		if (sheet == null) {
			throw new IllegalStateException("sheet is null");
		}
		return sheetHelpers.get(sheet);
	}

	@Override
	public TypewriterContent<HSSFRow, HSSFCell> getContent(
			final String regionName) {
		TypewriterContent<HSSFRow, HSSFCell> content = getContent();
		final Integer index = getSheetIndex(regionName);
		if (index != null) {
			final String sheetName = regionName;
			checkWorkbook();
			final HSSFSheet sheet = workbook.getSheet(sheetName);
			content = sheetHelpers.get(sheet);
		}
		if (content == null) {
			throw new IllegalStateException("content is null");
		}
		return content;
	}

	public TypewriterContent<HSSFRow, HSSFCell> getContent() {
		if (sheet == null) {
			throw new IllegalStateException("sheet is null");
		}
		return sheetHelpers.get(sheet);
	}

	public String getCellAddress(final int rowIndex, final int columnIndex) {
		return CellAddress.getCellAddress(rowIndex, columnIndex);
	}

	public String getCellRange(final int rowIndexStart, final int rowIndexEnd,
			final int columnIndexStart, final int columnIndexEnd) {
		final CellRangeAddress addr = new CellRangeAddress(rowIndexStart,
				rowIndexEnd, columnIndexStart, columnIndexEnd);
		return addr.toString();
	}

	public void addSheet(final String sheetName) {
		checkWorkbook();
		sheet = workbook.createSheet(sheetName);
		if (logger.isDebugEnabled()) {
			logger.debug("sheet " + sheetName + " was created");
		}
		sheetsByName.put(sheetName, sheet);
		sheetHelpers.put(sheet, new SheetHelper(workbook, sheet, styleFactory));
	}

	public Integer getSheetIndex(final String sheetName) {
		final int ndx = new ArrayList<String>(sheetsByName.keySet())
				.indexOf(sheetName);
		return (ndx == -1) ? null : new Integer(ndx);
	}

	public void renameSheet(final int sheetIndex, final String newSheetName) {
		checkWorkbook();
		workbook.setSheetName(sheetIndex, newSheetName);
	}

	public void renameSheet(final String oldSheetName, final String newSheetName) {
		checkWorkbook();
		final Integer ndx = getSheetIndex(oldSheetName);
		if (ndx == null) {
			throw new IllegalArgumentException("no sheet named " + oldSheetName);
		}
		workbook.setSheetName(ndx, newSheetName);
	}

	public HSSFWorkbook getWorkbook() {
		checkWorkbook();
		return workbook;
	}

	public void setWorkbook(final HSSFWorkbook workbook) {
		this.workbook = workbook;
		checkWorkbook();
		styleFactory = new HSSFCellStyleFactory(workbook);
		this.sheet = null;
		this.sheetHelpers.clear();
	}

	@Override
	public void write(final OutputStream outputStream) throws IOException {
		checkWorkbook();
		workbook.write(outputStream);
		outputStream.flush();
	}

	/*
	 * public int getRowIndex() { return getTypewriter().getRowIndex(); }
	 * 
	 * public int getColumnIndex() { return getTypewriter().getColumnIndex(); }
	 * 
	 * public HSSFSheet getSheet() { return sheet; }
	 * 
	 * public int addRow() { if (getTypewriter().getBehavior().getOrientation()
	 * != TypewriterOrientation.HORIZONTAL) { throw new
	 * IllegalStateException("addRow was called for " +
	 * "non-horizontal typewriter orientation"); } getTypewriter().nextLine();
	 * return getRowIndex(); }
	 * 
	 * protected int addRows(int rowCount) { for (int i = 0; i < rowCount; i++)
	 * { addRow(); } return getRowIndex(); }
	 * 
	 * public void setRowHeight(int height) { setRowHeight(getRowIndex(),
	 * height); }
	 * 
	 * public void setRowHeight(int rowIndex, int height) { HSSFRow row =
	 * getSheetHelper().getRowAt(rowIndex, true); row.setHeight((short) height);
	 * }
	 * 
	 * public void setColumnWidth(int columnIndex, final int characterWidth) {
	 * final int unitsPerChar = 300; final int funkyWidth = characterWidth
	 * unitsPerChar; final short shortFunkyWidth = funkyWidth < Short.MAX_VALUE
	 * ? (short) funkyWidth : Short.MAX_VALUE; sheet.setColumnWidth(columnIndex,
	 * shortFunkyWidth); }
	 * 
	 * public void setColumnWidth(final int characterWidth) {
	 * setColumnWidth(getColumnIndex() - 1, (short) characterWidth); }
	 * 
	 * public void setColumnIndex(int columnIndex) {
	 * getTypewriter().setColumnIndex(columnIndex); }
	 * 
	 * public void setRowIndex(int rowIndex) {
	 * getTypewriter().setRowIndex(rowIndex); }
	 * 
	 * public void skipCells(int cells) { int columnIndex = getColumnIndex();
	 * for (int i = 0; i < cells; i++) { columnIndex++; } }
	 * 
	 * public void setPreviousColumnWidth(int i) { int columnIndex =
	 * getColumnIndex(); setColumnWidth((short) (columnIndex - 1), (short) i); }
	 * 
	 * public void createHorizontalFreezePaneRow() { int rowIndex =
	 * getRowIndex(); // // Creates a split (freezepane). // // @param colSplit
	 * // Horizontal position of split. // @param rowSplit // Vertical position
	 * of split. // @param topRow // Top row visible in bottom pane // @param
	 * leftmostColumn // Left column visible in right pane. //
	 * sheet.createFreezePane(0, rowIndex, 0, rowIndex); }
	 * 
	 * 
	 * public void addBlankCell(Style style) { int columnIndex =
	 * getColumnIndex(); int rowIndex = getRowIndex();
	 * getSheetHelper().setBlankCellAt(rowIndex, columnIndex, style);
	 * skipCells(1); }
	 * 
	 * public void addCell(Object data, Style style) { int columnIndex =
	 * getColumnIndex(); int rowIndex = getRowIndex();
	 * getSheetHelper().setCellAt(rowIndex, columnIndex, data, style);
	 * skipCells(1); }
	 * 
	 * public void addCell(Object data, int charWidth, Style cellStyle) {
	 * addCell(data, cellStyle); setPreviousColumnWidth(charWidth); }
	 * 
	 * public void addFormulaCell(String formula, Style style) { int columnIndex
	 * = getColumnIndex(); int rowIndex = getRowIndex(); getSheetHelper()
	 * .setFormulaCellAt(rowIndex, columnIndex, formula, style);
	 * getTypewriter().skip(1); }
	 * 
	 * public void addFormulaCell(String formula, int charWidth, Style
	 * cellStyle) { addFormulaCell(formula, cellStyle);
	 * setPreviousColumnWidth(charWidth); }
	 * 
	 * public void addColumnFormulaCell(int firstFormulaRowIndex, int
	 * lastFormulaRowIndex, Style style, String function) { int columnIndex =
	 * getColumnIndex(); int rowIndex = getRowIndex();
	 * getSheetHelper().setColumnFormulaCellAt(rowIndex, columnIndex,
	 * firstFormulaRowIndex, lastFormulaRowIndex, columnIndex, style, function);
	 * getTypewriter().skip(1); }
	 * 
	 * public void addRowFormulaCell(int firstFormulaColumnIndex, int
	 * lastFormulaColumnIndex, Style style, String function) { int columnIndex =
	 * getColumnIndex(); int rowIndex = getRowIndex();
	 * getSheetHelper().setRowFormulaCellAt(rowIndex, columnIndex, rowIndex,
	 * firstFormulaColumnIndex, lastFormulaColumnIndex, style, function);
	 * getTypewriter().skip(1); }
	 * 
	 * public void fixColumnWidths(int startColumnIndex, int endColumnIndex) {
	 * for (int i = startColumnIndex; i <= endColumnIndex; i++) {
	 * fixColumnWidth(i); } }
	 * 
	 * public void fixColumnWidth(int columnIndex) { int rowCount =
	 * sheet.getPhysicalNumberOfRows(); int maxWidth = 0; for (int i = 0; i <
	 * rowCount; i++) { HSSFRow r = sheet.getRow(i); HSSFCell c =
	 * r.getCell(columnIndex);
	 * 
	 * if (c != null) { String v = c.getRichStringCellValue().getString(); if (v
	 * != null && v.length() > maxWidth) { maxWidth = v.length();
	 * 
	 * } } } if (maxWidth > 8) { sheet.setColumnWidth(columnIndex, maxWidth); }
	 * }
	 * 
	 * public void mergePreviousColumnRight(int numberOfColumns) { if
	 * (numberOfColumns < 1) { throw new
	 * IllegalStateException("numberOfColumn must " + " be greater than 0"); }
	 * int columnIndex = getColumnIndex(); int startColumnIndex = columnIndex -
	 * 1; int endColumnIndex = columnIndex + numberOfColumns - 1;
	 * sheet.addMergedRegion(new CellRangeAddress(getRowIndex(), getRowIndex(),
	 * startColumnIndex, endColumnIndex)); skipCells(numberOfColumns); }
	 * 
	 * public void addMergedRegion(CellRangeAddress cellRangeAddress) {
	 * sheet.addMergedRegion(cellRangeAddress); }
	 * 
	 * public void mergeCellRange(int rowIndexStart, int rowIndexEnd, int
	 * columnIndexStart, int columnIndexEnd) { sheet.addMergedRegion(new
	 * CellRangeAddress(rowIndexStart, rowIndexEnd, columnIndexStart,
	 * columnIndexEnd)); }
	 */
}
