package org.javautil.lang;

public interface ExceptionHandler {
	public void handleException(Exception exception);

	/**
	 * Release any resources held.
	 * @throws Exception 
	 */
	void dispose();

	void handleException(Exception exception, boolean printStackTrace);
}
