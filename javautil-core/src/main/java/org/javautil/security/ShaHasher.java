package org.javautil.security;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class ShaHasher {

	public String hashAsBase64(final int strength, final String password,
			final String salt) {
		final byte[] result = hashAsByteArray(strength, password, salt);
		return new String(Base64.encodeBase64(result));
	}

	public String hashAsHex(final int strength, final String password,
			final String salt) {
		final byte[] result = hashAsByteArray(strength, password, salt);
		return new String(Hex.encodeHex(result));
	}

	public byte[] hashAsByteArray(final int strength, final String password,
			final String salt) {
		try {
			final MessageDigest messageDigest = MessageDigest
					.getInstance("SHA-" + strength);
			final String saltedPass = HashUtil.mergePasswordAndSalt(password,
					salt, false);
			return messageDigest.digest(saltedPass.getBytes("UTF-8"));
		} catch (final Exception e) {
			throw new RuntimeException("failure while hashing password", e);
		}
	}
}
