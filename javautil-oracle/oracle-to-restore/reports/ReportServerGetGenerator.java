package com.dbexperts.oracle.reports;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ReportServerGetGenerator {

	public static boolean wrapArgInQuotes = false;
	
	public static String getServerRequest(ReportServerInfo info, Map<String, String> parms) {
		return getServerUrl(info) + parmsToArgs(parms);
	}

	
	public static String getServerRequest(String rendererUrl, String entryId, Map<String, String> parms) {
		return rendererUrl + "?" + entryId + "&" + parmsToArgs(parms);
	}
	public static String getServerUrl(ReportServerInfo info) {
		StringBuilder b = new StringBuilder();
		b.append("http://");
		b.append(info.getReportServerName());
		b.append(":");
		b.append(info.getServerPort());
		b.append("/");
		b.append(info.getApplicationPath());
		b.append("?");
		b.append(info.getReportServerConfigurationEntry());
		b.append("&");
		return b.toString();
	}

	public static String parmsToArgs(Map<String, String> parms) {
		StringBuilder b = new StringBuilder();
		String amp = "";
		for (String key : parms.keySet()) {
			b.append(amp);
			b.append(encodePair(key, parms.get(key)));
			amp = "&";
		}
		return b.toString();
	}

	public static String encodePair(String name, String value) {
		final String UTF8 = "UTF-8";
		StringBuilder b = new StringBuilder();
		try {
			b.append(URLEncoder.encode(name, UTF8));
			b.append("=");
			if (wrapArgInQuotes) {
			b.append(URLEncoder.encode("\"" + value + "\"", "UTF8"));
			} else {
				b.append(URLEncoder.encode( value , "UTF8"));
			}
			return b.toString();
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}

	}
}
