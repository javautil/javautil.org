package org.javautil.document.style;

import java.util.TreeMap;

/**
 * Basic implementation of FontAttributes taking most of its functionality from
 * java.util.TreeMap.
 * 
 * @author bcm
 */
public class FontAttributesImpl extends TreeMap<String, String> implements
		FontAttributes {

	private static final long serialVersionUID = -6941060441403605723L;

	@Override
	public String get(final String fontAttributeName) {
		return super.get(fontAttributeName);
	}

	@Override
	public int hashCode() {

		final StringBuilder sb = new StringBuilder();
		for (final String key : keySet()) {
			sb.append(key);
			sb.append("=");
			sb.append(get(key));
			sb.append(":");
		}
		final int hashCode = sb.toString().hashCode();

		return hashCode;
	}

	@Override
	public FontAttributes copyOf() {
		return (FontAttributes) super.clone();
	}

	// TODO: document this, make it to do something, tell what these vales are
	// and what they mean
	public String getBoldness() {
		return get(FontAttributes.BOLDNESS);
	}

	// TODO: document this, make it to do something, tell what these vales are
	// and what they mean
	public String setBoldness(final String boldNess) {
		return put(FontAttributes.BOLDNESS, boldNess);
	}

	public String setUnderlineStyle(final String underlineStyle) {
		return put(FontAttributes.KEY_UNDERLINE_STYLE, underlineStyle);
	}

	public String getUnderlineStyle() {
		return get(FontAttributes.KEY_UNDERLINE_STYLE);
	}

	public String setWordWrap(final String wordWrap) {
		return put(FontAttributes.KEY_WORD_WRAP, wordWrap);
	}

	public String getWordWrap() {
		return get(FontAttributes.KEY_WORD_WRAP);
	}

}
