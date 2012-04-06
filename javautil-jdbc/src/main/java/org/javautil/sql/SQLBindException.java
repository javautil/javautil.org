package org.javautil.sql;

/**
 * Thrown prior to a SQL statement bind when it would would fail. This happends
 * when the bind value or name is inconsistent with the sql bind variables and
 * the bind laxness settings.
 * 
 * @author bcm
 */
public class SQLBindException extends RuntimeException {

	public SQLBindException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SQLBindException(final String message) {
		super(message);
	}
}
