package org.javautil.mp3;

import java.util.HashSet;

import org.javautil.collections.ArrayComparator;
import org.javautil.text.WordExtractor;
import org.javautil.text.WordExtractorImpl;
import org.junit.Test;

public class WordExtractorImplTest {
	private final ArrayComparator ac = new ArrayComparator();

	// TOOD this has been moved to ArrayComparator
	void ensureEquals(final String[] expected, final String[] actual) {
		if (ac.compare(expected, actual) != 0) {
			final StringBuilder b = new StringBuilder();
			b.append("expected ");
			for (final String s : expected) {
				b.append("'");
				b.append(s);
				b.append("' ");
			}
			b.append(" actual ");
			for (final String s : actual) {
				b.append("'");
				b.append(s);
				b.append("' ");
			}
			throw new IllegalArgumentException(b.toString());
		}
	}

	@Test
	public void test() {
		final WordExtractor extractor = new WordExtractorImpl();

		String[] result = extractor.getWords("Blood, Sweat & Tears");
		ensureEquals(new String[] { "BLOOD", "SWEAT", "TEARS" }, result);
		result = extractor.getWords("Blood Sweat And Tears");
		ensureEquals(new String[] { "AND", "BLOOD", "SWEAT", "TEARS" }, result);
		result = extractor.getWords("Blood, Sweat And Tears");
		ensureEquals(new String[] { "AND", "BLOOD", "SWEAT", "TEARS" }, result);
		result = extractor.getWords("Blood, Sweat, And Tears");
		ensureEquals(new String[] { "AND", "BLOOD", "SWEAT", "TEARS" }, result);
	}

	@Test
	public void test2() {
		final WordExtractor extractor = new WordExtractorImpl();
		final HashSet<String> excludeSet = new HashSet<String>();
		excludeSet.add("AND");
		excludeSet.add("THE");
		extractor.setExcludeWords(excludeSet);
		String[] result = extractor.getWords("Blood, Sweat & Tears");
		ensureEquals(new String[] { "BLOOD", "SWEAT", "TEARS" }, result);
		result = extractor.getWords("Blood Sweat And Tears");
		ensureEquals(new String[] { "BLOOD", "SWEAT", "TEARS" }, result);
		result = extractor.getWords("Blood, Sweat And Tears");
		ensureEquals(new String[] { "BLOOD", "SWEAT", "TEARS" }, result);
		result = extractor.getWords("Blood, Sweat, And Tears");
		ensureEquals(new String[] { "BLOOD", "SWEAT", "TEARS" }, result);
	}
}
