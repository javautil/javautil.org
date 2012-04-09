package org.javautil.text;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class WordExtractorImpl implements WordExtractor {

	private final Logger logger = Logger.getLogger(getClass());

	private Set<String> excludeWords;

	private final HashSet<String> words = new HashSet<String>();
	private final StringBuffer wordBuffer = new StringBuffer();

	void addWord() {
		if (wordBuffer.length() > 0) {
			final String word = wordBuffer.toString().toUpperCase();
			if (logger.isDebugEnabled()) {
				logger.debug("adding '" + word + "'");
			}
			if (excludeWords == null) {
				words.add(word);
			} else if (!excludeWords.contains(word)) {
				words.add(word);
			}
			wordBuffer.setLength(0);
		}
	}

	@Override
	public String[] getWords(final String text) {
		String[] results = null;
		words.clear();
		wordBuffer.setLength(0);
		if (text != null) {
			final char[] chars = text.toCharArray();
			for (final char c : chars) {
				if (logger.isDebugEnabled()) {
					final int type = Character.getType(c);
					logger.debug("'" + c + "' is type " + type);
				}
				if (c >= 0x30 && c <= 0x7f) {
					wordBuffer.append(c);
				} else {
					addWord();
				}
			}
			addWord();
			final Object o = words.toArray();
			if (logger.isDebugEnabled()) {
				logger.debug("o is" + o.getClass().getName() + " " + o);
			}
			results = new String[words.size()];
			int idx = 0;
			for (final String w : words) {
				results[idx++] = w;
			}
			Arrays.sort(results);
			// results = (String[]) words.toArray();
		}
		return results;
	}

	@Override
	public void setExcludeWords(final Set<String> excludeSet) {
		this.excludeWords = excludeSet;

	}

	@Override
	public String getConcatenatedString(final String[] strings) {
		final StringBuilder sb = new StringBuilder();

		if (strings != null) {
			boolean needsSpace = false;
			for (final String string : strings) {
				sb.append(string);
				if (needsSpace) {
					sb.append(string);
				}
				needsSpace = true;
			}
		}
		return sb.toString();
	}
}
