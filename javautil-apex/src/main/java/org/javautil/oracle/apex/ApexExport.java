package org.javautil.oracle.apex;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

import oracle.jdbc.OracleDriver;

import org.apache.log4j.Logger;
import org.javautil.commandline.CommandLineHandler;

public class ApexExport {

	private ApexExportArguments arguments;

	private Connection conn;

	public static final String revision = "$Revision: 1.2 $";

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private Collection<org.javautil.oracle.apex.ApexApplicationBean> applications = null;

	private long startMillis;

	private long endMillis;

	public static String getBuildIdentifier() {
		final String[] words = revision.split(" ");
		return words[1];
	}

	private Connection getConnection() throws SQLException {
		DriverManager.registerDriver(new OracleDriver());
		final Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@" + arguments.getDatabaseURL(), //
				arguments.getUserName(), arguments.getPassword());
		conn.setAutoCommit(false);
		return conn;
	}

	private void setApplications() {
		ApexApplicationDAO dao = null;
		if (arguments.getApplicationId() != null) {
			dao = ApexApplicationBeanDAOFactory.forIds(arguments
					.getApplicationId());
		} else if (arguments.getWorkspaceName() != null) {
			dao = ApexApplicationBeanDAOFactory.forWorkspaceNameMask(arguments
					.getWorkspaceName());
		}
		applications = dao.getApplications(conn);
	}

	private void process(final ApexExportArguments arguments)
			throws IOException {
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
			final double elapsedSeconds = ((double) (endMillis - startMillis)) / 1000;
			logger.info("elapsed seconds: " + elapsedSeconds);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			try {
				conn.close();
			} catch (final SQLException e) {
				logger.error("unable to close connection " + e.getMessage());
				throw new IllegalStateException(e);
			}
		}

	}

	private void showApplications() {

		for (final ApexApplicationBean app : applications) {
			logger.info(app.toString());
		}
	}

	private void processApplications() throws SQLException, IOException {

		for (final ApexApplicationBean app : applications) {

			final ApexApplicationExporter exporter = new ApexApplicationExporter();
			final ApexExportFileMaker fileMaker = new ApexExportFileMakerImpl();

			final File exportFile = fileMaker.getExportFile(app.getId(),
					app.getApplicationName(), app.getWorkspaceName(),
					arguments.getExportDirectory(), arguments.isVerbose());

			logger.info("exporting application " + app.getId());
			exporter.exportToFile(conn, app.getId(), exportFile,
					arguments.isSkipDate(), arguments.isVerbose());
		}

	}

	public static void main(final String args[]) throws Exception {
		final ApexExportArguments arguments = new ApexExportArguments();
		final ApexExport exporter = new ApexExport();
		final CommandLineHandler cmd = new CommandLineHandler(arguments);
		cmd.evaluateArguments(args);
		exporter.process(arguments);
		// TODO what is this
		exporter.conn.close();
	}

}