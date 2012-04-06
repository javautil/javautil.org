package com.dbexperts.oracle.apex;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class ApexExportFileMaker implements ApexExportFileMakerInterface {
    /* (non-Javadoc)
     * @see com.javautil.oracle.apex.ApexExportFileMakerInterface#getExportFile(java.lang.Integer, java.lang.String, java.lang.String, java.io.File)
     */
    
    private Logger logger = Logger.getLogger(getClass());
    
	public File getExportFile(Integer applicationId, String applicationName,
			String workspaceName, File exportDirectory, boolean verbose ) throws IOException {
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
		String fileName = applicationId + "-" + applicationName + ".sql";
		String nospaceFileName = fileName.replaceAll(" ", "_");
		String noSlashFileName = nospaceFileName.replaceAll("/", "_");
		String cleanFileName = noSlashFileName;
		
		String path = exportDirectory + "/" + workspaceName;
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			boolean made = pathFile.mkdirs();
			if (!made) {
				throw new IOException("cannot locate or create "
						+ pathFile.getAbsolutePath());
			}
		}
		if (!pathFile.canWrite()) {
			throw new IOException("can't write to "
					+ pathFile.getAbsolutePath());
		}
		File returnFile = new File(path + "/" + cleanFileName);
		if (! returnFile.getCanonicalPath().startsWith(exportDirectory.getCanonicalPath())) {
		    String message = "A security violation has occurred: the export direcotory is " + //
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
		return  returnFile;
	}

}
