package org.javautil.mp3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.javautil.mp3.formatter.Mp3MetadataFormatter;
import org.junit.Ignore;
import org.junit.Test;

public class Mp3MetaDataPopulatorTest {
	private static Logger logger = Logger
			.getLogger(Mp3MetaDataPopulatorTest.class);

	// TODO write more
	@Ignore
	@Test
	public void test() throws Exception {
		final Mp3Metadata meta = testFile("src/test/resources/Aerosmith/Aerosmith - Janies Got A Gun.mp3");
		assertEquals("Big Ones", meta.getAlbumTitle1());
		assertNull(meta.getAlbumTitle2());
	}

	@Ignore
	@Test
	public void test2() throws Exception {
		testFile("testdata/Allman Brothers Band - Live - 00 - Sugar Magnolia.mp3");
	}

	@Test
	public void test3() throws Exception {
		testFile("testdata/Bob Seger - We_ve Got Tonig.mp3");
	}

	public Mp3Metadata testFile(final String fileName) throws Exception {
		final Mp3Metadata meta = new Mp3Metadata();
		meta.setFileName(fileName);
		final Mp3MetadataProcessor populator = new Mp3MetadataPopulatorImpl();
		populator.process(meta);
		final Mp3MetadataFormatter formatter = new Mp3MetadataFormatter();
		logger.info(formatter.toLongString(meta));
		return meta;
	}
	// public static void main(final String args[])
	// throws MetadataPopulationException {
	// final Mp3MetaDataPopulatorTest t = new Mp3MetaDataPopulatorTest();
	// t.test();
	// }
}
