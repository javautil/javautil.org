package org.javautil.mp3;

import org.javautil.core.CommandLineTokenizer;
import org.junit.Test;

public class ArtistCsvExtractorTest {

	@Test
	public void test1() {

		System.setProperty("DATASOURCES_FILE",
				"target/test-classes/DataSources.xml");

		final String argString = "-datasource mp3-populated -outputfile target/artists.csv ";

		final String[] args = new CommandLineTokenizer().tokenize(argString);
		ArtistCsvExtractor.main(args);
		// TODO need to make sure the database is populated
		// TODO what the hell kind of test is this?
	}
}
