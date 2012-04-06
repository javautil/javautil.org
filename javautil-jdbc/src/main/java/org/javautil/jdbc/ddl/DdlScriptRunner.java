package org.javautil.jdbc.ddl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

// TODO this should be moved to a better location
public class DdlScriptRunner {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DataSource datasource;

	private Connection conn;

	private String fileName;

	public DdlScriptRunner(Connection conn, String fileName) {
		super();
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		if (fileName == null) {
			throw new IllegalArgumentException("fileName is null");
		}
		this.conn = conn;
		this.fileName = fileName;
	}

	public DdlScriptRunner(DataSource datasource, String fileName) {
		super();
		if (datasource == null) {
			throw new IllegalArgumentException("datasource is null");
		}
		if (fileName == null) {
			throw new IllegalArgumentException("fileName is null");
		}
		this.datasource = datasource;
		this.fileName = fileName;
	}

	public void afterPropertiesSet() {
		if (datasource == null && conn == null) {
			throw new IllegalStateException(
					"datasource is null and conn is null");
		}
		if (fileName == null) {
			throw new IllegalStateException("fileName is null");
		}
		File f = new File(fileName);
		if (!f.canRead()) {
			throw new IllegalArgumentException("can't read '" + fileName + "'");
		}
	}

	public void runScripts() {
		afterPropertiesSet();
		final Resource res = new FileSystemResource(fileName);
		Connection conn = getConnection();
		InputStream is = null;
		Reader reader = null;
		try {
			is = res.getInputStream();
			reader = new InputStreamReader(is);
			org.h2.tools.RunScript.execute(conn, reader);
			conn.close();
			reader.close();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (final SQLException e) {
					logger.error(e);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (final IOException e) {
					logger.error(e);
				}
			}
		}
	}

	Connection getConnection() {
		Connection conn;
		if (this.conn != null) {
			conn = this.conn;
		} else {
			if (datasource != null) {
				try {
					conn = datasource.getConnection();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			} else {
				throw new IllegalStateException(
						"both datasource and conn are null, must precede with call to setDatasource or setConnection or inject");
			}
		}

		return conn;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDatasource() {
		return datasource;
	}

	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDatasource(final DataSource dataSource) {
		this.datasource = dataSource;
	}

}
