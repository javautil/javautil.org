package org.javautil.util;

import java.util.List;
import java.util.Map;

public class JavaScriptUtils {

	@SuppressWarnings("unchecked")
	public static String asConstructor(final String clazz,
			final List<Object> objects) {
		final StringBuilder s = new StringBuilder();
		s.append("new " + clazz + "(");
		int index = 0;
		for (final Object object : objects) {
			if (index > 0) {
				s.append(",\n\t");
			}
			if (object != null && Map.class.isAssignableFrom(object.getClass())) {
				s.append(asJSON((Map<String, String>) object));
			} else {
				s.append(String.valueOf(object));
			}
			index++;
		}
		s.append(")");
		return s.toString();
	}

	public static String asJSON(final Map<String, String> options) {
		final StringBuilder s = new StringBuilder();
		s.append("{ ");
		int index = 0;
		for (final String key : options.keySet()) {
			if (index > 0) {
				s.append(",\n");
			}
			s.append(asString(key));
			s.append(" : " + options.get(key));
			index++;
		}
		s.append(" }");
		return s.toString();
	}

	public static String asString(final String text) {
		final StringBuilder s = new StringBuilder();
		s.append("'");
		s.append(text);
		s.append("'");
		return s.toString();
	}

}
