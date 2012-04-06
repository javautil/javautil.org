package org.javautil.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class StringFileWriter {

	private static final Logger logger = Logger
			.getLogger(StringFileWriter.class);

	static String getDirectory(String directory, String packageName) {
		String classPath = packageName.replace(".", "/");
		String pathName = directory + "/" + classPath;
		File path = new File(pathName);
		boolean createdDir = path.mkdirs();
		if (logger.isDebugEnabled()) {
			if (createdDir) {
				logger.debug("created " + pathName);
			} else {
				logger.debug("did not create: " + pathName);
			}
		}
		return pathName;
	}

	static File getFile(String directory, String packageName, String className) {
		String filePath = getDirectory(directory, packageName) + "/"
				+ className + ".java";
		File path = new File(filePath);
		return path;
	}

	/**
	 * Writes to a file with the name ".java" appended to the className in a
	 * derived path.
	 * 
	 * The path is derived by constructing a directory name from the package
	 * name located under the specified directory, creating all intermediate
	 * directories as necessary.
	 * 
	 * @param directory
	 *            source directory
	 * @param packageName
	 *            name of the package in normal java "." notation
	 * @param className
	 * @param string
	 * @throws IOException
	 */
	public static void writeClassAsPath(String directory, String packageName,
			String className, String string) throws IOException {
		File path = getFile(directory, packageName, className);
		write(path, string);
	}

	public static void write(String directory, Class<?> clazz, String string)
			throws IOException {
		String pathName = directory + "/" + clazz.getName();
		write(pathName, string);
	}

	public static void write(String directory, String fileName, String string)
			throws IOException {
		String pathName = directory + "/" + fileName;
		write(new File(pathName), string);
	}

	public static void write(String fileName, String string) throws IOException {
		write(new File(fileName), string);
	}

	public static void write(File file, String string) throws IOException {
		FileWriter fw = new FileWriter(file);
		fw.write(string);
		fw.flush();
		fw.close();
	}
}
