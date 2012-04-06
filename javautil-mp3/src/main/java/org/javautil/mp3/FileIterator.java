package org.javautil.mp3;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Returns all of the MP3's in a a directory and all directories underneath it.
 * 
 * Directorys are processed depth first, in alphabetical order with the
 * directories and the files comingled and collated.
 * 
 * TODO move to javautil core TODO support multiple file extensions TODO support
 * ignore case on extension
 * 
 * TODO add getCount
 * 
 * @author jjs
 * 
 */
public class FileIterator {
	private FilenameFilter filter = new Mp3FileFilter();

	private final Logger logger = Logger.getLogger(getClass());

	private long fileCount;

	private TreeMap<String, File> directories;

	private static final String newline = System.getProperty("line.separator");

	private final TreeMap<String, File> files = new TreeMap<String, File>();

	public FileIterator(final FilenameFilter filter) {
		if (filter == null) {
			throw new IllegalArgumentException("filter is null");
		}
		this.filter = filter;

	}

	public FileIterator(final File[] directories) {
		if (directories == null) {
			throw new IllegalArgumentException("directories is null");
		}
		populateDirectories(directories);
	}

	public FileIterator(final Collection<File> directories) {
		if (directories == null) {
			throw new IllegalArgumentException("directories is null");
		}
		populateDirectories((File[]) directories.toArray());
	}

	void populateDirectories(final File[] directories) {
		Arrays.sort(directories);
		this.directories = new TreeMap<String, File>();
		for (final File directory : directories) {
			this.directories.put(directory.getName(), directory);
		}
	}

	public Collection<File> getSortedByName(final File[] files) {
		final TreeMap<String, File> sortedFiles = new TreeMap<String, File>();
		for (final File file : files) {
			sortedFiles.put(file.getName(), file);
		}
		final Collection<File> returnValue = sortedFiles.values();
		if (logger.isDebugEnabled()) {
			final StringBuilder b = new StringBuilder();
			for (final File file : returnValue) {
				b.append(file);
				b.append(newline);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("sorted list " + newline + b.toString());
			}
		}
		return returnValue;
	}

	// TODO evaluate for excess complexity now that files is a treemap
	private void processDirectory(final File dir) {
		if (dir == null) {
			throw new IllegalArgumentException("dir is null");
		}
		final File[] fileList = dir.listFiles(filter);
		if (fileList == null) {
			// TODO if this is null the next thing is broken, fix this.
			logger.info("no files passed filter for " + dir.getAbsolutePath());
		} else {
			for (final File file : fileList) {
				file.getName();
				files.put(file.getName(), file);
				if (logger.isDebugEnabled()) {
					final String type = file.isDirectory() ? "directory"
							: "file";
					logger.debug("adding " + type + "'"
							+ file.getAbsolutePath() + "'");
				}
			}
		}
	}

	/**
	 * Returns the next file.
	 * 
	 * @return The next file
	 */
	public synchronized File next() {
		File file = null;
		File returnValue = null;

		while ((returnValue == null)
				&& ((directories.size() > 0) || (files.size() > 0))) {
			if ((files == null) || (files.size() == 0)) {
				if (directories.size() > 0) {
					final File directory = directories.firstEntry().getValue();
					processDirectory(directory);
					directories.remove(directories.firstEntry().getKey());
				}
			}

			if (files.size() > 0) {
				file = files.firstEntry().getValue();
				files.remove(files.firstEntry().getKey());

				if (file.isDirectory()) {
					processDirectory(file);
				} else {
					returnValue = file;
					fileCount++;
					break;
				}
			}
		}
		if ((returnValue != null) && returnValue.isDirectory()) {
			throw new IllegalStateException("tried to return directory "
					+ file.getName());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("returning file: " + returnValue);
		}
		return returnValue;
	}

	/**
	 * @param directories
	 *            the directories to set
	 */
	public void setDirectories(final Collection<File> directories) {
		final TreeMap<String, File> sortedDirectories = new TreeMap<String, File>();
		for (final File directory : directories) {
			sortedDirectories.put(directory.getName(), directory);
		}
		this.directories = sortedDirectories;
	}

}
