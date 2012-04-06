package org.javautil.text;

import org.apache.log4j.Logger;

// TODO implement choice for handling  skip, replace, toHex with escape character
public class StringCleanerImpl implements StringCleaner {
	private final Logger logger = Logger.getLogger(getClass());

	@Override
	public String clean(final String in) {
		String result = null;
		if (in != null) {
			if (hasObjectionableCharacters(in)) {
				final StringBuilder sb = new StringBuilder(in.length());
				final char[] chars = in.toCharArray();
				for (final char c : chars) {
					if (isObjectionableCharacter(c)) {
						// sb.append('~');
					} else {
						sb.append(c);
					}
				}

				result = sb.toString();
				if (logger.isDebugEnabled()) {
					final String message = "was: '" + toHex(in) + "' is '"
							+ toHex(result) + "'";
					logger.debug(message);
				}
			} else {
				result = in;
			}
		}
		return result;
	}

	public boolean hasObjectionableCharacters(final String in) {
		final char[] chars = in.toCharArray();
		boolean result = false;
		for (final char c : chars) {
			if (isObjectionableCharacter(c)) {
				result = true;
				break;
			}
		}
		return result;
	}

	boolean isObjectionableCharacter(final char in) {
		boolean result = false;
		if (in < 0x20 || in > 0x7f) {
			result = true;
		}
		// switch(Character.getType(in)) {
		// case Character.CURRENCY_SYMBOL:
		// case Character.DASH_PUNCTUATION:
		// case Character.DECIMAL_DIGIT_NUMBER:
		// case Character.ENCLOSING_MARK:
		// case Character.END_PUNCTUATION:
		// case Character.FINAL_QUOTE_PUNCTUATION:
		// case Character.INITIAL_QUOTE_PUNCTUATION:
		// case Character.LETTER_NUMBER:
		// case Character.SPACE_SEPARATOR:
		// break;
		// default:
		// result = true;
		// break;
		// }
		return result;
	}

	public String toHex(final String in) {
		if (in == null) {
			throw new IllegalArgumentException("in is null");
		}
		final StringBuilder b = new StringBuilder(in.length() * 5);
		final char[] chars = in.toCharArray();
		for (final char c : chars) {
			// b.append( Integer.toHexString(c).toUpperCase());
			if (isObjectionableCharacter(c)) {
				b.append("%");
				b.append(Integer.toHexString(c).toUpperCase());
			} else {
				b.append(c);
			}
		}

		return b.toString();
	}
}
