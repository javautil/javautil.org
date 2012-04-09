package org.javautil.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetIterator;
import org.javautil.dataset.DatasetMetadata;
import org.javautil.document.BreakHelper;
import org.javautil.document.MimeType;
import org.javautil.document.renderer.AbstractRenderer;
import org.javautil.document.renderer.RenderingCapability;

/**
 * Renders an XML document using dom4j. Supports multiple break columns. Most of
 * the code of the code from this class is based on
 * com.dbexperts.dex.renderer.XmlWriter from dbexperts3.
 * 
 * @author jjs-javautil@dbexperts.com, bcm
 */
public class XmlRenderer extends AbstractRenderer {

	private static Log logger = LogFactory.getLog(XmlRenderer.class.getName());

	private Element root;

	private final BreakHelper breakHelper = new BreakHelper();

	private XmlRendererRequest xrr;

	private static final RenderingCapability capability = new RenderingCapability(
			MimeType.XML);

	public XmlRenderer() {
		super(capability);
	}

	public XmlRenderer(final XmlRendererRequest request) {
		super(capability);
		setRequest(request);
	}

	@Override
	public void process() throws IOException {
		xrr = (XmlRendererRequest) request;
		setStreamResult(request.getStreamResult());
		setRootElement();
		try {
			writeXml();
		} catch (final SQLException e) {
			throw new IOException(e);
		}

	}

	private void setRootElement() {
		root = DocumentHelper.createElement("resultset");
		if (xrr.getResultSetId() != null) {
			root.addAttribute("id", xrr.getResultSetId());
		}
	}

	/**
	 * 
	 * TODO check for duplicate column names TODO conserve cooked names, don't
	 * recompute and waste space
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("null")
	private void writeXml() throws SQLException, IOException {

		final Dataset dataset = getRequest().getDataset();
		final DatasetMetadata metadata = dataset.getMetadata();
		if (xrr.isEmitColumnsInLowerCase()) {
			breakHelper.setLowerCase(true);
		}
		breakHelper.setBreaks(xrr.getBreaks());
		breakHelper.setDataset(dataset);
		try {
			breakHelper.afterPropertiesSet();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		final long startTime = System.nanoTime();
		final int columnCount = metadata.getColumnCount();

		final List<String> breaks = breakHelper.getBreaks();
		ArrayList<Element> breaksList = null;
		if (breaks != null) {
			breaksList = new ArrayList<Element>(breaks.size());
		}
		final HashMap<String, Element> nodeReference = new HashMap<String, Element>();
		Element currentNode = root;

		// Object[] rowData;

		// prepare the XML for its first row data from the breaks
		if (breaks != null) {
			for (int a = 0; a < breaks.size(); a++) {
				final String currentBreak = breaks.get(a);
				currentNode = currentNode.addElement(currentBreak);
				breaksList.add(currentNode);
				nodeReference.put(currentBreak, currentNode);
			}
		}

		int rowsProcessed = 0;
		Object[] previousRow = null;
		Object[] currentRow = null;

		// populate the XML
		final DatasetIterator iterator = dataset.getDatasetIterator();
		while (iterator.next()) {
			currentRow = new Object[metadata.getColumnCount()];
			for (int i = 0; i < metadata.getColumnCount(); i++) {
				currentRow[i] = iterator.getObject(i);
			}
			rowsProcessed++;

			int breakLevel = -1;
			if (previousRow != null) {
				breakLevel = breakHelper.getBreakLevel(previousRow, currentRow);
			}
			if (breakLevel != -1) {
				currentNode = breaksList.get(breakLevel - 1).getParent();
				for (int a = breakLevel - 1; a < breaks.size(); a++) {
					final String currentBreak = breakHelper.getBreak(a);
					currentNode = currentNode.addElement(currentBreak);
					breaksList.set(a, currentNode);
					nodeReference.put(currentBreak, currentNode);
				}
				// @todo process when logic here
			}
			final Element row = currentNode.addElement("row");
			// logger.info("adding row");
			for (int i = 0; i < columnCount; i++) {
				final String cellValue = iterator.getString(i);
				if (cellValue != null) {
					String rawAttributeName = metadata.getColumnName(i);
					if (xrr.isEmitColumnsInLowerCase()) {
						rawAttributeName = rawAttributeName.toLowerCase();
					}
					final String cookedName = XmlTagUtils
							.getTagName(rawAttributeName);
					if (breaks != null && breaks.contains(rawAttributeName)) {
						nodeReference.get(cookedName).addAttribute("value",
								cellValue);
						// todo check what to do if emitXml
					} else {
						if (xrr.isEmitColumnsAsElementText()) {
							final Element columnEl = row.addElement(cookedName);
							columnEl.setText(cellValue);
						} else {
							row.addAttribute(cookedName, cellValue);
						}
					}
				}
			}
			previousRow = currentRow;
		}
		final long endTime = System.nanoTime();
		final double elapsedSeconds = (endTime - startTime) / 1e9;
		if (logger.isDebugEnabled()) {
			logger.debug("xml row count " + rowsProcessed + " elapsedSeconds "
					+ elapsedSeconds);
		}

		// write the root and its children to the stream result
		Writer targetWriter = getStreamResult().getWriter();
		if (targetWriter == null) {
			final OutputStream out = getStreamResult().getOutputStream();
			targetWriter = new OutputStreamWriter(out);
		}
		root.write(targetWriter);
		targetWriter.flush();

	}

}
