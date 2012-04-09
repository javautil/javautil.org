package org.javautil.dataset;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.javautil.collections.ListHelper;
import org.javautil.dataset.filter.MutableDatasetFilter;
import org.javautil.dataset.math.Summation;
import org.javautil.document.renderer.CsvRenderer;
import org.javautil.document.renderer.CsvRendererRequest;
import org.javautil.document.renderer.CsvRendererRequestImpl;
import org.junit.Ignore;
import org.junit.Test;

public class MatrixModificationTest {

	private final Logger logger = Logger.getLogger(getClass());
	private final ResultValidator resultValidator = new ResultValidator();

	/**
	 * getDataSet() method moved to this class and created within a method so
	 * that subsequent calls modify their own, unique, dataset
	 * 
	 * @return
	 */
	public static MutableDataset<?> getDataset() {

		final DatasetMetadataImpl meta = new DatasetMetadataImpl() {
			{
				addColumn(new ColumnMetadata("STATE", 0, DataType.STRING, null,
						null, null, null, null, null));
				addColumn(new ColumnMetadata("CITY", 1, DataType.STRING, null,
						null, null, null, null, null));
				addColumn(new ColumnMetadata("MONTH", 2, DataType.INTEGER,
						null, null, null, null, null, null));
				addColumn(new ColumnMetadata("TICKETS", 3, DataType.DOUBLE,
						null, null, null, null, null, null));
			}
		};

		final MatrixDataset matrixDataset = new MatrixDataset(meta) {
			{
				addRow(ListHelper.toList("TX", "DALLAS", new Integer(1),
						new Double(42)));
				addRow(ListHelper.toList("TX", "DALLAS", new Integer(2),
						new Double(32.2)));
				addRow(ListHelper.toList("TX", "DALLAS", new Integer(3),
						new Double(34.2)));
				addRow(ListHelper.toList("TX", "HOUSTON", new Integer(1),
						new Double(28)));
				addRow(ListHelper.toList("TX", "HOUSTON", null, null));
				addRow(ListHelper.toList("TX", "HOUSTON", new Integer(3),
						new Double(19)));
			}
		};

		return matrixDataset;
	}

	/**
	 * Verifies that a row can be added properly.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testAppendRow() throws IOException {
		logger.debug("testAppendRow");
		final MutableDataset<?> set = getDataset();

		set.appendRow(ListHelper.toList("TX", "DALLAS", new Integer(4),
				new Double(28)));

		final String csv = getRenderedCsv(set);
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		resultValidator.validateResult(ste, csv.getBytes());
	}

	// /**
	// * Test the addition of a single column using the Variance MathOperation
	// *
	// * @throws IOException
	// */
	// @Test
	// public void testColumnAddition() throws IOException {
	// logger.info("testColumnAddition");
	// MutableDataset set = getDataset();
	//
	// set.insertColumn("VARIANCE", 3, 2, 4, new Variance());
	//
	// String csv = getRenderedCsv(set);
	// ResultValidator.validateResult(this, csv.getBytes());
	// }

	/**
	 * Test the addition of two footers using the MathSummer operation.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testFooterAddition() throws IOException {
		logger.debug("testFooterAddition");
		final MutableDataset<?> set = getDataset();

		set.addFooter(3, new Summation());
		set.addFooter(2, new Summation());
		set.appendFooter();

		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		final String csv = getRenderedCsv(set);
		resultValidator.validateResult(ste, csv.getBytes());
	}

	// /**
	// * Combines testColumnAddition and testFooterAddition into a single test
	// to
	// * verify that the column addition properly applies the math logic to the
	// * footers as well.
	// *
	// * @throws IOException
	// */
	// @Test
	// public void testFooterAndColumnAddition() throws IOException {
	// logger.info("testFooterAndColumnAddition");
	// MutableDataset set = getDataset();
	//
	// set.addFooter(3, new Summation());
	// set.addFooter(2, new Summation());
	//
	// set.insertColumn("VARIANCE", 3, 2, 4, new Variance());
	//
	// String csv = getRenderedCsv(set);
	// ResultValidator.validateResult(this, csv.getBytes());
	// }

	/**
	 * The implementation of MatrixDataset is such that all rows must be added
	 * before any footers are added. This test ensures that an error is thrown
	 * if an attempt to add a row after a footer has been added is made.
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalStateException.class)
	public void testAppendRowFailureAfterFooterAddition() throws IOException {
		logger.debug("testAppendRowFailureAfterFooterAddition");
		final MutableDataset<?> set = getDataset();

		set.addFooter(3, new Summation());

		set.appendRow(ListHelper.toList("TX", "DALLAS", new Integer(4),
				new Double(28)));

		final String csv = getRenderedCsv(set);
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		resultValidator.validateResult(ste, csv.getBytes());
	}

	@Ignore
	// TODO fix this so that it works again
	@Test(expected = IllegalArgumentException.class)
	public void testBadTypeInAdditionalRow() {
		logger.debug("testBadTypeInAdditionalRow");
		final MutableDataset<?> set = getDataset();

		set.appendRow(ListHelper.toList("TX", "DALLAS", new Double(4),
				new Double(28)));
	}

	/**
	 * Tests that ascending sort works properly.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testSortAscending() throws IOException {
		logger.trace("testSortAscending");
		final MutableDataset<?> set = getDataset();

		final SortColumn sortTickets = new SortColumn();
		sortTickets.setAscending(true);
		sortTickets.setSortColumn("TICKETS");

		set.applySorts(sortTickets);

		final String csv = getRenderedCsv(set);
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		resultValidator.validateResult(ste, csv.getBytes());
	}

	/**
	 * Tests that descending sort works properly.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testSortDescending() throws IOException {
		logger.debug("testSortDescending");
		final MutableDataset<?> set = getDataset();

		final SortColumn sortTickets = new SortColumn();
		sortTickets.setAscending(false);
		sortTickets.setSortColumn("TICKETS");

		set.applySorts(sortTickets);

		final String csv = getRenderedCsv(set);
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		resultValidator.validateResult(ste, csv.getBytes());
	}

	@Test
	public void testFilter() throws IOException {
		logger.debug("testFilter");
		final MutableDataset<?> set = getDataset();

		final MutableDatasetFilter cityFilter = new DefaultDatasetFilter();
		cityFilter.setColumnName("CITY");
		cityFilter.setColumnValue("DALLAS");

		set.applyFilters(cityFilter);

		final String csv = getRenderedCsv(set);
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		resultValidator.validateResult(ste, csv.getBytes());
	}

	// TODO fix this test
	@Ignore
	@Test
	public void testInverseFilter() throws IOException {
		logger.debug("testFilter");
		final MutableDataset<?> set = getDataset();

		final MutableDatasetFilter cityFilter = new DefaultDatasetFilter();
		cityFilter.setColumnName("CITY");
		cityFilter.setColumnValue("DALLAS");
		cityFilter.setInverseMatch(true);

		set.applyFilters(cityFilter);

		final String csv = getRenderedCsv(set);
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		resultValidator.validateResult(ste, csv.getBytes());
	}

	/**
	 * Renders the dataset into csv format.
	 * 
	 * @param set
	 * @return
	 * @throws IOException
	 */
	private String getRenderedCsv(final Dataset<?> set) throws IOException {
		final CsvRendererRequest crr = new CsvRendererRequestImpl();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		crr.setStreamResult(new StreamResult(baos));
		crr.setDataset(set);

		final CsvRenderer cr = new CsvRenderer();
		cr.setRequest(crr);
		cr.process();
		final String text = new String(baos.toByteArray());
		logger.trace("\n" + text);
		return text;
	}
}
