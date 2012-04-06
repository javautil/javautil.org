package com.dbexperts.oracle.trace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegularExpressionHelper {


		public static int getInt(final String recordText, final Pattern pattern) {
			if (recordText == null) {
				throw new IllegalStateException("recordText is null");
			}
			int returnValue = -1;
			final Matcher m = pattern.matcher(recordText);
			if (m.find()) {
				returnValue = Integer.parseInt(m.group(1));
			}
			return returnValue;
		}

		public static long getLong(final String recordText, final Pattern pattern) {
			long returnValue = -1;
			final Matcher m = pattern.matcher(recordText);
			if (m.find()) {
				returnValue = Long.parseLong(m.group(1));
			}
			return returnValue;
		}

		public static String getString(final String recordText, final Pattern pattern) {
			String returnValue = null;
			final Matcher m = pattern.matcher(recordText);
			if (m.find()) {
				returnValue = m.group(1);
			}
			return returnValue;
		}

}
