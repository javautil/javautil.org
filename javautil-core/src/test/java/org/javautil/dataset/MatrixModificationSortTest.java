package org.javautil.dataset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.javautil.collections.ListHelper;
import org.javautil.dataset.math.Summation;
import org.javautil.document.renderer.CsvRenderer;
import org.javautil.document.renderer.CsvRendererRequest;
import org.javautil.document.renderer.CsvRendererRequestImpl;
import org.javautil.file.InputStreamComparator;
import org.javautil.lang.SystemProperties;
import org.junit.Test;

public class MatrixModificationSortTest {

	private final Logger logger = Logger.getLogger(getClass());
	private final ResultValidator resultValidator = new ResultValidator();
	private final DatasetSource datasetSource = new DatasetSource();

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
		final MutableDataset<?> set = DatasetSource.getDataset();

		set.addFooter(3, new Summation());

		set.appendRow(ListHelper.toList("TX", "DALLAS", new Integer(4),
				new Double(28)));

		final String csv = getRenderedCsv(set);
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		resultValidator.validateResult(ste, csv.getBytes());
	}

	/**
	 * Tests that ascending sort works properly.
	 * 
	 * @throws IOException
	 */
	// TODO fix this test

	@Test
	public void testSortAscending() throws IOException {
		logger.debug("testSortAscending");
		final MutableDataset<?> set = DatasetSource.getDataset();

		final SortColumn sortTickets = new SortColumn();
		sortTickets.setAscending(true);
		sortTickets.setSortColumn("TICKETS");

		set.applySorts(sortTickets);

		final String csv = getRenderedCsv(set);
		final InputStream is = new FileInputStream(
				"src/test/resources/org/javautil/dataset/MatrixModificationTest/testSortAscending.csv");
		final InputStreamComparator isc = new InputStreamComparator();
		final InputStream actualInput = new ByteArrayInputStream(csv.getBytes());
		isc.compare(actualInput, is);
	}

	/**
	 * Tests that descending sort works properly.
	 * 
	 * TODO how is this descending?
	 * 
	 * @throws IOException
	 */
	// TODO fix this test

	@Test
	public void testSortDescending() throws IOException {
		logger.debug("testSortDescending");
		final MutableDataset<?> set = DatasetSource.getDataset();

		final SortColumn sortTickets = new SortColumn();
		sortTickets.setAscending(false);
		sortTickets.setSortColumn("TICKETS");

		set.applySorts(sortTickets);

		final String csv = getRenderedCsv(set);

		final InputStream actualInput = new ByteArrayInputStream(csv.getBytes());
		final InputStreamComparator isc = new InputStreamComparator();
		final InputStream is = new FileInputStream(
				"src/test/resources/org/javautil/dataset/MatrixModificationTest/testSortAscending.csv");
		final int result = isc.compare(actualInput, is);
		assertTrue(0 != result);

	}

	/**
	 * Tests that ascending sort works properly.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testDuplicateAscending() throws IOException {
		logger.debug("testDuplicateAscending");
		final MutableDataset<?> set = DatasetSource.getDupeFinesDataset();

		final SortColumn sortTickets = new SortColumn();
		sortTickets.setAscending(true);
		sortTickets.setSortColumn("FINES");

		set.applySorts(sortTickets);

		final String csv = getRenderedCsv(set);

		logger.debug("actual " + csv.length() + SystemProperties.newline + csv
				+ SystemProperties.newline);
		final InputStream expectedInputStream = new FileInputStream(
				"src/test/resources/org/javautil/dataset/MatrixModificationTest/testDuplicateFinesAscending.csv");

		final InputStreamComparator isc = new InputStreamComparator();
		final InputStream actualInput = new ByteArrayInputStream(csv.getBytes());
		final int result = isc.compare(expectedInputStream, actualInput);
		assertEquals(0, result);
	}

	/**
	 * Tests that ascending sort works properly.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testDuplicateDescending() throws IOException {
		logger.debug("testDuplicateAscending");
		final MutableDataset<?> set = DatasetSource.getDupeFinesDataset();

		final SortColumn sortTickets = new SortColumn();
		sortTickets.setAscending(false);
		sortTickets.setSortColumn("FINES");

		set.applySorts(sortTickets);

		final String csv = getRenderedCsv(set);
		logger.debug(csv);
		final InputStream is = new FileInputStream(
				"src/test/resources/org/javautil/dataset/MatrixModificationTest/testDuplicateFinesDescending.csv");
		final InputStreamComparator isc = new InputStreamComparator();
		final InputStream actualInput = new ByteArrayInputStream(csv.getBytes());
		final int result = isc.compare(actualInput, is);
		assertEquals(0, result);
	}

	/**
	 * Tests that ascending sort works properly.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testTwoColumnSort() throws IOException {
		logger.debug("testTwoColumnSort");
		final MutableDataset<?> set = DatasetSource.getDupeFinesDataset();

		final SortColumn fines = new SortColumn();
		fines.setAscending(false);
		fines.setSortColumn("FINES");

		final SortColumn monthSort = new SortColumn("MONTH", true);

		set.applySorts(monthSort, fines);

		final String csv = getRenderedCsv(set);
		logger.debug(csv);
		final InputStream is = new FileInputStream(
				"src/test/resources/org/javautil/dataset/MatrixModificationTest/twoColumnSort.csv");
		final InputStreamComparator isc = new InputStreamComparator();
		final InputStream actualInput = new ByteArrayInputStream(csv.getBytes());
		final int result = isc.compare(actualInput, is);
		assertEquals(0, result);
	}

	/**
	 * Renders the dataset into csv format. TODO move this somewhere.
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
		logger.debug("\n" + text);
		return text;
	}
}
