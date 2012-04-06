package org.javautil.poi;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.javautil.dataset.render.typewriter.AbstractTypewriterContentSupport;
import org.javautil.dataset.render.typewriter.TypewriterContents;
import org.javautil.document.style.HorizontalAlignment;
import org.javautil.document.style.Style;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.javautil.workbook.CellAddress;

/**
 * Helper class for manipulating a POI worksheet while masking the POI apis. All
 * manipulations to the worksheet are performed absolutely. There is no
 * distinction between updating or creating a cell. If a cell already exists at
 * the previous location, it is overriden.
 * 
 * If you need to debug your worksheet rendering code because you are
 * overwriting cells and need to know where this is happening; see
 * setProtectUnmodifiableCells() and setUnmodifiableCell().
 * 
 * If you need to manipulate an entire workbook or handle multiple worksheets in
 * a relative fashion (like incrementing to the next column after adding a cell)
 * see the the AbstractWorkbookRenderer class.
 * 
 * On last HUGE tip: the row and column indexes are ZERO based, like POI (for
 * example), but your workbook viewer will probably display rows using a ONE
 * based index, so keep this in mind when debugging your code.
 * 
 * @see {@link AbstractWorkbookRenderer}
 * @See {@link HSSFCellStyleFactory}
 * @see {@link Style}
 * 
 */
public class SheetHelper extends
		AbstractTypewriterContentSupport<HSSFRow, HSSFCell> {

	public static final int PAGE_ORIENTATION_LANDSCAPE = 0;

	public static final int PAGE_ORIENTATION_PORTRAIT = 1;

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * The underlying workbook that the helper will operate on
	 */
	protected HSSFWorkbook workbook;

	/**
	 * The underlying sheet that the helper will operate on
	 */
	protected HSSFSheet sheet;

	/**
	 * Provides conversion from a style to a poi workbook style
	 */
	protected HSSFCellStyleFactory styleFactory;

	/**
	 * Contains y,x string indexes of cells that cannot be modified
	 */
	protected HashSet<String> unmodifiableCells = new HashSet<String>();

	protected boolean protectUnmodifiableCells = true;

	protected TypewriterContents<HSSFRow, HSSFCell> content = this;

	public SheetHelper(final HSSFWorkbook workbook, final HSSFSheet sheet,
			final HSSFCellStyleFactory styleFactory) {
		this(sheet, styleFactory);
		if (logger.isDebugEnabled()) {
			logger.debug("SheetHelper created for workbook " + workbook);
		}
		this.workbook = workbook;
	}

	public SheetHelper(final HSSFSheet sheet,
			final HSSFCellStyleFactory styleFactory) {
		if (sheet == null) {
			throw new IllegalStateException("sheet is null");
		}
		this.sheet = sheet;
		if (styleFactory == null) {
			throw new IllegalStateException("styleFactory is null");
		}
		this.styleFactory = styleFactory;
		setCreateMissingCells(false);
		setCreateMissingRows(false);
	}

	protected HSSFCellStyle getHSSFCellStyle(final Style style) {
		return styleFactory.getHSSFCellStyle(style);
	}

	protected void setCellStyle(final HSSFCell cell, final Style style) {
		beforeCellModification(cell);
		cell.setCellStyle(getHSSFCellStyle(style));
	}

	protected void setCellData(final HSSFCell cell, final Object data) {
		beforeCellModification(cell);
		if (data == null || String.class.equals(data.getClass())
				&& ((String) data).length() == 0) {
			cell.setCellValue(new HSSFRichTextString(""));
			cell.setCellType(Cell.CELL_TYPE_BLANK);
			// logger.debug("null data " + rowIndex + " " + columnIndex);
		} else if (data instanceof String) {
			String text = (String) data;
			if (text.indexOf("\\n") > -1) {
				text = text.replace("\\n", "\n");
			}
			cell.setCellValue(new HSSFRichTextString(text));
		} else if (data instanceof Number) {
			cell.setCellValue(((Number) data).doubleValue());
		} else if (data instanceof Boolean) {
			cell.setCellValue(((Boolean) data).booleanValue());
		} else if (data instanceof Date) {
			cell.setCellValue((Date) data);
		} else {
			throw new IllegalArgumentException("data is of an unknown type: "
					+ data.getClass().getName());
		}
		if (logger.isTraceEnabled()) {
			logger.trace("updated sheet " + cell.getSheet() + ", cell ("
					+ cell.getRowIndex() + "," + cell.getColumnIndex()
					+ ") data to " + data);
		}
	}

	protected int getTypeForCellData(final Object data) {
		int ret;
		if (data == null) {
			ret = Cell.CELL_TYPE_BLANK;
		} else if (data instanceof String) {
			ret = Cell.CELL_TYPE_STRING;
		} else if (data instanceof Number) {
			ret = Cell.CELL_TYPE_NUMERIC;
		} else if (data instanceof Date) {
			ret = Cell.CELL_TYPE_NUMERIC;
		} else if (data instanceof Boolean) {
			ret = Cell.CELL_TYPE_BOOLEAN;
		} else {
			throw new IllegalArgumentException("data is of an unknown type: "
					+ data.getClass().getName());
		}
		return ret;
	}

	protected void setCellFormula(final HSSFCell cell, final String formula) {
		beforeCellModification(cell);
		try {
			cell.setCellFormula(formula);
		} catch (final Exception e) {
			final String message = "An error occurred while trying to set a formula: "
					+ formula
					+ "; Note: POI is goofy with formulas; you need to replace "
					+ "semicolons with commas and POI will change them back";
			throw new IllegalArgumentException(message, e);
		}
	}

	protected void beforeCellModification(final HSSFCell cell) {
		if (!isProtectUnmodifiableCells() && cell != null) {
			final String cellId = cell.getRowIndex() + ", "
					+ cell.getColumnIndex();
			if (!unmodifiableCells.contains(cellId)) {
				throw new IllegalStateException("cell " + cellId
						+ " was previously set unmodifiable");
			}
		}
	}

	@Override
	public void setCellFormula(final HSSFCell cell, final String formula,
			final Style style) {
		setCellFormula(cell, formula);
		setCellStyle(cell, style);
	}

	@Override
	public void setCellData(final HSSFCell cell, final Object data,
			final Style style) {
		setCellData(cell, data);
		setCellStyle(cell, style);
	}

	@Override
	public HSSFRow getRowAt(final int rowIndex, final boolean create) {
		HSSFRow row = sheet.getRow(rowIndex);
		if (create && row == null) {
			row = sheet.createRow(rowIndex);
		}
		return row;
	}

	@Override
	public HSSFCell getCellAt(final int rowIndex, final int columnIndex,
			final boolean createRow, final boolean createCell) {
		HSSFRow row = getRowAt(rowIndex, false);
		if (!createRow && row == null) {
			throw new IllegalStateException("no row at index " + rowIndex);
		} else if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		HSSFCell cell = row.getCell(columnIndex);
		if (createCell && cell == null) {
			cell = row.createCell(columnIndex);
		}
		return cell;
	}

	public void mergeAddresses(final int rowIndexStart, final int rowIndexEnd,
			final int columnIndexStart, final int columnIndexEnd) {
		final CellRangeAddress addr = new CellRangeAddress(rowIndexStart,
				rowIndexEnd, columnIndexStart, columnIndexEnd);
		sheet.addMergedRegion(addr);
	}

	public void setColumnFormulaCellAt(final int rowIndex,
			final int columnIndex, final int firstFormulaRowIndex,
			final int lastFormulaRowIndex, final int formulaColumnIndex,
			final Style style, final String function,
			final List<String> argsBefore, final List<String> argsAfter) {

		final String a1 = CellAddress.getCellAddress(firstFormulaRowIndex,
				formulaColumnIndex);
		final String a2 = CellAddress.getCellAddress(lastFormulaRowIndex,
				formulaColumnIndex);
		String formula = function + "(";
		if (argsBefore != null) {
			for (int i = 0; i < argsBefore.size(); i++) {
				final String arg = argsBefore.get(i);
				if (i > 0) {
					formula += ",";
				}
				formula += arg;
			}
		}
		formula += a1 + ":" + a2;
		if (argsAfter != null) {
			for (final String arg : argsAfter) {
				formula += "," + arg;
			}
		}
		formula += ")";
		if (logger.isTraceEnabled()) {
			logger.trace("created formula " + formula);
		}
		final HSSFCell cell = getCellAt(rowIndex, columnIndex, true, true);
		beforeCellModification(cell);
		setCellFormula(cell, formula);
		cell.setCellStyle(getHSSFCellStyle(style));
	}

	public void setColumnFormulaCellAt(final int rowIndex,
			final int columnIndex, final int firstFormulaRowIndex,
			final int lastFormulaRowIndex, final int formulaColumnIndex,
			final Style style, final String function) {
		setColumnFormulaCellAt(rowIndex, columnIndex, firstFormulaRowIndex,
				lastFormulaRowIndex, formulaColumnIndex, style, function, null,
				null);
	}

	public void setRowFormulaCellAt(final int rowIndex, final int columnIndex,
			final int formulaRowIndex, final int firstFormulaColumnIndex,
			final int lastFormulaColumnIndex, final Style style,
			final String function) {
		final HSSFRow row = getRowAt(rowIndex, true);
		final String firstAddress = CellAddress.getCellAddress(row.getRowNum(),
				firstFormulaColumnIndex);
		final String secondAddress = CellAddress.getCellAddress(
				row.getRowNum(), lastFormulaColumnIndex);
		final String formula = function + "(" + firstAddress + ":"
				+ secondAddress + ")";
		final HSSFCell cell = row.createCell(columnIndex);
		setCellFormula(cell, formula);
		cell.setCellStyle(getHSSFCellStyle(style));
	}

	public void setPageOrientation(final int orientation) {
		if (orientation == PAGE_ORIENTATION_PORTRAIT) {
			sheet.getPrintSetup().setLandscape(false);
		} else if (orientation == PAGE_ORIENTATION_LANDSCAPE) {
			sheet.getPrintSetup().setLandscape(true);
		} else {
			throw new IllegalStateException("unknown print orientation");
		}
	}

	public void setPrintWidthPages(final int numberOfPages) {
		sheet.getPrintSetup().setFitWidth((short) numberOfPages);
		sheet.getPrintSetup().setFitHeight((short) 0);
	}

	public void setPrintHeightPages(final int numberOfPages) {
		sheet.getPrintSetup().setFitWidth((short) 0);
		sheet.getPrintSetup().setFitHeight((short) numberOfPages);
	}

	public void setPageHeaderLeftText(final String header) {
		sheet.getHeader().setLeft(header);
	}

	public void setPageHeaderCenterText(final String header) {
		sheet.getHeader().setCenter(header);
	}

	public void setPageHeaderRightText(final String header) {
		sheet.getHeader().setRight(header);
	}

	public void setPageFooterLeftText(final String header) {
		sheet.getFooter().setLeft(header);
	}

	public void setPageFooterCenterText(final String header) {
		sheet.getFooter().setCenter(header);
	}

	public void setPageFooterRightText(final String header) {
		sheet.getFooter().setRight(header);
	}

	public void setPageHeaderPageNumbers(final HorizontalAlignment position) {
		final String t = "Page " + HeaderFooter.page() + " of "
				+ HeaderFooter.numPages();
		if (position == HorizontalAlignment.LEFT) {
			sheet.getHeader().setLeft(t);
		} else if (position == HorizontalAlignment.CENTER) {
			sheet.getHeader().setCenter(t);
		} else if (position == HorizontalAlignment.RIGHT) {
			sheet.getHeader().setRight(t);
		} else {
			throw new IllegalStateException("unknown horizontal alignment");
		}
	}

	public void setPageFooterPageNumbers(final HorizontalAlignment position) {
		final String t = "Page " + HeaderFooter.page() + " of "
				+ HeaderFooter.numPages();
		if (position == HorizontalAlignment.LEFT) {
			sheet.getFooter().setLeft(t);
		} else if (position == HorizontalAlignment.CENTER) {
			sheet.getFooter().setCenter(t);
		} else if (position == HorizontalAlignment.RIGHT) {
			sheet.getFooter().setRight(t);
		} else {
			throw new IllegalStateException("unknown horizontal alignment");
		}
	}

	public void setPageMargins(final double[] margins) {
		if (margins == null) {
			throw new IllegalArgumentException("margins is null");
		}
		if (margins.length != 4) {
			throw new IllegalArgumentException("margins.length must be 4");
		}
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				sheet.setMargin(Sheet.TopMargin, margins[i]);
				break;
			case 1:
				sheet.setMargin(Sheet.LeftMargin, margins[i]);
				break;
			case 2:
				sheet.setMargin(Sheet.BottomMargin, margins[i]);
				break;
			case 3:
				sheet.setMargin(Sheet.RightMargin, margins[i]);
				break;
			default:
				throw new IllegalStateException("unsupported case");
			}
		}
	}

	public void setPageMargins(final double margin) {
		sheet.setMargin(Sheet.LeftMargin, margin);
		sheet.setMargin(Sheet.RightMargin, margin);
		sheet.setMargin(Sheet.TopMargin, margin);
		sheet.setMargin(Sheet.BottomMargin, margin);
	}

	public void setUnmodifiableCell(final int rowIndex, final int columnIndex) {
		final HSSFCell cell = getCellAt(rowIndex, columnIndex, false, false);
		final String cellId = rowIndex + "," + columnIndex;
		if (cell == null) {
			throw new IllegalStateException("no cell exists at " + cellId);
		}
		unmodifiableCells.add(cellId);
	}

	public boolean isProtectUnmodifiableCells() {
		return protectUnmodifiableCells;
	}

	public void setProtectUnmodifiableCells(
			final boolean protectUnmodifiableCells) {
		this.protectUnmodifiableCells = protectUnmodifiableCells;
	}

	@Override
	public Rectangle getBounds() {
		final int y1 = sheet.getFirstRowNum();
		final int y2 = sheet.getLastRowNum();
		final int x1a = getRowAt(y1, false).getFirstCellNum();
		final int x1b = getRowAt(y2, false).getFirstCellNum();
		final int x2a = getRowAt(y1, false).getLastCellNum();
		final int x2b = getRowAt(y2, false).getLastCellNum();
		final int x1 = Math.max(x1a, x1b);
		final int x2 = Math.max(x2a, x2b);
		return new Rectangle(new Point(x1, y2), new Dimension(x2 - x1, y2 - y1));
	}

	@Override
	public Dimension getDimension() {
		return getBounds().getSize();
	}

	@Override
	public void write(final OutputStream outputStream) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("writing workbook " + workbook);
		}
		workbook.write(outputStream);
		outputStream.flush();
	}
}