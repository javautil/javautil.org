package org.javautil.java;

import java.io.File;

import org.apache.log4j.Logger;
import org.javautil.lang.SystemProperties;

/**
 * 
 * @author jjs
 * 
 */

public class JavaFile {
	private final Logger logger = Logger.getLogger(getClass());

	private static final String fileSeparator = SystemProperties
			.getFileSeparator();

	/**
	 * Returns the File that represents the location of the source file.
	 * 
	 * Computes the file path for a qualified class name. If the baseDirectory
	 * is "/tmp" and the packageClassName is "com.disney.rodents.Mouse"
	 * 
	 * This will attempt to create the directory path, whether or not the return
	 * value is "/tmp/com/disney/rodents/Mouse".
	 * 
	 * @param baseDirectory
	 *            the top of the source tree
	 * @param packageClassName
	 * @return File
	 */
	public File getJavaFile(final String baseDirectoryName,
			final String packageName, final String className) {

		final File baseDirectory = new File(baseDirectoryName);
		if (!baseDirectory.exists()) {
			throw new java.lang.IllegalArgumentException("no such directory '"
					+ baseDirectory.getAbsolutePath() + "'");
		}
		final String filePathForClass = packageName.replace(".",
				SystemProperties.getFileSeparator());
		final String sourceFilePath = baseDirectory.getPath() + fileSeparator
				+ filePathForClass + fileSeparator + className + ".java";
		final File retval = new File(sourceFilePath);
		// create the directory
		final File directory = retval.getParentFile();
		directory.mkdirs();

		if (logger.isDebugEnabled()) {
			final StringBuilder b = new StringBuilder();
			b.append("destinationDirectoryRoot "
					+ baseDirectory.getAbsolutePath() + "\n");
			b.append("packageName " + packageName + "\n");
			b.append("sourceFilePath " + sourceFilePath + "\n");
			b.append("retval absolute " + retval.getAbsolutePath() + "\n");
			logger.debug(b.toString());
		}
		return retval;
	}

}
