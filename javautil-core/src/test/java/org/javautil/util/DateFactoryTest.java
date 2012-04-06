/**
 * 
 */
package org.javautil.util;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jjs
 * 
 */
public class DateFactoryTest {

	private static final int LOOP_COUNT = 100;
	private final SimpleDateFormat date24hTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final Logger logger = Logger.getLogger(getClass());
	private final DateFactory dateFactory = new DateFactory();

	@BeforeClass
	public static void beforeClass() {
		BasicConfigurator.configure();
	}

	@Test
	public void test() {
		final Date kjs = dateFactory.getDate(1988, 4, 8, 17, 32, 15);
		final String kjsFormat = date24hTime.format(kjs);
		assertEquals("1988-04-08 17:32:15", kjsFormat);
	}

	@Test
	public void test2() {
		final Date kjs = dateFactory.getDate(1988, 4, 8);
		final String kjsFormat = date24hTime.format(kjs);
		assertEquals("1988-04-08 00:00:00", kjsFormat);
	}

	@Test
	public void test3() {

		final Date karlsBirthDate = dateFactory.getDate(1988, 4, 8);
		final Date wtf = new Date(88, 3, 8);
		assertEquals(karlsBirthDate, wtf);
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String kjsFormat = sdf.format(karlsBirthDate);
		assertEquals("1988-04-08 00:00:00", kjsFormat);
	}

	@Test
	public void newCal() {
		final String testName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();

		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < LOOP_COUNT; i++) {
			// DateFactory df = new DateFactory();
			dateFactory.getDate(1988, 4, 8);
		}
		final long t2 = System.currentTimeMillis();
		final long elapsedMillis = t2 - t1;
		logger.debug(testName + " elapsed    "
				+ Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " " + elapsedMillis);
	}

	// reuse the GregorianCalendar
	@Test
	public void newCal2() {
		final String testName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		final long t1 = System.currentTimeMillis();
		final GregorianCalendar gc = new GregorianCalendar();
		for (int i = 0; i < LOOP_COUNT; i++) {
			@SuppressWarnings("unused")
			final DateFactory df = new DateFactory(gc);
			@SuppressWarnings("unused")
			final Date karlsBirthDate = dateFactory.getDate(1988, 4, 8);
		}
		final long t2 = System.currentTimeMillis();
		final long elapsedMillis = t2 - t1;
		logger.debug(testName + " elapsed    "
				+ Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " " + elapsedMillis);
		// return elapsedMillis;
	}

	@Test
	public void newCal4() {
		final String testName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();

		final long t1 = System.currentTimeMillis();
		// DateFactory df = new DateFactory();
		for (int i = 0; i < LOOP_COUNT; i++) {
			@SuppressWarnings("unused")
			final Date karlsBirthDate = dateFactory.getDate(1988, 4, 8);
		}
		final long t2 = System.currentTimeMillis();
		final long elapsedMillis = t2 - t1;
		logger.debug(testName + " elapsed    "
				+ Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " " + elapsedMillis);

	}

	@Test
	public void newCal5() {
		final Date aprilEighth = dateFactory.getDate(1988, 4, 8);
		final Date thirtyNinthOfMarch = dateFactory.getDate(1988, 3, 39);
		assertEquals(aprilEighth, thirtyNinthOfMarch);
		logger.debug("39th of march " + aprilEighth);
	}

	@Test
	public void wtf() {
		final Date d = dateFactory.getDate(1988, 3, 38, 27, 13, 5);
		logger.debug("27th hour of 38 of March " + d);
	}
}
