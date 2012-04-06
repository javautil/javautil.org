package org.javautil.properties;

import java.util.Map;
import java.util.Properties;

public class PropertiesHelper {
	private static final String newline = System.getProperty("line.separator");

	@SuppressWarnings("unchecked")
	public static String asText(final String label, final Properties p) {

		final StringBuilder sb = new StringBuilder();
		if (label != null) {
			sb.append(label);
			sb.append(newline);
		}

		if (p == null) {
			sb.append("property is null" + newline);
		} else {
			for (final Map.Entry es : p.entrySet()) {
				sb.append(es.getKey());
				sb.append(": ");
				sb.append(es.getValue());
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
