package org.javautil.text;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class StringBuilderHelper {
	private StringBuilder b = new StringBuilder();
	private final String newline = System.getProperty("line.separator");
	private String separator = ": ";
	private String pairSeparator = newline;

	public StringBuilderHelper() {

	}

	public StringBuilderHelper(final StringBuilder val) {
		b = val;
	}

	public void addNameValue(final String name, final boolean value) {
		b.append(name);
		b.append(separator);
		b.append(value);
		b.append(pairSeparator);
	}

	public void addNameValue(final String name, final int value) {
		b.append(name);
		b.append(separator);
		b.append(value);
		b.append(pairSeparator);
	}

	public void addNameValue(final String name, final Object value) {
		b.append(name);
		b.append(separator);
		if (value == null) {
			b.append("null");
		} else {
			b.append("'");
			b.append(value);
			b.append("'");
		}
		b.append(pairSeparator);
	}

	public StringBuilder getStringBuilder() {
		return b;
	}

	public void setPairSeparator(final String val) {
		pairSeparator = val;
	}

	public void setValueSeparator(final String val) {
		separator = val;
	}

	@Override
	public String toString() {
		return b.toString();
	}
}
