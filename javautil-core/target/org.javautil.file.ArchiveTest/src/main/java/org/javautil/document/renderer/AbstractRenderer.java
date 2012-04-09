package org.javautil.document.renderer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;

/**
 * 
 * @author jjs
 * 
 */
public abstract class AbstractRenderer implements Renderer {

	private final RenderingCapability capability;

	private SimpleDateFormat timestampFormat;

	private StreamResult streamResult;

	// protected so that extending classes can cast to the actual class
	protected DatasetRendererRequest request;

	private final Logger logger = Logger.getLogger(getClass());

	// todo looks like I have to revert to writerset

	private Writer writer;

	public AbstractRenderer(final RenderingCapability capability) {
		if (capability == null) {
			throw new IllegalArgumentException("capability is null");
		}
		this.capability = capability;
	}

	@Override
	public final void setRendererRequest(final DatasetRendererRequest request) {
		this.request = request;

	}

	public DatasetRendererRequest getRequest() {
		if (request == null) {
			throw new IllegalStateException("request is null");
		}
		return request;
	}

	public void setRequest(final DatasetRendererRequest request) {
		this.request = request;
	}

	// public final void setDataSet(AbstractDataSet rset) throws
	// DataSetException {
	// if (rset == null) {
	// throw new IllegalArgumentException("rset is null");
	// }
	// this.dataset = rset;
	// }

	public SimpleDateFormat getTimestampFormatter() {
		if (timestampFormat == null) {
			timestampFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss"); // todo
			// check
			// for
			// 24
			// hour
			// format
		}
		return timestampFormat;
	}

	@Override
	public final boolean canRender(final DatasetRendererRequest state) {
		if (state == null) {
			throw new IllegalArgumentException("state is null");
		}
		if (capability == null) {
			throw new IllegalStateException("capability is null");
		}
		return capability.canRender(state);
	}

	public void flush() throws IOException {
		if (streamResult != null) {
			streamResult.getOutputStream().flush();
		}
		if (writer != null) {
			writer.flush();
		}
	}

	public void write(final String text) throws IOException {
		if (streamResult == null) {
			throw new IllegalStateException("streamResult has not been set");
		}
		// Writer writer = streamResult.getWriter();
		// todo research what a streamResult is supposed to do for me
		if (writer == null) {
			final OutputStream os = streamResult.getOutputStream();
			if (os == null) {
				throw new IllegalArgumentException(
						"streamResult has no OutputStream");
			}
			writer = new OutputStreamWriter(os);
			// throw new IllegalStateException("streamResult has null writer");
		}
		writer.write(text);

	}

	protected final StreamResult getStreamResult() {
		return streamResult;
	}

	protected final void setStreamResult(final StreamResult streamResult) {
		this.streamResult = streamResult;
	}

}
