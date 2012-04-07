package org.javautil.text;

public class TokenizingException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int index;
	private int column;

	public TokenizingException(final String msg, final int index) {
		super(msg);
		this.index = index;
	}

	public TokenizingException(final String string) {
		super(string);
	}

	public int getIndex() {
		return index;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(final int column) {
		this.column = column;
	}
}
