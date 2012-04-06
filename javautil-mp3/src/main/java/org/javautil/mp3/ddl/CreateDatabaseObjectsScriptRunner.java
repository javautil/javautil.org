package org.javautil.mp3.ddl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javautil.jdbc.ddl.DdlScriptRunner;

public class CreateDatabaseObjectsScriptRunner implements CreateDatabaseObjects {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DdlScriptRunner runner;

	private DataSource datasource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.mp3.CreateDatabaseObjects#createObjects()
	 */
	@Override
	public void createObjects() {
		runner = new DdlScriptRunner(datasource,
				"src/main/resources/create_tables.h2.sql");
		runner.runScripts();
	}

	@Override
	public void dropObjects() {
		runner = new DdlScriptRunner(datasource,
				"src/main/resources/drop_objects.h2.sql");
		runner.setDatasource(datasource);
		runner.runScripts();
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
