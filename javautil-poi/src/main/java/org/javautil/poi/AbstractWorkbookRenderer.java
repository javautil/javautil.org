package org.javautil.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class AbstractWorkbookRenderer extends POIWorkbookRenderer
		implements WorksheetGenerator {

	public AbstractWorkbookRenderer(final HSSFWorkbook workbook) {
		super(workbook);
	}

}