package org.javautil.dataset;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;

import org.javautil.document.renderer.CsvRenderer;
import org.javautil.document.renderer.CsvRendererRequest;
import org.javautil.document.renderer.CsvRendererRequestImpl;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 *         TODO how many variants of this are there?
 * 
 */
public class DatasetRendererHelper {

	public static byte[] getCsvMarshalledDataset(final Dataset dataset,
			final String separator) throws IOException {
		final CsvRendererRequest crr = new CsvRendererRequestImpl();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		crr.setStreamResult(new StreamResult(baos));
		if (separator != null) {
			crr.setColumnSeparator(separator);
		}
		crr.setDataset(dataset);

		final CsvRenderer cr = new CsvRenderer();
		cr.setRequest(crr);
		cr.process();
		final byte[] retval = baos.toByteArray();
		return retval;
	}

	public static String getDatasetAsCsvString(final Dataset dataset)
			throws IOException {
		final byte[] bytes = getCsvMarshalledDataset(dataset, ",");
		return bytes.toString();
	}

}
