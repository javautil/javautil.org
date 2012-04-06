package org.javautil.mp3;

import org.javautil.core.CommandLineTokenizer;
import org.junit.Test;

public class ArtistCsvListExtractorSpringTest {

	@Test
	public void test1() {

		final String argString = "-applicationContext src/test/resources/ArtistCsvExtractorApplicationContext.xml  -outputfile target/artists.csv ";

		final String[] args = new CommandLineTokenizer().tokenize(argString);
		ArtistCsvListExtractor.main(args);
		// TODO need to make sure the database is populated
	}
}
