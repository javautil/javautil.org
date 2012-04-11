package org.javautil.util;

import static org.javautil.text.ToStringStyleFlags.DONT_EMIT_FIELD_NAMES;
import static org.javautil.text.ToStringStyleFlags.DONT_EMIT_IDENTITY_HASH_CODE;
import static org.javautil.text.ToStringStyleFlags.EMIT_FIELD_NAMES;
import static org.javautil.text.ToStringStyleFlags.SUPPRESS_TIME;
import static org.javautil.text.ToStringStyleFlags.SUPPRESS_TIME_IF_MIDNIGHT;
import static org.javautil.text.ToStringStyleFlags.USE_ISO_DATE_TIME_FORMAT;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.javautil.text.ConfigurableToStringStyle;
import org.junit.Test;

/**
 * @author jjs at dbexperts dot com
 */
@SuppressWarnings({ "serial", "synthetic-access" })
public class ConfigurableToStringTest {

	transient private Logger logger = Logger.getLogger(getClass());

	private final DateFactory dateFactory = new DateFactory();

	private class Data {
		@SuppressWarnings("synthetic-access")
		Date afternoon = dateFactory.getDate(2008, 5, 6, 13, 27, 32);
	}

	private class TwoTimer {
		@SuppressWarnings("synthetic-access")
		Date afternoon = dateFactory.getDate(2008, 5, 6, 13, 27, 32);
		Date midnight = dateFactory.getDate(2008, 5, 6);
	}

	Data data = new Data();

	// private String shortDataName = data.getClass().getSimpleName();

	private final String dataLabel = "ConfigurableToStringTest.Data";
	private final String twoTimerLabel = "ConfigurableToStringTest.TwoTimer";

	Date e = dateFactory.getDate(2009, 03, 03);

	ArrayList<Date> dates = new ArrayList<Date>() {
		{
			add(new Date());
		}
	};

	/**
	 * Expect"yyyy-MM-dd HH:mm:ss" format
	 */
	@Test
	public void test1() {
		final String text = ReflectionToStringBuilder.toString(data,
				new ConfigurableToStringStyle(DONT_EMIT_IDENTITY_HASH_CODE));
		final String expected = dataLabel + "[afternoon=2008-05-06 13:27:32]";
		logger.debug("expected " + expected);
		logger.debug("got " + text);
		assertEquals(expected, text);
	}

	@Test
	public void test2() {
		final String text = ReflectionToStringBuilder.toString(data,
				new ConfigurableToStringStyle(DONT_EMIT_IDENTITY_HASH_CODE,
						USE_ISO_DATE_TIME_FORMAT));
		logger.debug("text " + text);
		final String expected = dataLabel + "[afternoon=2008-05-06 13:27:32]";
		// String expected = dataLabel + "[afternoon=2008-05-06]";
		logger.debug("expected " + expected);
		assertEquals(expected, text);
	}

	@Test
	public void test3() {
		logger.debug("test3");
		final ConfigurableToStringStyle style = new ConfigurableToStringStyle(
				EMIT_FIELD_NAMES, DONT_EMIT_IDENTITY_HASH_CODE);
		final String text = ReflectionToStringBuilder.toString(data, style);
		final String expected = "ConfigurableToStringTest.Data[afternoon=2008-05-06 13:27:32]";
		logger.debug("actual '" + text + "'");
		logger.debug("expected '" + expected + "'");
		assertEquals(expected, text);

	}

	@Test
	public void test4() {
		final ConfigurableToStringStyle style = new ConfigurableToStringStyle(
				DONT_EMIT_FIELD_NAMES, DONT_EMIT_IDENTITY_HASH_CODE,
				SUPPRESS_TIME);
		final String text = ReflectionToStringBuilder.toString(data, style);
		final String expected = dataLabel + "[2008-05-06]";
		logger.debug("expected '" + expected + "'");
		logger.debug("text '" + text + "'");
		assertEquals(expected, text);
	}

	@Test
	public void test5() {
		final ConfigurableToStringStyle style = new ConfigurableToStringStyle(
				DONT_EMIT_IDENTITY_HASH_CODE,
				// SUPPRESS_TIME,
				SUPPRESS_TIME_IF_MIDNIGHT);
		final String text = ReflectionToStringBuilder.toString(new TwoTimer(),
				style);
		logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
		final String expected = twoTimerLabel
				+ "[afternoon=2008-05-06 13:27:32,midnight=2008-05-06]";
		logger.debug("expected '" + expected + "'");
		logger.debug("text '" + text + "'");
		assertEquals(expected, text);
	}

	public static void main(final String[] args) {
		final ConfigurableToStringTest t = new ConfigurableToStringTest();
		t.test5();
	}
}
