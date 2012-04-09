package org.javautil.document.renderer;

import org.javautil.dataset.Dataset;

/**
 * 
 * @author jjs@javautil.org
 * 
 */
public interface DatasetRendererRequest extends RendererRequest {

	@Override
	public Dataset getDataset();

	public void setDataset(Dataset dataset);

}
