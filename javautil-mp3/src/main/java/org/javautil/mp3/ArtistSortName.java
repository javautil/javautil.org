package org.javautil.mp3;

import java.util.regex.Pattern;

public class ArtistSortName {
	Pattern trailingThe = Pattern.compile(".*, *THE");

	String getSortName(final String name) {
		String result = name;
		if (name != null) {
			String current = name.toUpperCase();
			current = current.trim();
			current = processThe(current);
			result = current;
		}
		return result;
	}

	String processThe(final String in) {
		String result = in;
		if (in.endsWith(" THE")) {
			result = in.substring(0, in.length() - 5);
		}
		result = result.trim();
		if (result.endsWith(",")) {
			result = result.substring(0, result.length() - 1);
		}
		if (result.startsWith("THE ") & (result.length() > 4)) {
			result = result.substring(4);
		}
		return result;
	}
}
