package org.javautil.collections;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.optional.junit.BaseTest;
import org.junit.Test;

public class ArrayComparatorTest extends BaseTest {

	private final ArrayComparator ac = new ArrayComparator();

	private final Logger logger = Logger.getLogger(getClass());

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testTroubleNotArray() {
		final Double trouble = new Double(1);
		final Double[] steve = new Double[0];
		ac.compare(trouble, steve);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testNotArray() {
		final Double trouble = new Double(1);
		final Double[] steve = new Double[0];
		ac.compare(steve, trouble);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testNull1() {
		final Double[] trouble = null;
		final Double[] steve = null;
		ac.compare(steve, trouble);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testNull2() {
		final Double[] trouble = new Double[2];
		final Double[] steve = null;
		ac.compare(steve, trouble);
	}

	@Test(expected = org.javautil.collections.ComparisonException.class)
	public void testNotCo() {
		final Double[] trouble = new Double[] { new Double(1), new Double(3) };
		final String[] steve = new String[] { "1", "3" };
		ac.compare(steve, trouble);
	}

	@Test
	public void testDifferentArrayLengths() {
		final String[] jimmi = new String[] { "1", "3", "4" };
		final String[] stevie = new String[] { "1", "3" };
		final int rc = ac.compare(stevie, jimmi);
		assertEquals(-3, rc); // I don't know that was less occurred on the
								// third element base 1
	}

	@Test
	public void testDifferentArrayLengths2() {
		final String[] jimmi = new String[] { "1", "3", "4" };
		final String[] stevie = new String[] { "1", "3" };
		final int rc = ac.compare(jimmi, stevie);
		assertEquals(3, rc); // I don't know that was less occurred on the third
								// element base 1
	}

	@Test
	// (expected=org.javautil.collections.ComparisonException.class)
	public void testNotCompatible() {
		final NotComparable[] trouble = new NotComparable[2];
		final String[] steve = new String[] { "1", "3" };
		ac.compare(steve, trouble);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testNotComparableObject() {
		final NotComparable[] trouble = new NotComparable[2];
		final String[] steve = new String[] { "1", "3" };
		ac.compareObjects(trouble, steve);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testNotComparable() {
		logger.debug("in testNotComparable");
		final NotComparable[] trouble = new NotComparable[] {
				new NotComparable(), new NotComparable() };
		final String[] steve = new String[] { "1", "3" };
		ac.compare(trouble, steve);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testNotComparable2() {
		final NotComparable[] trouble = new NotComparable[] {
				new NotComparable(), new NotComparable() };
		final String[] steve = new String[] { "1", "3" };
		ac.compare(steve, trouble);
	}

	@Test
	public void testNull() {
		final Double[] trouble = new Double[2];
		final Double[] steve = new Double[2];
		final int rc = ac.compare(trouble, steve);
		assertEquals(0, rc);
	}

	@Test
	public void testFirstNull() {
		final Double[] trouble = new Double[2];
		final Double[] steve = new Double[] { new Double(1), new Double(2) };
		final int rc = ac.compare(trouble, steve);
		assertEquals(-1, rc);
	}

	@Test
	public void testSecondNull() {
		final Double[] trouble = new Double[2];
		final Double[] steve = new Double[] { new Double(1), new Double(2) };
		final int rc = ac.compare(steve, trouble);
		assertEquals(1, rc);
	}

	public class NotComparable {

	}

	// public static void main(String[] args) {
	// ArrayComparatorTest2 t = new ArrayComparatorTest2();
	// t.testNotCo();
	// }
}
