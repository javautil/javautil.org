package org.javautil.util;

public class InvalidEnvironmentException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = -1132272391456941556L;

	public InvalidEnvironmentException(final String environmentVariableName) {
		super("The environment variable '" + environmentVariableName
				+ "' has not been set ");
	}

	public InvalidEnvironmentException(final String environmentVariableName,
			final String message) {
		// environmentVariableName not used at this time
		super(message);
	}
}
