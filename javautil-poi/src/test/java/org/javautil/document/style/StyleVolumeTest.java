package org.javautil.document.style;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.junit.Test;

public class StyleVolumeTest {
	GregorianCalendar cal = new GregorianCalendar();

	Map<Integer, String> dateFormat = new HashMap<Integer, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 35299690295416463L;

		{
			put(0, "mm/dd/yyyy");
			put(1, "mm-dd-yyyy");
			put(2, "mm dd, yyyy");
			put(3, "mmm dd, yyyy");
			put(4, "mmm dd, yy");
			put(5, "MMM D, YY");
			put(6, "MMMM D, YYYY");
		}
	};

	private HSSFSheet sheet;
	private int rowIndex = 0;

	private final Log logger = LogFactory.getLog(getClass());

	private HSSFCellStyleFactory styleFactory;

	// TODO fix this test

	@Test
	public void styleVolume() throws Exception {

		final String fileName = "/tmp/" + getClass().getName() + ".xls";
		final OutputStream outputstream = new FileOutputStream(fileName);

		try {
			final HSSFWorkbook workbook = new HSSFWorkbook();
			sheet = workbook.createSheet();
			styleFactory = new HSSFCellStyleFactory(workbook);

			addHeader();
			addRows();

			logger.debug("workbook building finished");

			workbook.write(outputstream);

			logger.debug("writing to " + fileName);

		} finally {
			outputstream.close();
		}
	}

	private void addHeader() {
		final HSSFRow row = createRow();
		final HSSFCell cell = row.createCell(0);
		cell.setCellValue("date");
		final Style style = new StyleImpl();
		style.setFont(new Font("Arial", Font.BOLD, 10));
		final HSSFCellStyle cellStyle = styleFactory.getHSSFCellStyle(style);
		cell.setCellStyle(cellStyle);
	}

	private void addRows() {

		Date d = new Date();

		for (int i = 0; i < 65000; i++) {
			Style s = new StyleImpl();
			s = s.copyOf();

			final String format = getDateFormat(d);
			s.setFormatMask(format);

			styleFactory.getHSSFCellStyle(s);

			d = getNextDate(d);

		}

	}

	private String getDateFormat(final Date d) {
		cal.setTime(d);
		final int dow = cal.get(Calendar.DAY_OF_WEEK);
		return dateFormat.get(dow);
	}

	private Date getNextDate(final Date d) {
		cal.setTime(d);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	private HSSFRow createRow() {
		final HSSFRow row = sheet.createRow(rowIndex++);
		return row;
	}
}
