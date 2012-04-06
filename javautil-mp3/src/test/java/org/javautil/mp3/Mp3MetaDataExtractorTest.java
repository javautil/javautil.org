package org.javautil.mp3;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.javautil.mp3.persistence.CsvPersister;
import org.junit.Test;

public class Mp3MetaDataExtractorTest {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		final MetadataExtractor extractor = new MetadataExtractor();
		// set persistence
		final CsvPersister persister = new CsvPersister();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final Writer writer = new OutputStreamWriter(baos);
		persister.setWriter(writer);
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
		extractor.setPersistence(persister);
		extractor.setDirectories(directories);
		// TODO document as example of populating by hand
		extractor.setPopulator(new org.javautil.mp3.Mp3MetadataPopulatorImpl());
		extractor.process();
		final String result = baos.toString();
		logger.debug(result);

	}
}
