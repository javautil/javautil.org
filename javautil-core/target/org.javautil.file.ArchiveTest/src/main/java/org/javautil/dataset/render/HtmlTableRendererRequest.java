package org.javautil.dataset.render;

import org.javautil.dataset.Dataset;

public class HtmlTableRendererRequest extends DefaultDatasetRendererRequest {

	@SuppressWarnings("unchecked")
	public HtmlTableRendererRequest(final Dataset dataset) {
		super(dataset);
	}

	public HtmlTableRendererRequest() {
		super();
	}
}
