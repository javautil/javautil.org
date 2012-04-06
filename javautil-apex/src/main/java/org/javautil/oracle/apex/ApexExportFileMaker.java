package org.javautil.oracle.apex;

import java.io.File;
import java.io.IOException;

public interface ApexExportFileMaker {

	/**
	 * Creates a File of the form
	 * $EXPORT_DIR/WORKSPACE/###_Application_Name.sql.
	 * 
	 * 
	 * @param applicationId
	 *            the Apex Application Number
	 * @param applicationName
	 *            the Name of Apex Application associated with the Apex
	 *            Application Number
	 * @param workspaceName
	 *            the name of the workspace that contains the apex application
	 * @param exportDirectory
	 *            the directory into which the export should be made
	 * @param verbose
	 *            -
	 * @return
	 * @throws IOException
	 */
	public abstract File getExportFile(Integer applicationId,
			String applicationName, String workspaceName, File exportDirectory,
			boolean verbose) throws IOException;

}