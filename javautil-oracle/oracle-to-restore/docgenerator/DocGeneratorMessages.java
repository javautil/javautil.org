package com.dbexperts.oracle.docgenerator;

import java.util.ResourceBundle;

public class DocGeneratorMessages {
	private static final String BUNDLE_NAME = "com.dbexperts.oracle.docgenerator.docgenerator"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private DocGeneratorMessages() {
	}

	public static String getString(String key) {
			return RESOURCE_BUNDLE.getString(key);
	}
}
