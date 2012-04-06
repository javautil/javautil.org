package org.javautil.jdbc.metadata;

import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "org.javautil.jdbc.metadata.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	public static String getString(final String key) {

		return RESOURCE_BUNDLE.getString(key);

	}

	private Messages() {
	}
}
