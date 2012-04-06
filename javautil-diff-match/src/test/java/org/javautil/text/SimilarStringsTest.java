package org.javautil.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;

public class SimilarStringsTest {
	private final ArrayList<String> strings = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("BEATLES");
			add("BEATELS");
			add("BEATLESS");
			add("BEAT");
		}
	};


	@Test
	public void test() {
		final StringSimilarity similar = new StringSimilarity();
		final Set<String> candidates = similar.getSimilarStringSet("BEATLES",
				strings, .80f, 2);
		assertEquals(3, candidates.size());
		assertTrue(candidates.contains("BEATLES"));
		assertTrue(candidates.contains("BEATELS"));
		assertTrue(candidates.contains("BEATLESS"));
	}
}
