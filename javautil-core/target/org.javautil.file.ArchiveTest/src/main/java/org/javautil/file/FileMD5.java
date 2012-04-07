package org.javautil.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Should be based on InputStream
 * 
 * @author jjs
 * 
 */
public class FileMD5 {

	public static BigInteger getMD5Digest(final File f) throws IOException {
		System.currentTimeMillis();
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		final FileInputStream fr = new FileInputStream(f);
		final byte[] buff = new byte[8192];
		int bytes;
		while ((bytes = fr.read(buff, 0, buff.length)) > -1) {
			m.update(buff, 0, bytes);
		}
		fr.close();

		final BigInteger digest = new BigInteger(1, m.digest());
		System.currentTimeMillis();
		// System.out.println("elapsed " + (end - start) / 1000);
		return digest;
	}

	public static String getMD5String(final File f) throws IOException {
		final BigInteger digest = getMD5Digest(f);
		return digest.toString(16);
	}

	// public static void main(String[] args) throws IOException {
	// File f = new File(args[0]);
	//
	// BigInteger digest = getMD5Digest(f);
	//
	// System.out.println(digest.toString(16));
	// }
}
