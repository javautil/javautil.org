package org.javautil.dataset.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.javautil.dataset.DatasetMetadata;
import org.junit.Test;

public class DatasetCsvUnmarshallerTest {
	@Test
	public void test() throws IOException {
		final DatasetMetadataUnmarshallerCsv unmarshaller = new DatasetMetadataUnmarshallerCsv();
		final File f = new File(
				"src/test/resources/org/javautil/dataset/ExtendedSales.meta.csv");
		final InputStream is = new FileInputStream(f);
		final DatasetMetadata meta = unmarshaller.getMetadata(is);
		assertNotNull(meta);
		assertEquals(9, meta.getColumnCount());

		// TODO more write tests
	}

}
