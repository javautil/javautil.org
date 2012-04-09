package org.javautil.text;

// TODO does this belong in text or util package?
public enum CommonDateFormat {
	ISO_DATE, ISO_SECOND, ISO_NANO_SECOND, SECONDS;
	
	public String getFormat() {
		String retval = null;
		switch (this) {
		case ISO_DATE:
			retval = "yyyy-MM-dd";
			break;
		case ISO_SECOND:
			retval = "yyyy-MM-dd HH:mm:ss";
			break;
		case SECONDS:
			retval = "HH:mm:ss";
			break;
//		case ISO_NANO_SECOND:
//			retval = "yyyy-MM-dd hh:mm:sss";
//			break;
		}
		return retval;
	}
}
