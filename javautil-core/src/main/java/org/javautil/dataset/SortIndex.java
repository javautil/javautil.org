package org.javautil.dataset;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class SortIndex {

	private int columnIndex;

	private boolean ascending = true;

	public SortIndex() {
	}

	public SortIndex(final int sortColumn, final boolean ascending) {
		super();
		this.columnIndex = sortColumn;
		this.ascending = ascending;
	}

	public int getSortColumn() {
		return columnIndex;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(final boolean ascending) {
		this.ascending = ascending;
	}

	public void setSortColumn(final int columnName) {
		this.columnIndex = columnName;
	}

}
