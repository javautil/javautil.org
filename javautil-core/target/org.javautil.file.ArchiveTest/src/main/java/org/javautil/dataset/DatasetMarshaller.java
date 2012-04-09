package org.javautil.dataset;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.javautil.document.renderer.CsvRenderer;
import org.javautil.document.renderer.CsvRendererRequest;
import org.javautil.document.renderer.CsvRendererRequestImpl;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class DatasetMarshaller {

	private final Logger logger = Logger.getLogger(getClass());

	public void write(final Dataset ds, final OutputStream outputStream)
			throws IOException {
		writeMetadata(ds, outputStream);
		writeData(ds, outputStream);
	}

	public void writeMetadata(final Dataset dataset, final OutputStream os)
			throws IOException {
		if (dataset == null) {
			throw new IllegalArgumentException("dataset is null");
		}
		if (os == null) {
			throw new IllegalArgumentException("os is null");
		}
		final List<ColumnMetadata> columns = dataset.getMetadata()
				.getColumnMetadata();
		final ColumnMetadataCsvMarshaller marshaller = new ColumnMetadataCsvMarshaller(
				os);
		marshaller.write(columns);
	}

	public void writeData(final Dataset dataset, final OutputStream os)
			throws IOException {
		if (dataset == null) {
			throw new IllegalArgumentException("dataset is null");
		}
		if (os == null) {
			throw new IllegalArgumentException("os is null");
		}
		final CsvRendererRequest request = new CsvRendererRequestImpl();
		request.setUseDefaultDateFormatForAllDates(true);
		request.setStreamResult(new StreamResult(os));
		request.setDataset(dataset);
		final CsvRenderer renderer = new CsvRenderer();
		renderer.process(request);
	}

	public static String asString(final Dataset dataset) {
		final DatasetMarshaller dm = new DatasetMarshaller();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			dm.write(dataset, baos);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return baos.toString();
	}

}
