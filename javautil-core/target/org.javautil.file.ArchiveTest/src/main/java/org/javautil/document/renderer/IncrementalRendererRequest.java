package org.javautil.document.renderer;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.render.typewriter.TypewriterContent;
import org.javautil.dataset.render.typewriter.TypewriterContents;
import org.javautil.dataset.render.typewriter.TypewriterRendererFactory;
import org.javautil.document.Document;
import org.javautil.document.DocumentRegion;
import org.javautil.document.MimeType;

// TODO document
public class IncrementalRendererRequest implements DocumentRendererRequest,
		DocumentRegionRendererRequest {

	private final Log logger = LogFactory.getLog(getClass());

	private DocumentRenderStatus status;

	private Document document;

	private DocumentRegion documentRegion;

	private TypewriterContent<?, ?> content;

	private int nextRegionIndex = 0;

	private StreamResult streamResult;

	private SimpleDateFormat dateFormatter;

	private TypewriterContents<?, ?> contents;

	private MimeType mimeType;

	private String dateFormat;

	@Override
	public DocumentRegion getDocumentRegion() {
		if (documentRegion == null) {
			next();
		}
		return documentRegion;
	}

	@Override
	public TypewriterContent<?, ?> getContent() {
		getDocumentRegion();
		return content;
	}

	@Override
	public TypewriterRendererFactory getRendererFactory() {
		return getContent().getRendererFactory();
	}

	public boolean next() {
		final Document document = getDocument();
		if (document == null) {
			throw new IllegalStateException("document is null");
		}
		final boolean hasNext = document.getRegions().size() > nextRegionIndex;
		if (hasNext) {
			final int currentRegionIndex = nextRegionIndex;
			documentRegion = document.getRegions().get(nextRegionIndex++);
			content = getContents().getContent(documentRegion.getName());
			content.setStylesByName(getDocumentRegion().getDocumentStyles());
			if (logger.isDebugEnabled()) {
				if (documentRegion != null) {
					logger.debug("currentRegion is " + documentRegion.getName()
							+ ", index " + currentRegionIndex);
				} else {
					logger.debug("currentRegion was null at index "
							+ currentRegionIndex);
				}
			}
		}
		return hasNext;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Dataset getDataset() {
		return getDocumentRegion().getDataset();
	}

	@Override
	public Document getDocument() {
		return document;
	}

	public void setDocument(final Document document) {
		this.document = document;
		status = new DocumentRenderStatusImpl(document);
	}

	@Override
	public StreamResult getStreamResult() {
		return streamResult;
	}

	@Override
	public void setStreamResult(final StreamResult streamResult) {
		this.streamResult = streamResult;
	}

	@Override
	public SimpleDateFormat getDateFormatter() {
		return dateFormatter;
	}

	@Override
	public void setDateFormatter(final SimpleDateFormat dateFormatter) {
		this.dateFormatter = dateFormatter;
	}

	@Override
	public DocumentRenderStatus getStatus() {
		return status;
	}

	public void setStatus(final DocumentRenderStatus status) {
		this.status = status;
	}

	@Override
	public void write() throws IOException {
		final OutputStream outputstream = getStreamResult().getOutputStream();
		if (outputstream == null) {
			throw new IllegalStateException("streamResult.outputStream is null");
		}
		getContents().write(outputstream);
		outputstream.flush();
	}

	@Override
	public TypewriterContents<?, ?> getContents() {
		return contents;
	}

	public void setContents(final TypewriterContents<?, ?> contents) {
		this.contents = contents;
	}

	@Override
	public MimeType getMimeType() {
		return mimeType;
	}

	@Override
	public void setMimeType(final MimeType mimeType) {
		this.mimeType = mimeType;

	}

	@Override
	public void setDataset(final Dataset dataset) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setDateFormat(final String dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Override
	public String getDateFormat() {
		return dateFormat;
	}

}