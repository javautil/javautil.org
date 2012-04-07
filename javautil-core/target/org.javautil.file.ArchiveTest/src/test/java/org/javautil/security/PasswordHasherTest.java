package org.javautil.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PasswordHasherTest {

	@Test
	public void test() {
		final String expected = "4f46d98a6e8cae551e0afb9c038bd07d";
		new PasswordHasher();
		String result = PasswordHasher.hashPassword("cheney", "evil");
		assertEquals(expected, result);
		result = PasswordHasher.hashPassword("Cheney", "evil");
		assertEquals(expected, result);
	}
}
