package org.javautil.dataset.render;

import org.javautil.dataset.DatasetException;
import org.javautil.document.renderer.DatasetRendererRequest;

public interface DatasetEventListener<T> {

	public void handleEvent(DatasetRendererRequest request,
			DatasetEventType type, DatasetEvent<T> event)
			throws DatasetException;

}
