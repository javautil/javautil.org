package org.javautil.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author jjs
 * 
 */
public class TextFileHelper {

	public static void checkCanRead(final File f) {
		if (f == null) {
			throw new IllegalArgumentException("file is null");
		}
		if (!f.exists()) {
			throw new IllegalArgumentException("file '" + f.getAbsolutePath()
					+ "' does not exist");
		}
		if (!f.isFile()) {
			throw new IllegalArgumentException(" file '" + f.getAbsolutePath()
					+ "' is not a normal file");
		}
		if (!f.canRead()) {
			throw new IllegalArgumentException(" file '" + f.getAbsolutePath()
					+ "' is not readable");
		}
	}

	/**
	 * gets all of the lines in the File with line terminators stripped
	 * 
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> getLines(final File file)
			throws IOException {
		checkCanRead(file);
		final ArrayList<String> lines = new ArrayList<String>();

		final BufferedReader buffFile = new BufferedReader(new FileReader(file));
		while (buffFile.ready()) {
			lines.add(buffFile.readLine());
		}
		buffFile.close();

		return lines;
	}

	public static StringBuilder getFileText(final File file) throws IOException {
		checkCanRead(file);

		final StringBuilder buff = new StringBuilder(
				(int) (file.length() * 1.1));
		final BufferedReader buffFile = new BufferedReader(new FileReader(file));
		if (!file.canRead()) {
			throw new IllegalArgumentException("cannot read file "
					+ file.getAbsolutePath());
		}
		while (buffFile.ready()) {
			buff.append(buffFile.readLine());
			buff.append("\n");

		}
		buffFile.close();

		return buff;
	}

}
