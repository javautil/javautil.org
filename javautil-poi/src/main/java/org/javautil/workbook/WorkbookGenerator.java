package org.javautil.workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public interface WorkbookGenerator {
	public void generateWorkbook(Map<String, Object> parms, OutputStream os)
			throws IOException;
}
