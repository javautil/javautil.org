package org.javautil.dataset.render;

import java.util.List;

import org.javautil.dataset.Dataset;
import org.javautil.document.renderer.AbstractRendererRequestImpl;
import org.javautil.document.renderer.MutableDatasetRendererRequest;
import org.javautil.document.renderer.ReorderColumnsRendererRequest;

public class DefaultDatasetRendererRequest extends AbstractRendererRequestImpl
		implements MutableDatasetRendererRequest, ReorderColumnsRendererRequest {

	private List<String> columnOrder;

	public DefaultDatasetRendererRequest() {
		super();
	}

	public DefaultDatasetRendererRequest(final Dataset dataset) {
		super();
		setDataset(dataset);
	}

	@Override
	public List<String> getColumnOrder() {
		return columnOrder;
	}

	@Override
	public void setColumnOrder(final List<String> columnOrder) {
		this.columnOrder = columnOrder;
	}

}
