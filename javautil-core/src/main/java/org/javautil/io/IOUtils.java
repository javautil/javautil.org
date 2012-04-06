package org.javautil.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;

/**
 * 
 * @author bcm
 * 
 */
public class IOUtils {

	private IOUtils() {
	}

	public static byte[] readBytesFromStream(final InputStream input)
			throws IOException {
		return IOUtils.readBytesFromStream(input, 4096, true); // 4k
	}

	public static byte[] readBytesFromStream(final InputStream input,
			final boolean close) throws IOException {
		return IOUtils.readBytesFromStream(input, 4096, close); // 4k
	}

	public static byte[] readBytesFromStream(final InputStream input,
			final int initialSize, final boolean close) throws IOException {
		if (input == null) {
			throw new IllegalArgumentException("input is null");
		}
		final ByteArrayOutputStream content = new ByteArrayOutputStream(
				initialSize);
		final byte[] byteArray = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = input.read(byteArray)) != -1) {
			content.write(byteArray, 0, bytesRead);
		}
		// todo jjs what is the point of closing or not closing
		if (close) {
			content.close();
		}
		return content.toByteArray();
	}

	public static byte[] readBytesFromStream(final InputStream input,
			final int initialSize) throws IOException {
		return readBytesFromStream(input, initialSize, true);
	}

	public static String readStringFromStream(final InputStream input,
			final int initialSize, final boolean close) throws IOException {
		return new String(readBytesFromStream(input, initialSize, close));
	}

	public static String readStringFromStream(final InputStream input,
			final int initialSize) throws IOException {
		return new String(readBytesFromStream(input, initialSize, true));
	}

	public static String readStringFromStream(final InputStream input,
			final boolean close) throws IOException {
		return new String(readBytesFromStream(input, 4096, close)); // 4k
	}

	public static String readStringFromStream(final InputStream input)
			throws IOException {
		return new String(readBytesFromStream(input, 4096, true)); // 4k
	}

	/**
	 * Reads the input data and writes to output until the input is exhausted.
	 * 
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static int pump(final Reader input, final Writer output)
			throws IOException {
		if (input == null) {
			throw new IllegalArgumentException("input is null");
		}
		if (output == null) {
			throw new IllegalArgumentException("output is null");
		}
		final CharBuffer charBuffer = CharBuffer.allocate(1024);
		int charsRead = 0;
		int charsStreamed = 0;
		while ((charsRead = input.read(charBuffer)) != -1) {
			output.write(charBuffer.array(), 0, charsRead);
			charsStreamed += charsRead;
		}
		return charsStreamed;
	}

	/**
	 * Reads the input data and writes to output until the input is exhausted.
	 * 
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static int pump(final InputStream input, final OutputStream output)
			throws IOException {
		if (input == null) {
			throw new IllegalArgumentException("input is null");
		}
		if (output == null) {
			throw new IllegalArgumentException("output is null");
		}
		final byte[] byteArray = new byte[1024];
		int bytesRead = 0;
		int bytesStreamed = 0;
		while ((bytesRead = input.read(byteArray)) != -1) {
			output.write(byteArray, 0, bytesRead);
			bytesStreamed += bytesRead;
		}
		return bytesStreamed;
	}
}
