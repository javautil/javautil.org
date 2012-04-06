package com.dbexperts.oracle.apex;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;

import oracle.jdbc.OracleDriver;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.javautil.commandline.CommandLineHandler;



public class ApexExport {

    private static final String newline = System.getProperty("line.separator");

    private ApexExportArguments arguments;

    private Connection conn;

    public static final String revision = "$Revision: 1.1 $";


    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    private Collection<ApexApplicationBean> applications = null;
    
    private long startMillis;

    private long endMillis;

    public static String getBuildIdentifier() {
	String[] words = revision.split(" ");
	return words[1];
    }

    private Connection getConnection() throws SQLException {
	DriverManager.registerDriver(new OracleDriver());
	Connection conn = DriverManager.getConnection(
		"jdbc:oracle:thin:@"+ arguments.getDatabaseURL(), //
		arguments.getUserName(),
		arguments.getPassword());
	conn.setAutoCommit(false);
	return conn;
    }

    private void setApplications() {
	ApexApplicationDAO dao = null;
	if (arguments.getApplicationId() != null) {
	    dao = ApexApplicationBeanDAOFactory.forIds(arguments.getApplicationId());
	} else if (arguments.getWorkspaceName() != null) {
	    dao = ApexApplicationBeanDAOFactory.forWorkspaceNameMask(arguments.getWorkspaceName());
	}
	applications = dao.getApplications(conn);
    }
   
    
   

    private void process(ApexExportArguments arguments) throws 
	    IOException {
	startMillis = System.currentTimeMillis();
	
	if (arguments == null) {
	    throw new IllegalArgumentException("arguments is null");
	}
	this.arguments = arguments;
	try {
	    conn = getConnection();
		setApplications();
		if (arguments.isShowAppInfoOnly()) {
		    showApplications();
		} else {
		processApplications();
		}
		conn.close();
		endMillis = System.currentTimeMillis();
		double elapsedSeconds = ((double)(endMillis - startMillis)) / 1000;
		logger.info("elapsed seconds: " + elapsedSeconds);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	} finally {
	    try {
		conn.close();
	    } catch (SQLException e) {
		logger.error("unable to close connection " + e.getMessage());
		throw new IllegalStateException(e);
	    }
	}

    }
    
    private void showApplications() {

	for (ApexApplicationBean app : applications) {
	    logger.info(app.toString());
	}
    }

    private void processApplications() throws SQLException, IOException {

	for (ApexApplicationBean app : applications) {
	
	    ApexApplicationExporter exporter = new ApexApplicationExporter();
	    ApexExportFileMakerInterface fileMaker = new ApexExportFileMaker();

	    File exportFile = fileMaker.getExportFile(app.getId(),
		    app.getApplicationName(), app.getWorkspaceName(), arguments
			    .getExportDirectory(), arguments.isVerbose());

	    logger.info("exporting application " + app.getId());
	    // exportToFile(applicationId, exportFile);
	    exporter.exportToFile(conn, app.getId(), exportFile, arguments
		    .isSkipDate(), arguments.isVerbose());
	}

    }

//    public static void main(String args[]) throws Exception {
//	ApexExportArguments arguments = new ApexExportArguments();
//
//	BasicConfigurator.configure();
//	Logger logger = Logger.getRootLogger();
//	logger.setLevel(Level.DEBUG);
//	ApexExport exporter = new ApexExport();
//
//	CommandLineHandler cmd = new CommandLineHandler();
//
//
//	cmd.setResourceBundle(ResourceBundle.getBundle("resources/ApexExports"));
//	cmd.setArguments(arguments);
//	cmd.setApplicationVersion(ApexExport.getBuildIdentifier());
//
//	cmd.parse(args);
//
//	exporter.process(arguments);
//	exporter.conn.close();
//    }

   
}