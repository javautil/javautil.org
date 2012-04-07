package org.javautil.io;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class StringFileWriterTest {

	@Test
	public void testGetFile() {
		File file = StringFileWriter.getFile("src/main/java",
				"org.javautil.io", "test");
		assertEquals("src/main/java/org/javautil/io/test.java", file.getPath());
	}
}
