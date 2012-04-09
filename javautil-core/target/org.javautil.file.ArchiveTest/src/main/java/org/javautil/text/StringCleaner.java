package org.javautil.text;

public interface StringCleaner {

	/**
	 * Take action on a String to remove or escape objectionable characters
	 * 
	 * @param in
	 * @return
	 */
	public String clean(String in);
}
