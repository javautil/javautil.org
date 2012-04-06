package org.javautil.sql;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.xml.transform.stream.StreamResult;

import org.javautil.dataset.Dataset;
import org.javautil.xml.XmlRenderer;
import org.javautil.xml.XmlRendererRequest;
import org.javautil.xml.XmlRendererRequestImpl;

public class QueryResourceXmlRenderer implements QueryResourceRenderer {

	private QueryResource queryResource;

	public String[] getBreaks() {
		final String[] breaks = getQueryResource().getConfiguration()
				.getStringArray("breaks");
		return breaks;
	}

	@Override
	public QueryResource getQueryResource() {
		return queryResource;
	}

	public void setQueryResource(final QueryResource queryResource) {
		this.queryResource = queryResource;
	}

	@Override
	@SuppressWarnings("unchecked")
	public XmlRendererRequest getRendererRequest() {
		final XmlRendererRequestImpl request = new XmlRendererRequestImpl();
		request.setEmitColumnsAsElementText(true);
		request.setEmitColumnsInLowerCase(true);
		final Dataset dataset = getQueryResource().getDataset();
		request.setDataset(dataset);
		request.setBreaks(Arrays.asList(getBreaks()));
		return request;
	}

	@Override
	public XmlRenderer getRenderer() {
		return new XmlRenderer();
	}

	@Override
	public void write(final OutputStream out) throws IOException {
		final XmlRenderer renderer = getRenderer();
		final XmlRendererRequest request = getRendererRequest();
		request.setStreamResult(new StreamResult(out));
		renderer.setRequest(request);
		renderer.process();
	}
}
