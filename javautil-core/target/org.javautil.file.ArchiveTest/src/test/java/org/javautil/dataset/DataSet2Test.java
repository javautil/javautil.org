package org.javautil.dataset;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.javautil.collections.ListHelper;
import org.javautil.dataset.testdata.ConvictionsDataset;
import org.javautil.dataset.testdata.FeesDataset;
import org.javautil.dataset.testdata.TicketsDataset;
import org.javautil.dataset.testdata.TrailingNullsDataset;
import org.javautil.document.crosstab.CrosstabColumns;
import org.javautil.document.crosstab.CrosstabColumnsImpl;
import org.javautil.document.renderer.CsvRenderer;
import org.javautil.document.renderer.CsvRendererRequest;
import org.javautil.document.renderer.CsvRendererRequestImpl;
import org.javautil.lang.ThreadHelper;
import org.junit.Ignore;
import org.junit.Test;

// TODO clean up
public class DataSet2Test extends BaseTest {
	public Logger logger = Logger.getLogger(getClass());
	private final ResultValidator resultValidator = new ResultValidator();

	void writeFile(final String fileName, final byte[] data) throws IOException {
		final File expected = new File(fileName);
		final FileOutputStream fos = new FileOutputStream(expected);
		fos.write(data);
		fos.close();
	}

	void display(final byte[] data) {
		// System.out.println(data);
	}

	@Test
	public void getMethodName() {
		final String methodName = ThreadHelper.getMethodName();
		assertEquals(methodName, "getMethodName");
	}

	// TODO fix this test
	@Ignore
	@Test
	public void testA() throws IOException {

		final String testResult = DatasetRendererHelper
				.getDatasetAsCsvString(TrailingNullsDataset.getDataset());
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		resultValidator.validateResult(ste, testResult.getBytes());
	}

	// TODO fix this test
	@Ignore
	@Test
	public void test2() throws IOException {
		final TicketsDataset td = new TicketsDataset();
		final Dataset<?> dst = td.getDataset();
		final DatasetCrosstabber CROSSTABBER = new DatasetCrosstabber();
		final List<String> rowId = ListHelper.toStringList("STATE", "CITY");
		final List<String> cellId = ListHelper.toStringList("TICKETS");

		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, "MONTH",
				cellId);
		CROSSTABBER.setCrosstabColumns(ctc);
		CROSSTABBER.setDataSet(dst);

		final AbstractDataset ds = CROSSTABBER.getDataSet();

		final byte[] testResult = DatasetRendererHelper
				.getCsvMarshalledDataset(ds, ",");
		// System.out.println(new String(testResult));
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		resultValidator.validateResult(ste, testResult);
	}

	@Test
	public void test3() throws IOException {
		final DatasetCrosstabber CROSSTABBER = new DatasetCrosstabber();
		final List<String> rowId = ListHelper.toStringList("STATE", "CITY");
		final List<String> cellId = ListHelper
				.toStringList("Fine", "Legal Fee");

		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, "MONTH",
				cellId);
		CROSSTABBER.setCrosstabColumns(ctc);
		CROSSTABBER.setDataSet(FeesDataset.getDataset());

		final AbstractDataset ds = CROSSTABBER.getDataSet();
		assertExpected(ds);

	}

	@Test
	public void test4() throws IOException {
		// get the data
		final MutableDataset fees = FeesDataset.getDataset();

		final DatasetCrosstabber crosstabber = new DatasetCrosstabber();
		// crosstab rules
		final List<String> rowId = ListHelper.toStringList("STATE", "CITY");
		final List<String> cellId = ListHelper
				.toStringList("Fine", "Legal Fee");
		final String columnId = "MONTH";

		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, columnId,
				cellId);
		crosstabber.setCrosstabColumns(ctc);

		crosstabber.setDataSet(fees);

		final AbstractDataset ds = crosstabber.getDataSet();
		assertExpected(ds);

	}

	@SuppressWarnings("serial")
	@Test
	public void test5() throws IOException {

		final Map<String, String> idKeyMap = new TreeMap<String, String>() {
			{
				put("STATE", "STATE");
				put("CITY", "CITY");
				put("MONTH", "MONTH");
			}
		};
		final DatasetAppender dsa = new DatasetAppender();
		final TicketsDataset td = new TicketsDataset();
		final MutableDataset tickets = td.getDataset();
		final Dataset fees = FeesDataset.getDataset();
		dsa.appendRight(tickets, fees, idKeyMap);
		assertExpected(tickets);

	}

	@Test(expected = DatasetOperationException.class)
	public void test6() throws IOException {

		final Map<String, String> idKeyMap = new TreeMap<String, String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("STATE", "STATE");
				put("CITY", "CITY");
				put("MONTH", "MONTH");
			}
		};
		final DatasetAppender dsa = new DatasetAppender();
		dsa.setRequireSourceRowForEachTargetRow(true);
		final MutableDataset fees = FeesDataset.getDataset();
		final TicketsDataset td = new TicketsDataset();

		final MutableDataset tickets = td.getDataset();
		dsa.appendRight(tickets, fees, idKeyMap);
		assertExpected(tickets);

	}

	/*
	 * Take a dataset, crosstab it and then right append to the crosstabbed
	 * result
	 */
	// TODO fix this test
	@Ignore
	@SuppressWarnings({ "serial", "unchecked" })
	@Test
	public void test7() throws IOException {

		final Map<String, String> idKeyMap = new TreeMap<String, String>() {
			{
				put("STATE", "STATE");
				put("CITY", "CITY");
				put("MONTH", "MONTH");
			}
		};
		final DatasetCrosstabber CROSSTABBER = new DatasetCrosstabber();
		final List<String> rowId = ListHelper.toStringList("STATE", "CITY");
		final List<String> cellId = ListHelper
				.toStringList("Fine", "Legal Fee");

		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, "MONTH",
				cellId);
		CROSSTABBER.setCrosstabColumns(ctc);
		final MutableDataset fees = FeesDataset.getDataset();
		CROSSTABBER.setDataSet(fees);

		final MutableDataset crosstabbed = (MutableDataset) CROSSTABBER
				.getDataSet();
		final DatasetAppender dsa = new DatasetAppender();
		dsa.appendRight(crosstabbed, fees, idKeyMap);
		assertExpected(crosstabbed);
	}

	// TODO fix
	@Ignore
	@SuppressWarnings("unchecked")
	@Test
	public void test8() throws IOException {

		final CsvRendererRequest crr = new CsvRendererRequestImpl();
		final MutableDataset convictions = ConvictionsDataset.getConvictions();
		crr.setDataset(convictions);
		// crr.setStreamResult(new StreamResult(System.out));
		final CsvRenderer cr = new CsvRenderer();
		cr.setRequest(crr);
		cr.process();
		// TODO validate result
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test9() throws IOException {

		final CsvRendererRequest crr = new CsvRendererRequestImpl();
		final MutableDataset convictions = ConvictionsDataset.getConvictions();
		crr.setDataset(convictions);
		crr.setStreamResult(new StreamResult(System.out));
		crr.setDateFormat("yyyy-MM-dd");
		final CsvRenderer cr = new CsvRenderer();
		cr.setRequest(crr);
		cr.process();
		// TODO need to test
	}

	@Test
	public void test10() throws IOException {

		final CsvRendererRequest crr = new CsvRendererRequestImpl();
		final MutableDataset convictions = ConvictionsDataset.getConvictions();
		crr.setDataset(convictions);
		crr.setStreamResult(new StreamResult(System.out));
		// final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		crr.setDateFormat("yyyy/MM/dd");
		final CsvRenderer cr = new CsvRenderer();
		cr.setRequest(crr);
		cr.process();
		// TODO validate result
	}

	@Test
	public void test11() {

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final Date d = new Date();
		logger.debug("it is now " + sdf.format(d));

	}
	//
	// public static void main(final String[] args) throws IOException {
	// // BasicConfigurator.configure();
	// final DataSet2Test m = new DataSet2Test();
	// m.logger.debug("beginning");
	// m.testA();
	// // m.test1();
	// // m.test3();
	// // m.test5();
	// // m.test11();
	// // m.test10();
	// }
}
