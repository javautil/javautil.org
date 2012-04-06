package org.javautil.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 
 * @author bcm
 * 
 *         
 *         http://forum.java.sun.com/thread.jspa?threadID=730559&messageID=4204132
 */
public class RedirectingServletResponse extends HttpServletResponseWrapper {
	RedirectServletStream out;

	PrintWriter writer;

	/**
	 * @param arg0
	 */
	public RedirectingServletResponse(HttpServletResponse response,
			OutputStream out) {
		super(response);
		this.out = new RedirectServletStream(out);
		this.writer = new PrintWriter(out);
	}

	public RedirectingServletResponse(HttpServletResponse response,
			Writer writer) {
		super(response);
		this.writer = new PrintWriter(writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#flushBuffer()
	 */
	@Override
	public void flushBuffer() throws IOException {
		if (out != null) {
			out.flush();
		} else if (writer != null) {
			writer.flush();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#getOutputStream()
	 */
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#getWriter()
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	private static class RedirectServletStream extends ServletOutputStream {
		OutputStream out;

		RedirectServletStream(OutputStream _out) {
			out = _out;
		}

		@Override
		public void write(byte[] bytes) throws java.io.IOException {
			out.write(bytes);
		}

		@Override
		public void write(byte[] bytes, int off, int len)
				throws java.io.IOException {
			out.write(bytes, off, len);
		}

		@Override
		public void write(int param) throws java.io.IOException {
			out.write(param);
		}
	}

}
