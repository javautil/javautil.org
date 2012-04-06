package org.javautil.xml;

import java.util.List;

import org.javautil.document.renderer.DatasetRendererRequest;

public interface XmlRendererRequest extends DatasetRendererRequest {

	public void setResultSetId(String resultSetId);

	public String getResultSetId();

	public void setEmitColumnsAsElementText(boolean emitColumnsAsElementText);

	public boolean isEmitColumnsAsElementText();

	public void setEmitColumnsInLowerCase(boolean emitColumnsInLowerCase);

	public boolean isEmitColumnsInLowerCase();

	public List<String> getBreaks();

	public void setBreaks(List<String> breaks);

}
