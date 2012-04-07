package org.javautil.dataset;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
@SuppressWarnings("serial")
public class DatasetException extends RuntimeException {

	public DatasetException(final String message) {
		super(message);
	}

	public DatasetException(final String message, final Throwable t) {
		super(message, t);
	}

	public DatasetException(final Throwable t) {
		super(t);
	}
}
