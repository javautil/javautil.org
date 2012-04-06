package org.javautil.mp3.formatter;

import java.io.File;

public class FileInfo {

	// TODO move to core, test for Linux?

	public String getFileInfo(final String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("fileName is null");
		}
		final File file = new File(fileName);
		if (!file.canRead()) {
			throw new IllegalArgumentException("can't read " + fileName);
		}
		final CommandOutput commandRunner = new CommandOutput();

		// TODO check can read file
		final String retval = commandRunner
				.getCommandOutput("file " + fileName);
		return retval;
	}

}
