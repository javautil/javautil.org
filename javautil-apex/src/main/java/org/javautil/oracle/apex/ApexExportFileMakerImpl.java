package org.javautil.oracle.apex;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class ApexExportFileMakerImpl implements ApexExportFileMaker {
	/*
	 * 
	 */

	private final Logger logger = Logger.getLogger(getClass());

	public File getExportFile(final Integer applicationId,
			final String applicationName, final String workspaceName,
			final File exportDirectory, final boolean verbose)
			throws IOException {
		if (applicationId == null) {
			throw new IllegalArgumentException("applicationId is null");
		}
		if (applicationName == null) {
			throw new IllegalArgumentException("applicationName is null");
		}
		if (workspaceName == null) {
			throw new IllegalArgumentException("workspaceName is null");
		}
		if (exportDirectory == null) {
			throw new IllegalArgumentException("exportDirectory is null");
		}
		final String fileName = applicationId + "-" + applicationName + ".sql";
		final String nospaceFileName = fileName.replaceAll(" ", "_");
		final String noSlashFileName = nospaceFileName.replaceAll("/", "_");
		final String cleanFileName = noSlashFileName;

		final String path = exportDirectory + "/" + workspaceName;
		final File pathFile = new File(path);
		if (!pathFile.exists()) {
			final boolean made = pathFile.mkdirs();
			if (!made) {
				throw new IOException("cannot locate or create "
						+ pathFile.getAbsolutePath());
			}
		}
		if (!pathFile.canWrite()) {
			throw new IOException("can't write to "
					+ pathFile.getAbsolutePath());
		}
		final File returnFile = new File(path + "/" + cleanFileName);
		if (!returnFile.getCanonicalPath().startsWith(
				exportDirectory.getCanonicalPath())) {
			final String message = "A security violation has occurred: the export direcotory is "
					+ //
					"'" + exportDirectory.getCanonicalPath() + "'" + //
					" but the resolved canonical path for the export file " + //
					"'" + returnFile.getCanonicalPath() + "'" + //
					" is not located below the export directory";
			throw new IOException(message);
		}
		if (verbose) {
			logger.info("exporting application " + applicationId + //
					" named '" + applicationName + //
					"' to '" + returnFile.getAbsolutePath());
		}
		return returnFile;
	}

}
