package org.javautil.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author jjs @ dbexperts.com
 * 
 *         Provides a simple way to get a date.
 * 
 *         The Standard Date constructors for Date are deprecated for good
 *         reason, they are goofy.
 * 
 * 
 */
public class DateFactory {
	private GregorianCalendar cal = new GregorianCalendar();

	public DateFactory() {

	}

	/**
	 * Instantiate DateFactory using specified Calendar.
	 * 
	 * Calendars are expensive to create, additionally you may want to tweak the
	 * time zone to affect what time midnight is.
	 * 
	 * @param pope
	 */
	public DateFactory(final GregorianCalendar pope) {
		this.cal = pope;
	}

	public Date getDate(final int year, final int month, final int day) {
		return getDate(year, month, day, 0, 0, 0);

	}

	/**
	 * Easy way to get a date (I'll skip the obvious jokes).
	 * 
	 * All parameters are things a non programmer would expect.
	 * 
	 * <code>
	 * 
	 * 	<pre>
	 * public void test3() {
	 *   
	 *    Date karlsBirthDate = dateFactory.getDate(1988,4,8);
	 *    Date wtf = new Date(88,3,8);
	 *    assertEquals(karlsBirthDate,wtf);
	 *    SimpleDateFormat sdf = new SimpleDateFormat(&quot;yyyy-MM-dd HH:mm:ss&quot;);
	 *    String kjsFormat = sdf.format(karlsBirthDate);
	 *    assertEquals(&quot;1988-04-08 00:00:00&quot;,kjsFormat);
	 *  }
	 *  </pre></code>
	 * 
	 * The parameters are not checked and the strangeness of the Calendar class
	 * prevails.
	 * 
	 * Hence the 39th of March is April 8. * <code>
	 * 
	 * 	<pre>
	 *  	Date aprilEighth = dateFactory.getDate(1988, 4, 8);
	 * 	    Date thirtyNinthOfMarch = dateFactory.getDate(1988,3,39);
	 * 	    assertEquals(aprilEighth,thirtyNinthOfMarch);
	 *  </pre></code>
	 * 
	 * @param year
	 *            The year, for the year 2008 use 2008
	 * @param month
	 *            1 = Jan .. 12 = Dec
	 * @param day
	 *            1 - 31 as appropriate for month
	 * @param hour
	 *            0 - 23
	 * @param minute
	 *            0 - 60
	 * @param second
	 *            0 - 60
	 * @return
	 */
	public Date getDate(final int year, final int month, final int day,
			final int hour, final int minute, final int second) {

		synchronized (cal) {

			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DAY_OF_MONTH, day);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);
			cal.set(Calendar.SECOND, second);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();

		}
	}

}
