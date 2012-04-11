package org.javautil.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

public class DateFormatTest {

	private final Logger logger = Logger.getLogger(getClass());

	@Test
	public void test() {
		final Calendar cal = new GregorianCalendar();
		final Date date = new Day(2001, 9, 11);
		assertEquals("09-11-2001", DateFormat.DASH_MM_DD_YYYY.format(cal, date));
		assertEquals("3-2001", DateFormat.DASH_QQ_YYYY.format(cal, date));
		assertEquals("09-2001", DateFormat.DASH_MM_YYYY.format(cal, date));

	}

	// much slower under test suites than indicated
	@Ignore
	@Test
	public void speedTest() {
		final Calendar cal = new GregorianCalendar();
		final Date date = new Day(2001, 9, 11);
		final long start = System.currentTimeMillis();
		final int iterations = 1000000;
		for (int i = 0; i < 1000000; i++) {
			DateFormat.DASH_MM_DD_YYYY.format(cal, date);
			DateFormat.DASH_QQ_YYYY.format(cal, date);
			DateFormat.DASH_MM_YYYY.format(cal, date);
		}
		final long end = System.currentTimeMillis();
		final long elapsed = end - start;
		logger.debug("elapsed millis for " + iterations + " iterations "
				+ elapsed);
		final boolean fastEnough = elapsed < 2000;
		assertTrue(fastEnough);
	}
}
