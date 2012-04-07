package org.javautil.util;

public class EnvironmentProperty {

	public static String getProperty(final String propertyName) {
		String retval = null;

		retval = System.getProperty(propertyName);

		if (retval == null) {
			retval = System.getenv(propertyName);
		}
		return retval;
	}

	public static String getMandatoryProperty(final String propertyName) {
		final String retval = getProperty(propertyName);

		if (retval == null && propertyName != null) {
			throw new RuntimeException("can't find property " + propertyName);
		}
		return retval;
	}

	public static String getDefaultProperty(final String propertyName,
			final String defaultValue) {
		String retval = getProperty(propertyName);
		if (retval == null) {
			retval = defaultValue;
		}
		return retval;
	}

}
