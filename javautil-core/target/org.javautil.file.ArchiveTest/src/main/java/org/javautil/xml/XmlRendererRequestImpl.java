package org.javautil.xml;

import java.util.List;

import org.javautil.document.renderer.AbstractRendererRequestImpl;
import org.javautil.document.renderer.Renderer;

public class XmlRendererRequestImpl extends AbstractRendererRequestImpl
		implements XmlRendererRequest {

	private String resultSetId;

	private boolean emitColumnsInLowerCase = true;

	private boolean emitColumnsAsElementText = false;

	public List<String> breaks;

	@Override
	public String getResultSetId() {
		return resultSetId;
	}

	@Override
	public void setResultSetId(final String resultSetId) {
		this.resultSetId = resultSetId;
	}

	@Override
	public boolean isEmitColumnsAsElementText() {
		return emitColumnsAsElementText;
	}

	@Override
	public void setEmitColumnsAsElementText(
			final boolean emitColumnsAsElementText) {
		this.emitColumnsAsElementText = emitColumnsAsElementText;
	}

	@Override
	public List<String> getBreaks() {
		return breaks;
	}

	@Override
	public void setBreaks(final List<String> breaks) {
		this.breaks = breaks;
	}

	public Renderer getRenderer() {
		return new XmlRenderer();
	}

	@Override
	public boolean isEmitColumnsInLowerCase() {
		return emitColumnsInLowerCase;
	}

	@Override
	public void setEmitColumnsInLowerCase(final boolean emitColumnsInLowerCase) {
		this.emitColumnsInLowerCase = emitColumnsInLowerCase;
	}

}
