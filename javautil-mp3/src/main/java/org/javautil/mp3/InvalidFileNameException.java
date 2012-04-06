package org.javautil.mp3;

public class InvalidFileNameException extends UnprocessableFileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8004606394674555560L;

	public InvalidFileNameException() {
		super();
	}

	public InvalidFileNameException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidFileNameException(final String message) {
		super(message);
	}

	public InvalidFileNameException(final Throwable cause) {
		super(cause);
	}

}
