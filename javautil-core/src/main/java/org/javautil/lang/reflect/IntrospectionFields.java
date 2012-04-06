package org.javautil.lang.reflect;

import java.util.HashMap;

public class IntrospectionFields {

	private final HashMap<String, IntrospectedFieldHelper> fields = new HashMap<String, IntrospectedFieldHelper>();

	// TOOD Finish this
	public void populateFields(Class clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("clazz is null");
		}
		clazz.getDeclaredFields();
	}
}
