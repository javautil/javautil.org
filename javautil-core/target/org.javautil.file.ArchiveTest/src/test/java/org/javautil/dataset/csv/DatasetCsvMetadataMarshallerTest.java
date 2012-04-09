package org.javautil.dataset.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.ColumnMetadataCsvMarshaller;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMarshaller;
import org.javautil.dataset.ExtendedSalesDataset;
import org.javautil.dataset.testdata.TicketsDataset;
import org.javautil.file.FileHelper;
import org.junit.Ignore;
import org.junit.Test;

public class DatasetCsvMetadataMarshallerTest {
	private static final String expectedMeta = "./src/test/resources/org/javautil/dataset/ExtendedSales.meta.csv";
	// private static final String expectedMeta =
	// "./target/org.javautil.file.ArchiveTest/src/test/resources/org/javautil/dataset/ExtendedSales.meta.csv";

	private final Logger logger = Logger.getLogger(getClass());

	// @BeforeClass
	// public static void beforeClass() {
	// BasicConfigurator.configure();
	// }

	/**
	 * 
	 * Checks to make sure that marshalling is as expected.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testMarshalling() throws IOException {
		final ExtendedSalesDataset salesData = new ExtendedSalesDataset();
		final DatasetMarshaller marshaller = new DatasetMarshaller();
		final Dataset ds = salesData.getDataset();
		ds.getMetadata();

		final File metaFile = File.createTempFile("ExtendedSales", ".meta.csv");
		logger.debug("metaFile " + metaFile);
		final File dataFile = File.createTempFile("ExtendedSales", ".data.csv");
		final FileOutputStream data = new FileOutputStream(dataFile);
		final FileOutputStream metaOS = new FileOutputStream(metaFile);
		marshaller.writeMetadata(ds, metaOS);
		data.close();
		metaOS.close();
		boolean contentsMatch = FileHelper.fileContentsMatch(metaFile,
				new File(expectedMeta));
		if (!contentsMatch) {
			logger.error("File: " + metaFile.getAbsolutePath() + " "
					+ expectedMeta);
		}
		assertTrue(contentsMatch);
		// assertTrue(FileHelper.fileContentsMatch(dataFile, new
		// File("./target/org.javautil.file.ArchiveTest/src/test/resources/org/javautil/dataset/tickets.meta.csv")));
	}

	/**
	 * 
	 * Checks to make sure that marshalling is as expected.
	 * 
	 * @throws IOException
	 */
	@Ignore
	@Test
	public void testMetadataMarshalling() throws IOException {
		final TicketsDataset td = new TicketsDataset();
		final Dataset<?> ds = td.getDataset();

		final File metaFile = File.createTempFile("Tickets", ".meta.csv");
		final FileOutputStream meta = new FileOutputStream(metaFile);
		new ColumnMetadataCsvMarshaller(meta);
		ColumnMetadataCsvMarshaller.writeToFile(metaFile, ds.getMetadata()
				.getColumnMetadata());
		meta.close();
		assertTrue(FileHelper.fileContentsMatch(metaFile,
				new File(expectedMeta)));

	}

	@Ignore
	@Test
	public void testUnMarshall() throws IOException {
		final TicketsDataset td = new TicketsDataset();
		td.getDataset();

		final InputStream in = new FileInputStream(expectedMeta);
		final ColumnMetadataCsvMarshaller unmarshaller = new ColumnMetadataCsvMarshaller(
				in);
		final List<ColumnMetadata> meta = unmarshaller.readAll();
		assertEquals(4, meta.size());
		final ColumnMetadata column = meta.get(0);
		assertEquals("STATE", column.getColumnName());
		assertEquals(Integer.valueOf(0), column.getColumnIndex());
		assertEquals("STRING", column.getDataType());
		assertFalse(column.isExternalFlag());

		in.close();
		// now we need some tests
	}
}
