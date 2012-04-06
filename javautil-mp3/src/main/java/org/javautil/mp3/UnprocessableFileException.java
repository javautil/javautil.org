package org.javautil.mp3;

public class UnprocessableFileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8004606394674555560L;

	public UnprocessableFileException() {
		super();
	}

	public UnprocessableFileException(final String message,
			final Throwable cause) {
		super(message, cause);
	}

	public UnprocessableFileException(final String message) {
		super(message);
	}

	public UnprocessableFileException(final Throwable cause) {
		super(cause);
	}

}
