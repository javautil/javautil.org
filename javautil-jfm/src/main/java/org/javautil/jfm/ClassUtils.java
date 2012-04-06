package org.javautil.jfm;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassUtils {

	private static Log logger = LogFactory.getLog(ClassUtils.class);

	public static boolean isJavaStandardEditionOrExtensionClass(
			final Class<? extends Object> clazz) {
		final Package pkg = clazz.getPackage();
		final String nm = pkg == null ? null : pkg.getName();
		return (pkg != null && (nm.startsWith("java.") || nm
				.startsWith("javax.")));
	}

	public static Class<? extends Object> loadClassFromFile(final File file) {
		final String fn = file.getName().toLowerCase();
		if (!fn.endsWith(".java") && !fn.endsWith(".class")) {
			logger.warn("file \"" + file.getName()
					+ "\" does not end with .java or .class");
		}
		final String packageName = null;
		final String className = FilenameUtils.getBaseName(file.getName());
		return loadClassFromFile(file, packageName, className);
	}

	public static Class<? extends Object> loadClassFromFile(final File file,
			final String packageName, final String className) {
		URL url;
		try {
			url = file.toURI().toURL();
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		}
		final URL[] urls = new URL[] { url };
		final ClassLoader cl = new URLClassLoader(urls);
		try {
			String fullClassName = className;
			if (packageName != null) {
				fullClassName = packageName + "." + className;
			}
			return cl.loadClass(fullClassName);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException("class not found using url " + url
					+ " to load classes", e);
		}
	}
}
