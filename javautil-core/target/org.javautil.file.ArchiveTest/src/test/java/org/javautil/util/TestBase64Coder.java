package org.javautil.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestBase64Coder {

	@Test
	// Test Base64Coder with constant strings.
	public void test1() {
		System.out.println("test1 started");
		check("Aladdin:open sesame", "QWxhZGRpbjpvcGVuIHNlc2FtZQ=="); // example
																		// from
																		// RFC
																		// 2617
		check("", "");
		check("1", "MQ==");
		check("22", "MjI=");
		check("333", "MzMz");
		check("4444", "NDQ0NA==");
		check("55555", "NTU1NTU=");
		check("abc:def", "YWJjOmRlZg==");
		System.out.println("test1 completed");
	}

	private static void check(final String plainText, final String base64Text) {
		final String s1 = Base64Coder.encodeString(plainText);
		final String s2 = Base64Coder.decodeString(base64Text);
		assertEquals(s1, base64Text);
		assertEquals(s2, plainText);

	}
}
