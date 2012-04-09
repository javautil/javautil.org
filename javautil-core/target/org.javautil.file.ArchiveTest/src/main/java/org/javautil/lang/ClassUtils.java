package org.javautil.lang;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.logging.Events;
import org.javautil.text.StringUtils;

/**
 * This class can be used to obtain a list of all classes in a classpath.
 * 
 * based on: http://xmlizer.biz/java/classloader/ClassList.java
 * 
 * <em>Caveat:</em> When used in environments which utilize multiple class
 * loaders--such as a J2EE Container like Tomcat--it is important to select the
 * correct classloader otherwise the classes returned, if any, will be
 * incompatible with those declared in the code employing this class lister. to
 * get a reference to your classloader within an instance method use:
 * <code>this.getClass().getClassLoader()</code> or
 * <code>Thread.currentThread().getContextClassLoader()</code> anywhere else
 * <p>
 * 
 * @author bcm, Kris Dover<krisdover@hotmail.com>
 * 
 */
public abstract class ClassUtils {

	private static Log logger = LogFactory.getLog(ClassUtils.class);

	/**
	 * Searches the classpath for all resources matching a specified search
	 * criteria, returning them in a map keyed with the interfaces they
	 * implement or null if they have no interfaces. The search criteria can be
	 * specified via interface, package and jar name filter arguments
	 * <p>
	 * 
	 * @param classLoader
	 *            The classloader whose classpath will be traversed
	 * @param interfaceFilter
	 *            A Set of fully qualified interface names to search for or null
	 *            to return classes implementing all interfaces
	 * @param packageFilter
	 *            A Set of fully qualified package names to search for or or
	 *            null to return classes in all packages
	 * @param jarFilter
	 *            A Set of jar file names to search for or null to return
	 *            classes from all jars
	 * @return A Map of a Set of Classes keyed to their interface names or a
	 *         filename with a null value when the extension filter does not
	 *         include .class
	 * 
	 * @throws ClassNotFoundException
	 *             if the current thread's classloader cannot load a requested
	 *             class for any reason
	 */
	public static Map<String, Set<Object>> findResources(
			final ClassLoader classLoader, final String extension,
			final String pathFilter, final Set<String> packageFilter,
			final Set<String> jarFilter) throws ClassNotFoundException {

		final String _extension = extension != null ? extension.toLowerCase()
				: null;

		final Map<String, Set<Object>> resultTable = new HashMap<String, Set<Object>>();
		Object[] classPaths;
		try {
			// get a list of all classpaths
			classPaths = ((java.net.URLClassLoader) classLoader).getURLs();
		} catch (final ClassCastException cce) {
			// or cast failed; tokenize the system classpath
			classPaths = System.getProperty("java.class.path", "").split(
					File.pathSeparator);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("classPaths:\n" + ArrayHelper.asString(classPaths));
		}

		for (final Object classPath2 : classPaths) {
			Enumeration<? extends Object> files = null;
			JarFile module = null;
			// for each classpath ...
			File classPath = new File(
					(URL.class).isInstance(classPath2) ? ((URL) classPath2)
							.getFile() : classPath2.toString());
			classPath = new File(classPath.getPath().replaceAll("\\%20", " "));
			if (classPath.isDirectory() && jarFilter == null) { // is our
				// classpath a directory and jar filters are not active?
				final List<String> dirListing = new ArrayList<String>();
				// get a recursive listing of this classpath
				recursivelyListDir(dirListing, classPath, new StringBuffer());
				// an enumeration wrapping our list of files
				files = Collections.enumeration(dirListing);
			} else if (classPath.getName().endsWith(".jar")) { // is our
				// classpath a jar? skip any jars not list in the filter
				if (jarFilter != null
						&& !jarFilter.contains(classPath.getName())) {
					continue;
				}
				try {
					// if our resource is a jar, instantiate a jarfile using the
					// full path to resource
					module = new JarFile(classPath);
				} catch (final MalformedURLException mue) {
					logger.warn("Bad classpath. Error: " + mue.getMessage());
				} catch (final IOException io) {
					logger.warn("jar file '"
							+ classPath.getName()
							+ "' could not be instantiate from file path. Error: "
							+ io.getMessage());
				}
				// get an enumeration of the files in this jar
				files = module.entries(); // todo jjs fix -- module could be
				// null at this point
			}

			// for each file path in our directory or jar
			while (files != null && files.hasMoreElements()) {
				// get each fileName
				final String fileName = files.nextElement().toString();
				if (pathFilter == null || fileName.startsWith(pathFilter)) {
					// we only want files of the matching extension
					if (_extension == null
							|| fileName.toLowerCase()
									.endsWith("." + _extension)) {
						if (_extension != null && "class".equals(_extension)) {
							if (!populateClasses(classLoader, resultTable,
									fileName, packageFilter)) {
								continue;
							}
						} else {
							resultTable.put(fileName, null);
						}
					}
				}
			}

			// close the jar if it was used
			if (module != null) {
				try {
					module.close();
				} catch (final IOException ioe) {
					logger.warn("The module jar file '" + classPath.getName()
							+ "' could not be closed. Error: "
							+ ioe.getMessage());
				}
			}

		} // end for loop

		if (Events.isRegistered("classpath") && logger.isTraceEnabled()) {
			logger.trace("result table has size of " + resultTable.size()
					+ ", details follow:\n" + StringUtils.asString(resultTable));
		}

		return resultTable;
	} // end method

	/**
	 * Recursively lists a directory while generating relative paths. This is a
	 * helper function for findClasses. Note: Uses a StringBuffer to avoid the
	 * excessive overhead of multiple String concatentation
	 * 
	 * @param dirListing
	 *            A list variable for storing the directory listing as a list of
	 *            Strings
	 * @param dir
	 *            A File for the directory to be listed
	 * @param relativePath
	 *            A StringBuffer used for building the relative paths
	 */
	private static void recursivelyListDir(final List<String> dirListing,
			final File dir, final StringBuffer relativePath) {
		int prevLen; // used to undo append operations to the StringBuffer

		// if the dir is really a directory
		if (dir.isDirectory()) {
			// get a list of the files in this directory
			final File[] files = dir.listFiles();
			// for each file in the present dir
			for (final File file : files) {
				// store our original relative path string length
				prevLen = relativePath.length();
				// call this function recursively with file list from present
				// dir and relative to appended with present dir
				recursivelyListDir(
						dirListing,
						file,
						relativePath.append(prevLen == 0 ? "" : "/").append(
								file.getName()));
				// delete subdirectory previously appended to our relative path
				relativePath.delete(prevLen, relativePath.length());
			}
		} else {
			// this dir is a file; append it to the relativeto path and add it
			// to the directory listing
			dirListing.add(relativePath.toString());
		}
	}

	protected static boolean populateClasses(final ClassLoader classLoader,
			final Map<String, Set<Object>> classTable, // todo jjs this is not
														// used
			final String fileName, final Set<String> packageFilter)
			throws ClassNotFoundException {

		boolean ret = true;
		// convert our full filename to a fully qualified class name
		final String className = fileName.replaceAll("/", ".").substring(0,
				fileName.length() - 6);

		// debug class list System.out.println(className); skip any classes in
		// packages not explicitly requested in our package filter
		if (packageFilter != null
				&& !packageFilter.contains(className.substring(0,
						className.lastIndexOf(".")))) {
			ret = false;
		} else {
			// get the class for our class name
			Class<? extends Object> theClass = null;
			try {
				theClass = Class.forName(className, false, classLoader);
				// skip interfaces
				if (theClass.isInterface()) {
					ret = false;
				}
			} catch (final NoClassDefFoundError e) {
				System.out.println("Skipping file '" + className
						+ "' for reason " + e.getMessage());
				ret = false;
			}
		}
		return ret;
	}
	
	
}
