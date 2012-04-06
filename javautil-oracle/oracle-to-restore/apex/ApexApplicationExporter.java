package com.dbexperts.oracle.apex;

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

import org.apache.log4j.Logger;

import oracle.jdbc.OracleCallableStatement;
import oracle.sql.CLOB;

public class ApexApplicationExporter {
    private Logger logger = Logger.getLogger(this.getClass());
    private static final String newline = System.getProperty("line.separator");
    public void exportToFile(Connection conn, Integer applicationId, File file, boolean skipDate, boolean verbose)
	    throws SQLException, IOException {
	long startExport = System.currentTimeMillis();
	logger.info("writing application " + applicationId + " to "
		+ file.getAbsolutePath());
	
	// get the reader
	OracleCallableStatement exportStmt = (OracleCallableStatement) conn
		.prepareCall("begin ? := wwv_flow_utilities.export_application_to_clob(?); end;");
	exportStmt.setObject(2, applicationId);
	exportStmt.registerOutParameter(1, 2005);
	exportStmt.execute();
	CLOB clob = exportStmt.getCLOB(1);
	Reader reader = clob.getCharacterStream();
	BufferedReader in = new BufferedReader(reader);
	// get the writer


	Writer bw = getWriter(file);
	//

	String inLine = null;
	pump(in, bw, skipDate);
	
	bw.flush();
//	if (verbose) {
//	    logger.info(" Wrote " + file.length() + " bytes to "
//		    + file.getAbsolutePath());
//	}
	reader.close();
	bw.close();
	//fos.close();
	exportStmt.close();
	long endExport = System.currentTimeMillis();
	double exportSeconds = (endExport - startExport) / 1000d;
	logger.info("exported app: " + applicationId  + " in " + exportSeconds + " seconds " + 
			"bytes " + file.length() + " in file '" + file.getAbsolutePath() + "'");
			
	//logger.info("export time " + exportSeconds + "seconds");
    }
    
    /**
     * Write the apex data to the output file.
     * @param reader
     * @param out
     * @param skipDate skip the Date and Time line, useful to avoid version control check-in for otherwise
     *    identical files
     * @throws IOException
     */
    public  void pump(Reader reader, Writer out, boolean skipDate) throws IOException {
	String inLine;
	/**
	 * We are in the preamble until a line containing "set define " is found
	 */
	//boolean inPreamble = true;
	if (reader == null) {
	    throw new IllegalArgumentException("reader is null");
	}
	if (out == null) {
	    throw new IllegalArgumentException("out is null");
	}
	BufferedReader in =  new BufferedReader(reader);

	while ((inLine = in.readLine()) != null)
	{	
		    out.write(inLine, 0, inLine.length());
		    out.write(newline);
		    break;
	}
	
	while ((inLine = in.readLine()) != null)
	{
		if (! skipDate
			|| inLine.indexOf("--   Date and Time:") != 0) {
		    out.write(inLine, 0, inLine.length());
		    out.write(newline);
		}   
	}
    }
    
    
    
    // todo move to io package 
    public static Writer getWriter(File file) throws IOException {
	file.delete();
	file.createNewFile();
	FileOutputStream fos = new FileOutputStream(file);
	OutputStreamWriter os = new OutputStreamWriter(fos, "UTF-8");
	BufferedWriter bw = new BufferedWriter(os);
	return bw;
	
    }

}
