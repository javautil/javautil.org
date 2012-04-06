package org.javautil.document.style;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.junit.Before;
import org.junit.Test;

public class WorkbookCellStyleFactoryTest extends PoiStyleDefinitionsTest {

	private final Log logger = LogFactory.getLog(getClass());

	private static Map<String, Style> styles;

	@Before
	public void parse() {
		final StyleParser parser = new DefaultStyleParser();
		styles = StyleUtil.getStylesFromContext(appContext, parser);
		logger.info("style names = " + styles.keySet());
		Assert.assertTrue(styles.size() > 0);
	}

	@Test
	public void testRedIntegerStyleWorkbook() throws Exception {

		final File workbookFile = createWorkbook("redInteger", -12345678);
		final InputStream inputstream = new FileInputStream(workbookFile);
		try {
			final HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			final HSSFSheet sheet = workbook.getSheetAt(0);
			final HSSFCell cell = getCell(sheet, 1, 1);
			final HSSFCellStyle style = cell.getCellStyle();
			final HSSFColor color = getFontColor(workbook, style);
			assertColorRgb(color, 255, 0, 0);
			Assert.assertEquals(CellStyle.ALIGN_RIGHT, style.getAlignment());
			Assert.assertEquals(CellStyle.VERTICAL_TOP,
					style.getVerticalAlignment());
			Assert.assertEquals((float) -12345678,
					(float) cell.getNumericCellValue());
			Assert.assertEquals("###,###,###,###,##0",
					style.getDataFormatString());
		} finally {
			inputstream.close();
		}

	}

	@Test
	public void testSingleUnderlineStyleWorkbook() throws Exception {
		final File workbookFile = createWorkbook("singleUnderline", 0.11);
		final InputStream inputstream = new FileInputStream(workbookFile);
		try {
			final HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			final HSSFSheet sheet = workbook.getSheetAt(0);
			final HSSFCell cell = getCell(sheet, 1, 1);
			final HSSFCellStyle style = cell.getCellStyle();
			final HSSFFont font = getFont(workbook, style);
			final byte underlineStyle = font.getUnderline();
			Assert.assertEquals(Font.U_SINGLE, underlineStyle);
		} finally {
			inputstream.close();
		}
	}

	@Test
	public void testDoubleUnderlineStyleWorkbook() throws Exception {
		final File workbookFile = createWorkbook("doubleUnderline", 0.12);
		final InputStream inputstream = new FileInputStream(workbookFile);
		try {
			final HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			final HSSFSheet sheet = workbook.getSheetAt(0);
			final HSSFCell cell = getCell(sheet, 1, 1);
			final HSSFCellStyle style = cell.getCellStyle();
			final HSSFFont font = getFont(workbook, style);
			final byte underlineStyle = font.getUnderline();
			Assert.assertEquals(Font.U_DOUBLE, underlineStyle);
		} finally {
			inputstream.close();
		}
	}

	@Test
	public void testWrapWorkbook() throws Exception {
		final File workbookFile = createWorkbook("wrap",
				"wiki\nwiki\nwiki\nwiki");
		final InputStream inputstream = new FileInputStream(workbookFile);
		try {
			final HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			final HSSFSheet sheet = workbook.getSheetAt(0);
			final HSSFCell cell = getCell(sheet, 1, 1);
			final HSSFCellStyle style = cell.getCellStyle();
			final boolean wrapText = style.getWrapText();
			Assert.assertTrue(wrapText);
		} finally {
			inputstream.close();
		}
	}

	@Test
	public void testIntegerBlueBgStyleWorkbook() throws Exception {

		final File workbookFile = createWorkbook("integerBlueBg", -1.21);
		final InputStream inputstream = new FileInputStream(workbookFile);
		try {
			final HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			final HSSFSheet sheet = workbook.getSheetAt(0);
			final HSSFCell cell = getCell(sheet, 1, 1);
			final HSSFCellStyle style = cell.getCellStyle();
			Assert.assertEquals(CellStyle.SOLID_FOREGROUND,
					style.getFillPattern());
			HSSFColor color = getBackgroundColor(workbook, style);
			assertColorRgb(color, 153, 153, 204);
			color = getFontColor(workbook, style);
			assertColorRgb(color, 0, 0, 0);
			Assert.assertEquals(CellStyle.ALIGN_RIGHT, style.getAlignment());
			Assert.assertEquals(CellStyle.VERTICAL_TOP,
					style.getVerticalAlignment());
			Assert.assertEquals((float) -1.21,
					(float) cell.getNumericCellValue());
			Assert.assertEquals("###,###,###,###,##0",
					style.getDataFormatString());
		} finally {
			inputstream.close();
		}

	}

	private File createWorkbook(final String styleName, final Object cellValue)
			throws Exception {
		final String prefix = getClass().getName() + "_";
		final File workbookFile = File.createTempFile(prefix, ".xls");
		final OutputStream outputstream = new FileOutputStream(workbookFile);
		try {
			final HSSFWorkbook workbook = new HSSFWorkbook();
			final Map<String, HSSFCellStyle> hssfCellStyles = HSSFCellStyleFactory
					.getHSSFCellStyles(workbook, styles.values());
			final HSSFSheet sheet = workbook.createSheet();
			final HSSFRow row = sheet.createRow(1);
			final HSSFCell cell = row.createCell(1);
			final HSSFCellStyle targetStyle = hssfCellStyles.get(styleName);
			logger.debug("using style " + styleName);
			final Style style = styles.get(styleName);
			if (style == null) {
				throw new IllegalArgumentException("no such style named \""
						+ styleName + "\"");
			}
			logger.debug("style = " + style);
			if (targetStyle == null) {
				throw new IllegalArgumentException("factory returned null for "
						+ "style named \"" + styleName + "\"");
			}
			cell.setCellStyle(targetStyle);
			if (cellValue instanceof Double) {
				cell.setCellValue((Double) cellValue);
			} else if (cellValue instanceof Integer) {
				cell.setCellValue((Integer) cellValue);
			} else if (cellValue instanceof String) {
				cell.setCellValue((String) cellValue);
			} else {
				throw new IllegalArgumentException("unknown cell data of type "
						+ cellValue.getClass());
			}
			workbook.write(outputstream);
		} finally {
			outputstream.close();
		}

		// Runtime.getRuntime().exec("ooffice " +
		// workbookFile.getAbsolutePath());
		// Thread.sleep(10000);

		// workbookFile.deleteOnExit();
		return workbookFile;
	}

	private HSSFCell getCell(final HSSFSheet sheet, final int rowNum,
			final int colNum) {
		final HSSFRow row = sheet.getRow(rowNum);
		final HSSFCell cell = row.getCell(colNum);
		return cell;
	}

	private HSSFColor getFontColor(final HSSFWorkbook workbook,
			final HSSFCellStyle style) {
		final short colorIndex = style.getFont(workbook).getColor();
		final HSSFColor color = workbook.getCustomPalette()
				.getColor(colorIndex);
		return color;
	}

	private HSSFFont getFont(final HSSFWorkbook workbook,
			final HSSFCellStyle style) {
		final short fontIndex = style.getFontIndex();
		final HSSFFont font = workbook.getFontAt(fontIndex);
		return font;
	}

	private HSSFColor getBackgroundColor(final HSSFWorkbook workbook,
			final HSSFCellStyle style) {
		final short colorIndex = style.getFillForegroundColor();
		final HSSFColor color = workbook.getCustomPalette()
				.getColor(colorIndex);
		return color;
	}

	private void assertColorRgb(final HSSFColor color, final int r,
			final int g, final int b) {
		Assert.assertEquals(r, color.getTriplet()[0]);
		Assert.assertEquals(g, color.getTriplet()[1]);
		Assert.assertEquals(b, color.getTriplet()[2]);
	}

}
