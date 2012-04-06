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

public class MatrixModificationNoSortTest extends BaseTest {

	private final Logger logger = Logger.getLogger(getClass());
	private final ResultValidator resultValidator = new ResultValidator();

	private final DatasetSource datasetSource = new DatasetSource();

	/**
	 * Verifies that a row can be added properly.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	// TODO fix this test
	@Test
	public void testAppendRow() throws IOException {
		logger.debug("testAppendRow");
		final MutableDataset set = DatasetSource.getDataset();

		set.appendRow(ListHelper.toList("TX", "DALLAS", new Integer(4),
				new Double(28)));
		assertExpected(set);
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

	// footer addition doesn't do anything.
	// /**
	// * Test the addition of two footers using the MathSummer operation.
	// *
	// * @throws IOException
	// */
	// // TODO fix this test
	//
	// @Test
	// public void testFooterAddition() throws IOException {
	// logger.info("testFooterAddition");
	// MutableDataset<?> set = getDataset();
	//
	// set.addFooter(3, new Summation());
	// set.addFooter(2, new Summation());
	//
	// String csv = getRenderedCsv(set);
	// logger.info("csv" + SystemProperties.newline + csv);
	// StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
	// resultValidator.validateResult(ste, csv.getBytes());
	// }

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
	@SuppressWarnings("unchecked")
	@Test(expected = IllegalStateException.class)
	public void testAppendRowFailureAfterFooterAddition() throws IOException {
		logger.debug("testAppendRowFailureAfterFooterAddition");
		final MutableDataset set = DatasetSource.getDataset();

		set.addFooter(3, new Summation());

		set.appendRow(ListHelper.toList("TX", "DALLAS", new Integer(4),
				new Double(28)));
		assertExpected(set);

	}

	@Ignore
	// TODO review
	@SuppressWarnings("unchecked")
	@Test(expected = IllegalArgumentException.class)
	public void testBadTypeInAdditionalRow() {
		logger.debug("testBadTypeInAdditionalRow");
		final MutableDataset set = DatasetSource.getDataset();

		set.appendRow(ListHelper.toList("TX", "DALLAS", new Double(4),
				new Double(28)));
	}

	@Ignore
	@Test
	public void testFilter() throws IOException {
		logger.debug("testFilter");
		final MutableDataset<?> set = DatasetSource.getDataset();

		final MutableDatasetFilter cityFilter = new DefaultDatasetFilter();
		cityFilter.setColumnName("CITY");
		cityFilter.setColumnValue("DALLAS");

		set.applyFilters(cityFilter);
		assertExpected(set);
		final String csv = getRenderedCsv(set);
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		resultValidator.validateResult(ste, csv.getBytes());
	}

	// TODO fix this test
	@Ignore
	@Test
	public void testInverseFilter() throws IOException {
		logger.debug("testFilter");
		final MutableDataset<?> set = DatasetSource.getDataset();

		final MutableDatasetFilter cityFilter = new DefaultDatasetFilter();
		cityFilter.setColumnName("CITY");
		cityFilter.setColumnValue("DALLAS");
		cityFilter.setInverseMatch(true);

		set.applyFilters(cityFilter);
		assertExpected(set);

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
		if (logger.isDebugEnabled()) {
		logger.debug("\n" + text);
		}
		return text;
	}
}
