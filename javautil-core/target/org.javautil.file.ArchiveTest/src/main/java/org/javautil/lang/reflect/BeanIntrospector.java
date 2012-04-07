package org.javautil.lang.reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author bcm
 * 
 */

public class BeanIntrospector {

	private Object hibernateBean = null;

	private final Map<String, IntrospectionHelper> helpers = new HashMap<String, IntrospectionHelper>();

	public BeanIntrospector(final Object hibernateBean) {
		this.hibernateBean = hibernateBean;
	}

	public Object getBeanAttribute(final String attributeName) {
		return getHelper(attributeName).invokeGetter();
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T getBeanAttribute(final Class<T> clazz,
			final String attributeName) {
		final IntrospectionHelper helper = getHelper(attributeName);
		final Object _result = helper.invokeGetter();
		T result = null;
		if (clazz.isAssignableFrom(_result.getClass())) {
			result = (T) _result;
		} else {
			throw new IllegalArgumentException("class "
					+ _result.getClass().getName() + " is not assignable from "
					+ clazz.getName());
		}
		return result;
	}

	public void setBeanAttribute(final String attributeName,
			final Object attributeValue) {
		final IntrospectionHelper helper = getHelper(attributeName);
		helper.setPropertyType(attributeValue == null ? null : attributeValue
				.getClass());
		helper.invokeSetter(attributeValue);
	}

	private IntrospectionHelper getHelper(final String attributeName) {
		IntrospectionHelper helper = helpers.get(attributeName);
		if (helper == null) {
			helper = new IntrospectionHelper(hibernateBean, attributeName);
		}
		return helper;
	}

}
