package org.javautil.poi;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMetadata;
import org.javautil.document.style.ColorUtil;
import org.javautil.document.style.Style;
import org.javautil.document.style.StyleImpl;
import org.javautil.file.InputStreamComparator;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.junit.Ignore;
import org.junit.Test;

public class CrosstabbedWorksheetRenderTest {

	private final Logger logger = Logger.getLogger(getClass());

	protected void initializeStyles(final HSSFCellStyleFactory styleFactory) {
		final Color c = ColorUtil.parseColor("#6996AD");
		final Color c1 = ColorUtil.parseColor("#FFFFFF");
		final Style headingStyle = new StyleImpl();
		headingStyle.setBackgroundColor(c);
		headingStyle.setFontColor(c1);
		styleFactory.setBaseHeaderStyle(headingStyle);

		styleFactory.setBaseDataStyle(new StyleImpl());
		styleFactory.setBaseFooterStyle(new StyleImpl());

	}

	@Ignore
	@Test
	public void test1() throws IOException {
		final CrosstabDataset datasetCrosstabbed = new CrosstabDataset();
		final Dataset ds = datasetCrosstabbed.getCrosstabbedDataset();
		if (logger.isDebugEnabled()) {
			final DatasetMetadata dm = ds.getMetadata();
			logger.debug(dm);
			// todo not properly marshalling crosstabbed info
			// DatasetCSVMetadataMarshaller.write(ds, System.out, System.out);
		}
		final List<String> rowIdentifyingColumns = datasetCrosstabbed
				.getRowIdentifyingColumns();
		datasetCrosstabbed.getColumnIdentifyingColumns();

		final HSSFWorkbook workbook = new HSSFWorkbook();
		final HSSFSheet sheet = workbook.createSheet();
		final HSSFCellStyleFactory styleFactory = new HSSFCellStyleFactory(
				workbook);
		initializeStyles(styleFactory);

		final CrosstabbedWorksheetRenderer crosstabSheet = new CrosstabbedWorksheetRenderer(
				sheet, styleFactory);
		final Collection<ColumnMetadata> columnMeta = ds.getMetadata()
				.getColumnMetadata();
		// List<String> nonRowIdentifyingColumns = new ArrayList<String>();
		// nonRowIdentifyingColumns.add(columnIdentifyColumns);
		final List<String> cellColumns = datasetCrosstabbed
				.getCellColumnNames();

		final int startingRow = 0;
		final int startingColumn = 0;
		crosstabSheet.emitCrosstabRegion(ds, startingRow, startingColumn,
				rowIdentifyingColumns, cellColumns,
				// nonRowIdentifyingColumns,
				columnMeta);
		final File actualFile = File.createTempFile("Crosstab", ".xls");

		final OutputStream os = new FileOutputStream(actualFile);

		workbook.write(os);
		os.close();
		final FileInputStream actual = new FileInputStream(actualFile);
		final FileInputStream expected = new FileInputStream(
				"src/test/data/org/javautil/poi/CrosstabbedWorksheetTest1.xls");
		final InputStreamComparator isc = new InputStreamComparator();
		final int result = isc.compare(actual, expected);
		actual.close();
		expected.close();
		actualFile.delete();
		assertEquals(0, result);

	}

	@Ignore
	// TODO this is cut and paste from test1 strip out common code
	@Test
	public void test2() throws IOException {
		// get a crosstabbed dataset
		final CrosstabDataset datasetCrosstabbed = new CrosstabDataset();
		final Dataset ds = datasetCrosstabbed.getCrosstabbedDataset();
		if (logger.isDebugEnabled()) {
			final DatasetMetadata dm = ds.getMetadata();
			logger.debug(dm);
			// todo not properly marshalling crosstabbed info
			// DatasetCSVMetadataMarshaller.write(ds, System.out, System.out);
		}
		//
		final List<String> rowIdentifyingColumns = datasetCrosstabbed
				.getRowIdentifyingColumns();
		datasetCrosstabbed.getColumnIdentifyingColumns();
		//
		final HSSFWorkbook workbook = new HSSFWorkbook();
		final HSSFSheet sheet = workbook.createSheet();
		final HSSFCellStyleFactory styleFactory = new HSSFCellStyleFactory(
				workbook);
		initializeStyles(styleFactory);
		//

		final CrosstabbedWorksheetHelper crosstabSheet = new CrosstabbedWorksheetHelper(
				sheet, styleFactory);
		final Collection<ColumnMetadata> columnMeta = ds.getMetadata()
				.getColumnMetadata();
		// List<String> nonRowIdentifyingColumns = new ArrayList<String>();
		// nonRowIdentifyingColumns.add(columnIdentifyColumns);
		final List<String> cellColumns = datasetCrosstabbed
				.getCellColumnNames();

		final int startingRow = 0;
		final int startingColumn = 0;
		crosstabSheet.setSingleCellCrosstabColumnHeading(false); // the only
																	// difference
		crosstabSheet.emitCrosstabRegion(ds, startingRow, startingColumn,
				rowIdentifyingColumns, cellColumns,
				// nonRowIdentifyingColumns,
				columnMeta);
		final File actualFile = File.createTempFile("Crosstab", ".xls");
		logger.debug("created temporary file " + actualFile.getAbsolutePath());
		final OutputStream os = new FileOutputStream(actualFile);

		workbook.write(os);
		// os.close();
		// FileInputStream actual = new FileInputStream(actualFile);
		// FileInputStream expected = new
		// FileInputStream("src/test/data/org/javautil/poi/CrosstabbedWorksheetTest2.xls");
		// InputStreamComparator isc = new InputStreamComparator();
		// int result = isc.compare(actual,expected);
		// actual.close();
		// expected.close();
		// actualFile.delete();
		// assertEquals(0,result);

	}

	// TODO this is cut and paste from test1 strip out common code
	// TODO fix
	@Ignore
	@Test
	public void testCrosstabbedFormulas() throws IOException {
		// get a crosstabbed dataset
		final SalesDataset datasetCrosstabbed = new SalesDataset();
		final Dataset ds = datasetCrosstabbed.getCrosstabbedDataset();
		// if (logger.isDebugEnabled()) {
		// DatasetMetadata dm = ds.getMetadata();
		// logger.debug(dm);
		// // todo not properly marshalling crosstabbed info
		// DatasetCSVMetadataMarshaller.write(ds, System.out, System.out);
		// }
		//
		final List<String> rowIdentifyingColumns = datasetCrosstabbed
				.getRowIdentifyingColumns();
		datasetCrosstabbed.getColumnIdentifyingColumns();
		//
		final HSSFWorkbook workbook = new HSSFWorkbook();
		final HSSFSheet sheet = workbook.createSheet();
		final HSSFCellStyleFactory styleFactory = new HSSFCellStyleFactory(
				workbook);
		initializeStyles(styleFactory);
		//

		final CrosstabbedWorksheetHelper crosstabSheet = new CrosstabbedWorksheetHelper(
				sheet, styleFactory);
		final Collection<ColumnMetadata> columnMeta = datasetCrosstabbed
				.getColumnMetadata();
		datasetCrosstabbed.getCellColumnNames();

		final int startingRow = 0;
		final int startingColumn = 0;
		crosstabSheet.setSingleCellCrosstabColumnHeading(false); // the only
																	// difference
		crosstabSheet.emitCrosstabRegion(ds, startingRow, startingColumn,
				rowIdentifyingColumns,
				datasetCrosstabbed.getRenderCellColumnNames(),
				// cellColumns,
				columnMeta);
		final File actualFile = File.createTempFile("Crosstab", ".xls");
		logger.debug("created temporary file " + actualFile.getAbsolutePath());
		final OutputStream os = new FileOutputStream(actualFile);

		workbook.write(os);
		os.close();
		final FileInputStream actual = new FileInputStream(actualFile);
		final FileInputStream expected = new FileInputStream(
				"src/test/data/org/javautil/poi/testCrosstabbedFormulas1.xls");
		final InputStreamComparator isc = new InputStreamComparator();
		final int result = isc.compare(actual, expected);
		actual.close();
		expected.close();
		// actualFile.delete();
		assertEquals(0, result);

	}

	// TODO need a test that has multiple cell values for a crosstab
}
