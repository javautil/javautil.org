package org.javautil.document.renderer;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.xml.transform.stream.StreamResult;

import org.javautil.dataset.render.typewriter.TypewriterContents;
import org.javautil.document.Document;

public interface DocumentRendererRequest {

	public DocumentRenderStatus getStatus();

	public TypewriterContents<?, ?> getContents();

	public Document getDocument();

	public StreamResult getStreamResult();

	public void setStreamResult(StreamResult streamResult);

	public void setDateFormatter(SimpleDateFormat dateFormatter);

	public SimpleDateFormat getDateFormatter();

	public void write() throws IOException;
}
