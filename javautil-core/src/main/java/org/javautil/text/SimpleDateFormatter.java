package org.javautil.text;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Decorates SimpleDateFormat to add support for Quarters.
 * 
 * A 'Q' may be placed in a format String. Every occurance of 'Q' is replaced
 * with the quarter, i.e.
 * <table>
 * <tr>
 * <td>jan, feb, mar</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>apr, may, jun</td>
 * <td>2</td>
 * </tr>
 * <tr>
 * <td>jul, aug, sep</td>
 * <td>3</td>
 * </tr>
 * <tr>
 * <td>oct, nov, dec</td>
 * <td>4</td>
 * </tr>
 * </table>
 */
public class SimpleDateFormatter {

	/**
	 * Raw pattern with 'Q' replaced with '^'. That way this can be passed to
	 * SimpleDateFormat without objection. SimpleDateFormat has reserved every
	 * letter in the alphabet.
	 */
	private String cookedPattern;
	/**
	 * The Decorated formatter.
	 */
	private SimpleDateFormat formatter;
	/**
	 * True if the raw pattern has a 'Q' in it.
	 */
	private boolean hasQuarter = false;
	/**
	 * The calendar used to extract the Date fields.
	 */
	private final Calendar cal = new GregorianCalendar();

	public SimpleDateFormatter() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected String cookPattern(final String rawPattern) {
		if (rawPattern.indexOf("Q") > -1) {
			hasQuarter = true;
		}
		this.cookedPattern = rawPattern.replace("Q", "^");
		return cookedPattern;
	}

	public SimpleDateFormatter(final String pattern,
			final DateFormatSymbols formatSymbols) {
		formatter = new SimpleDateFormat(cookPattern(pattern), formatSymbols);
	}

	public SimpleDateFormatter(final String pattern, final Locale locale) {
		formatter = new SimpleDateFormat(cookPattern(pattern), locale);
	}

	public SimpleDateFormatter(final String pattern) {
		formatter = new SimpleDateFormat(cookPattern(pattern));
	}

	public SimpleDateFormatter(final CommonDateFormat format) {
		if (format == null) {
			throw new IllegalArgumentException("format is null");
		}
		formatter = new SimpleDateFormat(format.getFormat());
	}

	/**
	 * Works exactly like SimpleDateFormat.format(Date date) with support for
	 * quarters.
	 * 
	 * Add a calendar quarter to the format String with the letter 'Q'.
	 * 
	 * @param date
	 * @return
	 */
	public String format(final Date date) {

		String f = formatter.format(date);
		if (hasQuarter) {
			cal.setTime(date);
			final int quarter = (cal.get(Calendar.MONTH) + 3) / 3;
			final String quarterString = Integer.toString(quarter);
			f = f.replace("^", quarterString);
		}
		return f;
	}

	public Date parse(String dateString) throws ParseException {
		return formatter.parse(dateString);
	}
}
