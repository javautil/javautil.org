package org.javautil.text;

import java.util.Collection;

/**
 * 
 * @author jjs
 * 
 */
public class CommaFormatter {
	/**
	 * 
	 * maxLineLength is only
	 * 
	 * @param words
	 * @param leadInLength
	 * @param maxLineLength
	 * @return
	 */
	public static String formatWithCommas(final Collection<String> words,
			final int leadInLength, final int maxLineLength) {
		int maxLength = 0;
		for (final String word : words) {
			if (word.length() > maxLength) {
				maxLength = word.length();
			}
		}
		final StringBuilder mb = new StringBuilder();
		for (int i = 0; i < leadInLength; i++) {
			mb.append(" ");
		}
		final String margin = mb.toString();
		final int workLength = maxLineLength - maxLength;
		final int wordsPerLine = workLength / (maxLength + 2);

		final StringBuilder b = new StringBuilder();
		int wordsThisLine = 0;

		b.append(margin);

		System.out.println(b.toString());
		int wordNbr = 0;
		for (final String word : words) {
			wordNbr++;

			b.append(word);
			if (wordNbr < words.size()) {
				b.append(",");
			}
			for (int i = word.length(); i <= maxLength; i++) {
				b.append(" ");
			}

			wordsThisLine++;

			if (wordsThisLine >= wordsPerLine) {
				b.append("\n");
				b.append(margin);
				wordsThisLine = 0;
			}

		}
		final String retval = b.toString();
		System.out.println(b);
		return retval;
	}
}
