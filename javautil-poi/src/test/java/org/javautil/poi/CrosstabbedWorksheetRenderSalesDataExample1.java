package org.javautil.poi;

import java.awt.Color;
import java.io.File;
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
import org.javautil.document.style.ColorUtil;
import org.javautil.document.style.Style;
import org.javautil.document.style.StyleImpl;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.junit.Test;

public class CrosstabbedWorksheetRenderSalesDataExample1 {

	private final Logger logger = Logger.getLogger(getClass());

	// TODO put this in a base class or a helper class
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

	File getOutputFile(final String filename) {
		final String directory = "target/generated-data";
		final File f = new File(directory);
		f.mkdirs();
		final String outputFileName = directory + "/" + filename;
		return new File(outputFileName);

	}

	// TODO this is cut and paste from test1 strip out common code
	@Test
	public void test2() throws IOException {
		// get a crosstabbed dataset
		final CrosstabDataset datasetCrosstabbed = new CrosstabDataset();
		final Dataset ds = datasetCrosstabbed.getCrosstabbedDataset();

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

		final List<String> cellColumns = datasetCrosstabbed
				.getCellColumnNames();

		final int startingRow = 0;
		final int startingColumn = 0;
		crosstabSheet.setSingleCellCrosstabColumnHeading(false); // the only
																	// difference
		crosstabSheet.emitCrosstabRegion(ds, startingRow, startingColumn,
				rowIdentifyingColumns, cellColumns, columnMeta);

		final File actualFile = getOutputFile("Crosstab.xls");
		logger.debug("created temporary file " + actualFile.getAbsolutePath());
		final OutputStream os = new FileOutputStream(actualFile);

		workbook.write(os);
		os.close();

	}

}
