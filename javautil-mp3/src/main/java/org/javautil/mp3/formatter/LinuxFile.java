package org.javautil.mp3.formatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxFile {

	// TODO make
	public String getFileInfo(final String fileName) {

		return getCommandOutput("file " + fileName);

	}

	public String getCommandOutput(final String command) {
		Process p;
		final StringBuilder sb = new StringBuilder();
		String line;
		try {
			p = Runtime.getRuntime().exec(command);

			final BufferedReader input = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				sb.append(line);
			}
			input.close();

		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		return sb.toString();
	}
}
