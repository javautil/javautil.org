package org.javautil.workbook.formula;

public class SumColumnFormula extends AbstractColumnFormula {

	/**
	 * If you don't know the column index for the letter of the column use POI's
	 * CellReference.convertColStringToIndex method to find it.
	 */
	@Override
	public String getFormula(final int columnIndex, final int startRow,
			final int endRow) {
		return "SUM(" + getRange(columnIndex, startRow, endRow) + ")";
	}

}
