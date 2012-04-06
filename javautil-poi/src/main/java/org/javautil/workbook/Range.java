package org.javautil.workbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javautil.dataset.ColumnMetadata;

public class Range {
	private int firstColumn;

	private int firstRow;

	private int lastColumn;

	private int lastRow;

	private List<ColumnMetadata> columns;

	private final Map<String, Integer> columnByName = new HashMap<String, Integer>();

	public List<ColumnMetadata> getColumns() {
		return columns;
	}

	public void setColumns(final List<ColumnMetadata> columns) {
		this.columns = columns;
		int i = 0;
		for (final ColumnMetadata column : columns) {
			columnByName.put(column.getColumnName(), i++);
		}
	}

	public Range() {
	}

	public Range(final int firstRow, final int firstColumn, final int lastRow,
			final int lastColumn) {
		super();
		this.firstColumn = firstColumn;
		this.firstRow = firstRow;
		this.lastColumn = lastColumn;
		this.lastRow = lastRow;
	}

	public int getColumnIndexByColumnName(final String columnName) {
		final Integer offset = columnByName.get(columnName);
		if (offset == null) {
			throw new IllegalArgumentException("no such column as '"
					+ columnName + "'");
		}
		return firstColumn + offset;
	}

	public static String getRangeAddress(final int firstRow,
			final int firstColumn, final int lastRow, final int lastColumn) {
		final String addr1 = CellAddress.getCellAddress(firstRow, firstColumn);
		final String addr2 = CellAddress.getCellAddress(lastRow, lastColumn);
		return addr1 + ":" + addr2;

	}

	public String getRangeAddress() {
		return getRangeAddress(firstRow, firstColumn, lastRow, lastColumn);
	}

	public int getFirstColumn() {
		return firstColumn;
	}

	public void setFirstColumn(final int firstColumn) {
		this.firstColumn = firstColumn;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(final int firstRow) {
		this.firstRow = firstRow;
	}

	public int getLastColumn() {
		return lastColumn;
	}

	public void setLastColumn(final int lastColumn) {
		this.lastColumn = lastColumn;
	}

	public int getLastRow() {
		return lastRow;
	}

	public void setLastRow(final int lastRow) {
		this.lastRow = lastRow;
	}

}
