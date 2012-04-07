package org.javautil.document;

import java.util.ArrayList;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public enum MimeType {
	CSV, HTML, TAB_SEPARATED, DOM, XML, HTML_SELECT, EXCEL, EXCEL_XML, FIXED_WIDTH, XML_CHART, XML_ELEMENT;

	/**
	 * 
	 * @param val
	 * @return
	 */
	public static MimeType getAsMimeType(final String val) {
		MimeType retval = null;
		if (val == null) {
			throw new IllegalArgumentException("val is null");
		}
		final String upper = val.toUpperCase();
		retval = MimeType.valueOf(upper);
		return retval;
	}

	public static MimeType getMimeType(final String val) {
		if (val == null) {
			throw new IllegalArgumentException("val is null");
		}
		if (val.equalsIgnoreCase("excel")) {
			return EXCEL;
		}
		if (val.equalsIgnoreCase("csv")) {
			return CSV;
		}
		if (val.equalsIgnoreCase("html")) {
			return HTML;
		}
		if (val.equalsIgnoreCase("tab_separated")) {
			return TAB_SEPARATED;
		}
		if (val.equalsIgnoreCase("DOM")) {
			return DOM;
		}
		if (val.equalsIgnoreCase("XML")) {
			return XML;
		}
		if (val.equalsIgnoreCase("htmlSelect")) {
			return HTML_SELECT;
		}
		if (val.equalsIgnoreCase("EXCEL_XML")) {
			return EXCEL_XML;
		}
		if (val.equalsIgnoreCase("FIXED_WIDTH")) {
			return FIXED_WIDTH;
		}
		if (val.equalsIgnoreCase("CHART")) {
			return XML_CHART;
		}
		if (val.equalsIgnoreCase("XML_CHART")) {
			return XML_CHART;
		}
		if (val.equalsIgnoreCase("XML_ELEMENT")) {
			return XML_ELEMENT;
		}
		throw new IllegalArgumentException(getParseErrorMessage(val));
	}

	public static String getParseErrorMessage(final String val) {
		final StringBuilder b = new StringBuilder();
		b.append("unsupported mime time '" + val + "'");
		b.append(" acceptable values: ");
		for (final MimeType m : values()) {
			b.append(" ");
			b.append(m);
		}
		return b.toString();
	}

	public static MimeType parse(final String val) {
		return getMimeType(val);
	}

	public static String[] toStringArray() {
		final ArrayList<String> rc = new ArrayList<String>();
		for (final MimeType m : values()) {
			rc.add(m.toString());
		}
		return rc.toArray(new String[0]);
	}
}
