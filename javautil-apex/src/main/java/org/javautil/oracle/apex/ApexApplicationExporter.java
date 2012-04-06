package org.javautil.oracle.apex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.sql.CLOB;

import org.apache.log4j.Logger;

public class ApexApplicationExporter {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final String newline = System.getProperty("line.separator");

	public void exportToFile(final Connection conn,
			final Integer applicationId, final File file,
			final boolean skipDate, final boolean verbose) throws SQLException,
			IOException {
		final long startExport = System.currentTimeMillis();
		logger.info("writing application " + applicationId + " to "
				+ file.getAbsolutePath());

		// get the reader
		final OracleCallableStatement exportStmt = (OracleCallableStatement) conn
				.prepareCall("begin ? := wwv_flow_utilities.export_application_to_clob(?); end;");
		exportStmt.setObject(2, applicationId);
		exportStmt.registerOutParameter(1, 2005);
		exportStmt.execute();
		final CLOB clob = exportStmt.getCLOB(1);
		final Reader reader = clob.getCharacterStream();
		final BufferedReader in = new BufferedReader(reader);
		// get the writer

		final Writer bw = getWriter(file);
		//

		pump(in, bw, skipDate);

		bw.flush();
		// if (verbose) {
		// logger.info(" Wrote " + file.length() + " bytes to "
		// + file.getAbsolutePath());
		// }
		reader.close();
		bw.close();
		// fos.close();
		exportStmt.close();
		final long endExport = System.currentTimeMillis();
		final double exportSeconds = (endExport - startExport) / 1000d;
		logger.info("exported app: " + applicationId + " in " + exportSeconds
				+ " seconds " + "bytes " + file.length() + " in file '"
				+ file.getAbsolutePath() + "'");

		// logger.info("export time " + exportSeconds + "seconds");
	}

	/**
	 * Write the apex data to the output file.
	 * 
	 * @param reader
	 * @param out
	 * @param skipDate
	 *            skip the Date and Time line, useful to avoid version control
	 *            check-in for otherwise identical files
	 * @throws IOException
	 */
	public void pump(final Reader reader, final Writer out,
			final boolean skipDate) throws IOException {
		String inLine;
		/**
		 * We are in the preamble until a line containing "set define " is found
		 */
		// boolean inPreamble = true;
		if (reader == null) {
			throw new IllegalArgumentException("reader is null");
		}
		if (out == null) {
			throw new IllegalArgumentException("out is null");
		}
		final BufferedReader in = new BufferedReader(reader);

		while ((inLine = in.readLine()) != null) {
			out.write(inLine, 0, inLine.length());
			out.write(newline);
			break;
		}

		while ((inLine = in.readLine()) != null) {
			if (!skipDate || inLine.indexOf("--   Date and Time:") != 0) {
				out.write(inLine, 0, inLine.length());
				out.write(newline);
			}
		}
	}

	// todo move to io package
	public static Writer getWriter(final File file) throws IOException {
		file.delete();
		file.createNewFile();
		final FileOutputStream fos = new FileOutputStream(file);
		final OutputStreamWriter os = new OutputStreamWriter(fos, "UTF-8");
		final BufferedWriter bw = new BufferedWriter(os);
		return bw;

	}

}
