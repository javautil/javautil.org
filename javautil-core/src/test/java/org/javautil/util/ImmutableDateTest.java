package org.javautil.util;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.javautil.text.CommonDateFormat;
import org.javautil.text.SimpleDateFormatter;
import org.javautil.text.StringHelper;
import org.junit.Test;

public class ImmutableDateTest {
	private final Logger logger = Logger.getLogger(getClass());

	@Test
	public void test1() {
		final ImmutableDate d = new ImmutableDate(1922, 06, 22);
		final String result = d.toString();
		assertEquals("1922/06/22", result);
		logger.debug(d.toString());
	}

	@Test
	public void testZone() {
		final ImmutableDate d = new ImmutableDate(1922, 06, 22);
		new Date(d.getTime());
		// String result = date.toString();
		final SimpleDateFormatter sdf = new SimpleDateFormatter("yyyy/MM/dd");
		final String result = sdf.format(d);
		assertEquals("1922/06/22", result);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void test2() {
		final ImmutableDate d = new ImmutableDate();
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

	// @SuppressWarnings("deprecation")
	// @Test
	// public void test3() {
	// final ImmutableDate d = ImmutableDate.toImmutableDate();
	// final String result = d.toString();
	// final Date dt = new Date();
	// final String expected = dt.getYear()
	// + 1900
	// + "/"
	// + StringHelper.leftPadWithChar(
	// String.valueOf((dt.getMonth() + 1)), 2, "0")
	// + "/"
	// + StringHelper.leftPadWithChar(String.valueOf((dt.getDate())),
	// 2, "0");
	// assertEquals(expected, result);
	// logger.debug("Expected = " + expected);
	// logger.debug("Actual = " + d.toString());
	// }

	@SuppressWarnings("deprecation")
	@Test
	public void test4() {
		final Date dt = new Date();
		final ImmutableDate d = new ImmutableDate(dt);
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
		final ImmutableDate d = new ImmutableDate(dt, cal);
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
		final ImmutableDate d = new ImmutableDate(currentTime);
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
		final ImmutableDate d = new ImmutableDate("2009/01/02", "yyyy/MM/dd");
		assertEquals("2009/01/02", d.toString());
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("unused")
	public void test8() {
		final ImmutableDate d = new ImmutableDate(null);
	}

	@Test
	public void test9() throws ParseException {
		final ImmutableDate d = new ImmutableDate(2009, 01, 02, 03, 04, 05);
		final SimpleDateFormatter formatter = new SimpleDateFormatter(
				CommonDateFormat.ISO_SECOND);
		final String actual = formatter.format(d);
		assertEquals("2009-01-02 03:04:05", actual);
	}

	@Test
	public void test10() throws ParseException {
		final ImmutableDate d = new ImmutableDate(2009, 01, 02, 13, 04, 05);
		final SimpleDateFormatter formatter = new SimpleDateFormatter(
				CommonDateFormat.ISO_SECOND);
		final String actual = formatter.format(d);
		assertEquals("2009-01-02 13:04:05", actual);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setDateTest() {
		final ImmutableDate d = new ImmutableDate();
		d.setDate(d);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setImmutableDateTest() {
		final ImmutableDate d = new ImmutableDate();
		d.setDate(1);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setMonthTest() {
		final ImmutableDate d = new ImmutableDate();
		d.setMonth(1);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setYearTest() {
		final ImmutableDate d = new ImmutableDate();
		d.setYear(2009);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setTimeTest() {
		final ImmutableDate d = new ImmutableDate();
		d.setTime(294502959);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setHoursTest() {
		final ImmutableDate d = new ImmutableDate();
		d.setHours(1);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setMinutesTest() {
		final ImmutableDate d = new ImmutableDate();
		d.setMinutes(1);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setSecondsTest() {
		final ImmutableDate d = new ImmutableDate();
		d.setSeconds(1);
	}

	@Test
	public void toTimestampTest() {
		final DateFactory df = new DateFactory();
		final Date dt2 = df.getDate(2007, 2, 13);
		final ImmutableDate d = new ImmutableDate(dt2);
		final Timestamp t = d.toTimestamp();
		assertEquals(dt2.getTime(), t.getTime());
	}

}
