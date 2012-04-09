package org.javautil.xml;

public class XmlTagUtils {

	public static String getTagName(final String tag) {
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < tag.length(); i++) {
			final char character = tag.charAt(i);
			switch (character) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '\"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#039;");
				break;
			case '\\':
				result.append("&#092;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case ' ':
				break;
			default:
				result.append(character);
			}
		}
		return result.toString();
	}
}