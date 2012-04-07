package org.javautil.io;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ResourceTextTest {

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * getResourceText("classpath:resource.txt");
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		final ResourceText rt = new ResourceText();
		final String text = rt.getResourceText("classpath:testText.txt");
		assertEquals("Test", text);
	}

}
