package org.javautil.mp3;

import static org.junit.Assert.assertEquals;

import org.javautil.mp3.formatter.FileInfo;
import org.junit.Test;

public class FileInfo2Test {

	@Test
	public void test2() {
		final FileInfo fi = new FileInfo();
		final String actual = fi.getFileInfo("/etc/passwd");
		final String expected = "/etc/passwd: ASCII text";
		assertEquals(expected, actual);
	}

	// //TODO why does this hang?
	// @Test
	// public void test() {
	// String expected =
	// "target/test-classes/Aerosmith/Aerosmith - Janies Got A Gun.mp3: MPEG ADTS, layer III, v1, 128 kbps, 44.1 kHz, JntStereo";
	// String filename =
	// "target/test-classes/Aerosmith/Aerosmith - Janies Got A Gun.mp3";
	// FileInfo fi = new FileInfo();
	// String actual = fi.getFileInfo(filename);
	// assertEquals(expected,actual);
	//
	// }
}
