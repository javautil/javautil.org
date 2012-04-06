package org.javautil.poi;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;

public interface CellEvaluator {

	public boolean isDate(HSSFCell cell);

	public boolean isNumeric(HSSFCell cell);

	public boolean isString(HSSFCell cell);

	public boolean isFormula(HSSFCell cell);

	public Date getAsDate();

	public Double getAsDouble();

}
