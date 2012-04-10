package org.javautil.dataset.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;

import javax.xml.transform.stream.StreamResult;

import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.dataset.MutableDatasetMetadata;

public abstract class AbstractTableRendererTest {

	private static StringWriter buffer = null;

	private static File tempFile = null;

	private static StreamResult streamResult = null;

	@SuppressWarnings("unchecked")
	static Dataset getSampleDataset() {
		final MutableDatasetMetadata metadata = new DatasetMetadataImpl();
		int index = 0;

		metadata.addColumn(new ColumnMetadata("yr", index++, DataType.INTEGER,
				8, 0, null));
		metadata.addColumn(new ColumnMetadata("per", index++, DataType.INTEGER,
				1, 0, null));
		metadata.addColumn(new ColumnMetadata("mth", index++, DataType.INTEGER,
				2, 0, null));

		final ColumnMetadata dollarsColumn = new ColumnMetadata("dollars",
				index++, DataType.DOUBLE, 14, 2, null);
		dollarsColumn.setJavaFormat("#,###,###.00");
		metadata.addColumn(dollarsColumn);

		final ColumnMetadata dateColumn = new ColumnMetadata("date", index++,
				DataType.DATE, null, null, null);
		dateColumn.setJavaFormat("MM/dd/yyyy");
		metadata.addColumn(dateColumn);

		final MatrixDataset dataset = new MatrixDataset(metadata);
		dataset.addRow(Arrays.asList(new Object[] { 2008, 1, 1, 100,
				new Date(1900000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 1, 2, 200,
				new Date(999999999) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 1, 3, 300,
				new Date(200000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 2, 4, 800,
				new Date(600000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 2, 5, 1000,
				new Date(950000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 2, 6, 1200,
				new Date(750000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 3, 7, 2100,
				new Date(0) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 3, 8, 2400,
				new Date(1000000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 3, 9, 2700,
				new Date(800000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 4, 10, 4000,
				new Date(130000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 4, 11, 4400,
				new Date(500000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2008, 4, 12, 4800,
				new Date(1250000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 1, 1, 100,
				new Date(1900000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 1, 2, 200,
				new Date(999999999) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 1, 3, 300,
				new Date(200000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 2, 4, 800,
				new Date(600000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 2, 5, 1000,
				new Date(950000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 2, 6, 1200,
				new Date(750000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 3, 7, 2100,
				new Date(0) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 3, 8, 2400,
				new Date(1000000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 3, 9, 2700,
				new Date(800000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 4, 10, 4000,
				new Date(130000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 4, 11, 4400,
				new Date(500000000) }));
		dataset.addRow(Arrays.asList(new Object[] { 2009, 4, 12, 4800,
				new Date(1250000000) }));
		return dataset;
	}

	static void useBufferStreamResult() throws Exception {
		buffer = new StringWriter();
		streamResult = new StreamResult(buffer);
	}

	static void useTempFileStreamResult() throws Exception {
		final boolean useGeneratedFileName = false;
		if (useGeneratedFileName) {
			tempFile = File.createTempFile("html_dataset", ".html");
			tempFile.deleteOnExit();
		} else {
			tempFile = new File("/tmp/html_dataset.html");
			tempFile.createNewFile();
		}
		streamResult = new StreamResult(new FileOutputStream(tempFile));
	}

	public static StringWriter getBuffer() {
		return buffer;
	}

	public static void setBuffer(final StringWriter buffer) {
		AbstractTableRendererTest.buffer = buffer;
	}

	public static File getTempFile() {
		return tempFile;
	}

	public static void setTempFile(final File tempFile) {
		AbstractTableRendererTest.tempFile = tempFile;
	}

	public static StreamResult getStreamResult() {
		return streamResult;
	}

	public static void setStreamResult(final StreamResult streamResult) {
		AbstractTableRendererTest.streamResult = streamResult;
	}
}
