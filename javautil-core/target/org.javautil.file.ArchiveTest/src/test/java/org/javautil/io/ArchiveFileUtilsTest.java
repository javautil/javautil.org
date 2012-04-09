package org.javautil.io;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ArchiveFileUtilsTest {

	@Test
	public void test1() throws IOException {
		assertTrue(ArchiveFileUtils.isZip(new File(
				"src/test/resources/org/javautil/io/readme.zip")));
		assertFalse(ArchiveFileUtils.isGzip(new File(
				"src/test/resources/org/javautil/io/readme.zip")));
	}

	@Test
	public void test2() throws IOException {
		assertTrue(ArchiveFileUtils.isGzip(new File(
				"src/test/resources/org/javautil/io/readme.text.gz")));
		assertFalse(ArchiveFileUtils.isZip(new File(
				"src/test/resources/org/javautil/io/readme.text.gz")));
	}
}
