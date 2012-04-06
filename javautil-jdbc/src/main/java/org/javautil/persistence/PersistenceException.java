package org.javautil.persistence;

import java.sql.SQLException;

/**
 * <p>
 * Title: Persistence Exception
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Diamond-Erp.com
 * </p>
 * 
 * @author jim schmidt
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PersistenceException extends Exception {
	private static final String newline = System.getProperty("line.separator");
	private SQLException sqlException;
	private String sqlText;

	public PersistenceException(final Exception rootCause) {
		super(rootCause);
	}

	public PersistenceException(final String message, final Exception rootCause) {
		super(message, rootCause);
	}

	public PersistenceException(final String string) {
		super(string);
	}

	public PersistenceException(final SQLException sqe, final String sqlText) {
		this.sqlException = sqe;
		this.sqlText = sqlText;
	}

	@Override
	public String getMessage() {
		String retval;
		if (sqlException != null) {
			retval = sqlException.getMessage() + newline + sqlText;
		} else {
			retval = super.getMessage();
		}
		return retval;
	}
}