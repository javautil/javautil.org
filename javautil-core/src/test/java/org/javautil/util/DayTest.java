package org.javautil.util;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.javautil.text.SimpleDateFormatter;
import org.javautil.text.StringHelper;
import org.junit.Test;

public class DayTest {
	private final Logger logger = Logger.getLogger(getClass());

	@Test
	public void test1() {
		final Day d = new Day(1922, 06, 22);
		final String result = d.toString();
		assertEquals("1922/06/22", result);
		logger.debug(d.toString());
	}

	@Test
	public void testZone() {
		final Day d = new Day(1922, 06, 22);
		new Date(d.getTime());
		// String result = date.toString();
		final SimpleDateFormatter sdf = new SimpleDateFormatter("yyyy/MM/dd");
		final String result = sdf.format(d);
		assertEquals("1922/06/22", result);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void test2() {
		final Day d = new Day();
		final String result = d.toString();
		final Date dt = new Date();
		final String expected = dt.getYear()
				+ 1900
				+ "/"
				+ StringHelper.leftPadWithChar(
						String.valueOf((dt.getMonth() + 1)), 2, "0")
				+ "/"
				+ StringHelper.leftPadWithChar(String.valueOf((dt.getDate())),
						2, "0");
		assertEquals(expected, result);
		logger.debug("Expected = " + expected);
		logger.debug("Actual = " + d.toString());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test3() {
		final Day d = Day.today();
		final String result = d.toString();
		final Date dt = new Date();
		final String expected = dt.getYear()
				+ 1900
				+ "/"
				+ StringHelper.leftPadWithChar(
						String.valueOf((dt.getMonth() + 1)), 2, "0")
				+ "/"
				+ StringHelper.leftPadWithChar(String.valueOf((dt.getDate())),
						2, "0");
		assertEquals(expected, result);
		logger.debug("Expected = " + expected);
		logger.debug("Actual = " + d.toString());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test4() {
		final Date dt = new Date();
		final Day d = new Day(dt);
		final String result = d.toString();
		final String expected = dt.getYear()
				+ 1900
				+ "/"
				+ StringHelper.leftPadWithChar(
						String.valueOf((dt.getMonth() + 1)), 2, "0")
				+ "/"
				+ StringHelper.leftPadWithChar(String.valueOf((dt.getDate())),
						2, "0");
		assertEquals(expected, result);
		logger.debug("Expected = " + expected);
		logger.debug("Actual = " + d.toString());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test5() {
		final Calendar cal = Calendar.getInstance();
		final Date dt = new Date();
		final Day d = new Day(dt, cal);
		final String result = d.toString();
		final String expected = dt.getYear()
				+ 1900
				+ "/"
				+ StringHelper.leftPadWithChar(
						String.valueOf((dt.getMonth() + 1)), 2, "0")
				+ "/"
				+ StringHelper.leftPadWithChar(String.valueOf((dt.getDate())),
						2, "0");
		assertEquals(expected, result);
		logger.debug("Expected = " + expected);
		logger.debug("Actual = " + d.toString());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test6() {
		final long currentTime = System.currentTimeMillis();
		final Day d = new Day(currentTime);
		final Date dt = new Date();
		final String result = d.toString();
		final String expected = dt.getYear()
				+ 1900
				+ "/"
				+ StringHelper.leftPadWithChar(
						String.valueOf((dt.getMonth() + 1)), 2, "0")
				+ "/"
				+ StringHelper.leftPadWithChar(String.valueOf((dt.getDate())),
						2, "0");
		assertEquals(expected, result);
		logger.debug("Expected = " + expected);
		logger.debug("Actual = " + d.toString());
	}

	@Test
	public void test7() throws ParseException {
		final Day d = new Day("2009/01/01", "yyyy/MM/dd");
		assertEquals("2009/01/01", d.toString());
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("unused")
	public void test8() {
		final Day d = new Day(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setDateTest() {
		final Day d = new Day();
		d.setDate(d);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setDayTest() {
		final Day d = new Day();
		d.setDay(1);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setMonthTest() {
		final Day d = new Day();
		d.setMonth(1);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setYearTest() {
		final Day d = new Day();
		d.setYear(2009);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setTimeTest() {
		final Day d = new Day();
		d.setTime(294502959);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setHoursTest() {
		final Day d = new Day();
		d.setHours(1);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setMinutesTest() {
		final Day d = new Day();
		d.setMinutes(1);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setSecondsTest() {
		final Day d = new Day();
		d.setSeconds(1);
	}

	@Test
	public void toTimestampTest() {
		final DateFactory df = new DateFactory();
		final Date dt2 = df.getDate(2007, 2, 13);
		final Day d = new Day(dt2);
		final Timestamp t = d.toTimestamp();
		assertEquals(dt2.getTime(), t.getTime());
	}

}
