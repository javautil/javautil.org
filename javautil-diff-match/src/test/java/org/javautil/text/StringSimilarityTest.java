package org.javautil.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class StringSimilarityTest {


	@Ignore
	@Test
	public void test() {
		final StringSimilarity differ = new StringSimilarity();

		final String text1 = "Blood, Sweat,and Tears"; // length 22
		final String text2 = "Blood, Sweat & Tears";
		int diffCount = differ.getDiffCount(text1, text2);
		assertEquals(4, diffCount);
		final float ratio = differ.getMatchRatio(text1, text2);
		assertEquals(1.0 - 4f / 22, ratio, .01);

	
		diffCount = differ.getDiffCount("BEATLES", "BEATELS");
		assertEquals(2, diffCount);

	}

	@Test
	public void test2() {
		final StringSimilarity differ = new StringSimilarity();
		final String a = "BEATLES";
		final String b = "BEATELS";

		final int diffCount = differ.getDiffCount(a, b);
		assertEquals(2, diffCount);
		final float ratio = differ.getMatchRatio(a, b);
		assertEquals(1.0 - (2.0 / 7), ratio, .1);
		assertTrue(differ.isSimilar(a, b, 80, 2));
		assertTrue(differ.isSimilar(a, b, 90, 2));
		assertFalse(differ.isSimilar(a, b, 90, 1));

	}
}
