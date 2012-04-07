package org.javautil.text;

/**
 * 
 * Expands tab characters to spaces.
 * 
 * Supports fixed tab sizes only.
 * 
 * @author jjs
 */
public class TabExpander {

	private final char tabCharacter = '\t';

	public static int fillCount(final int pos, final int tabWidth) {
		if (tabWidth < 1) {
			throw new IllegalArgumentException("illegal tabwidth: " + tabWidth);
		}

		final int position = pos % tabWidth;
		final int fill = tabWidth - position;
		return fill;
	}

	public String expand(final String in, final int tabWidth) {
		if (tabWidth < 1) {
			throw new IllegalArgumentException("tabWidth must be > 0");
		}
		String retval = null;
		if (in != null) {

			final char[] chars = in.toCharArray();
			final StringBuilder b = new StringBuilder();
			for (int idx = 0; idx < chars.length; idx++) {
				if (chars[idx] == tabCharacter) {
					int fillCount = fillCount(idx, tabWidth);
					while (fillCount-- > 0) {
						b.append(" ");
					}
				} else {
					b.append(chars[idx]);
				}
			}
			retval = b.toString();

		}
		return retval;
	}

	/**
	 * @todo currently expands all tabs and doesn't consider tabstops
	 * @param text
	 *            StringBuffer
	 * @param tabSize
	 *            int
	 * @return StringBuffer
	 */
	public StringBuffer expandLeadingTabs(final String text, final int tabSize) {
		final String[] lines = text.split("\n");
		final StringBuffer pad = new StringBuffer();
		for (int i = 0; i < tabSize; i++) {
			pad.append(" ");
		}
		final StringBuffer buff = new StringBuffer();
		for (final String line : lines) {
			buff.append(line.replaceAll("\t", "    "));
			// buff.append(line);
			buff.append("\n");
		}
		return buff;
	}

}
