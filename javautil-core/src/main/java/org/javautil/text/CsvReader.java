package org.javautil.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.javautil.dataset.DataType;

public class CsvReader {
	private final BufferedReader reader;

	private final CSVTokenizer tokenizer = new CSVTokenizer();

	private List<DataType> datatypes;

	public CsvReader(final InputStream is) {
		if (is == null) {
			throw new IllegalArgumentException("is is null");
		}
		final InputStreamReader sr = new InputStreamReader(is);
		reader = new BufferedReader(sr);
	}

	public List<String> readLine() throws IOException {

		final String line = reader.readLine();
		List<String> retval = null;
		if (line != null) {
			retval = tokenizer.parse(line);
		}
		return retval;
	}

	// TODO document
	public List<Object> readLineAsObjects() throws IOException {
		if (datatypes == null) {
			throw new IllegalStateException(
					"must be preceded by call to setDatatypes");
		}
		final List<String> stringValues = readLine();
		List<Object> values = null;
		if (stringValues != null) {
			values = new ArrayList<Object>(stringValues.size());
			for (int i = 0; i < stringValues.size(); i++) {
				final String s = stringValues.get(i);
				values.add(datatypes.get(i).coerceString(s));
			}
		}
		return values;
	}

	/**
	 * @return the datatypes
	 */
	public List<DataType> getDatatypes() {
		return datatypes;
	}

	/**
	 * @param datatypes
	 *            the datatypes to set
	 */
	public void setDatatypes(final List<DataType> datatypes) {
		this.datatypes = datatypes;
	}
	
	public void close() throws IOException {
		reader.close();
	}
}
