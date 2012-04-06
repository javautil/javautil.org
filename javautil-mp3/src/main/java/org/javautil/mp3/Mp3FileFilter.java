package org.javautil.mp3;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;

public class Mp3FileFilter implements FilenameFilter {

	// TODO allow for upper case
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public boolean accept(final File dir, final String name) {
		boolean returnValue = false;
		final File testFile = new File(dir.getAbsolutePath() + "/" + name);
		if (testFile.canRead()) {
			if (testFile.isDirectory()
					|| (testFile.isFile() && name.endsWith(".mp3"))) {
				returnValue = true;
			}
		} else {
			logger.warn("can't read file: '" + testFile.getAbsolutePath()
					+ "' skipping");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Mp3FileFilter '" + testFile.getAbsolutePath() + "' "
					+ returnValue);
		}
		return returnValue;
	}
}
