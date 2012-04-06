package org.javautil.workbook;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public abstract class AbstractWorkbookStyle implements WorkbookStyle {

	protected HSSFWorkbook workbook;
	private HSSFCellStyle headerStyle;
	private HSSFCellStyle dataStyle;
	private HSSFCellStyle dollarStyle;
	private HSSFCellStyle leftHeaderString;
	private HSSFCellStyle rightHeaderString;
	private HSSFCellStyle leftDataString;
	private HSSFCellStyle rightDataNumber;
	private HSSFCellStyle leftBigBold;
	private HSSFCellStyle leftBig;
	private HSSFFont hdrFont;
	private HSSFFont dataFont;
	private HSSFCellStyle centerHeader;
	private HSSFCellStyle centerData;
	private HSSFFont bigFont;
	private HSSFFont bigBoldFont;

	/**
	 * @param leftHeaderString
	 *            the leftHeaderString to set
	 */
	protected void setLeftHeaderString(final HSSFCellStyle leftHeaderString) {
		this.leftHeaderString = leftHeaderString;
	}

	/**
	 * @param rightHeaderString
	 *            the rightHeaderString to set
	 */
	protected void setRightHeaderString(final HSSFCellStyle rightHeaderString) {
		this.rightHeaderString = rightHeaderString;
	}

	/**
	 * @param leftDataString
	 *            the leftDataString to set
	 */
	protected void setLeftDataString(final HSSFCellStyle leftDataString) {
		this.leftDataString = leftDataString;
	}

	/**
	 * @param hdrFont
	 *            the hdrFont to set
	 */
	protected void setHdrFont(final HSSFFont hdrFont) {
		this.hdrFont = hdrFont;
	}

	/**
	 * @param dataFont
	 *            the dataFont to set
	 */
	protected void setDataFont(final HSSFFont dataFont) {
		this.dataFont = dataFont;
	}

	/**
	 * @return the workbook
	 */
	@Override
	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	/**
	 * @return the leftHeaderString
	 */
	@Override
	public HSSFCellStyle getLeftHeaderString() {
		return leftHeaderString;
	}

	/**
	 * @return the rightHeaderString
	 */
	@Override
	public HSSFCellStyle getRightHeader() {
		return rightHeaderString;
	}

	/**
	 * @return the leftDataString
	 */
	@Override
	public HSSFCellStyle getLeftData() {
		return leftDataString;
	}

	/**
	 * @return the hdrFont
	 */
	@Override
	public HSSFFont getHdrFont() {
		return hdrFont;
	}

	/**
	 * @return the dataFont
	 */
	@Override
	public HSSFFont getDataFont() {
		return dataFont;
	}

	@Override
	public void setWorkbook(final HSSFWorkbook workbook) {
		if (workbook == null) {
			throw new IllegalArgumentException("workbook is null");
		}
		this.workbook = workbook;

	}

	/**
	 * @return the rightDataNumber
	 */
	@Override
	public HSSFCellStyle getRightDataNumber() {
		return rightDataNumber;
	}

	/**
	 * @param rightDataNumber
	 *            the rightDataNumber to set
	 */
	protected void setRightDataNumber(final HSSFCellStyle rightDataNumber) {
		this.rightDataNumber = rightDataNumber;
	}

	public HSSFCellStyle getHeaderStyle() {
		return headerStyle;
	}

	public HSSFCellStyle getDataStyle() {
		return dataStyle;
	}

	public void setDataStyle(final HSSFCellStyle dataStyle) {
		this.dataStyle = dataStyle;
	}

	@Override
	public HSSFCellStyle getDollarStyle() {
		return dollarStyle;
	}

	protected void setDollarStyle(final HSSFCellStyle dollarStyle) {
		this.dollarStyle = dollarStyle;
	}

	public void setCenterHeader(final HSSFCellStyle headerStyle) {
		this.centerHeader = headerStyle;

	}

	public void setCenterData(final HSSFCellStyle dataStyle) {
		this.centerData = dataStyle;

	}

	@Override
	public HSSFCellStyle getCenterHeader() {
		return centerHeader;
	}

	@Override
	public HSSFCellStyle getCenterData() {
		return centerData;
	}

	@Override
	public HSSFFont getBigBoldFont() {
		return bigBoldFont;
	}

	@Override
	public HSSFFont getBigFont() {
		return bigFont;
	}

	protected void setBigFont(final HSSFFont bigFont) {
		this.bigFont = bigFont;
	}

	protected void setBigBoldFont(final HSSFFont bigBoldFont) {
		this.bigBoldFont = bigBoldFont;
	}

	@Override
	public HSSFCellStyle getLeftBigBold() {
		return leftBigBold;
	}

	protected void setLeftBigBold(final HSSFCellStyle leftBigBold) {
		this.leftBigBold = leftBigBold;
	}

	@Override
	public HSSFCellStyle getLeftBig() {
		return leftBig;
	}

	public void setLeftBig(final HSSFCellStyle leftBig) {
		this.leftBig = leftBig;
	}

}
