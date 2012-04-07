package org.javautil.dataset;

/**
 * 
 * @author jjs@javautil.org
 * 
 */

public class DatasetOperationException extends DatasetException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6031802856309252203L;

	public DatasetOperationException(final String message) {
		super(message);
	}

	public DatasetOperationException(final String message, final Throwable t) {
		super(message, t);
	}

	public DatasetOperationException(final Throwable t) {
		super(t);
	}
}
