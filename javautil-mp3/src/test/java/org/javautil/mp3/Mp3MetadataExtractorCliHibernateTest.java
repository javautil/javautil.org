package org.javautil.mp3;

import java.io.FileNotFoundException;
import java.util.Collection;

import org.javautil.core.CommandLineTokenizer;
import org.javautil.mp3.persistence.Mp3Persistence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ExtractorTestH2HibernateApplicationContext.xml" })
public class Mp3MetadataExtractorCliHibernateTest {

	@Autowired
	private Mp3Persistence persistence;

	@Before
	public void before() {
		final CreateDatabaseObjectsScriptRunnerTest createDatabase = new CreateDatabaseObjectsScriptRunnerTest();
		// CreateDatabaseObjectsJDBCTest createDatabase = new
		// CreateDatabaseObjectsJDBCTest();
		createDatabase.createDatabaseTest();
	}

	private void populate() throws FileNotFoundException {
		// String argString = "-directory tunes -outputFile target/test.out";
		final String argString = "-directory /disk/signature/music "
				+ "-directory /disk/signature/music2 "
				+ "-directory /disk/signature/music3 "
				+ "-directory \"/disk/signature/music - discographies\" "
				+ "-outputFile target/test.out";
		final String[] args = new CommandLineTokenizer().tokenize(argString);
		Mp3MetadataExtractorCli.main(args);
	}

	Collection<Mp3> getAll() {
		return persistence.getAll();
	}

	@Test
	public void check() throws FileNotFoundException {
		populate();

	}
}
