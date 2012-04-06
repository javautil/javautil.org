package org.javautil.mp3;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javautil.jdbc.datasources.SimpleDatasourcesFactory;
import org.javautil.mp3.ddl.CreateDatabaseObjectsJdbc;
import org.javautil.mp3.persistence.Mp3PersisterJdbc;
import org.junit.Test;

public class Mp3MetaDataExtractorJDBCTest {
	private final Logger logger = Logger.getLogger(this.getClass());

	private DataSource datasource;

	void instantiateDatasource() {
		System.setProperty("DATASOURCES_FILE",
				"target/test-classes/DataSources.xml");
		datasource = SimpleDatasourcesFactory.getDataSource("mp3");
	}

	// TODO reduce duplication
	void createDatabaseObjects() {
		final CreateDatabaseObjectsJdbc createDatabaseObjects = new CreateDatabaseObjectsJdbc();
		createDatabaseObjects.setDatasource(datasource);
		createDatabaseObjects.dropObjects();
		createDatabaseObjects.createObjects();
	}

	@Test
	public void test() {
		final MetadataExtractor extractor = new MetadataExtractor();
		instantiateDatasource();
		createDatabaseObjects();
		// set persistence
		final Mp3PersisterJdbc persister = new Mp3PersisterJdbc();
		persister.setDatasource(datasource);
		persister.afterPropertiesSet();
		// set directories
		final Collection<File> directories = new ArrayList<File>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(new File("src/test/resources"));
			}
		};
		extractor.setPopulator(new org.javautil.mp3.Mp3MetadataPopulatorImpl());
		extractor.setPersistence(persister);
		extractor.setDirectories(directories);
		extractor.process();
		// TODO should this be everywhere?
		persister.dispose();
		// WRITE TESTS

	}
}
