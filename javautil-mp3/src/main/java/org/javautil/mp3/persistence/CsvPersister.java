package org.javautil.mp3.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

import org.javautil.mp3.ArtistSuspect;
import org.javautil.mp3.Mp3;
import org.javautil.mp3.Mp3Metadata;
import org.javautil.mp3.formatter.CsvFormatter;

public class CsvPersister implements Mp3OutputStreamPersistence {
	private final CsvFormatter formatter = new CsvFormatter();

	private Writer writer;

	private final String newline = System.getProperty("line.separator");

	private boolean initted = false;

	public void init() {
		if (!initted) {
			initted = true;

			if (writer == null) {
				throw new IllegalStateException("writer is null");
			}
			final String titles = formatter.getTitles();
			try {
				writer.write(titles);
				writer.write(newline);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void save(final Mp3Metadata mp3data) {
		init();
		final String text = formatter.format(mp3data);
		try {
			writer.write(text);
			writer.write(newline);
		} catch (final IOException e) {
			// TODO should this be a PersistenceException?
			throw new RuntimeException(e);
		}
	}

	@Override
	public void flushAndCommit() {
		try {
			writer.flush();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void dispose() {
		try {
			writer.close();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param writer
	 *            the writer to set
	 */
	public void setWriter(final Writer writer) {
		this.writer = writer;
	}

	public void setOutputFile(final File outputFile) {
		FileOutputStream fos;
		try {

			if (outputFile == null) {
				throw new IllegalArgumentException("outputFile is null");
			}
			fos = new FileOutputStream(outputFile);
		} catch (final FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		final Writer w = new OutputStreamWriter(fos);
		setWriter(w);

	}

	@Override
	public List<Mp3> getByTitle(final String songTitle) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Mp3> getAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ArtistSuspect> getArtistSuspects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsStreamWriter() {
		return true;
	}

	@Override
	public void setOutputStream(final OutputStream os) {
		writer = new OutputStreamWriter(os);

	}

}
