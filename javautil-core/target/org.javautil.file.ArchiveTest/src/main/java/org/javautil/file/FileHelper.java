package org.javautil.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * 
 * @author jjs
 * 
 */
// todo jjs this has become a dumping ground need to clean up
public class FileHelper {

	private static final Logger logger = Logger.getLogger(FileHelper.class);

	public static String getFileText(final File file) throws IOException {
		return getFileText(file.getCanonicalPath());
	}

	/**
	     *
	     */
	public static String getFileText(final String fileName)
			throws java.io.IOException {
		final File file = new File(fileName);
		final StringBuilder buff = new StringBuilder(
				(int) (file.length() * 1.1));
		final BufferedReader buffFile = new BufferedReader(new FileReader(file));
		if (!file.canRead()) {
			throw new IllegalArgumentException("cannot read file " + fileName
					+ " " + file.getCanonicalPath());
		}
		while (buffFile.ready()) {
			buff.append(buffFile.readLine());
			buff.append("\n");
			// System.out.println("read: " + line);
		}
		buffFile.close();
		return new String(buff);
	}

	public static String getFullPathName(final String leadingPath,
			final Integer nbr) {
		final StringBuilder buff = new StringBuilder();
		buff.append(leadingPath);
		buff.append(getPathName(nbr));
		return new String(buff);
	}

	public static BufferedWriter getJavaSourceWriter(
			final String destinationDirectory, final String packageName,
			final String className) throws IOException {
		final StringBuilder buff = new StringBuilder();
		final File destinationRoot = new File(destinationDirectory);
		if (!destinationRoot.exists()) {
			throw new java.lang.IllegalArgumentException("no such directory '"
					+ destinationDirectory + "'");
		}
		buff.append(destinationDirectory);
		final String[] directories = packageName.split("\\.");
		for (final String component : directories) {
			buff.append("/");
			buff.append(component);
		}
		final String pathName = buff.toString();
		final File directory = new File(pathName);
		directory.mkdirs();
		final String fileName = pathName + "/" + className + ".java";
		final File outputFile = new File(fileName);
		final BufferedWriter returnValue = new BufferedWriter(new FileWriter(
				outputFile));
		return returnValue;
	}

	public static String getPathForFileName(final String fileName) {
		final int lastVirgule = fileName.lastIndexOf("/");
		return fileName.substring(0, lastVirgule);
	}

	/**
	 * construct a path name from a surrogate key by recursively creating paths
	 * based on a substring of the number.
	 * 
	 * This results in a path with no more than 100 immediate subdirectories.
	 */
	public static String getPathName(final Integer nbr) {
		{
			final StringBuilder buff = new StringBuilder();
			String prefix = null;
			String suffix = nbr.toString();
			while (suffix.length() > 2) {
				prefix = suffix.substring(0, 2);
				buff.append(prefix + "/");
				suffix = suffix.substring(2);
			}
			buff.append(nbr.toString());
			return new String(buff);
		}
	}

	public static ArrayList<String> getStringArrayList(final File file)
			throws java.io.IOException {
		final ArrayList<String> list = new ArrayList<String>();
		String line = null;
		final BufferedReader buffFile = new BufferedReader(new FileReader(file));
		while ((line = buffFile.readLine()) != null) {
			list.add(line);
		}
		buffFile.close();
		return list;
	}

	/**
	 * obtain a lock on a file and block until lock is obtained.
	 */
	public static void lock(final File file) throws java.io.IOException {
		new FileInputStream(file).getChannel().lock();
		// FileLock lock = channel.lock();
	}

	/**
	 * Assume the name
	 * 
	 * @param file
	 */
	public static boolean makePathForFileName(final String fileName) {
		final Logger logger = Logger.getLogger(FileHelper.class.getName());
		final String path = getPathForFileName(fileName);
		final File dirFile = new File(path);
		final boolean worked = dirFile.mkdirs();
		if (worked) {
			logger.info("made directory " + path);
		}
		return dirFile.mkdirs();
	}

	public static File getTmpDir() {
		final String propertyName = "java.io.tmpdir";
		final String tmpPath = System.getProperty(propertyName);
		final File f = new File(tmpPath);
		final String dirName = f.getAbsolutePath();
		if (!f.isDirectory()) {
			throw new IllegalArgumentException(propertyName + " '" + dirName
					+ "' is not a directory");
		}
		if (!f.canWrite()) {
			throw new IllegalArgumentException(propertyName
					+ " cannot write to " + dirName);
		}
		return f;
	}

	public static File getWriteableFile(final File directory,
			final String fileName) throws IOException {
		if (directory == null) {
			throw new java.io.IOException("directory is null");
		}
		if (fileName == null) {
			throw new java.io.IOException("fileName is null");
		}
		if (!directory.isDirectory()) {
			throw new java.io.IOException("directory: '" + directory
					+ "' is not a directory");
		}
		if (!directory.canWrite()) {
			throw new java.io.IOException("directory: '" + directory
					+ "' is not writeable");
		}
		final String filePath = directory.getAbsolutePath()
				+ File.separatorChar + fileName;
		final File f = new File(filePath);
		// todo doesn't check if the file exists need new getNewWriteableFile
		return f;
	}

	public static File getWritableFileInTemp(final String fileName)
			throws IOException {
		final File tmpDir = getTmpDir();
		final File f = getWriteableFile(tmpDir, fileName);
		return f;
	}

	public static void closeInputStream(final InputStream in) {
		try {
			in.close();
		} catch (final IOException ioe) {
			logger.error(ioe);
		}
	}

	public static boolean fileContentsMatch(final File file1, final File file2) {
		InputStream in1;
		InputStream in2;
		try {
			in1 = new FileInputStream(file1);
			in2 = new FileInputStream(file2);
		} catch (final IOException io) {
			throw new RuntimeException(io);
		}
		final InputStreamComparator isc = new InputStreamComparator();
		final boolean result = isc.compare(in1, in2) == 0;
		closeInputStream(in1);
		closeInputStream(in2);
		// we've silently cleaned up resources
		return result;
	}
}
