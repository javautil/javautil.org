package org.javautil.poi;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.javautil.document.style.HorizontalAlignment;
import org.javautil.document.style.Style;
import org.javautil.document.style.StyleFactory;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.junit.Test;

public class HSSFCellStyleFactoryTest {

	@Test
	public void example1() throws IOException {
		final HSSFWorkbook book = new HSSFWorkbook();
		final HSSFSheet sheet = book.createSheet();
		final HSSFCellStyleFactory cellStyleFactory = new HSSFCellStyleFactory(
				book);

		final Style s = StyleFactory.getStyle(HorizontalAlignment.RIGHT,
				"#,##0.00");
		final HSSFCellStyle cs = cellStyleFactory.getHSSFCellStyle(s);

		final HSSFCell cell = sheet.createRow(0).createCell(0);
		cell.setCellValue(3456.7);
		cell.setCellStyle(cs);
		final String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		final File tempFile = File.createTempFile(getClass().getName() + "."
				+ methodName, ".xls");
		final FileOutputStream fos = new FileOutputStream(tempFile);
		book.write(fos);
		fos.close();
	}

	@Test
	public void example2() throws IOException {
		final HSSFWorkbook book = new HSSFWorkbook();
		final HSSFSheet sheet = book.createSheet();
		final HSSFCellStyleFactory cellStyleFactory = new HSSFCellStyleFactory(
				book);

		final Style backgroundRed = StyleFactory.getStyle(
				HorizontalAlignment.RIGHT, Color.RED, "#,##0.00");
		final HSSFCellStyle cs = cellStyleFactory
				.getHSSFCellStyle(backgroundRed);

		final HSSFCell cell = sheet.createRow(0).createCell(0);
		cell.setCellValue(3456.7);
		cell.setCellStyle(cs);
		final String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		final File tempFile = File.createTempFile(getClass().getName() + "."
				+ methodName, ".xls");
		final FileOutputStream fos = new FileOutputStream(tempFile);
		book.write(fos);
		fos.close();
	}
}
