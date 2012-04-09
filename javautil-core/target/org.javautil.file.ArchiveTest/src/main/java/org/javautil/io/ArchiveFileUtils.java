package org.javautil.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 * @author jjs@javautil.org
 * 
 */
public class ArchiveFileUtils {

	public static boolean isGzip(final File f) throws IOException {
		FileInputStream fis;
		boolean returnValue = false;

		fis = new FileInputStream(f);
		try {
			new GZIPInputStream(fis);
			returnValue = true;
		} catch (final IOException io) {
			// If we got here the file is readable but not in GZIP format
			// throw new RuntimeException(io);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

		return returnValue;
	}

	public static boolean isZip(final File f) throws IOException {
		// String absoluteName = f.getAbsolutePath();
		FileInputStream fis = null;
		boolean returnValue = false;
		try {
			fis = new FileInputStream(f);
			final CheckedInputStream cis = new CheckedInputStream(fis,
					new CRC32());
			final ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(cis));
			final ZipEntry ze = zis.getNextEntry();
			returnValue = false;

			if (ze != null) {
				returnValue = true;
			}
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return returnValue;
	}
}
