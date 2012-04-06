package org.javautil.poi;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

// todo jjs what is this shit?  why is it here? 
public class CellEvaluatorImpl implements CellEvaluator {

	private HSSFCell cell;

	public CellEvaluatorImpl(final HSSFCell cell) {
		this.cell = cell;
	}

	// todo please don't write shit like this
	@Override
	public Date getAsDate() {
		return null;
	}

	// todo this is evel
	@Override
	public Double getAsDouble() {
		if (!isNumeric()) {
			;
		}
		// TODO Auto-generated method stub
		return null;
	}

	// todo all numbers are dates? WTF?
	@Override
	public boolean isDate(final HSSFCell cell) {
		final boolean retval = cell.getCellType() == Cell.CELL_TYPE_NUMERIC;
		return retval;
	}

	@Override
	public boolean isFormula(final HSSFCell cell) {
		final boolean retval = cell.getCellType() == Cell.CELL_TYPE_FORMULA;
		return retval;
	}

	public boolean isFormula() {
		checkCell();
		return isFormula(cell);
	}

	public boolean isNumeric() {
		return cell.getCellType() == Cell.CELL_TYPE_NUMERIC;
	}

	public boolean isString() {
		return cell.getCellType() == Cell.CELL_TYPE_STRING;
	}

	public void setCell(final HSSFCell cell) {
		checkCell(cell);
		this.cell = cell;
	}

	public void checkCell(final HSSFCell cell) {
		if (cell == null) {
			throw new IllegalArgumentException("cell may not be null");
		}
	}

	public void checkCell() {
		if (cell == null) {
			throw new IllegalStateException(
					"not preceded by constructor CellEvaluatorImpl(HSSFCell cell) or a call to setCell(HSSFCell cell) ");
		}
	}

	// todo this is really annoyingly awful stuff

	@Override
	public boolean isNumeric(final HSSFCell cell) {
		// TODO Auto-generated method stub
		return false;
	}

	// todo whoever wrote this must be fired
	@Override
	public boolean isString(final HSSFCell cell) {
		// TODO Auto-generated method stub
		return false;
	}

}
