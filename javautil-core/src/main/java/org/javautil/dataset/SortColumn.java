package org.javautil.dataset;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class SortColumn {

	private String sortColumn;

	private boolean ascending = true;

	public SortColumn() {

	}

	public SortColumn(final String sortColumn, final boolean ascending) {
		super();
		this.sortColumn = sortColumn;
		this.ascending = ascending;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(final boolean ascending) {
		this.ascending = ascending;
	}

	public void setSortColumn(final String columnName) {
		this.sortColumn = columnName;
	}

}
