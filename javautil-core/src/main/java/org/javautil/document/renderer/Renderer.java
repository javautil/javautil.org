package org.javautil.document.renderer;

import java.io.IOException;

import org.javautil.dataset.render.DatasetRenderer;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 * @Deprecated
 * 
 * @See {@link DocumentRenderer}
 * @See {@link DatasetRenderer}
 * 
 */

public interface Renderer {

	public void process() throws RendererException, IOException;

	public boolean canRender(DatasetRendererRequest dexterRequest);

	public void setRendererRequest(DatasetRendererRequest dexterRequest);

}
