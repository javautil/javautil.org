package org.javautil.mp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ExtractorTest {
	private FileSystemXmlApplicationContext applicationContext;
	private MetadataExtractor extractor;

	Collection<File> getDirectories() {
		final File music = new File("target/test-classes");
		final ArrayList<File> dirs = new ArrayList<File>();
		dirs.add(music);
		return dirs;
	}

	@Test
	public void toCSV() throws FileNotFoundException {
		applicationContext = new FileSystemXmlApplicationContext(
				"target/test-classes/ExtractorTestCsvFileApplicationContext.xml");
		final MetadataExtractorFileWriter extractor = (MetadataExtractorFileWriter) applicationContext
				.getBean("extractor");
		extractor.setDirectories(getDirectories());

		extractor.setOutputFile(new File("target/music.csv"));
		extractor.process();
		// TODO not testing anything
	}

	@Ignore
	@Test
	public void toH2Jdbc() {

		applicationContext = new FileSystemXmlApplicationContext(
				"target/test-classes/ExtractorTestH2JdbcApplicationContext.xml");
		extractor = (MetadataExtractor) applicationContext.getBean("extractor");
		extractor.setDirectories(getDirectories());
		extractor.process();
	}

	// // TODO make it drop it first
	// @Test
	// public void toH2Hibernate() {
	// CreateDatabaseObjectsJDBCTest creator = new
	// CreateDatabaseObjectsJDBCTest();
	// creator.createDatabaseTest();
	// applicationContext = new
	// FileSystemXmlApplicationContext("target/test-classes/ExtractorTestH2HibernateApplicationContext.xml");
	// extractor = (MetadataExtractor) applicationContext.getBean("extractor");
	// extractor.setDirectories(getDirectories());
	// // TODO don't like this
	// extractor.process();
	// }

}
