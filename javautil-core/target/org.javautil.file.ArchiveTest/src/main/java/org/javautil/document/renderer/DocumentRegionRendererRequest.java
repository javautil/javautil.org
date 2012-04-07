package org.javautil.document.renderer;

import javax.xml.transform.stream.StreamResult;

import org.javautil.dataset.render.typewriter.TypewriterContent;
import org.javautil.dataset.render.typewriter.TypewriterRendererFactory;
import org.javautil.document.DocumentRegion;

public interface DocumentRegionRendererRequest extends DatasetRendererRequest {

	public DocumentRenderStatus getStatus();

	@Override
	public StreamResult getStreamResult();

	public DocumentRegion getDocumentRegion();

	public TypewriterContent<?, ?> getContent();

	public TypewriterRendererFactory getRendererFactory();

}
