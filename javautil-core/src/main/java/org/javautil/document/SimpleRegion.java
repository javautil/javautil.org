package org.javautil.document;

import java.util.Map;

import org.javautil.dataset.Dataset;
import org.javautil.document.layout.LayoutConstraints;
import org.javautil.document.renderer.RenderTemplate;
import org.javautil.document.style.DocumentStyles;

public class SimpleRegion implements DocumentRegion {

	private String name;

	private LayoutConstraints layoutConstraints;

	private RenderTemplate renderTemplate;

	@SuppressWarnings("unchecked")
	private Dataset dataset;

	private DocumentStyles documentStyles;

	private Map<String, Object> parameters;

	@Override
	public LayoutConstraints getLayoutConstraints() {
		return layoutConstraints;
	}

	public void setLayoutConstraints(final LayoutConstraints layoutConstraints) {
		this.layoutConstraints = layoutConstraints;
	}

	@Override
	public RenderTemplate getRenderTemplate() {
		return renderTemplate;
	}

	public void setRenderTemplate(final RenderTemplate renderTemplate) {
		this.renderTemplate = renderTemplate;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Dataset getDataset() {
		return dataset;
	}

	@SuppressWarnings("unchecked")
	public void setDataset(final Dataset dataset) {
		this.dataset = dataset;
	}

	@Override
	public DocumentStyles getDocumentStyles() {
		return documentStyles;
	}

	public void setDocumentStyles(final DocumentStyles documentStyles) {
		this.documentStyles = documentStyles;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(final Map<String, Object> parameters) {
		this.parameters = parameters;
	}

}