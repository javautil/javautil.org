package org.javautil.util;

import java.util.Random;

public class RandomUtils {
	// Random string. Pilfered from Chris Ellis. via Oracle DBMS_RANDOM

	public static final char[] UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();
	public static final char[] LOWER_CASE = "abcdefghijklmnopqrstuvwxyz"
			.toCharArray();
	public static final char[] ALPHA = (new String(UPPER_CASE) + new String(
			LOWER_CASE)).toCharArray();
	public static final char[] NUMERIC = "0123456789".toCharArray();
	public static final char[] UPPER_ALPHANUMERIC = (new String(UPPER_CASE) + new String(
			NUMERIC)).toCharArray();
	public static final char[] OTHER_PRINTABLE_ASCII = " !\"#$%&''()*+,-./:;<=>?@"
			.toCharArray();
	public static final char[] PRINTABLE_ASCII = (new String(ALPHA)
			+ new String(NUMERIC) + new String(OTHER_PRINTABLE_ASCII))
			.toCharArray();

	private final Random random = new Random();

	public void setSeed(final long seed) {
		random.setSeed(seed);
	}

	/**
	 * Not even unit tested.
	 * 
	 * @param lower
	 * @param upper
	 * @return
	 */
	public int getRandomInt(final int lower, final int upper) {

		final double r = random.nextDouble();
		final int range = upper - lower;
		final int val = ((int) (r * range)) + lower;
		return val;

	}

	public String getRandomString(final char characterDomain,
			final int minLength, final int maxLength) {
		char[] domain;

		switch (characterDomain) {
		case 'u':
			domain = UPPER_CASE;
			break;
		case 'l':
			domain = LOWER_CASE;
			break;
		case 'a':
			domain = ALPHA;
			break;
		case 'n':
			domain = NUMERIC;
			break;
		default:
			throw new IllegalArgumentException();
		}
		final int len = getRandomInt(minLength, maxLength);
		final int upper = domain.length - 1;
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			final int index = getRandomInt(0, upper);
			sb.append(domain[index]);
		}
		return sb.toString();
	}

}
