package org.javautil.security;

public class PasswordHasher {
	public static String hashPassword(final String userName,
			final String password) {
		if (userName == null) {
			throw new IllegalArgumentException("userName is null");
		}
		if (password == null) {
			throw new IllegalArgumentException("password is null");
		}
		final Md5Hasher hasher = new Md5Hasher();
		final String retval = hasher
				.hashAsHex(password, userName.toUpperCase());
		return retval;
	}
}
