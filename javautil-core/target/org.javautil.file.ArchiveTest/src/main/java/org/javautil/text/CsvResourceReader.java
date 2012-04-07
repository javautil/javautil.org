package org.javautil.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvResourceReader {

	private final InputStream is;

	private final BufferedReader reader;

	public CsvResourceReader(final Class referenceClass,
			final String resourceName) {
		if (referenceClass == null) {
			throw new IllegalArgumentException("referenceClass is null");
		}
		if (resourceName == null) {
			throw new IllegalArgumentException("resourceName is null");
		}
		is = referenceClass.getResourceAsStream(resourceName);
		if (is == null) {
			throw new IllegalArgumentException("unable to get resource "
					+ resourceName + " from "
					+ referenceClass.getCanonicalName());
		}
		reader = new BufferedReader(new InputStreamReader(is));
	}

	public void close() throws IOException {

		is.close();
	}

	public List<String> read() {

		String in;
		final CSVTokenizer tokenizer = new CSVTokenizer();
		ArrayList<String> tokens = null;
		try {
			if ((in = reader.readLine()) != null) {
				tokens = tokenizer.parse(in);

			} else {
				is.close();
			}
		} catch (final TokenizingException e) {
			throw new RuntimeException(e);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return tokens;
	}
}
