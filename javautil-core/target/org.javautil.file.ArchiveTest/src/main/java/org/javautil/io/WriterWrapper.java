package org.javautil.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class WriterWrapper {
	private OutputStream os;
	private String description;
	private boolean pretty;
	// private OutputStreamWriter osw;
	private Writer w;
	private boolean closed = false;
	private boolean cache = false;
	private File file = null;
	private long writerSetMask;

	public WriterWrapper(final Writer _writer, final String description) {
		if (_writer == null) {
			throw new IllegalArgumentException("_writer is null");
		}
		w = _writer;

	}

	public WriterWrapper(final OutputStream writer, final File file,
			final boolean pretty) {
		this.os = writer;
		this.file = file;
		this.pretty = pretty;
	}

	public WriterWrapper(final OutputStream writer, final File file,
			final boolean pretty, final long mask) {
		this.os = writer;
		this.file = file;
		this.pretty = pretty;
		this.writerSetMask = mask;
	}

	public WriterWrapper(final OutputStream writer, final String description,
			final boolean pretty) {
		this.os = writer;
		this.description = description;
		this.pretty = pretty;
	}

	// TODO check that new spool on scripts doesn't support caching as we have
	// no idea what it is doing
	public WriterWrapper(final OutputStream writer, final String description,
			final boolean pretty, final boolean isCache) {
		this.cache = isCache;
		this.os = writer;
		this.description = description;
		this.pretty = pretty;
	}

	// TODO this code duplication is a nasty hack, fix it
	public WriterWrapper(final OutputStream writer, final String description,
			final boolean pretty, final boolean isCache, final long mask) {
		this.cache = isCache;
		this.os = writer;
		this.description = description;
		this.pretty = pretty;
		this.writerSetMask = mask;
	}

	public WriterWrapper(final OutputStream writer, final String description,
			final boolean pretty, final long mask) {
		this.os = writer;
		this.description = description;
		this.pretty = pretty;
		this.writerSetMask = mask;
	}

	public void close() throws IOException {
		if (os != null) {
			os.close();
		}
		if (w != null) {
			w.close();
			closed = true;
		}
	}

	public void flush() throws IOException {
		if (os != null) {
			os.flush();
		}
		if (w != null && !closed) {
			w.flush();
		}
	}

	public String getDescription() {
		return description;
	}

	public File getFile() {
		return file;
	}

	public OutputStream getWriter() {
		return os;
	}

	public long getWriterSetMask() {
		return writerSetMask;
	}

	public boolean isCache() {
		return cache;
	}

	public boolean isPretty() {
		return pretty;
	}

	public void setFile(final File file) {
		this.file = file;
	}

	public void setWriterSetMask(final long writerSet) {
		this.writerSetMask = writerSet;
	}

	public void write(final byte b) throws IOException {
		if (os == null) {
			throw new UnsupportedOperationException("not an output stream "
					+ description);
		}
		os.write(b);
	}

	public void write(final byte[] b, final int offset, final int len)
			throws IOException {
		if (os == null) {
			throw new UnsupportedOperationException("not an output stream "
					+ description);
		}
		os.write(b, offset, len);
	}

	public void write(final String text) throws IOException {
		if (w == null) {
			w = new OutputStreamWriter(os);
		}
		w.write(text);
		w.flush();
	}

	public void write(final char[] buffer, final int offset, final int length)
			throws IOException {
		if (w == null) {
			w = new OutputStreamWriter(os);
		}
		w.write(buffer, offset, length);
		w.flush();
	}
}
