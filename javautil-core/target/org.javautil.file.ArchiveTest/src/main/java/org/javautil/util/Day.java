package org.javautil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 
 * TODO add a constructor for java.sql.Date TODO add a constructor for time in
 * milliseconds and nanoseconds. TODO add a getSqlDate method.
 * 
 * @author jjs
 * 
 *         TODO add constructor with formatter
 * 
 */

public class Day extends java.sql.Date {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7738877493647339711L;
	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy/MM/dd");
	private static GregorianCalendar cal = new GregorianCalendar();

	public Day() {

		super(new java.util.Date().getTime());
		synchronized (cal) {
			cal.setTime(this);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			super.setTime(cal.getTime().getTime());

		}
	}

	public static Day today() {
		return new Day();
	}

	/**
	 * 
	 * @param year
	 *            the four digit year 1988 = 1988, not the java.sql.Date format
	 *            of 88.
	 * @param month
	 *            1 jan to 12 December
	 * @param day
	 *            TODO this returns Standard Time for example Eastern Standard
	 *            Time not Daylight times if Daylight savings is in effect
	 */
	@SuppressWarnings("deprecation")
	public Day(final int year, final int month, final int day) {
		super(year - 1900, month - 1, day);
	}

	/**
	 * 
	 * @param year
	 *            the four digit year 1988 = 1988, not the java.sql.Date format
	 *            of 88.
	 * @param month
	 *            1 jan to 12 December
	 * @param day
	 */
	@SuppressWarnings("deprecation")
	public Day(final int year, final int month, final int day,
			final Calendar cal) {
		super(0L); // unfortunate
		// super(date.getTime());
		synchronized (cal) {
			cal.set(Calendar.YEAR, year - 1900);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DATE, day);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			super.setTime(cal.getTime().getTime());
		}
	}

	public Day(final java.util.Date date) {
		super(date != null ? date.getTime() : null);
		if (date == null) {
			throw new IllegalArgumentException("date is null");
		}
		synchronized (cal) {
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			super.setTime(cal.getTime().getTime());
		}
	}

	public Day(final java.util.Date date, final Calendar cal) {
		super(date.getTime());
		synchronized (cal) {
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			super.setTime(cal.getTime().getTime());
		}
	}

	public Day(final long time) {
		super(time);
		synchronized (cal) {
			cal.setTime(this);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			super.setTime(cal.getTime().getTime());
		}
	}

	public Day(final String date, final String format) throws ParseException {
		super((new java.util.Date()).getTime());
		final SimpleDateFormat formatter = new SimpleDateFormat(format);
		final java.util.Date d = formatter.parse(date);
		super.setTime(d.getTime());
	}

	public final void setDate(
			@SuppressWarnings("unused") final java.util.Date date) {
		throw new UnsupportedOperationException("immutable");
	}

	public void setDay(@SuppressWarnings("unused") final int day) {
		throw new UnsupportedOperationException("immutable");
	}

	@Override
	public void setMonth(final int month) {
		throw new UnsupportedOperationException("immutable");
	}

	@Override
	public void setTime(final long time) {
		throw new UnsupportedOperationException("immutable");
	}

	@Override
	public void setYear(final int year) {
		throw new UnsupportedOperationException("immutable");
	}

	@Override
	public String toString() {
		synchronized (formatter) {
			return formatter.format(this);
		}
	}

	public java.sql.Timestamp toTimestamp() {
		return new java.sql.Timestamp(this.getTime());
	}

	@Override
	public void setHours(final int i) {
		throw new UnsupportedOperationException("immutable");
	}

	@Override
	public void setMinutes(final int i) {
		throw new UnsupportedOperationException("immutable");
	}

	@Override
	public void setSeconds(final int i) {
		throw new UnsupportedOperationException("immutable");
	}
}
