package org.javautil.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class WriterSet extends Writer {

	private String name = "setName() not called";

	private Set<WriterWrapper> writers = new HashSet<WriterWrapper>();

	private WriterWrapper[] writersArray = new WriterWrapper[0];

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private long writeCount;

	private long charsWritten = 0;
	private long bytesWritten = 0;

	//
	// Constructors
	//
	public WriterSet() {

	}

	public WriterSet(final String name) {
		setName(name);
	}

	public WriterSet(final OutputStream w, final String descr) {
		addWriter(w, descr, true);
	}

	public WriterSet(final Set<WriterWrapper> writers) {
		this.writers = writers;
		setArray();
	}

	//
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("bytes written " + bytesWritten);
		b.append("chars written " + charsWritten);
		return b.toString();
	}

	private void setArray() {
		writersArray = writers.toArray(new WriterWrapper[0]);
	}

	public synchronized void addOrReplaceFileWriter(final OutputStream os,
			final File newFile, final boolean pretty) {
		final Iterator<WriterWrapper> it = writers.iterator();
		boolean replaced = false;
		while (it.hasNext()) {
			final WriterWrapper ww = it.next();
			if (ww.getFile() != null) {
				ww.setFile(newFile);
				replaced = true;
				break;
			}
		}
		if (!replaced) {
			addWriterWrapper(new WriterWrapper(os, newFile, pretty));
		}

	}

	public synchronized void addWriter(final OutputStream os,
			final String newFileName, final boolean pretty) {
		addWriterWrapper(new WriterWrapper(os, newFileName, pretty));

	}

	public synchronized void addWriter(final OutputStream os,
			final String newFileName, final boolean pretty,
			final long fileSetMask) {
		addWriterWrapper(new WriterWrapper(os, newFileName, pretty, fileSetMask));
	}

	public synchronized void addWriterWrapper(final WriterWrapper wrapper) {
		writers.add(wrapper);
		setArray();
	}

	public synchronized void clear() {
		writers.clear();
		setArray();
	}

	@Override
	public void close() throws IOException {
		for (final WriterWrapper writer : writers) {
			writer.flush();
			writer.close();
		}
		writers.clear();
		setArray();
	}

	@Override
	public void flush() throws IOException {
		for (final WriterWrapper writer : writers) {
			writer.flush();
			// logger.info("flushing " + writer.getDescription());
		}
	}

	public String getWriterDescriptions() {
		String returnValue = null;
		if (writers.size() > 0) {
			final StringBuilder b = new StringBuilder();
			b.append("writers:\n");
			for (final WriterWrapper writer : writers) {
				b.append("  ");
				b.append(writer.getDescription());
				b.append("\n");
			}
			returnValue = b.toString();
		} else {
			returnValue = "no writers";
		}
		return returnValue;
	}

	// public Iterator<WriterWrapper> iterator() {
	// return writers.iterator();
	// }

	public WriterWrapper[] getWriters() {
		return writersArray;
	}

	public void remove(final WriterWrapper wrapper) {
		writers.remove(wrapper);
		setArray();
	}

	public int size() {
		return writers.size();
	}

	public long getWriteCount() {
		return writeCount;
	}

	private void addDefaultWriter(final long mask) {

		if (writers.size() == 0) {
			logger.debug("no writer specified adding stdout");

			addWriterWrapper(new WriterWrapper(System.out, "stdout", true, mask));

		}
	}

	//
	//
	//
	public void write(final byte b, final long setMask) throws IOException {
		writeCount++;
		for (final WriterWrapper writer : writers) {
			final long mask = writer.getWriterSetMask();
			if (setMask > 0 || mask > 0) {

				if ((mask & setMask) > 0) {
					writer.getWriter().write(b);
				}
			} else {
				writer.getWriter().write(b);
			}

		}
		bytesWritten++;
	}

	public void write(final byte[] buffer, final int length) throws IOException {
		write(buffer, length, 0L);
	}

	public void write(final byte[] buffer, final int length, final long setMask)
			throws IOException {
		// checkNoWriters();
		// String x = new String(buffer);
		writeCount++;
		WriterWrapper currentWrapper = null;
		try {
			for (final WriterWrapper writer : writersArray) {
				currentWrapper = writer;
				final long mask = writer.getWriterSetMask();
				if (setMask > 0 || mask > 0) { // @todo should move to this
												// test to
					// a function

					if ((mask & setMask) > 0) {
						writer.getWriter().write(buffer, 0, length);
					}
				} else {
					writer.getWriter().write(buffer, 0, length);
				}

			}
		} catch (final IOException e) {
			e.printStackTrace();
			// exception = e;
			final String message = "while writing to "
					+ currentWrapper.getDescription() + e.getMessage();
			logger.error(message);
			throw new IOException(message);
		}
		bytesWritten += length;
	}

	public void write(final String text, final long setMask) throws IOException {
		writeCount++;
		if (writers.size() == 0) {
			addDefaultWriter(setMask);
		}

		for (final WriterWrapper writer : writersArray) {
			final long mask = writer.getWriterSetMask();
			if (setMask > 0 && mask > 0) {
				if ((mask & setMask) > 0) {
					writer.write(text);
					writer.flush();
				}

			} else {
				writer.write(text);
				writer.flush();
			}
		}
		charsWritten += text.length();
	}

	@Override
	public void write(final String text) throws IOException {
		write(text, 0L);
	}

	@Override
	public void write(final char[] buffer, final int off, final int length)
			throws IOException {
		writeCount++;
		for (final WriterWrapper writer : writersArray) {
			writer.write(buffer, off, length);
		}
		charsWritten += length;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
