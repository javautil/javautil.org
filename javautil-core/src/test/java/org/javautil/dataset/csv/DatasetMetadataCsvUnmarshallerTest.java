package org.javautil.dataset.csv;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.ColumnMetadataTest;
import org.javautil.dataset.DatasetMetadata;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.dataset.MutableDatasetMetadata;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DatasetMetadataCsvUnmarshallerTest {

	private static DatasetMetadata meta;

	private final ColumnMetadataTest columns = new ColumnMetadataTest();

	private final Logger logger = Logger.getLogger(getClass());

	// public ColumnMetadata(final String columnName, final Integer columnIndex,
	// final DataType dataType, final Integer precision,
	// final Integer scale, final Integer columnDisplaySize,
	// final HorizontalAlignment horizontalAlignment,
	// final String excelFormat, final String javaFormat)
	@BeforeClass
	public static void beforeClass() {
		// TODO move this to ColumnMetadataTest and reuse
		meta = new DatasetMetadataImpl();

	}

	@Test
	public void testMarshallOrthogonality() throws IOException {
		DatasetMetadataImpl meta = new DatasetMetadataImpl();
		ColumnMetadata column = ColumnMetadataTest.dollarsColumn;
		assertNotNull(column);
		meta.addColumn(column);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DatasetMetadataMarshallerCsv.write(baos, meta);
		byte[] bytes = baos.toByteArray();
		String metaString = new String(bytes);
		logger.debug("metadata is \n" + metaString);
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

		DatasetMetadataUnmarshallerCsv unmarshaller = new DatasetMetadataUnmarshallerCsv();
		MutableDatasetMetadata metaIn = unmarshaller.getMetadata(bais);
		ColumnMetadata dollarsIn = metaIn.getColumnMetaData(0);
		ColumnMetadataTest.testDollarsColumn(dollarsIn);
	}

	@Ignore
	@Test
	public void test() throws IOException {
		final DatasetCsvUnmarshaller unmarshaller = new DatasetCsvUnmarshaller();
		final File metaFile = new File(
				"src/test/resources/org.javautil.dataset.MatrixDatasetTest.test2.expected.meta.csv");
		final InputStream metaStream = new FileInputStream(metaFile);

		final File dataFile = new File(
				"src/test/resources/org.javautil.dataset.MatrixDatasetTest.test2.expected.data.csv");
		final InputStream dataStream = new FileInputStream(dataFile);

		DatasetMetadataUnmarshallerCsv metaUnmarshaller = new DatasetMetadataUnmarshallerCsv();
		MutableDatasetMetadata metadata = metaUnmarshaller
				.getMetadata(metaStream);
		logger.debug("metadata is\n" + metadata);

		final MatrixDataset dataset = unmarshaller.getDataset(metaStream,
				dataStream);
		assertNotNull(dataset);
		// TODO write more
		// assertEquals(9, meta.getColumnCount());

		// TODO more write tests
	}

}
