package org.javautil.dataset.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.stream.StreamResult;

import org.javautil.dataset.Dataset;
import org.javautil.document.renderer.CsvRenderer;
import org.javautil.document.renderer.CsvRendererRequest;
import org.javautil.document.renderer.CsvRendererRequestImpl;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class DatasetCsvMarshaller {

	public static void write(final Dataset ds, final OutputStream os)
			throws IOException {
		if (ds == null) {
			throw new IllegalArgumentException("ds is null");
		}
		if (os == null) {
			throw new IllegalArgumentException("os is null");
		}
		final CsvRendererRequest crr = new CsvRendererRequestImpl();
		final StreamResult sr = new StreamResult(os);

		crr.setStreamResult(sr);
		crr.setColumnSeparator(",");
		crr.setDataset(ds);
		crr.setDateFormat("YYYY-MM-DD");

		final CsvRenderer cr = new CsvRenderer();
		cr.setRequest(crr);
		cr.process();

	}

	public static void writeToFile(final Dataset ds, final File file)
			throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("file is null");
		}
		final FileOutputStream fos = new FileOutputStream(file);
		write(ds, fos);
	}

	public static void writeToFileName(final Dataset ds, final String fileName)
			throws IOException {
		final FileOutputStream fos = new FileOutputStream(fileName);
		write(ds, fos);
	}

}
