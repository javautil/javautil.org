package org.javautil.dataset;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class DatasetMarshallerTest {

	// TODO write to file and check against expected
	@Test
	public void test1() throws IOException {
		final ExtendedSalesDataset esd = new ExtendedSalesDataset();
		final Dataset dataset = esd.getDataset();
		final DatasetMarshaller marshaller = new DatasetMarshaller();

		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		marshaller.write(dataset, os);

	}
}
