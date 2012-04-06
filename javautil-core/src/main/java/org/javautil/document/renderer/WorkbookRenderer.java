package org.javautil.document.renderer;

public interface WorkbookRenderer {

	public void addSheet(String sheetName);

	public String getCellAddress(int rowIndex, int columnIndex);

	public String getCellRange(int rowIndexStart, int rowIndexEnd,
			int columnIndexStart, int columnIndexEnd);

	public void setRowHeight(int rowIndex, int height);

	public void setColumnWidth(int columnIndex, int width);

	public void mergeCellRange(int rowIndexStart, int rowIndexEnd,
			int columnIndexStart, int columnIndexEnd);
}