package org.javautil.mp3;

import java.io.FileNotFoundException;
import java.util.Collection;

import org.javautil.core.CommandLineTokenizer;
import org.javautil.mp3.persistence.Mp3Persistence;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Mp3MetadataExtractorCliTest {

	@Autowired
	private Mp3Persistence persistence;

	public void before() {
		final CreateDatabaseObjectsJDBCTest createDatabase = new CreateDatabaseObjectsJDBCTest();
		createDatabase.createDatabaseTest();
	}

	// TODO output file should be optional
	@Test
	public void populate() throws FileNotFoundException {
		before();
		final String argString = "-directory /media/signature_/music -outputFile target/noise -applicationContextFile src/test/resources/ExtractorTestH2HibernateApplicationContext.xml";
		final String[] args = new CommandLineTokenizer().tokenize(argString);
		Mp3MetadataExtractorCli.main(args);
	}

	@Ignore
	@Test
	public void populateMyCollection() throws FileNotFoundException {
		final String argString = "-directory /media/signature_/music -outputFile target/test.out -applicationContextFile src/test/resources/ExtractorTestCsvFileApplicationContext.xml -exceptionFile target/exceptions.out";
		final String[] args = new CommandLineTokenizer().tokenize(argString);
		Mp3MetadataExtractorCli.main(args);
	}

	Collection<Mp3> getAll() {
		return persistence.getAll();
	}

	public static void main(final String[] arguments)
			throws FileNotFoundException {
		new Mp3MetadataExtractorCliTest().populateMyCollection();
	}
}
