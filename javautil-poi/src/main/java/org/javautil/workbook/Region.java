package org.javautil.workbook;

public class Region {
	private Integer startingRowIndex;

	private Integer startingColumnIndex;

	public Region() {

	}

	public Region(final Integer startingRowIndex,
			final Integer startingColumnIndex) {
		super();
		this.startingRowIndex = startingRowIndex;
		this.startingColumnIndex = startingColumnIndex;
	}

	public Integer getStartingRowIndex() {
		return startingRowIndex;
	}

	public void setStartingRowIndex(final Integer startingRowIndex) {
		this.startingRowIndex = startingRowIndex;
	}

	public Integer getStartingColumnIndex() {
		return startingColumnIndex;
	}

	public void setStartingColumnIndex(final Integer startingColumnIndex) {
		this.startingColumnIndex = startingColumnIndex;
	}

}
