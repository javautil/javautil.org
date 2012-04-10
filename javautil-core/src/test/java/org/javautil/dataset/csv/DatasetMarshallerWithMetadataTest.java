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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.ColumnMetadataCsvMarshaller;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMetadata;
import org.javautil.dataset.testdata.TicketsDataset;
import org.javautil.file.FileHelper;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DatasetMarshallerWithMetadataTest {
	private static final String expectedMeta = "src/test/resources/org/javautil/dataset/tickets.meta.csv";
	private final Logger logger = Logger.getLogger(getClass());

	@BeforeClass
	public static void beforeClass() {
		BasicConfigurator.configure();
	}

	/**
	 * 
	 * Checks to make sure that marshalling is as expected.
	 * 
	 * @throws IOException
	 */
	@Ignore
	@Test
	public void testMarshalling() throws IOException {
		final TicketsDataset td = new TicketsDataset();
		final Dataset ds = td.getDataset();
		final File metaFile = File.createTempFile("Tickets", ".meta.csv");
		final File dataFile = File.createTempFile("Tickets", ".data.csv");
		final FileOutputStream data = new FileOutputStream(dataFile);
		final FileOutputStream meta = new FileOutputStream(metaFile);
		DatasetMetadataMarshallerCsv.write(ds, data, meta);
		data.close();
		meta.close();
		assertTrue(FileHelper.fileContentsMatch(metaFile,
				new File(expectedMeta)));
		assertTrue(FileHelper.fileContentsMatch(dataFile, new File(
				"src/test/data/org/javautil/dataset/csv/tickets.data.csv")));
	}

	/**
	 * TODO Checks to make sure that marshalling is as expected.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testMetadataMarshalling() throws IOException {
		final TicketsDataset td = new TicketsDataset();
		final Dataset ds = td.getDataset();

		final File metaFile = File.createTempFile("Tickets", ".meta.csv");
		final File expectedFile = new File(expectedMeta);
		final FileOutputStream meta = new FileOutputStream(metaFile);
		final DatasetMetadata metadata = ds.getMetadata();
		ColumnMetadataCsvMarshaller.writeToFile(metaFile,
				metadata.getColumnMetadata());
		meta.close();
		final boolean match = FileHelper.fileContentsMatch(metaFile,
				expectedFile);
		final String message = "expected in " + expectedFile.getPath()
				+ " does not match " + metaFile.getPath();
		assertTrue(message, match);
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
