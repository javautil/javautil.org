package org.javautil.document.renderer;

import org.javautil.dataset.Dataset;

/**
 * 
 * TODO why is this a MutableDatasetRenderer and Takes a Renderer
 * 
 */
public interface MutableDatasetRendererRequest extends DatasetRendererRequest {

	@Override
	@SuppressWarnings("unchecked")
	public void setDataset(Dataset dataset);

}
