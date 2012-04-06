package org.javautil.workbook;

public class DataRange extends Range {

	private int dataBeginsRow;

	private int footerBeginsRow;

	public int getDataBeginsRow() {
		return dataBeginsRow;
	}

	public void setDataBeginsRow(final int dataBeginsRow) {
		this.dataBeginsRow = dataBeginsRow;
	}

	public int getFooterBeginsRow() {
		return footerBeginsRow;
	}

	public void setFooterBeginsRow(final int footerBeginsRow) {
		this.footerBeginsRow = footerBeginsRow;
	}

	public String getColumnDataRange(final String columnName) {
		final int firstRow = getDataBeginsRow();
		final int endRow = getFooterBeginsRow();
		final int columnIndex = getColumnIndexByColumnName(columnName);
		return getRangeAddress(firstRow, columnIndex, endRow, columnIndex);

	}

}
