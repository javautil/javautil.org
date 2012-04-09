package org.javautil.text;

public enum Trim {
	TRIM_LEADING_WHITESPACE, TRIM_TRAILING_WHITESPACE, TRIM_LEADING_AND_TRAILING_WHITESPACE, TRIM_NONE;
	
	public void trim(String in) {
		switch (this) {
		case TRIM_LEADING_WHITESPACE:
			break;
		case TRIM_TRAILING_WHITESPACE:
			break;
		case TRIM_LEADING_AND_TRAILING_WHITESPACE:
			break;
		case TRIM_NONE:
			break;
		}
	}
}
