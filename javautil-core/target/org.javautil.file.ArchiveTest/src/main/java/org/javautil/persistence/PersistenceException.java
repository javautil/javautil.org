package org.javautil.persistence;

import java.sql.SQLException;

public class PersistenceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersistenceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public PersistenceException(final SQLException e) {
		super(e);
	}

}
