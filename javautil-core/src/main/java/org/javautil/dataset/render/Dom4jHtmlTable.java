package org.javautil.dataset.render;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.javautil.dataset.render.typewriter.AbstractTypewriterContentSupport;
import org.javautil.document.style.Style;
import org.javautil.util.LinkedCaseInsensitiveMap;
import org.javautil.xml.ElementHelper;

@SuppressWarnings("serial")
public class Dom4jHtmlTable extends
		AbstractTypewriterContentSupport<Element, Element> {

	private final Log logger = LogFactory.getLog(getClass());

	private Map<String, String> tableTagAttributes = new LinkedCaseInsensitiveMap<String>() {
		{
			put("cellpadding", "0");
			put("cellspacing", "0");
		}
	};

	private Map<String, String> tableRowTagAttributes = new LinkedCaseInsensitiveMap<String>();

	private Map<String, String> tableHeadTagAttributes = new LinkedCaseInsensitiveMap<String>();

	private Map<String, String> tableBodyTagAttributes = new LinkedCaseInsensitiveMap<String>();

	private Map<String, String> tableHeadRowCellTagAttributes = new LinkedCaseInsensitiveMap<String>();

	private Map<String, String> tableBodyRowCellTagAttributes = new LinkedCaseInsensitiveMap<String>();

	private String tableTagName = "table";

	private String headRowTagName = "tr";

	private String headTagName = "thead";

	private String headCellTagName = "th";

	private String bodyRowTagName = "tr";

	private String bodyTagName = "tbody";

	private String bodyCellTagName = "td";

	private Element tableElement;

	private Element headElement;

	private Element headRowElement;

	private Element bodyElement;

	private Element bodyRowElement;

	private Element cellElement;

	private final List<Element> rowElements = new ArrayList<Element>();

	private int lastColumnIndex = -1;

	private int lastRowIndex = -1;

	private int nextColumnIndex = -1;

	private boolean renderingHead = false;

	private boolean applyStyleFormatMaskToCellData = true;

	public Dom4jHtmlTable() {
		setCreateMissingCells(true);
		setCreateMissingRows(true);
	}

	public Map<String, String> getTableTagAttributes() {
		return tableTagAttributes;
	}

	public void setTableTagAttributes(
			final Map<String, String> tableTagAttributes) {
		this.tableTagAttributes = tableTagAttributes;
	}

	public Map<String, String> getTableRowTagAttributes() {
		return tableRowTagAttributes;
	}

	public void setTableRowTagAttributes(
			final Map<String, String> tableRowTagAttributes) {
		this.tableRowTagAttributes = tableRowTagAttributes;
	}

	public Map<String, String> getTableHeadTagAttributes() {
		return tableHeadTagAttributes;
	}

	public void setTableHeadTagAttributes(
			final Map<String, String> tableHeadTagAttributes) {
		this.tableHeadTagAttributes = tableHeadTagAttributes;
	}

	public Map<String, String> getTableBodyTagAttributes() {
		return tableBodyTagAttributes;
	}

	public void setTableBodyTagAttributes(
			final Map<String, String> tableBodyTagAttributes) {
		this.tableBodyTagAttributes = tableBodyTagAttributes;
	}

	public Map<String, String> getTableHeadRowCellTagAttributes() {
		return tableHeadRowCellTagAttributes;
	}

	public void setTableHeadRowCellTagAttributes(
			final Map<String, String> tableHeadRowCellTagAttributes) {
		this.tableHeadRowCellTagAttributes = tableHeadRowCellTagAttributes;
	}

	public Map<String, String> getTableBodyRowCellTagAttributes() {
		return tableBodyRowCellTagAttributes;
	}

	public void setTableBodyRowCellTagAttributes(
			final Map<String, String> tableBodyRowCellTagAttributes) {
		this.tableBodyRowCellTagAttributes = tableBodyRowCellTagAttributes;
	}

	public Element getTableElement() {
		return tableElement;
	}

	public Element getHeadElement() {
		return headElement;
	}

	public Element getHeadRowElement() {
		return headRowElement;
	}

	public Element getBodyElement() {
		return bodyElement;
	}

	public Element getBodyRowElement() {
		return bodyRowElement;
	}

	public Element getCellElement() {
		return cellElement;
	}

	public String getHeadRowTagName() {
		return headRowTagName;
	}

	public void setHeadRowTagName(final String headRowTagName) {
		this.headRowTagName = headRowTagName;
	}

	public String getHeadCellTagName() {
		return headCellTagName;
	}

	public void setHeadCellTagName(final String headCellTagName) {
		this.headCellTagName = headCellTagName;
	}

	public String getBodyRowTagName() {
		return bodyRowTagName;
	}

	public void setBodyRowTagName(final String bodyRowTagName) {
		this.bodyRowTagName = bodyRowTagName;
	}

	public String getBodyTagName() {
		return bodyTagName;
	}

	public void setBodyTagName(final String bodyTagName) {
		this.bodyTagName = bodyTagName;
	}

	public String getBodyCellTagName() {
		return bodyCellTagName;
	}

	public void setBodyCellTagName(final String bodyCellTagName) {
		this.bodyCellTagName = bodyCellTagName;
	}

	public String getTableTagName() {
		return tableTagName;
	}

	public void setTableTagName(final String tableTagName) {
		this.tableTagName = tableTagName;
	}

	public String getHeadTagName() {
		return headTagName;
	}

	public void setHeadTagName(final String headTagName) {
		this.headTagName = headTagName;
	}

	public List<Element> getRowElements() {
		return rowElements;
	}

	public Element createOrGetTableElement() {
		if (tableElement == null) {
			createTableElement();
		}
		return tableElement;
	}

	public Element createTableElement() {
		if (logger.isDebugEnabled()) {
			logger.debug("creating table");
		}
		tableElement = ElementHelper.createElement(getTableTagName(),
				getTableTagAttributes());
		return tableElement;
	}

	public Element createOrGetBodyElement() {
		if (bodyElement == null) {
			createBodyElement();
		}
		return bodyElement;
	}

	public Element createBodyElement() {
		if (logger.isDebugEnabled()) {
			logger.debug("creating table body");
		}
		setRenderingHead(false);
		bodyElement = ElementHelper.createElement(getBodyTagName(),
				getTableBodyTagAttributes());
		createOrGetTableElement().add(bodyElement);
		return bodyElement;
	}

	public Element createBodyRow() {
		setRenderingHead(false);
		nextColumnIndex = 0;
		return getRowAt(lastRowIndex + 1, true);
	}

	public Element createBodyRowCell() {
		setRenderingHead(false);
		return getCellAt(lastRowIndex, nextColumnIndex++, false, true);
	}

	public Element createOrGetHeadElement() {
		if (headElement == null) {
			createHeadElement();
		}
		return headElement;
	}

	public Element createHeadElement() {
		if (logger.isDebugEnabled()) {
			logger.debug("creating table head");
		}
		setRenderingHead(true);
		headElement = ElementHelper.createElement(getHeadTagName(),
				getTableHeadTagAttributes());
		createOrGetTableElement().add(getHeadElement());
		return headElement;
	}

	public Element createHeadRow() {
		setRenderingHead(true);
		nextColumnIndex = 0;
		return getRowAt(lastRowIndex + 1, true);
	}

	public Element createHeadRowCell() {
		setRenderingHead(true);
		return getCellAt(lastRowIndex, nextColumnIndex++, false, true);
	}

	protected Element createRowElement(final int rowIndex,
			final String tagName, final Map<String, String> attributes) {
		final Element row = ElementHelper.createElement(tagName, attributes);
		if (rowIndex > lastRowIndex) {
			lastRowIndex = rowIndex;
		}
		return row;
	}

	@Override
	public Element getRowAt(final int rowIndex, final boolean createRow) {
		Element row = getRowElement(rowIndex);
		if (!createRow && row == null) {
			throw new IllegalStateException("no row at index " + rowIndex);
		} else if (row == null) {
			String tagName = getBodyRowTagName();
			final Map<String, String> attributes = getTableRowTagAttributes();
			if (isRenderingHead()) {
				tagName = getHeadRowTagName();
			}
			while (rowElements.size() <= rowIndex) {
				if (logger.isTraceEnabled()) {
					logger.trace("added row at index " + rowIndex);
				}
				row = createRowElement(rowIndex, tagName, attributes);
				if (isRenderingHead()) {
					headRowElement = row;
					createOrGetHeadElement().add(headRowElement);
				} else {
					bodyRowElement = row;
					createOrGetBodyElement().add(bodyRowElement);
				}
				rowElements.add(row);
			}
		}
		return row;
	}

	@SuppressWarnings("unchecked")
	protected Element createCellElement(final Element row,
			final int columnIndex, final String tagName,
			final Map<String, String> attributes) {
		final Element cell = ElementHelper.createElement(tagName, attributes);
		final List<Element> elements = row.elements();
		if (elements.size() == columnIndex) {
			row.add(cell);
		} else {
			throw new IllegalStateException("unexpected columnIndex "
					+ columnIndex + ", row has " + elements.size());
		}
		if (columnIndex > lastColumnIndex) {
			lastColumnIndex = columnIndex;
		}
		cell.setText("");
		return cell;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Element getCellAt(final int rowIndex, final int columnIndex,
			final boolean createRow, final boolean createCell) {
		final Element row = getRowAt(rowIndex, createRow);
		final List<Element> cellElements = row.elements();
		Element cell = null;
		if (columnIndex < cellElements.size()) {
			cell = cellElements.get(columnIndex);
		}
		if (!createCell && cell == null) {
			throw new IllegalStateException("no cell at (" + rowIndex + ","
					+ columnIndex + ")");
		} else if (cell == null) {
			final boolean differentHeadAndBodyTags = !getHeadTagName().equals(
					getBodyTagName());
			String tagName = getBodyCellTagName();
			Map<String, String> attributes = getTableBodyRowCellTagAttributes();
			if (differentHeadAndBodyTags && row.getParent() != null
					&& row.getParent().getName().equals(getHeadTagName())) {
				tagName = getHeadCellTagName();
				attributes = getTableHeadRowCellTagAttributes();
			}
			int cellElementCount = cellElements.size();
			while (cellElementCount <= columnIndex) {
				if (logger.isTraceEnabled()) {
					logger.trace("adding cell at (" + rowIndex + ","
							+ columnIndex + ")");
				}
				cell = createCellElement(row, columnIndex, tagName, attributes);
				cellElementCount++;
			}
		}
		return cell;
	}

	Element getRowElement(final int rowIndex) {
		Element ret = null;
		if (getRowElements().size() > rowIndex) {
			ret = getRowElements().get(rowIndex);
			if (logger.isTraceEnabled()) {
				logger.trace("found row " + ret + " at rowIndex " + rowIndex);
			}
		} else if (logger.isTraceEnabled()) {
			logger.trace("no row at rowIndex " + rowIndex);
		}
		return ret;
	}

	@Override
	public void setCellData(final Element cell, final Object data,
			final Style style) {
		final String formatMask = style == null ? null : style.getFormatMask();
		setCellData(cell, data, isApplyStyleFormatMaskToCellData() ? formatMask
				: null, style);
	}

	public void setCellData(final Element cell, final Object data,
			final String formatMask, final Style style) {
		Format format = null;
		if (data != null && formatMask != null) {
			if (Date.class.isAssignableFrom(data.getClass())) {
				format = new SimpleDateFormat(formatMask);
			}
			if (Number.class.isAssignableFrom(data.getClass())) {
				format = new DecimalFormat(formatMask);
			}
		}
		setCellData(cell, data, format, style);
	}

	public void setCellData(final Element cell, final Object data,
			final Format format, final Style style) {
		String formatted = data == null ? "" : String.valueOf(data);
		if (data != null) {
			if (Date.class.isAssignableFrom(data.getClass())) {
				final DateFormat dateFormat = (DateFormat) format;
				formatted = formatDate(data, dateFormat);
			}
			if (Number.class.isAssignableFrom(data.getClass())) {
				final NumberFormat numberFormat = (NumberFormat) format;
				formatted = formatNumber(data, numberFormat);
			}
		}
		cell.setText(formatted);
		if (style != null) {
			ElementHelper.setAttribute(cell, "class", style.getName());
		}
	}

	@Override
	public void setCellFormula(final Element cell, final String formula,
			final Style style) {
		setCellData(cell, formula, style);
	}

	public XMLWriter getXMLWriter(final Writer writer, final boolean prettyPrint) {
		OutputFormat format = null;
		if (prettyPrint) {
			format = OutputFormat.createPrettyPrint();
		} else {
			format = OutputFormat.createCompactFormat();
		}
		final XMLWriter xmlWriter = new XMLWriter(writer, format);
		return xmlWriter;
	}

	@Override
	public void write(final OutputStream outputStream) throws IOException {
		if (outputStream == null) {
			throw new IllegalStateException("outputStream is null");
		}
		final Writer writer = new OutputStreamWriter(outputStream);
		this.write(writer, true);
		writer.flush();
	}

	public void write(final Writer writer, final boolean prettyPrint)
			throws IOException {
		final XMLWriter xmlWriter = getXMLWriter(writer, prettyPrint);
		xmlWriter.write(getTableElement());
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(new Point(0, 0), getDimension());
	}

	@Override
	public Dimension getDimension() {
		return new Dimension(lastColumnIndex, lastRowIndex);
	}

	public boolean isApplyStyleFormatMaskToCellData() {
		return applyStyleFormatMaskToCellData;
	}

	public void setApplyStyleFormatMaskToCellData(
			final boolean applyStyleFormatMaskToCellData) {
		this.applyStyleFormatMaskToCellData = applyStyleFormatMaskToCellData;
	}

	public boolean isRenderingHead() {
		return renderingHead;
	}

	public void setRenderingHead(final boolean renderingHead) {
		this.renderingHead = renderingHead;
	}

}