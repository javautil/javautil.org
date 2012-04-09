package org.javautil.dataset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.text.AsString;
import org.javautil.text.CSVTokenizer;
import org.javautil.text.CSVWriter;

public class ColumnMetadataCsvMarshaller {

	private BufferedReader reader;

	private CSVTokenizer tokenizer;

	private CSVWriter csvWriter;

	private Writer writer;

	private final Logger logger = Logger.getLogger(getClass());

	private static final String newline = System.getProperty("line.separator");

	private final AsString asString = new AsString();

	public ColumnMetadataCsvMarshaller(final Reader reader) {
		this.reader = new BufferedReader(reader);
		tokenizer = new CSVTokenizer();

	}

	public ColumnMetadataCsvMarshaller(final OutputStream os) {
		if (os == null) {
			throw new IllegalArgumentException("output stream is null");
		}
		this.writer = new OutputStreamWriter(os);
		csvWriter = new CSVWriter(writer);
	}

	public ColumnMetadataCsvMarshaller(final Writer writer) {
		if (writer == null) {
			throw new IllegalArgumentException("writer is null");
		}
		this.writer = writer;
		csvWriter = new CSVWriter(writer);
	}

	public ColumnMetadataCsvMarshaller(final InputStream is) {
		// this.is = is;
		this.reader = new BufferedReader(new InputStreamReader(is));
		tokenizer = new CSVTokenizer();

	}

	public static void writeToFile(final File file,
			final Collection<ColumnMetadata> meta) throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("file is null");
		}
		final OutputStream os = new FileOutputStream(file);
		final ColumnMetadataCsvMarshaller marshaller = new ColumnMetadataCsvMarshaller(
				os);
		marshaller.write(meta);
		os.close();
	}

	public ColumnMetadata read() throws IOException {
		if (reader == null) {
			throw new IllegalStateException(
					"was not constructed with ColumnMetadataCsvMarshaller(InputStream is) or ColumnMetadataCsvMarshaller(Reader reader) and attempt to read was made");
		}
		final String line = reader.readLine();
		ColumnMetadata retval = null;
		if (line != null) {
			final List<String> tokens = tokenizer.parse(line);

			for (int padLength = 16 - tokens.size(); padLength > 0; padLength--) {
				tokens.add(null);
			}
			retval = parse(tokens);
		}
		return retval;
	}

	public List<ColumnMetadata> readAll() throws IOException {
		final ArrayList<ColumnMetadata> columns = new ArrayList<ColumnMetadata>();
		ColumnMetadata column;
		while ((column = read()) != null) {
			columns.add(column);
		}
		return columns;
	}

	public void write(final Collection<ColumnMetadata> metas)
			throws IOException {
		for (final ColumnMetadata meta : metas) {
			write(meta);
		}
	}

	public void write(final ColumnMetadata meta) throws IOException {
		List<String> fields = meta.toStringList();
		if (logger.isDebugEnabled()) {
			logger.debug(fields);
		}

		final Object[] array = fields.toArray();
		if (logger.isDebugEnabled()) {
			logger.debug("ColumnMetadata fields: " + asString.toString(array));
		}
		csvWriter.write(array);
	}

	// TODO remove
	// public Object[] toArray(final ColumnMetadata m) {
	// final Object[] array = new Object[] { m.getColumnName(),
	// m.getColumnIndex(), m.getDataType(), m.getHeading(),
	// m.getLabel(), m.getPrecision(), m.getScale(),
	// m.getColumnDisplaySize(), m.getComments(), m.isExternalFlag(),
	// m.getAttributeName(), m.getWorkbookFormula(),
	// m.getExcelFormat(), m.getJavaFormat(),
	// m.getHorizontalAlignment(), m.getAggregateFunction(),
	// m.getGroupName() };
	// return array;
	// }

	public ColumnMetadata parse(final List<String> tokens) {
		final ColumnMetadata cm = new ColumnMetadata();
		int i = 0;
		cm.setColumnName(tokens.get(i++));
		cm.setColumnIndex(tokens.get(i++));
		cm.setDataType(tokens.get(i++));
		cm.setHeading(tokens.get(i++));
		cm.setLabel(tokens.get(i++));
		cm.setPrecision(tokens.get(i++));
		cm.setScale(tokens.get(i++));
		cm.setColumnDisplaySize(tokens.get(i++));
		cm.setComments(tokens.get(i++));
		cm.setExternalFlag(tokens.get(i++));
		cm.setAttributeName(tokens.get(i++));
		cm.setWorkbookFormula(tokens.get(i++));
		cm.setExcelFormat(tokens.get(i++));
		cm.setJavaFormat(tokens.get(i++));

		cm.setHorizontalAlignment(tokens.get(i++));
		cm.setAggregateFunction(tokens.get(i++));
		cm.setGroupName(tokens.get(i++));
		return cm;

		//
	}

	public static void write(final OutputStream metaOutputStream,
			final Collection<ColumnMetadata> columnMetadata) throws IOException {
		final ColumnMetadataCsvMarshaller marshaller = new ColumnMetadataCsvMarshaller(
				metaOutputStream);
		marshaller.write(columnMetadata);

	}
}
