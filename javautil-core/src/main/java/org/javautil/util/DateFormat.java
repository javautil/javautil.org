package org.javautil.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Simple hardwired replacement for something like SimpleDateFormat.
 * 
 * The constrained options versus the formatted string has a benefit of lack of
 * surprises over picking the wrong MM for minute versus month for instance.
 * 
 * Additionally this supports quarters, the real reason I wrote it.
 * 
 * As an added plus it is very fast.
 * 
 * @author jjs
 * 
 */
public enum DateFormat {
	/**
	 * example 09-11-2001
	 */
	DASH_MM_DD_YYYY,
	/**
	 * example 09-2001
	 */
	DASH_MM_YYYY, DASH_QQ_YYYY, YYYY, YYYYMMDD;

	public String format(final Calendar cal, final Date date) {
		String retval = null;
		cal.setTime(date);
		final int month = cal.get(Calendar.MONTH) + 1;
		final int day = cal.get(Calendar.DAY_OF_MONTH);
		final int yr = cal.get(Calendar.YEAR);
		final StringBuilder sb = new StringBuilder();
		switch (this) {

		case DASH_MM_DD_YYYY: {
			if (month < 10) {
				sb.append("0");
			}
			sb.append(month);
			sb.append("-");
			if (day < 10) {
				sb.append("0");
			}
			sb.append(day);
			sb.append("-");
			sb.append(yr);
			retval = sb.toString();
		}
			break;
		case YYYYMMDD:
			sb.append(yr);
			if (month < 10) {
				sb.append("0");
			}
			sb.append(month);
			if (day < 10) {
				sb.append("0");
			}
			sb.append(day);
			retval = sb.toString();
			break;
		case DASH_MM_YYYY: {
			if (month < 10) {
				sb.append("0");
			}
			sb.append(month);
			sb.append("-");
			sb.append(yr);
			retval = sb.toString();
		}
			break;
		case DASH_QQ_YYYY: {
			switch (month) {
			case 1:
			case 2:
			case 3:
				sb.append(1);
				break;
			case 4:
			case 5:
			case 6:
				sb.append(2);
				break;
			case 7:
			case 8:
			case 9:
				sb.append(3);
				break;
			case 10:
			case 11:
			case 12:
				sb.append(4);
				break;
			}
			sb.append("-");
			sb.append(yr);
			retval = sb.toString();
		}
			break;
		case YYYY: {
			retval = Integer.toString(yr);
		}

		}
		return retval;
	}
}
