package org.javautil.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtils {
	// extends org.apache.commons.lang.time.DateUtils {
	private final GregorianCalendar greg;

	public DateTimeUtils() {

		greg = new GregorianCalendar();
	}

	public DateTimeUtils(final GregorianCalendar cal) {
		if (cal == null) {
			throw new IllegalArgumentException("cal is null");
		}
		greg = cal;

	}

	/**
	 * Check if a date is midnight in the current locale (default constructor)
	 * or the Calendar specified in the constructor.
	 * 
	 * @param d
	 * @return
	 * 
	 *         todo where is the test for this
	 */
	public boolean isMidnight(final Date d) {
		boolean retval;
		synchronized (greg) {
			greg.setTime(d);
			if (greg.get(Calendar.HOUR) == 0 && greg.get(Calendar.MINUTE) == 0
					&& greg.get(Calendar.SECOND) == 0
					&& greg.get(Calendar.MILLISECOND) == 0) {
				retval = true;
			} else {
				retval = false;
			}
		}
		return retval;
	}
}
