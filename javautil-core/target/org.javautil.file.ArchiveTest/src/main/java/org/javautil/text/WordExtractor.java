package org.javautil.text;

import java.util.Set;

public interface WordExtractor {

	public void setExcludeWords(Set<String> excludeSet);

	public String[] getWords(String text);

	// TODO document
	public String getConcatenatedString(String[] strings);
}
