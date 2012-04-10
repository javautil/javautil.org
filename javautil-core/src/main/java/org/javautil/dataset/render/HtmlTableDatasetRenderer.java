package org.javautil.dataset.render;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.Dataset;
import org.javautil.document.renderer.DatasetRendererRequest;
import org.javautil.document.style.HorizontalAlignment;
import org.javautil.xml.ElementHelper;

public class HtmlTableDatasetRenderer<T> extends DatasetEventProducer<T>
		implements DatasetRenderer {

	public static List<String> RELATED_MIME_TYPES = Arrays.asList(new String[] {
			"text/xhtml", "text/html" });

	private final Log logger = LogFactory.getLog(getClass());

	private final EventDelegate delegate;

	private boolean applyEvenAndOddRowCssClassNames = true;

	private boolean automaticHorizontalAligment = true;

	private String evenRowCssClassName = null;

	private String oddRowCssClassName = "alt";

	private boolean prettyPrint = true;

	private transient Dom4jHtmlTable table;

	private transient Map<Long, DateFormat> dateFormats;

	private transient Map<Long, NumberFormat> numberFormats;

	public HtmlTableDatasetRenderer() {
		delegate = new EventDelegate();
		addDatasetEventListener(DatasetEventType.ANY, delegate);
		initialize();
	}

	@Override
	public void render(final DatasetRendererRequest rendererRequest)
			throws DatasetRenderException {
		super.render(rendererRequest);
		initialize();
	}

	protected void initialize() {
		dateFormats = new HashMap<Long, DateFormat>();
		numberFormats = new HashMap<Long, NumberFormat>();
		table = new Dom4jHtmlTable();
		initializeDom4jTable(table);
	}

	protected void initializeDom4jTable(final Dom4jHtmlTable table) {
		table.getTableTagAttributes().put("class", "dataset");
	}

	protected void renderTableStart(final Dataset dataset) throws Exception {
		for (final ColumnMetadata meta : dataset.getMetadata()
				.getColumnMetadata()) {
			final String formatMask = meta.getJavaFormat();
			if (formatMask != null && DataType.isDateType(meta.getDataType())) {
				if (logger.isDebugEnabled()) {
					logger.debug("setting dateFormat for column "
							+ meta.getColumnIndex() + ": " + formatMask);
				}
				dateFormats.put(meta.getColumnIndex().longValue(),
						new SimpleDateFormat(formatMask));
			} else if (formatMask != null
					&& DataType.isNumberType(meta.getDataType())) {
				if (logger.isDebugEnabled()) {
					logger.debug("setting numberFormat for column "
							+ meta.getColumnIndex() + ": " + formatMask);
				}
				numberFormats.put(meta.getColumnIndex().longValue(),
						new DecimalFormat(formatMask));
			}
		}
		table.createTableElement();
	}

	protected void renderTableHead() throws Exception {
		table.createHeadElement();
		table.createHeadRow();
	}

	protected void renderTableBody() throws Exception {
		table.createBodyElement();
	}

	protected void renderBodyRowStart(final Long rowIndex) {
		table.createBodyRow();
		if (isApplyEvenAndOddRowCssClassNames()) {
			applyEvenAndOddRowCssClassNames(table.getBodyRowElement(), rowIndex);
		}
	}

	protected void applyEvenAndOddRowCssClassNames(final Element row,
			final Long rowIndex) {
		String cssClassName = row.attributeValue("class", "");
		String addCssClassName = null;
		if ((rowIndex + 1) % 2 == 0) {
			addCssClassName = getEvenRowCssClassName();
		} else {
			addCssClassName = getOddRowCssClassName();
		}
		if (addCssClassName != null) {
			cssClassName = cssClassName.length() == 0 ? addCssClassName
					: cssClassName + " " + addCssClassName;
			ElementHelper.setAttribute(row, "class", cssClassName);
		}
	}

	protected void renderHeadRowData(final Long columnIndex, final Object data,
			final HorizontalAlignment ha) {
		final Element cellElement = table.createHeadRowCell();
		cellElement.setText(data == null ? "" : String.valueOf(data));
		if (ha != null) {
			ElementHelper.setAttribute(cellElement, "align", ha.toString()
					.toLowerCase());
		}
	}

	protected void renderBodyRowData(final Long columnIndex, final Object data,
			final HorizontalAlignment ha) {
		final Element cellElement = table.createBodyRowCell();
		cellElement.setText(data == null ? "" : String.valueOf(data));
		if (ha != null) {
			ElementHelper.setAttribute(cellElement, "align", ha.toString()
					.toLowerCase());
		}
	}

	protected void renderTableEnd(final Dataset dataset,
			final List<ColumnMetadata> columns, final PrintWriter writer)
			throws Exception {
		final XMLWriter xmlWriter = table.getXMLWriter(writer, isPrettyPrint());
		xmlWriter.write(table.getTableElement());
	}

	protected String formatData(final Long columnIndex, final Object data) {
		String formatted = data == null ? "" : String.valueOf(data);
		final NumberFormat numberFormat = numberFormats.get(columnIndex);
		if (numberFormat != null) {
			formatted = getTable().formatNumber(data, numberFormat);
		}
		final DateFormat dateFormat = dateFormats.get(columnIndex);
		if (dateFormat != null) {
			formatted = getTable().formatDate(data, dateFormat);
		}
		return formatted;
	}

	class EventDelegate extends AbstractDatasetWriter<T> {

		@Override
		public void writeEventData(
				final DatasetRendererRequest rendererRequest,
				final Writer writer, final DatasetEventType type,
				final DatasetEvent<T> event) {
			try {
				Long ci = null;
				HorizontalAlignment ha = null;
				switch (type) {
				case START:
					renderTableStart(event.getDataset());
					break;
				case HEADER_START:
					renderTableHead();
					break;
				case HEADER_ROW_DATA:
					String header = event.getColumn().getHeading();
					if (header == null) {
						header = event.getColumn().getColumnName();
					}
					ci = event.getColumnIndex();
					ha = getTable().getHorizontalAlignment(ci,
							event.getDataset().getMetadata(),
							event.getColumn(), isAutomaticHorizontalAligment());
					renderHeadRowData(ci, header, ha);
					break;
				case BODY_START:
					renderTableBody();
					break;
				case BODY_ROW_START:
					renderBodyRowStart(event.getRowIndex());
					break;
				case BODY_ROW_DATA:
					ci = event.getColumnIndex();
					ha = getTable().getHorizontalAlignment(ci,
							event.getDataset().getMetadata(),
							event.getColumn(), isAutomaticHorizontalAligment());
					final Object data = formatData(event.getColumnIndex(),
							event.getData());
					renderBodyRowData(ci, data, ha);
					break;
				case END:
					final Dataset dataset = event.getDataset();
					final List<ColumnMetadata> metas = getColumnMetadata(rendererRequest);
					renderTableEnd(dataset, metas, new PrintWriter(writer));
					break;
				}
			} catch (final Exception e) {
				if (DatasetRenderException.class.isAssignableFrom(e.getClass())) {
					throw (DatasetRenderException) e;
				} else {
					throw new DatasetRenderException(
							"error while writing xhtml", e);
				}
			}
		}
	}

	public boolean isPrettyPrint() {
		return prettyPrint;
	}

	public void setPrettyPrint(final boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
	}

	public boolean canRender(final DatasetRendererRequest rendererRequest) {
		return RELATED_MIME_TYPES.contains(rendererRequest.getMimeType()
				.toString());
	}

	public String getOddRowCssClassName() {
		return oddRowCssClassName;
	}

	public void setOddRowCssClassName(final String oddRowCssClassName) {
		this.oddRowCssClassName = oddRowCssClassName;
	}

	public String getEvenRowCssClassName() {
		return evenRowCssClassName;
	}

	public void setEvenRowCssClassName(final String evenRowCssClassName) {
		this.evenRowCssClassName = evenRowCssClassName;
	}

	public boolean isApplyEvenAndOddRowCssClassNames() {
		return applyEvenAndOddRowCssClassNames;
	}

	public void setApplyEvenAndOddRowCssClassNames(
			final boolean applyEvenAndOddRowCssClassNames) {
		this.applyEvenAndOddRowCssClassNames = applyEvenAndOddRowCssClassNames;
	}

	public boolean isAutomaticHorizontalAligment() {
		return automaticHorizontalAligment;
	}

	public void setAutomaticHorizontalAligment(
			final boolean automaticHorizontalAligment) {
		this.automaticHorizontalAligment = automaticHorizontalAligment;
	}

	public Dom4jHtmlTable getTable() {
		return table;
	}

}