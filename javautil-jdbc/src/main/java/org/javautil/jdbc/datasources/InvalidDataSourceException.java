package org.javautil.jdbc.datasources;

import java.sql.SQLException;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class InvalidDataSourceException extends RuntimeException {

	private static final long serialVersionUID = -3148528581300872087L;

	private String message;
	private Exception thrown;

	public InvalidDataSourceException(final Exception e) {
		super(e);
	}

	public InvalidDataSourceException(final String msg) {
		super(msg);
	}

	public InvalidDataSourceException(final SQLException sqe, final String msg) {
		super(sqe);
		this.thrown = sqe;
		this.message = msg;
	}

	@Override
	public String getMessage() {
		String retval = null;
		if (message != null) {
			retval = message + thrown.getMessage();
		} else {
			retval = super.getMessage();
		}
		return retval;
	}
}
