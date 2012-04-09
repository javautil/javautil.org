package org.javautil.dataset.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetIterator;
import org.javautil.document.renderer.DatasetRendererRequest;
import org.javautil.document.renderer.ReorderColumnsRendererRequest;

public class DatasetEventProducer<T> implements DatasetRenderer {

	private final Map<DatasetEventType, List<DatasetEventListener<T>>> listeners = new HashMap<DatasetEventType, List<DatasetEventListener<T>>>();

	protected List<ColumnMetadata> orderColumns(
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

	protected Dataset<T> sortData(final Dataset<T> dataset) {
		return dataset;
	}

	public void addDatasetEventListener(final DatasetEventType eventType,
			final DatasetEventListener<T> eventListener) {
		if (eventType == null) {
			throw new IllegalArgumentException("eventType is null");
		}
		if (eventListener == null) {
			throw new IllegalArgumentException("eventListener is null");
		}
		if (eventType == DatasetEventType.ANY) {
			for (final DatasetEventType type : DatasetEventType.values()) {
				if (type != DatasetEventType.ANY) {
					addDatasetEventListener(type, eventListener);
				}
			}
		} else {
			List<DatasetEventListener<T>> list = listeners.get(eventType);
			if (list == null) {
				list = new ArrayList<DatasetEventListener<T>>();
				listeners.put(eventType, list);
			}
			list.add(eventListener);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void render(final DatasetRendererRequest rendererRequest)
			throws DatasetRenderException {
		Dataset<T> dataset = rendererRequest.getDataset();
		if (dataset == null) {
			throw new IllegalArgumentException("dataset is null");
		}
		final DatasetIterator<T> iterator = dataset.getDatasetIterator();
		final List<ColumnMetadata> columnMetadatas = orderColumns(rendererRequest);
		dataset = sortData(dataset);
		final DatasetEventProcessor processor = new DatasetEventProcessor(
				rendererRequest, listeners);
		processor.fireDatasetStartEvent();
		processor.fireDatasetHeaderStartEvent();
		long colIndex = 0;
		for (final ColumnMetadata columnMetadata : columnMetadatas) {
			processor.fireDatasetHeaderRowDataEvent(columnMetadata, colIndex);
			colIndex++;
		}
		processor.fireDatasetHeaderEndEvent();
		processor.fireDatasetBodyStartEvent();
		long rowIndex = 0;
		while (iterator.hasNext()) {
			iterator.next();
			processor.fireDatasetBodyRowStartEvent(rowIndex);
			colIndex = 0;
			for (final ColumnMetadata columnMetadata : columnMetadatas) {
				final Object data = iterator.getObject(columnMetadata
						.getColumnName());
				processor.fireDatasetBodyRowDataEvent(columnMetadata, rowIndex,
						colIndex, data);
				colIndex++;
			}
			processor.fireDatasetBodyRowEndEvent(rowIndex);
			rowIndex++;
		}
		processor.fireDatasetBodyEndEvent();
		processor.fireDatasetEndEvent();
	}

}