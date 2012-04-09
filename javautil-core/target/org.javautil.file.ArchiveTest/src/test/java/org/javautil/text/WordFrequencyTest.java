package org.javautil.text;

import static org.junit.Assert.assertEquals;

import org.javautil.collections.ArrayComparator;
import org.junit.Test;

public class WordFrequencyTest {
	private final ArrayComparator ac = new ArrayComparator();

	@Test
	public void test() {
		final WordFrequency freq = new WordFrequency();

		freq.addWord("Blood");
		freq.addWord("Sweat");
		freq.addWord("Tears");
		freq.addWord("Tears");
		final String[] words = freq.getWordArray();

		ac.ensureEquals(new String[] { "BLOOD", "SWEAT", "TEARS" }, words);
	}

	@Test
	public void test2() {
		final WordFrequency freq = new WordFrequency();
		freq.setUpperCaseWords(false);
		freq.addWord("Blood");
		freq.addWord("Sweat");
		freq.addWord("Tears");
		freq.addWord("Tears");
		final String[] words = freq.getWordArray();

		ac.ensureEquals(new String[] { "Blood", "Sweat", "Tears" }, words);
		assertEquals(2, freq.getWordFrequency("Tears"));
		assertEquals(0, freq.getWordFrequency("TEARS"));
	}

}
