package org.javautil.test;

import org.apache.log4j.Logger;

public class StackTraceUtils {

	public static final String fileSeparator = System
			.getProperty("file.separator");

	private static Logger logger = Logger.getLogger(StackTraceUtils.class);

	/**
	 * todo doc
	 * 
	 * @param ste
	 * @return
	 */
	public static String getResourceMethodPath(final StackTraceElement ste) {

		final String className = ste.getClassName();
		logger.debug(className);
		final String methodName = ste.getMethodName();
		final String retval = className.replace(".", fileSeparator) + "."
				+ methodName;
		return retval;
	}

}
