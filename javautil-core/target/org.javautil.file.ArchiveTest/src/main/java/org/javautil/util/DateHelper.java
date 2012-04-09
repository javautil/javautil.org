package org.javautil.util;

// wtf
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * todo document and write unit tests todo review name Helper or Util
 * 
 * @author jjs @ dbexperts dot com
 * 
 */

public class DateHelper {
	static final long ONE_HOUR = 60 * 60 * 1000L; // number of milliseconds in
													// an hour
	private final java.util.Date date = null;

	private final String errorMessage = null;
	private static Logger logger = Logger.getLogger("DateHelper.class");

	boolean valid = true;

	// todo move remove code from here that is in date arithmetic
	// @todo document
	static public java.util.Date addDays(final GregorianCalendar cal,
			final java.util.Date startDate, final int days) {

		cal.setTime(startDate);
		cal.add(java.util.Calendar.DATE, days);
		return cal.getTime();
	}

	static public java.util.Date addDays(final java.util.Date startDate,
			final int days) {

		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(startDate);
		cal.add(java.util.Calendar.DATE, days);
		return cal.getTime();
	}

	static public java.util.Date addSeconds(final java.util.Date startDate,
			final int seconds) {

		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(startDate);
		cal.add(java.util.Calendar.SECOND, seconds);
		return cal.getTime();
	}

	public static String asLogFileComponent(final Date d) {
		if (d == null) {
			throw new IllegalArgumentException("date was null");
		}
		final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		final String retval = f.format(d);
		return retval;

	}

	/**
	 * @todo write unit test exhaustive
	 * 
	 * @todo why is this not using julian dates Calculate the number of days
	 *       between two dates.
	 * 
	 *       any time of day date2 any time of day date 1. if date2 is after
	 *       date1 the return is a postive number.
	 * 
	 * 
	 * 
	 * @param date1
	 *            The first date
	 * @param date2
	 *            The second date
	 * @return The number of days between date1 and date2. If date1 is before
	 *         date2 the result will be a positive integer. If date1 is after
	 *         date2 the result will be a negative integer.
	 */
	public static int daysBetween(final java.util.Date date1,
			final java.util.Date date2) {

		int differenceInDays;
		boolean swapSign = false;
		final java.util.Date date1Midnight = getDateMidnight(date1);
		final java.util.Date date2Midnight = getDateMidnight(date2);

		java.util.Date earlierDate;
		java.util.Date laterDate;
		java.util.Date tst1 = null;
		java.util.Date tst2 = null;

		if (date1.before(date2)) {
			earlierDate = date1Midnight;
			laterDate = date2Midnight;
		} else {
			earlierDate = date2Midnight;
			laterDate = date1Midnight;
			swapSign = true;
		}

		final GregorianCalendar cal = new GregorianCalendar();
		final long t1 = earlierDate.getTime();
		final long t2 = laterDate.getTime();
		final long diff = t2 - t1;
		// Add one hour in case the duration includes an unbalanced 23 hour
		// Daylight Savings spring forward day.
		differenceInDays = (int) ((diff + ONE_HOUR) / (ONE_HOUR * 24));
		if (swapSign) {
			differenceInDays *= -1;
		}

		// assert addDays(date1Midnight,differenceInDays).equals(date2Midnight);

		// assert that we did this correctly
		cal.setTime(date1Midnight);
		tst1 = cal.getTime();
		cal.add(java.util.Calendar.DATE, differenceInDays);
		tst2 = cal.getTime();
		if (!tst2.equals(date2Midnight)) {
			System.out.println("tst2 " + tst2.getTime() + " d "
					+ date2Midnight.getTime());
			throw new java.lang.IllegalStateException("Failure " + "  " + "\n"
					+ " date1 : "
					+ date1
					+ " "
					+ date1.getTime()
					+ "\n"
					+ " date2 : "
					+ date2
					+ " "
					+ date2.getTime()
					+ "\n"
					+ " t1 : "
					+ t1
					+ " "
					+ "\n"
					+ " t2 : "
					+ t2
					+ " "
					+ "\n"
					+ " earlierDate: "
					+ earlierDate
					+ earlierDate.getTime()
					+ "\n"
					+ " laterDate: "
					+ laterDate
					+ laterDate.getTime()
					+ "\n"
					+ " diff: "
					+ diff
					+ "\n"
					+ " differenceInDays "
					+ differenceInDays
					+ "\n"
					+ " tst1: "
					+ tst1
					+ " "
					+ tst1.getTime()
					+ "\n"
					+ " tst2 : "
					+ tst2
					+ " "
					+ tst2.getTime()
					+ "\n"
					+ " date2midnight: "
					+ date2Midnight + " " + date2Midnight.getTime());
		}

		return differenceInDays;

	}

	/**
	 * Removes the time component from a date, making the time midnight.
	 * 
	 */
	public static java.util.Date getDateMidnight() {
		return getDateMidnight(new java.util.Date());
	}

	/**
	 * Removes the time component from a date, making the time midnight.
	 * 
	 * @return Input argument with hours, minutes, seconds, and milliseconds set
	 *         to zero.
	 */
	public static java.util.Date getDateMidnight(final java.util.Date dt) {
		Date rc = null;
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dt);
		final int year = cal.get(java.util.Calendar.YEAR);
		final int month = cal.get(java.util.Calendar.MONTH);
		final int day = cal.get(java.util.Calendar.DAY_OF_MONTH);
		cal.clear();
		cal.set(java.util.Calendar.YEAR, year);
		cal.set(java.util.Calendar.MONTH, month);
		cal.set(java.util.Calendar.DAY_OF_MONTH, day);

		rc = cal.getTime();
		// System.out.println("midnight " + dt + " is " + rc);
		return rc;

	}

	/**
	 * Return midnight for the first day of the month.
	 * 
	 * @param dt
	 * @return midnight for the first day of the month.
	 */

	public static java.util.Date getFirstDateInMonth(final java.util.Date dt) {
		Date rc = null;
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dt);
		cal.set(java.util.Calendar.DATE, 1);
		cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
		cal.set(java.util.Calendar.MINUTE, 0);
		cal.set(java.util.Calendar.SECOND, 0);
		cal.set(java.util.Calendar.MILLISECOND, 0);
		rc = cal.getTime();
		return rc;
	}

	public static java.util.Date getFirstDateNextMonth(final java.util.Date dt) {
		Date rc = null;
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dt);
		cal.set(java.util.Calendar.DATE, 1);
		cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
		cal.set(java.util.Calendar.MINUTE, 0);
		cal.set(java.util.Calendar.SECOND, 0);
		cal.set(java.util.Calendar.MILLISECOND, 0);
		cal.add(java.util.Calendar.MONTH, 1);
		rc = cal.getTime();
		return rc;
	}

	// public static java.util.Date getNextDayOfWeek(final java.util.Date dt,
	// final DayOfWeek weekday) {
	// final int dow = weekday.getGregorianDayOfWeek();
	//
	//
	// Date rc = null;
	// final GregorianCalendar cal = new GregorianCalendar();
	// cal.setTime(dt);
	// final int dtDow = cal.get(java.util.Calendar.DAY_OF_WEEK);
	// final int daysPastPrevious = dtDow - dow;
	// final int daysToNext = 6 - daysPastPrevious;
	// cal.set(java.util.Calendar.DATE, 1);
	// cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
	// cal.set(java.util.Calendar.MINUTE, 0);
	// cal.set(java.util.Calendar.SECOND, 0);
	// cal.set(java.util.Calendar.MILLISECOND, 0);
	// cal.add(java.util.Calendar.DATE,daysToNext);
	// rc = cal.getTime();
	// return rc;
	// }

	/**
	 * Please note that the 4th of October, 1582 is directly followed by the
	 * 15th of October 1582 as decreed by Pope Gregor. (Pope Gregor forced this
	 * in order to achieve better agreement between the civil and the
	 * astronomical calendar)
	 */
	public static double julianDayNumber(final java.util.Date dt) {
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dt);
		int year = cal.get(java.util.Calendar.YEAR) + 1900;
		int month = cal.get(java.util.Calendar.MONTH) + 1;
		final int dayOfMonth = cal.get(java.util.Calendar.DATE);
		final int hour = cal.get(java.util.Calendar.HOUR);
		final int minutes = cal.get(java.util.Calendar.MINUTE);
		final int seconds = cal.get(java.util.Calendar.SECOND);
		//

		int leapCenturyDays;
		int regularLeapDays;
		if (month <= 2) {
			year--;
			month += 12;
		} else {
			month++;
		}

		// account for leap days using century
		// Pope Gregor's decree
		if (year > 1582 || year == 1582 && month > 10 || year == 1582
				&& month == 10 && dayOfMonth >= 15) {
			leapCenturyDays = 2 - year / 100 + year / 400;
		} else {
			leapCenturyDays = 0;
		}

		// add in regular leap days
		if (year < 0) {
			regularLeapDays = (int) (365.25 * year - 0.75);
		} else {
			regularLeapDays = (int) (365.25 * year);
		}

		final int D = (int) (30.6001 * (month + 1));

		final double jdd = leapCenturyDays + regularLeapDays + D + dayOfMonth
				+ hour / 24 + minutes / (24 * 60) + seconds / (24 * 60 * 60)
				+ 1720994.5;

		return jdd;
	}

	public static void main(final String args[]) {
		final GregorianCalendar cal = new GregorianCalendar();
		final java.util.Date today = new java.util.Date();
		System.out.println("today is " + today);
		cal.setTime(today);

		final double jd = julianDayNumber(today);
		System.out.println("jdn " + jd);
		final java.util.Date firstDay = DateHelper.getFirstDateInMonth(today);
		System.out.println("today " + today + " firstDay " + firstDay);
	}

	public static java.util.Date toDate(final String dt) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/yyyy");
		return sdf.parse(dt);
	}

	// static public CalendarDate addDays(GregorianCalendar cal,CalendarDate
	// startDate, int days) {
	//
	// cal.setTime(startDate.getCalDay());
	// cal.add(java.util.Calendar.DATE, days);
	// return new CalendarDate(new Day(cal.getTime(),cal));
	// }

	/**
	 * Returns a sql.Date without any time component.
	 */
	public static java.sql.Date toSqlDate(final java.util.Date dt) {
		java.sql.Date rc = null;
		if (dt != null) {
			rc = new java.sql.Date(getDateMidnight(dt).getTime());
		}
		return rc;
	}

	public static java.sql.Timestamp toSqlTimestamp(final java.util.Date dt) {
		java.sql.Timestamp rc = null;
		if (dt != null) {
			rc = new java.sql.Timestamp(dt.getTime());
		}
		return rc;
	}

	/**
	 * 
	 * todo mohan make this work
	 * 
	 * Calculate calendar date for Julian date field this.jd
	 */
	/*
	 * java.util.Date julianToDate(int julianDayNumber) {
	 * 
	 * double jd2=julianDayNumber+0.5; long I= (long) jd2; // fractional day
	 * double F=jd2-I; long A=0; long B=0;
	 * 
	 * if (I>2299160) { A =(long) ((double)I-1867216.25)/36524.25; double
	 * a3=(double)A/4.0; B=I+1+A-a3.longValue(); } else { B=I; }
	 * 
	 * double C=(double)B+1524; double d1=((C-122.1)/365.25); long D=d1; double
	 * e1=(365.25*(double)D); long E=e1; double g1=((double)(C-E)/30.6001); long
	 * G=g1; double h=((double)G*30.6001); long da=(long)C-E-h.longValue();
	 * date=new Integer((int)da);
	 * 
	 * if (G<14L) { month=new Integer((int)(G-2L)); } else { month=new
	 * Integer((int)(G-14L)); }
	 * 
	 * if (month.intValue()>1) { year=new Integer((int)(D-4716L)); } else {
	 * year=new Integer((int)(D-4715L)); }
	 * 
	 * // Calculate fractional part as hours, minutes, and seconds double
	 * dhr=(24.0*F); hour=new Integer(dhr.intValue()); double
	 * dmin=(dhr.doubleValue()-(double)dhr.longValue())*60.0; minute=new
	 * Integer(dmin.intValue()); double
	 * dsec=(dmin()-(double)dmin.longValue())*60.0; second=new
	 * Integer(dsec.intValue());
	 * 
	 * }
	 */

	public java.util.Date getDate() {
		return date;
	}

	public String getMessage() {
		return errorMessage;
	}

	public static boolean validateDate(final String dateStr, final String format) {
		if (dateStr == null || dateStr.equals("")) {
			return false;
		}
		final SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		try {
			dateFormatter.parse(dateStr);
		} catch (final ParseException pe) {
			return false;
		}
		return true;
	}

	public static int monthsBetween(final Date startDate, final Date endDate) {
		final Calendar cal = Calendar.getInstance();
		// default will be Gregorian in US Locales
		cal.setTime(endDate);
		final int endMonth = cal.get(Calendar.MONTH) + 1;
		final int endYear = cal.get(Calendar.YEAR);
		cal.setTime(startDate);
		final int startMonth = cal.get(Calendar.MONTH) + 1;
		final int startYear = cal.get(Calendar.YEAR);

		if (logger.isDebugEnabled()) {
			logger.debug("Start Month is " + startMonth + " and Start Year is "
					+ startYear);
			logger.debug("End Month is " + endMonth + " and End Year is "
					+ endYear);
			logger.debug("Months is Year is " + cal.getMaximum(Calendar.MONTH));
		}
		// the following will work okay for Gregorian but will not
		// work correctly in a Calendar where the number of months
		// in a year is not constant
		return ((endYear - startYear) * (cal.getMaximum(Calendar.MONTH) + 1))
				+ (endMonth - startMonth);
	}

}
