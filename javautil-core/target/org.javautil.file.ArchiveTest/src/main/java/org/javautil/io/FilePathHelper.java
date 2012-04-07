package org.javautil.io;

import java.io.File;

public class FilePathHelper {

	public static final String revision = "$Revision: 1.2 $";

	public static File file(final String path, final String fileName) {
		return new File(getFilePathName(path, fileName));
	}

	/**
	 * Builds a new path appending the fileName and adding a file separator if
	 * necessary.
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String getFilePathName(final String path,
			final String fileName) {
		final StringBuilder buff = new StringBuilder();
		if (path != null) {
			buff.append(path);
		}
		if (path != null && path.length() > 0) {
			final int lastIndex = path.length() - 1;
			if (path.charAt(lastIndex) != '/') {
				buff.append("/");
			}
		}
		buff.append(fileName);
		return new String(buff);
	}

	/**
	 * Returns the file extension everything after the last "." If there is no
	 * "." returns a zero length String;
	 * 
	 * @param f
	 * @return
	 */
	public static String getFileExtension(final File f) {

		String retval = "";
		final String fileName = f.getName();

		final int i = fileName.lastIndexOf(".");
		if (i > -1) {
			retval = fileName.substring(i + 1);

		}
		return retval;
	}

	/**
	 * Returns the name of the file with the path stripped and the extension
	 * stripped.
	 * 
	 * @param f
	 * @return
	 */
	public static String getFileNameWithoutExtension(final File f) {

		String retval;
		final String name = f.getName();
		final String extension = getFileExtension(f);
		if (extension.length() > 0) {
			final int baseLength = name.length() - extension.length() - 1;
			retval = name.substring(0, baseLength);
		} else {
			retval = name;
		}

		return retval;
	}

}
