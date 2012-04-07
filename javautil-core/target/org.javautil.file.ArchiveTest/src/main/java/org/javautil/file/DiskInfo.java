package org.javautil.file;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.javautil.io.FilePathHelper;

public class DiskInfo {

	private final String newline = System.getProperty("line.separator");
	private final Set<String> visitedDirs = new HashSet<String>();
	protected long digestCount = 0;
	protected long digestMillis = 0;
	private int depth = 0;
	private final Logger logger = Logger.getLogger(getClass());
	private FileInfoListener listener;
	private boolean generateDigest = true;
	private int maxBytesToDigest = 5 * 1024 * 1024;


	public DiskInfo() {
		super();
	}

	public FileInfo getFileInfo(final File f) throws IOException {

		if (f == null) {
			throw new IllegalArgumentException("file is null");
		}
		final FileInfo fi = new FileInfo();
		fi.setFile(f);
		fi.setDirectoryName(f.getParent());
		fi.setExtension(FilePathHelper.getFileExtension(f));
		fi.setFileBaseName(FilePathHelper.getFileNameWithoutExtension(f));
		fi.setLength(f.length());
		fi.setLastModTime(f.lastModified());
		fi.setLength(f.length());
		fi.setFileName(f.getName());

		if (f.canRead() && shouldDigest(f)) {

			try {
				final long a = System.currentTimeMillis();
				fi.setMd5Digest(FileMD5.getMD5Digest(f));
				digestCount++;
				final long b = System.currentTimeMillis();
				digestMillis += b - a;
			} catch (final IOException ioe) {
				logger.warn("while processing '" + f.getAbsolutePath() + "' "
						+ ioe.getMessage());
			}
		}

		return fi;
	}

	/**
	 * @return the maxBytesToDigest
	 */
	public int getMaxBytesToDigest() {
		return maxBytesToDigest;
	}

	/**
	 * @return the generateDigest
	 */
	public boolean isGenerateDigest() {
		return generateDigest;
	}

	public void process(final File f) throws IOException {
		if (f.isFile()) {
			listener.processFileInfo(getFileInfo(f));
			// writer.write(toCSV(f));
			// writer.write(newline);
		} else if (f.isDirectory()) {
			processDirectory(f);

		} else {
			logger.info("file " + f.getAbsolutePath()
					+ " is neither a file nor a directory");
		}
		if (depth < 2) {
			logger.info("depth is " + depth + " " + f.getAbsolutePath()
					+ " digestCount " + digestCount + " milliseconds "
					+ digestMillis);
		}
	}

	public void processDirectory(final File f) throws IOException {
		depth++;
		if (f == null) {
			throw new IllegalArgumentException("f is null");
		}
		final String canon = f.getCanonicalPath();
		if (visitedDirs.contains(canon)) {
			logger.warn("circular directory structure located at "
					+ f.getAbsolutePath());
		} else {
			visitedDirs.add(canon);

			logger.info("descending into '" + f.getAbsolutePath() + "'");
			if (!f.canRead()) {
				logger.warn("cannot read directory " + f.getAbsolutePath());
			} else {
				final File[] files = f.listFiles();
				if (files != null) { // files come and go, this is a mounted
										// filesystem
					for (final File file : files) {
						process(file);
					}
				} else {
					logger.warn("dir no longer exists or permission problem empty?"
							+ f.getAbsolutePath());
				}

			}
		}
		depth--;
		// else {
		// logger.warn("no files");
		// }
	}

	public void setFileInfoListener(final FileInfoListener lister) {
		this.listener = lister;

	}

	/**
	 * @param generateDigest the generateDigest to set
	 */
	public void setGenerateDigest(boolean generateDigest) {
		this.generateDigest = generateDigest;
	}
	
	/**
	 * @param maxBytesToDigest the maxBytesToDigest to set
	 */
	public void setMaxBytesToDigest(int maxBytesToDigest) {
		this.maxBytesToDigest = maxBytesToDigest;
	}

	public boolean shouldDigest(final File f) {
		boolean retval = true;
		if (!generateDigest || f.length() >= maxBytesToDigest) {
			retval = false;
			logger.info(f.getAbsolutePath() + " too long to digest "
					+ f.length() / (1024 * 1024) + " meg");
		}
		return retval;
	}


}
