package org.javautil.mp3;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class H2HibernateExtractorTest {
	private FileSystemXmlApplicationContext applicationContext;
	private MetadataExtractor extractor;

	// TODO should push up
	Collection<File> getDirectories() {
		final File music = new File("target/test-classes");
		final ArrayList<File> dirs = new ArrayList<File>();
		dirs.add(music);
		return dirs;
	}

	// TODO make it drop it first
	@Test
	public void toH2Hibernate() {
		final CreateDatabaseObjectsJDBCTest creator = new CreateDatabaseObjectsJDBCTest();
		creator.createDatabaseTest();
		applicationContext = new FileSystemXmlApplicationContext(
				"target/test-classes/ExtractorTestH2HibernateApplicationContext.xml");
		extractor = (MetadataExtractor) applicationContext.getBean("extractor");
		extractor.setDirectories(getDirectories());
		// TODO don't like this
		extractor.process();
	}

	public static void main(final String[] args) {
		final H2HibernateExtractorTest test = new H2HibernateExtractorTest();
		test.toH2Hibernate();
	}

}
