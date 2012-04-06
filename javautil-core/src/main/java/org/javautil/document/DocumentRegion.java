package org.javautil.document;

import java.util.Map;

import org.javautil.dataset.Dataset;
import org.javautil.document.layout.LayoutConstraints;
import org.javautil.document.renderer.RenderTemplate;
import org.javautil.document.style.DocumentStyles;

public interface DocumentRegion {

	public String getName();

	public Map<String, Object> getParameters();

	public LayoutConstraints getLayoutConstraints();

	public RenderTemplate getRenderTemplate();

	@SuppressWarnings("unchecked")
	public Dataset getDataset();

	public DocumentStyles getDocumentStyles();
}
