package org.javautil.workbook;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.javautil.poi.style.HSSFCellStyleFactory;

/**
 * 
 * @author jjs
 * 
 */
public interface BaseSheet {

	public abstract String getDateStyleFormat();

	public abstract void setPreviousColumnWidth(int i);

	/**
	 * Freeze Pane with last row added as the bottom of the frozen portion.
	 * 
	 * No left margin frozen.
	 * 
	 */
	public abstract void createFreezePaneRow();

	public abstract HSSFWorkbook getWorkbook();

	public abstract void addCell(Object data, HSSFCellStyle cellStyle);

	public abstract void addCell(Object data, int charWidth,
			HSSFCellStyle cellStyle);

	/**
	 * @return the rowIndex
	 */
	public abstract short getRowIndex();

	/**
	 * @return the columnIndex
	 */
	public abstract short getColumnIndex();

	public abstract void addFormulaCell(String formula, HSSFCellStyle cellStyle);

	// public StandardStyles getStyles();

	public void setColumnWidth(int columnIndex, final int characterWidth);

	/**
	 * Set the width, in characters of the column of the last cell created
	 * 
	 * @param characterWidth
	 */
	public void setColumnWidth(final int characterWidth);

	public HSSFRow addRow();

	public abstract void setRowHeight(int i);

	public HSSFCellStyleFactory getStyleFactory();

}
