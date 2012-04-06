package org.javautil.document.renderer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DatasetIterator;
import org.javautil.dataset.DatasetMetadata;
import org.javautil.document.MimeType;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */

// TODO so if I set the separator in CsvRendererRequest how does this class
// know?
// TODO if a date contains a comma it should be in quotes,
public class CsvRenderer extends AbstractRenderer {
	private static final RenderingCapability capability = new RenderingCapability(
			MimeType.CSV);
	private final Logger logger = Logger.getLogger(getClass());
	private String newline = System.getProperty("line.separator");
	private CsvRendererRequest crr;

	// TODO we have to deal with Q on input
	private final HashMap<String, SimpleDateFormat> dateFormats = new HashMap<String, SimpleDateFormat>();

	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	public CsvRenderer() {
		super(capability);
		logger.setLevel(Level.WARN);
	}

	public void process(final CsvRendererRequest request) throws IOException {
		if (request.getDateFormat() != null) {
			dateFormatter = new SimpleDateFormat(request.getDateFormat());
		}
		setRequest(request);
		process();
	}

	@Override
	public void process() throws IOException {
		crr = (CsvRendererRequest) getRequest();
		setStreamResult(crr.getStreamResult());
		// this.columnSeparator = crr.getColumnSeparator();
		this.newline = crr.getNewline();

		if (crr.isEmitHeader()) {
			final String line = getDatasetMetadataColumnNamesAsCSV(crr
					.getDataset().getMetadata());
			write(line);
			if (logger.isDebugEnabled()) {
				logger.debug(line);
			}
			write(crr.getNewline());
		}
		final DatasetIterator iter = getRequest().getDataset()
				.getDatasetIterator();
		if (logger.isDebugEnabled()) {
			logger.debug("columnCount "
					+ iter.getDatasetMetadata().getColumnCount());
		}
		while (iter.next()) {
			final String text = getNextAsCsv(iter);
			if (logger.isDebugEnabled()) {
				logger.debug(text);
			}
			write(text);
			write(newline);
			flush();
		}
		flush();

	}

	public String getDatasetMetadataColumnNamesAsCSV(final DatasetMetadata meta) {
		if (meta == null) {
			throw new IllegalArgumentException("meta is null");
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < meta.getColumnCount(); i++) {
			if (i > 0) {
				sb.append(crr.getColumnSeparator());
			}
			sb.append("\"");
			sb.append(meta.getColumnName(i));
			sb.append("\"");
		}
		return sb.toString();
	}

	public String getNextAsCsv(final DatasetIterator rset) {
		final StringBuilder buff = new StringBuilder();
		final DatasetMetadata meta = rset.getDatasetMetadata();

		int lastNotNullIndex = -1;

		final Object[] columns = new Object[meta.getColumnCount()];

		for (int i = 0; i < columns.length; i++) {
			columns[i] = rset.getObject(i);
			if (columns[i] != null) {
				lastNotNullIndex = i;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("lastNotNullIndex " + lastNotNullIndex);
		}

		for (int column = 0; column < meta.getColumnCount(); column++) {

			final Object data = columns[column];
			if (logger.isDebugEnabled()) {
				logger.debug("column " + column + " " + data);
			}
			if (data != null) {
				if (column > 0 && column <= lastNotNullIndex) {
					buff.append(crr.getColumnSeparator());
				}
				// TODO What is this
				try {
					getString(data, meta.getColumnMetaData(column));
				} catch (final ClassCastException cce) {
					throw new IllegalArgumentException(
							"while processing column " + column + " "
									+ cce.getMessage());
				}
				final ColumnMetadata cmeta = meta.getColumnMetaData(column);
				buff.append(getString(data, cmeta));

			} else {
				if (column <= lastNotNullIndex) {
					if (column != 0) {
						buff.append(crr.getColumnSeparator());
					}
				} else {
				}
			}
		}
		return buff.toString();

	}

	protected SimpleDateFormat getDateFormatter(final ColumnMetadata meta) {
		SimpleDateFormat retval = dateFormats.get(meta.getJavaFormat());
		if (retval == null) {
			final String format = meta.getJavaFormat();
			if (format != null) {
				retval = new SimpleDateFormat(meta.getJavaFormat());
				dateFormats.put(meta.getJavaFormat(), retval);
			}
		}
		if (retval == null) {
			retval = dateFormatter;
		}
		return retval;
	}

	private String getString(final Object data, final ColumnMetadata meta) {
		String retval = "";
		if (data != null) {
			switch (meta.getDataType()) {
			case STRING:
			case CLOB:
				final StringBuilder sbuff = new StringBuilder();
				// case CHAR:
				sbuff.append("\"");
				final String s = (String) data;
				sbuff.append(s.replaceAll("\"", "\"\""));
				sbuff.append("\"");
				retval = sbuff.toString();
				break;
			case DATE:
				final SimpleDateFormat formatter = getDateFormatter(meta);
				retval = formatter.format((Date) data);
				break;
			case TIMESTAMP: // get as java.sql.TimeStamp not
				// oracle.sql.Timestamp
				retval = getTimestampFormatter().format((Date) data);
				break;
			case INTEGER:
				retval = String.valueOf(((Number) data).intValue());
				break;
			case BIG_DECIMAL:
			case DOUBLE:
			case FLOAT:
			case NUMERIC:
			case BIG_INTEGER:
				retval = ((Number) data).toString();
				break;
			default:
				// TODO check metadata, log as error
				// TODO option to raise error,
				logger.warn("unsupported type for type " + meta.getDataType());
			}
		}
		return retval;
	}
}
