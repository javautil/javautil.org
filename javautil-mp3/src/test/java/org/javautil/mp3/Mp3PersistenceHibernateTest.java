package org.javautil.mp3;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.javautil.mp3.persistence.Mp3Persistence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ExtractorTestH2HibernateApplicationContext.xml" })
public class Mp3PersistenceHibernateTest {

	@Autowired
	private Mp3Persistence persistence;

	@Before
	public void before() {
		// final CreateDatabaseObjectsScriptRunnerTest createDatabase = new
		// CreateDatabaseObjectsScriptRunnerTest();
		// // CreateDatabaseObjectsJDBCTest createDatabase = new
		// // CreateDatabaseObjectsJDBCTest();
		// createDatabase.createDatabaseTest();
	}

	@Test
	public void test() {
		final List<ArtistSuspect> suspects = persistence.getArtistSuspects();
		assertNotNull(suspects);
		// TODO more tests
	}
}
