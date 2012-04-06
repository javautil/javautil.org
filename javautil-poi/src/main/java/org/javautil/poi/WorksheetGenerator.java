package org.javautil.poi;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.javautil.poi.style.HSSFCellStyleFactory;

public interface WorksheetGenerator {
	public void generateWorksheet(HSSFWorkbook workbook,
			HSSFCellStyleFactory styles, String datasourceName,
			Map<String, Object> parms);
}
