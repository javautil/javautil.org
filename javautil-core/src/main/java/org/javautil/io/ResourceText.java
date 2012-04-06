package org.javautil.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

public class ResourceText {
	/**
	 * getResourceText("classpath:resource.txt");
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String getResourceText(final String path) throws IOException {
		final DefaultResourceLoader loader = new DefaultResourceLoader();
		final Resource resource = loader.getResource(path);
		final InputStream is = resource.getInputStream();
		final InputStreamReader isr = new InputStreamReader(is);
		final BufferedReader reader = new BufferedReader(isr);
		final StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		is.close();
		return sb.toString();
	}

}
