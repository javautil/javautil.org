package org.javautil.io;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class FiltePathHelperTest {

	@Test
	public void getFilePathNameTest() {
		final String expected = "src/test/resources/org/javautil/io/readme.zip";
		String actual = FilePathHelper.getFilePathName(
				"src/test/resources/org/javautil/io", "readme.zip");
		assertEquals(expected, actual);
		actual = FilePathHelper.getFilePathName(
				"src/test/resources/org/javautil/io/", "readme.zip");
		assertEquals(expected, actual);
		actual = FilePathHelper.getFilePathName(null,
				"src/test/resources/org/javautil/io/readme.zip");
		assertEquals(expected, actual);
	}

	@Test
	public void getFileExtensionTest() {
		String ext = FilePathHelper.getFileExtension(new File(
				"src/test/resources/org/javautil/io/readme.zip"));
		assertEquals("zip", ext);

		ext = FilePathHelper.getFileExtension(new File(
				"src/test/resources/org/javautil/io/readme.text.gz"));
		assertEquals("gz", ext);

		ext = FilePathHelper.getFileExtension(new File(
				"src/test/resources/org/javautil/io/readme"));
		assertEquals("", ext);

	}

	@Test
	public void getFileNameWithoutExtensionTest() {
		String fileName = FilePathHelper.getFileNameWithoutExtension(new File(
				"src/test/resources/org/javautil/io/readme.zip"));
		assertEquals("readme", fileName);

		fileName = FilePathHelper.getFileNameWithoutExtension(new File(
				"src/test/resources/org/javautil/io/readme.text.gz"));
		assertEquals("readme.text", fileName);

	}

}
