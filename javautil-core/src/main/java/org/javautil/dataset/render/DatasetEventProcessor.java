package org.javautil.dataset.render;

import java.util.List;
import java.util.Map;

import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.Dataset;
import org.javautil.document.renderer.DatasetRendererRequest;

// TODO what is this?
public class DatasetEventProcessor<T> {

	private final DatasetRendererRequest rendererRequest;

	private final Map<DatasetEventType, List<DatasetEventListener<T>>> listeners;

	public DatasetEventProcessor(final DatasetRendererRequest rendererRequest,
			final Map<DatasetEventType, List<DatasetEventListener<T>>> listeners) {
		if (rendererRequest == null) {
			throw new IllegalArgumentException("rendererRequest is null");
		}
		if (rendererRequest.getDataset() == null) {
			throw new IllegalArgumentException(
					"rendererRequest.dataset is null");
		}
		if (listeners == null) {
			throw new IllegalArgumentException("listeners is null");
		}
		this.rendererRequest = rendererRequest;
		this.listeners = listeners;
	}

	void fireDatasetEndEvent() {
		fireEvent(DatasetEventType.END, new DatasetEvent<T>(getDataset()));
	}

	void fireDatasetBodyEndEvent() {
		fireEvent(DatasetEventType.BODY_END, new DatasetEvent<T>(getDataset()));
	}

	void fireDatasetBodyRowEndEvent(final long rowIndex) {
		fireEvent(DatasetEventType.BODY_ROW_END, new DatasetEvent<T>(
				getDataset(), new Long(rowIndex)));
	}

	void fireDatasetBodyRowDataEvent(final ColumnMetadata columnMetadata,
			final long columnIndex, final long rowIndex, final Object data) {
		fireEvent(DatasetEventType.BODY_ROW_DATA, new DatasetEvent<T>(
				getDataset(), columnMetadata, new Long(columnIndex), new Long(
						rowIndex), data));
	}

	void fireDatasetBodyRowStartEvent(final long rowIndex) {
		fireEvent(DatasetEventType.BODY_ROW_START, new DatasetEvent<T>(
				getDataset(), new Long(rowIndex)));
	}

	void fireDatasetBodyStartEvent() {
		fireEvent(DatasetEventType.BODY_START,
				new DatasetEvent<T>(getDataset()));
	}

	void fireDatasetHeaderEndEvent() {
		fireEvent(DatasetEventType.HEADER_END,
				new DatasetEvent<T>(getDataset()));
	}

	void fireDatasetHeaderRowDataEvent(final ColumnMetadata columnMetadata,
			final long columnIndex) {
		fireEvent(DatasetEventType.HEADER_ROW_DATA, new DatasetEvent<T>(
				getDataset(), columnMetadata, new Long(columnIndex)));
	}

	void fireDatasetHeaderStartEvent() {
		fireEvent(DatasetEventType.HEADER_START, new DatasetEvent<T>(
				getDataset()));
	}

	void fireDatasetStartEvent() {
		fireEvent(DatasetEventType.START, new DatasetEvent<T>(getDataset()));
	}

	private void fireEvent(final DatasetEventType type,
			final DatasetEvent<T> event) {
		if (event == null) {
			throw new IllegalArgumentException("event is null");
		}
		if (type == null) {
			throw new IllegalArgumentException("type is null");
		}
		if (type == DatasetEventType.ANY) {
			throw new IllegalStateException("cannot fireEvent of type "
					+ "DatasetEventType ANY");
		}
		final List<DatasetEventListener<T>> eventListeners = listeners
				.get(type);
		if (eventListeners != null) {
			for (final DatasetEventListener<T> eventListener : eventListeners) {
				eventListener.handleEvent(getRendererRequest(), type, event);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Dataset getDataset() {
		return getRendererRequest().getDataset();
	}

	public DatasetRendererRequest getRendererRequest() {
		return rendererRequest;
	}

}
