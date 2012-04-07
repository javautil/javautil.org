package org.javautil.collections;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NullCompareTest {

	private static final String dog = "dog";

	@Test
	public void test1() {

		NullCompare nc = new NullCompare(true); // null collates low
		assertEquals(-1, nc.compare(null, dog));
		assertEquals(1, nc.compare(dog, null));
		assertEquals(0, nc.compare(null, null));
		assertEquals(-1, nc.compare("cat", "dog"));
		nc = new NullCompare(false); // null collates low
		assertEquals(1, nc.compare(null, dog));
		assertEquals(-1, nc.compare(dog, null));
		assertEquals(0, nc.compare(null, null));
		assertEquals(-1, nc.compare("cat", "dog"));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void test2() {

		final NullCompare nc = new NullCompare(true); // null collates low
		assertEquals(-1, nc.compare(2, dog));

	}
}
