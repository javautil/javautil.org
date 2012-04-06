package org.javautil.document.renderer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.Dataset;
import org.javautil.document.MimeType;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
// todo this should encapsulate a request as bean now it is also an
// implementation
public abstract class AbstractRendererRequestImpl implements
		MutableDatasetRendererRequest, ReorderColumnsRendererRequest {

	private final Logger logger = Logger.getLogger(getClass());

	private MimeType mimeType = MimeType.CSV;

	private StreamResult streamResult;

	private Dataset dataset;

	private String dateFormat;

	/**
	 * @return the dateFormat
	 */
	@Override
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	@Override
	public void setDateFormat(final String dateFormat) {
		this.dateFormat = dateFormat;
	}

	private SimpleDateFormat dateFormatter;

	private List<String> columnOrder;

	/**
	 * Get the MimeType of which the workbook will be rendered.
	 * 
	 * @return
	 */
	@Override
	public MimeType getMimeType() {
		return mimeType;
	}

	/**
	 * Set the MimeType to be rendered.
	 * 
	 * @param mimeType
	 */
	@Override
	public void setMimeType(final MimeType mimeType) {
		if (mimeType == null) {
			throw new IllegalArgumentException("mimeType may not be null");
		}
		this.mimeType = mimeType;

		logger.debug("MimeType set to: " + mimeType.toString());

		// Reset the workbookRenderer as well
		// renderer = RendererFactory.getWorkbookRenderer(mimeType);
	}

	@Override
	public StreamResult getStreamResult() {
		return streamResult;
	}

	@Override
	public void setStreamResult(final StreamResult sr) {
		this.streamResult = sr;

	}

	public void setDateFormatter(final SimpleDateFormat sdf) {
		logger.debug("date formatter set to " + sdf.getNumberFormat());
		dateFormatter = sdf;
	}

	public SimpleDateFormat getDateFormatter() {
		if (dateFormatter == null) {
			setDateFormatter(new SimpleDateFormat("yyyy-mm-dd"));
		}
		return dateFormatter;
	}

	@Override
	public List<ColumnMetadata> getColumnMetadata() {

		List<ColumnMetadata> columnMetadata = getDataset().getMetadata()
				.getColumnMetadata();
		if (columnOrder != null) {
			final List<ColumnMetadata> metas = columnMetadata;
			final Map<String, ColumnMetadata> metasMap = new HashMap<String, ColumnMetadata>();
			for (final ColumnMetadata meta : metas) {
				metasMap.put(meta.getColumnName(), meta);
			}
			final List<ColumnMetadata> allMetas = new ArrayList<ColumnMetadata>(
					columnMetadata);
			columnMetadata = new ArrayList<ColumnMetadata>();
			for (final String columnName : columnOrder) {
				final ColumnMetadata meta = metasMap.get(columnName);
				if (meta == null) {
					throw new IllegalStateException("no column metadata for "
							+ "column: " + columnName + "; known columns are: "
							+ allMetas);
				}
			}
		}
		return columnMetadata;
	}

	@Override
	public List<String> getColumnOrder() {
		return columnOrder;
	}

	public void setColumnOrder(final List<String> columnOrder) {
		this.columnOrder = columnOrder;
	}

	@Override
	public void setDataset(final Dataset dataset) {
		this.dataset = dataset;
	}

	@Override
	public Dataset getDataset() {
		return this.dataset;
	}
}
