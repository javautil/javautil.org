package org.javautil.text;

import org.javautil.lang.SystemProperties;

/**
 * Support for creating a multiple line String with indentation.
 * 
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class MultiLineStringBuilder {
	private StringBuilder sb = new StringBuilder();
	private int indentLevel = 0;
	private String indentPad = "\t";

	private String newline = SystemProperties.newline;

	/**
	 * Noargs bean constructor.
	 */
	public MultiLineStringBuilder() {
	}

	/**
	 * 
	 * @param initialBufferSize
	 *            expected output size in byte
	 */
	public MultiLineStringBuilder(final int initialBufferSize) {
		sb = new StringBuilder(initialBufferSize);
	}

	/**
	 * 
	 * @return the String used to pad see {@link #indentLevel}
	 */
	public String getIndentPad() {
		return indentPad;
	}

	/**
	 * Set the indentation padding String
	 * 
	 * Each line is prepended by this string indent level see
	 * 
	 * @param indentPad
	 */
	public void setIndentPad(final String indentPad) {
		this.indentPad = indentPad;
	}

	public int getIndentLevel() {
		return indentLevel;
	}

	public String getNewline() {
		return newline;
	}

	public void setNewline(final String newline) {
		this.newline = newline;
	}

	public MultiLineStringBuilder appendLine(final String line) {
		leftPad();
		sb.append(line);
		sb.append(newline);
		return this;
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	private void leftPad() {
		for (int i = 0; i < indentLevel; i++) {
			sb.append(indentPad);
		}
	}

	public MultiLineStringBuilder indent() {
		indentLevel++;
		return this;
	}

	public MultiLineStringBuilder outdent() {
		if (indentLevel < 0) {
			throw new IllegalStateException("not indenting");
		}
		indentLevel--;
		return this;
	}

}
