package org.javautil.lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class ExceptionWriter implements ExceptionHandler {

	private Writer writer;

	public ExceptionWriter(File file) {
		if (file == null) {
			throw new IllegalArgumentException("file is null");
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
		writer = new OutputStreamWriter(fos);
	}

	public ExceptionWriter(Writer writer) {
		if (writer == null) {
			throw new IllegalArgumentException("writer is null");
		}
	}

	@Override
	public void handleException(Exception exception) {
		handleException(exception, true);
	}

	@Override
	public void handleException(Exception exception, boolean printStackTrace) {
		if (writer == null) {
			throw new IllegalStateException("writer is null");
		}
		try {
			if (printStackTrace) {
				String message = ExceptionHelper.asString(exception);
				writer.write(message);
				writer.write("\n");
				writer.flush();
			} else {
				writer.write(exception.toString());
				writer.write("\n");
				writer.flush();
			}
		} catch (IOException e) {
			throw new ExceptionHandlerException(e);
		}

	}

	public void setWriter(Writer writer) {
		if (writer == null) {
			throw new IllegalArgumentException("writer is null");
		}
		this.writer = writer;
	}

	@Override
	public void dispose() {
		try {
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		writer = null;
	}

}
