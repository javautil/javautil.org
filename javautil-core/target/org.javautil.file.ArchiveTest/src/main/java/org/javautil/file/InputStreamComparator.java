package org.javautil.file;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;

import org.apache.log4j.Logger;

/**
 * Does file comparison using streams
 * 
 * @return a negative number if the first stream is of "lower value" than the
 *         second stream.
 * 
 *         The magnitude of the number is the byte offset in the file modulus
 *         2^32
 * 
 */
public class InputStreamComparator implements Comparator<InputStream> {
	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * Does byte comparison on the given two input streams If InputStream1 is
	 * equal to InputStream2, returns 0 If InputStream1 is not equal to
	 * InputStream2, returns > 0 TODO jjs should be negative when input stream 1
	 * 
	 * @param inputStream1
	 *            the first InputStream to be compared
	 * @param inputStream2
	 *            the second InputStream to be compared
	 * @return the byte position that are different
	 * @throws IOException
	 */

	@Override
	public int compare(final InputStream inputStream1,
			final InputStream inputStream2) {

		int byteDifferencePosition = 0;
		BufferedInputStream buffStream1 = new BufferedInputStream(inputStream1);
		BufferedInputStream buffStream2 = new BufferedInputStream(inputStream2);

		try {

			int byte1 = 0;
			int byte2 = 0;
			int byteCount = 0;
			while ((byte1 = buffStream1.read()) != -1
					&& (byte2 = buffStream2.read()) != -1) {
				byteCount++;
				if (byte1 != byte2) {
					if (byte1 < byte2) {
						byteDifferencePosition = -1 * byteCount;
					} else {
						byteDifferencePosition = byteCount;
					}
					break;
				}
			}

			if (byteDifferencePosition == 0 && byte1 != -1) {
				byteCount++;
				byteDifferencePosition = byteCount;
			}

			if (byteDifferencePosition == 0 && byte2 != -1) {
				byte2 = buffStream2.read();
				if (byte2 != -1) {
					byteCount++;
					byteDifferencePosition = byteCount;
				}
			}

		} catch (final IOException io) {
			throw new RuntimeException(io);
		} finally {

			if (buffStream1 != null) {
				try {
					buffStream1.close();
				} catch (final IOException e) {
					logger.error(e.getMessage());
				}
				buffStream1 = null;
			}
			if (buffStream2 != null) {
				try {
					buffStream2.close();
				} catch (final IOException e) {
					logger.error(e.getMessage());
				}
				buffStream2 = null;
			}

		}

		return byteDifferencePosition;
	}

}
