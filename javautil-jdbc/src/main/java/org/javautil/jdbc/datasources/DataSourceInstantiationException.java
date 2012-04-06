package org.javautil.jdbc.datasources;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class DataSourceInstantiationException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataSourceInstantiationException(final String message) {
		super(message);
	}

	public DataSourceInstantiationException(final Exception e) {
		super(e);
	}
}
