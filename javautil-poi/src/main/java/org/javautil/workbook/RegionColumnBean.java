package org.javautil.workbook;

import org.javautil.document.style.Style;

/**
 * @author jjs at dbexperts dot com
 * 
 */
public class RegionColumnBean implements RegionColumn {

	/**
	 * A columnNumber must be unique, but does not have to be contiguous.
	 * 
	 * This is the columnNumber in the output region. It is not an index, but
	 * must be unique within a Region definition. todo define region definition.
	 */
	private Integer columnNumber;
	/**
	 * The index into the source of the data for this column.
	 * 
	 * The index of the column in the Dataset or resultSet that is the source of
	 * this column.
	 */

	private Integer sourceColumnIndex;
	/**
	 * A column name may occur multiple times in a region.
	 */
	private String columnName;
	/**
	 * The heading to be put over the column.
	 * 
	 * If a column comes from a Dataset it is common to emit a column heading.
	 */
	private String heading;

	private Style style;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.workbook.RegionColumn#getColumnNumber()
	 */
	@Override
	public Integer getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(final Integer columnNumber) {
		this.columnNumber = columnNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.workbook.RegionColumn#getSourceColumnIndex()
	 */
	@Override
	public Integer getSourceColumnIndex() {
		return sourceColumnIndex;
	}

	public void setSourceColumnIndex(final Integer sourceColumnIndex) {
		this.sourceColumnIndex = sourceColumnIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.workbook.RegionColumn#getColumnName()
	 */
	@Override
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.workbook.RegionColumn#getHeading()
	 */
	@Override
	public String getHeading() {
		return heading;
	}

	public void setHeading(final String heading) {
		this.heading = heading;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.workbook.RegionColumn#getStyle()
	 */
	@Override
	public Style getStyle() {
		return style;
	}

	public void setStyle(final Style style) {
		this.style = style;
	}

}
