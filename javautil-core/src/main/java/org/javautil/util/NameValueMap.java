package org.javautil.util;

import java.util.HashMap;

/**
 * 
 * @author bcm
 */
public class NameValueMap<T> extends HashMap<String, Object> implements
		NameAndValue<T> {

	private static final long serialVersionUID = 2753394193824422153L;

	private static final String KEY_NAME = "name";

	private static final String KEY_VALUE = "value";

	public NameValueMap() {
	}

	public NameValueMap(final String name, final T value) {
		setName(name);
		setValue(value);
	}

	@Override
	public String getName() {
		return (String) get(KEY_NAME);
	}

	@Override
	public void setName(final String name) {
		put(KEY_NAME, name);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) get(KEY_VALUE);
	}

	@Override
	public void setValue(final T value) {
		put(KEY_VALUE, value);
	}

}
