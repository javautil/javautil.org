package org.javautil.text;

import static org.javautil.text.ToStringStyleFlags.DONT_EMIT_FIELD_NAMES;
import static org.javautil.text.ToStringStyleFlags.DONT_EMIT_IDENTITY_HASH_CODE;
import static org.javautil.text.ToStringStyleFlags.EMIT_FIELD_NAMES;
import static org.javautil.text.ToStringStyleFlags.SUPPRESS_TIME;
import static org.javautil.text.ToStringStyleFlags.SUPPRESS_TIME_IF_MIDNIGHT;
import static org.javautil.text.ToStringStyleFlags.USE_ISO_DATE_TIME_FORMAT;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.javautil.text.AsString;
import org.javautil.util.DateFactory;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jjs at dbexperts dot com
 */
@SuppressWarnings({ "serial", "synthetic-access" })
public class AsStringTest {

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

	private final String dataLabel = "AsStringTest.Data";

	Date e = dateFactory.getDate(2009, 03, 03);

	ArrayList<Date> dates = new ArrayList<Date>() {
		{
			add(new Date());
		}
	};

	@BeforeClass
	public static void beforeClass() {
		BasicConfigurator.configure();
	}

	@Test
	public void test1() {
		final AsString as = new AsString(DONT_EMIT_IDENTITY_HASH_CODE);
		final String text = as.toString(data);
		// as.toString(data, new
		// ConfigurableToStringStyle(DONT_USE_IDENTITY_HASH_CODE));
		final String expected = dataLabel + "[afternoon=2008-05-06 13:27:32]";
		logger.debug("expected " + expected);
		logger.debug("got " + text);
		assertEquals(expected, text);
	}

	@Test
	public void testSuppressTime() {
		logger.debug("testSuppressTime");
		final AsString as = new AsString(DONT_EMIT_IDENTITY_HASH_CODE,
				SUPPRESS_TIME);
		final String text = as.toString(data);
		// as.toString(data, new
		// ConfigurableToStringStyle(DONT_USE_IDENTITY_HASH_CODE));
		final String expected = dataLabel + "[afternoon=2008-05-06]";
		logger.debug("expected " + expected);
		logger.debug("got " + text);
		assertEquals(expected, text);
	}

	@Test
	public void test2() {
		final AsString as = new AsString(DONT_EMIT_IDENTITY_HASH_CODE,
				USE_ISO_DATE_TIME_FORMAT);
		final String text = as.toString(data);
		logger.debug("text " + text);
		final String expected = dataLabel + "[afternoon=2008-05-06 13:27:32]";
		logger.debug("expected " + expected);
		assertEquals(expected, text);
	}

	@Test
	public void test3() {
		logger.debug("test3");
		final AsString as = new AsString(EMIT_FIELD_NAMES,
				DONT_EMIT_IDENTITY_HASH_CODE);
		final String text = as.toString(data);
		final String expected = dataLabel + "[afternoon=2008-05-06 13:27:32]";
		logger.debug("actual '" + text + "'");
		logger.debug("expected '" + expected + "'");
		assertEquals(expected, text);

	}

	@Test
	public void test4() {
		final AsString as = new AsString(DONT_EMIT_FIELD_NAMES,
				DONT_EMIT_IDENTITY_HASH_CODE, SUPPRESS_TIME);
		final String text = as.toString(data);
		final String expected = dataLabel + "[2008-05-06]";
		logger.debug("expected '" + expected + "'");
		logger.debug("text '" + text + "'");
		assertEquals(expected, text);
	}

	@Test
	public void test5() {
		final AsString as = new AsString(DONT_EMIT_IDENTITY_HASH_CODE,
				SUPPRESS_TIME, // this
				// should
				// be
				// ignored
				// as
				// it
				// is
				// overridden
				// by
				// next
				// parm
				SUPPRESS_TIME_IF_MIDNIGHT);
		final String text = as.toString(new TwoTimer());
		logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
		final String expected = "AsStringTest.TwoTimer[afternoon=2008-05-06,midnight=2008-05-06]";
		logger.debug("expected '" + expected + "'");
		logger.debug("text '" + text + "'");
		assertEquals(expected, text);
	}

	// @Test
	// public void test6() {
	// AsString as = new AsString();
	// String text = as.toString(new StaticData());
	// logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
	// String expected = "StaticData[]";
	// logger.debug("expected '" + expected + "'");
	// logger.debug("text '" + text + "'");
	// assertEquals(expected, text);
	//
	// }
	public static void main(final String[] args) {
		// BasicConfigurator.configure();
		// AsStringTest t = new AsStringTest();
		// t.test6();
	}
}
