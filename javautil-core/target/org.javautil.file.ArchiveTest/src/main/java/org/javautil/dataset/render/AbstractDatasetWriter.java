package org.javautil.dataset.render;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.transform.stream.StreamResult;

import org.javautil.dataset.ColumnMetadata;
import org.javautil.document.renderer.DatasetRendererRequest;
import org.javautil.document.renderer.ReorderColumnsRendererRequest;

public abstract class AbstractDatasetWriter<T> implements
		DatasetEventListener<T> {

	protected List<ColumnMetadata> getColumnMetadata(
			final DatasetRendererRequest rendererRequest) {
		List<ColumnMetadata> metas = rendererRequest.getDataset().getMetadata()
				.getColumnMetadata();
		if (ReorderColumnsRendererRequest.class
				.isAssignableFrom(rendererRequest.getClass())) {
			final ReorderColumnsRendererRequest request = (ReorderColumnsRendererRequest) rendererRequest;
			metas = request.getColumnMetadata();
		}
		return metas;
	}

	protected Writer getWriterFor(final DatasetRendererRequest rendererRequest) {
		if (rendererRequest == null) {
			throw new IllegalStateException("rendererRequest is null");
		}
		Writer writer = null;
		final StreamResult streamResult = rendererRequest.getStreamResult();
		if (streamResult == null) {
			throw new IllegalStateException(
					"rendererRequest.streamResult is null");
		}
		if (streamResult.getOutputStream() != null) {
			final OutputStream outputStream = streamResult.getOutputStream();
			writer = new OutputStreamWriter(outputStream);
		} else if (streamResult.getWriter() != null) {
			writer = streamResult.getWriter();
		} else {
			throw new IllegalStateException(
					"rendererRequest.streamResult.writer and "
							+ "rendererRequest.streamResult.outputStream"
							+ " are both null");
		}
		return writer;
	}

	@Override
	public void handleEvent(final DatasetRendererRequest rendererRequest,
			final DatasetEventType type, final DatasetEvent<T> event)
			throws DatasetRenderException {
		final Writer writer = getWriterFor(rendererRequest);
		try {
			writeEventData(rendererRequest, writer, type, event);
			writer.flush();
		} catch (final IOException e) {
			throw new DatasetRenderException("error writing event data", e);
		}
	}

	public abstract void writeEventData(DatasetRendererRequest request,
			Writer writer, DatasetEventType type, DatasetEvent<T> event)
			throws DatasetRenderException;

}
