package org.javautil.document.renderer;

public abstract class AbstractDocumentRenderer implements DocumentRenderer {

	private Class<?> rendererRequestClass;

	public boolean canRender(final DatasetRendererRequest rendererRequest) {
		if (rendererRequestClass == null) {
			throw new IllegalStateException("rendererRequestClass is null");
		}
		return rendererRequest != null
				&& rendererRequestClass.isAssignableFrom(rendererRequest
						.getClass());
	}

	public Class<?> getRendererRequestClass() {
		return rendererRequestClass;
	}

	public void setRendererRequestClass(final Class<?> rendererRequestClass) {
		this.rendererRequestClass = rendererRequestClass;
	}

}
