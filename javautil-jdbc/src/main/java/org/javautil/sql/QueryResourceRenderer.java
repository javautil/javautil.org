package org.javautil.sql;

import java.io.IOException;
import java.io.OutputStream;

import org.javautil.document.renderer.DatasetRendererRequest;
import org.javautil.document.renderer.Renderer;

public interface QueryResourceRenderer {

	public QueryResource getQueryResource();

	public DatasetRendererRequest getRendererRequest();

	public Renderer getRenderer();

	public void write(OutputStream out) throws IOException;

}
