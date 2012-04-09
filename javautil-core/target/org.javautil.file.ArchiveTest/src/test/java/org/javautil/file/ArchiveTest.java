package org.javautil.file;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class ArchiveTest {

	private static File archiveFile;
	private static File targetDir;
	private static File extractDir;

	@BeforeClass
	public static void before() throws Exception {
		archiveFile = File.createTempFile("archive", ".zip");
		targetDir = new File("src");
		extractDir = new File("target", ArchiveTest.class.getName());
	}

	@Test
	public void testAdd() throws IOException {
		final Archive archive = new Archive(archiveFile);
		archive.add(targetDir);
		assertTrue(archiveFile.exists());
		assertTrue(0 < archiveFile.length());
	}

	@Test
	public void testExtract() throws IOException {
		final Archive archive = new Archive(archiveFile);
		archive.extract(extractDir);
		assertTrue(new File(extractDir, "src").exists());
	}

}
