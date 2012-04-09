package org.javautil.collections;

import static org.junit.Assert.assertEquals;

import org.javautil.util.Day;
import org.junit.Test;

public class TupleTest {

	/**
	 * We create a new tuple
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test1() {
		// Object[] arr =
		final Tuple<Comparable<?>> a = new Tuple(new Comparable<?>[] { "A",
				'b', 1.2, new Day(1998, 4, 8) });
		final Tuple<Comparable<?>> b = new Tuple(new Comparable<?>[] { "A",
				'b', 1.2, new Day(1998, 4, 8), 3 });
		final Tuple c = new Tuple(a, 3);
		assertEquals(b.hashCode(), c.hashCode());
		assertEquals(b, c);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2() {
		final String a = "A";
		final Character b = 'b';
		final Double c = 1.2;
		final Day d = new Day(1998, 4, 8);

		final Tuple<Comparable<?>> e = new Tuple(new Object[] { a, b, c, d });
		final Object[] objects = e.getObjects();
		assertEquals(4, objects.length);
		assertEquals(objects[0], a);
		assertEquals(objects[1], b);
		assertEquals(objects[2], c);
		assertEquals(objects[3], d);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3() {
		final String a = "A";
		final Character b = 'b';
		final Double c = 1.2;
		final Day d = new Day(1998, 4, 8);

		final Tuple<Comparable<?>> e = new Tuple(new Object[] { a, b, c, d });
		final Object[] objects = e.getObjects();
		assertEquals(4, objects.length);
		assertEquals(objects[0], a);
		assertEquals(objects[1], b);
		assertEquals(objects[2], c);
		assertEquals(objects[3], d);

	}

	@SuppressWarnings("unchecked")
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void test4() {
		final String a = "A";
		final Tuple t = new Tuple(new Object[] { a });
		t.getDouble(0);

	}

	@SuppressWarnings("unchecked")
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void test5() {
		final String a = "A";
		final Tuple t = new Tuple(new Object[] { a });
		t.getDouble(1);

	}

	@Test
	public void noSize() {
		final Tuple t = new Tuple(new Object[0]);
		t.getObjects();
	}
}
