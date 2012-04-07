package org.javautil.security;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashUtil {

	public static String md5sum(final String string) {
		try {
			final MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(string.getBytes(), 0, string.length());
			final String md5 = new BigInteger(1, m.digest()).toString(16);
			return md5;
		} catch (final Exception ex) {
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * borrowed from spring security's BasePasswordEncoder
	 */
	public static String mergePasswordAndSalt(String password,
			final Object salt, final boolean strict) {
		if (password == null) {
			password = "";
		}
		if (strict && (salt != null)) {
			if ((salt.toString().lastIndexOf("{") != -1)
					|| (salt.toString().lastIndexOf("}") != -1)) {
				throw new IllegalArgumentException(
						"Cannot use { or } in salt.toString()");
			}
		}
		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}
}
