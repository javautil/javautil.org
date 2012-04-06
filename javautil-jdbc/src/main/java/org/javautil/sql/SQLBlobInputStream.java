package org.javautil.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * 
 * @author bcm
 * @author jjs at dbexperts dot com
 * 
 */
public class SQLBlobInputStream extends InputStream {

	private Blob blob = null;

	private InputStream inputStream = null;

	public SQLBlobInputStream(final Blob blob) {
		if (blob == null) {
			throw new IllegalArgumentException("blob is null");
		}
		this.blob = blob;
	}

	private void prepareInputStream() {
		if (inputStream == null) {
			try {
				inputStream = blob.getBinaryStream();
			} catch (final SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void closeInputStream() {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
			inputStream = null;
		}
	}

	@Override
	public int available() throws IOException {
		prepareInputStream();
		return inputStream.available();
	}

	@Override
	public void close() throws IOException {
		prepareInputStream();
		inputStream.close();
	}

	@Override
	public synchronized void mark(final int readlimit) {
		prepareInputStream();
		inputStream.mark(readlimit);
	}

	@Override
	public boolean markSupported() {
		prepareInputStream();
		return inputStream.markSupported();
	}

	@Override
	public int read() throws IOException {

		prepareInputStream();

		return inputStream.read();
	}

	@Override
	public int read(final byte[] b, final int off, final int len)
			throws IOException {
		prepareInputStream();
		return inputStream.read(b, off, len);
	}

	@Override
	public int read(final byte[] b) throws IOException {
		prepareInputStream();
		return inputStream.read(b);
	}

	@Override
	public synchronized void reset() throws IOException {
		closeInputStream();
		prepareInputStream();
	}

	@Override
	public long skip(final long n) throws IOException {
		prepareInputStream();
		return inputStream.skip(n);
	}
}
