package org.javautil.workbook;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public interface WorkbookStyle {

	/**
	 * @return the workbook
	 */
	public abstract HSSFWorkbook getWorkbook();

	/**
	 * @param workbook
	 *            the workbook to set
	 */
	public abstract void setWorkbook(HSSFWorkbook workbook);

	/**
	 * @return the leftHeaderString
	 */
	public abstract HSSFCellStyle getLeftHeaderString();

	/**
	 * @return the rightHeaderString
	 */
	public abstract HSSFCellStyle getRightHeader();

	/**
	 * @return the leftDataString
	 */
	public abstract HSSFCellStyle getLeftData();

	/**
	 * @return the hdrFont
	 */
	public abstract HSSFFont getHdrFont();

	public abstract HSSFCellStyle getDollarStyle();

	/**
	 * @return the dataFont
	 */
	public abstract HSSFFont getDataFont();

	public abstract HSSFCellStyle getRightDataNumber();

	public abstract HSSFCellStyle getCenterData();

	public abstract HSSFCellStyle getCenterHeader();

	public abstract HSSFFont getBigFont();

	public abstract HSSFFont getBigBoldFont();

	public abstract HSSFCellStyle getLeftBigBold();

	public abstract HSSFCellStyle getLeftBig();

}