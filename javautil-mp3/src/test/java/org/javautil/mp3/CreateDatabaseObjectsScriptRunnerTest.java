package org.javautil.mp3;

import javax.sql.DataSource;

import org.javautil.jdbc.datasources.SimpleDatasourcesFactory;
import org.javautil.mp3.ddl.CreateDatabaseObjects;
import org.javautil.mp3.ddl.CreateDatabaseObjectsScriptRunner;
import org.junit.Test;

/**
 * Example class on instantiating instances of classes to interfaces.
 * 
 * Precursor to Dependency Injection.
 * 
 * @author jjs
 * 
 */
public class CreateDatabaseObjectsScriptRunnerTest {

	private CreateDatabaseObjects createDatabaseObjects;

	private DataSource datasource;

	void instantiateCreateDatabaseObjects() {
		instantiateDatasource();
		final CreateDatabaseObjectsScriptRunner creator = new CreateDatabaseObjectsScriptRunner();
		creator.setDatasource(datasource);
		createDatabaseObjects = creator;
	}

	void instantiateDatasource() {
		System.setProperty("DATASOURCES_FILE",
				"target/test-classes/DataSources.xml");
		datasource = SimpleDatasourcesFactory.getDataSource("mp3");
	}

	@Test
	public void createDatabaseTest() {
		instantiateCreateDatabaseObjects();
		createDatabaseObjects.dropObjects();
		createDatabaseObjects.createObjects();
	}

}
