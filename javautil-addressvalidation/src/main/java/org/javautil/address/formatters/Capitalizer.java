package org.javautil.address.formatters;

import java.util.TreeMap;

/**
 * <p>Capitalizer class.</p>
 *
 * @author jjs Upper Cases first letter of all words that are not predefined.
 *         Predefined rules may be set to all upper case, all lower case, as
 *         specified by a literal.
 * @version $Id: Capitalizer.java,v 1.4 2012/03/04 12:31:18 jjs Exp $
 */
public class Capitalizer {
	private static TreeMap<String, String> capRule = new TreeMap<String, String>();
	static {
		capRule.put("and", "and");
		capRule.put("or", "or");
		capRule.put("of", "of");
		capRule.put("a", "a");
		capRule.put("i", "I");
		capRule.put("c/o", "c/o");
	}

	private static TreeMap<String, String> streetCapRule = new TreeMap<String, String>();
	static {
		streetCapRule.put("and", "and");
		streetCapRule.put("or", "or");
		streetCapRule.put("of", "of");
		streetCapRule.put("a", "a");
		streetCapRule.put("i", "I");
		streetCapRule.put("ih", "IH");
		streetCapRule.put("fm", "FM");
		streetCapRule.put("nw", "NW");
		streetCapRule.put("ne", "NE");
		streetCapRule.put("se", "SE");
		streetCapRule.put("sw", "SW");
		streetCapRule.put("n.w.", "NW");
		streetCapRule.put("n.e.", "NE");
		streetCapRule.put("s.e.", "SE");
		streetCapRule.put("s.w.", "SW");
		streetCapRule.put("s.", "S.");
		streetCapRule.put("n.", "N.");
		streetCapRule.put("e", "E.");
		streetCapRule.put("w.", "W.");
		streetCapRule.put("us", "US");

	}

	// should look for roman numerals
	// this will screw up Concourse A
	/**
	 * Capitalizes the input String excluding some words.
	 *
	 * TODO externalize rule map TODO should be in javautil-core If input is
	 * null, returns an zero length string.
	 *
	 * @param in a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String initCaps(final String in) {
		String retval = "";
		if (in != null) {
			final String lower = in.toLowerCase();
			final String[] words = lower.split(" ");
			final StringBuilder b = new StringBuilder();
			for (int i = 0; i < words.length; i++) {
				final String word = words[i];

				final String toWord = capRule.get(word);
				if (toWord != null) {
					b.append(toWord);

				} else {
					if (word.length() == 1) {
						b.append(word.toUpperCase());
					} else if (word.length() > 1) {
						b.append(word.substring(0, 1).toUpperCase());
						b.append(word.substring(1));
					}

				}
				if (i < words.length - 1) {
					b.append(" ");
				}
			}
			retval = b.toString();
		}
		return retval;
	}

	/**
	 * <p>initCapsStreet.</p>
	 *
	 * @param in a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String initCapsStreet(final String in) {
		String retval = "";
		if (in != null) {
			final String lower = in.toLowerCase();
			final String[] words = lower.split(" ");

			final StringBuilder b = new StringBuilder();
			for (int i = 0; i < words.length; i++) {
				final String word = words[i];

				String toWord = streetCapRule.get(word);
				if (toWord == null) {
					if (word.length() == 1) {
						toWord = word.toUpperCase();

					} else if (word.length() > 1) {
						toWord = word.substring(0, 1).toUpperCase()
								+ word.substring(1);
					}

				}

				b.append(toWord);
				if (i < words.length - 1) {
					b.append(" ");
				}
			}
			retval = b.toString();
		}
		return retval;
	}
}
