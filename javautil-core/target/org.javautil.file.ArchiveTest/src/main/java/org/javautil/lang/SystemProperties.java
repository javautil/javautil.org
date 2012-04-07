package org.javautil.lang;

import java.util.Map.Entry;
import java.util.Properties;

public class SystemProperties {
	public static final String newline = System.getProperty("line.separator");

	public static String props() {
		final Properties p = System.getProperties();
		final StringBuilder b = new StringBuilder();

		for (final Entry prop : p.entrySet()) {
			b.append(prop.getKey());
			b.append(": ");
			b.append(prop.getValue());
			b.append(newline);
		}
		return b.toString();
	}

	private static String getProperty(final String propertyName) {
		return System.getProperty(propertyName);
	}

	public static String getJavaRuntimeName() {
		return getProperty("java.runtime.name");
	}

	public static String getPathSeparator() {
		return getProperty("path.separator");
	}

	public static String getUserCountry() {
		return getProperty("user.country");
	}

	public static String getUserDir() {
		return getProperty("user.dir");
	}

	public static String getTempDirectory() {
		return getProperty("java.io.tmpdir");
	}

	public static String getLineSeparator() {
		return getProperty("line.separator");
	}

	public static String getUserHome() {
		return getProperty("user.home");
	}

	public static String getFileSeparator() {
		return getProperty("file.separator");
	}

	// public static void main(String[] args) {
	// SystemProperties sp = new SystemProperties();
	// System.out.println(sp.props());
	// }
}
