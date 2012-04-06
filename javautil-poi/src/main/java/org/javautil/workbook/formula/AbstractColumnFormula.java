package org.javautil.workbook.formula;

import org.javautil.workbook.WorkbookUtils;

public abstract class AbstractColumnFormula implements ColumnFormula {

	public String getRange(final int columnIndex, final int startRow,
			final int endRow) {
		final String columnId = WorkbookUtils.getColumnId(columnIndex);
		return columnId + startRow + ":" + columnId + endRow;
	}

}
