package org.javautil.dbunit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class AbstractExporter {
	/**
	 * JDBC datasource for database to be exported.
	 */
	private DataSource datasource;

	/**
	 * JDBC connection.
	 */
	private Connection connection;

	/**
	 * File to which xml output is to be written
	 */
	private File outputFile;

	private IDatabaseConnection iConnection;

	private OutputStream outputStream;

	public void afterPropertiesSet() throws FileNotFoundException {
		if (datasource == null) {
			throw new IllegalStateException("datasource is null");
		}
//		if (outputFile == null) {
//			throw new IllegalStateException("outputFile is null");
//		}
//		outputStream = new FileOutputStream(outputFile);
	}

	public void dispose() throws SQLException, IOException {
		try {
			connection.close();
		} catch (SQLException sqe) {
			throw sqe;
		} finally {
			outputStream.flush();
			outputStream.close();
		}
	}

	/**
	 * @return the datasource
	 */
	public DataSource getDatasource() {
		return datasource;
	}

	/**
	 * @param datasource
	 *            the datasource to set
	 */
	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	/**
	 * @return the outputFile
	 */
	public File getOutputFile() {
		return outputFile;
	}

	/**
	 * @param outputFile
	 *            the outputFile to set
	 */
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * @return the iConnection
	 * @throws SQLException
	 * @throws DatabaseUnitException
	 */
	protected IDatabaseConnection getIConnection() throws SQLException,
			DatabaseUnitException {
		if (iConnection == null) {
			connection = datasource.getConnection();
			iConnection = new DatabaseConnection(connection);
		}
		return iConnection;
	}

	/**
	 * @return the outputStream
	 * @throws FileNotFoundException 
	 */
	protected OutputStream getOutputStream() throws FileNotFoundException {
		if (outputStream == null) {
			if (outputFile == null) {
				throw new IllegalStateException("setOutputFile and setOutputStream have not been called");
			}
			outputStream = new FileOutputStream(outputFile);
		}
		return outputStream;
	}

	/**
	 * @param outputStream the outputStream to set
	 */
	protected void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

}
