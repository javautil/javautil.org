package org.javautil.text;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 
 * @author jjs
 * 
 */
public class CSVWriterTest {

	private final Logger logger = Logger.getLogger(getClass());

	@Test
	public void test3() {
		final CSVWriter cw = new CSVWriter();
		final Object oa = new Object[] { "STATE", Integer.valueOf(0), "STRING",
				null, null, null, null, null, null, Boolean.FALSE, null, null,
				null, null, null, null };
		final String expected = "\"STATE\",0,\"STRING\",,,,,,,\"false\"";
		final String actual = cw.asString(oa);
		logger.debug("e " + expected);
		logger.debug("a " + actual);

		assertEquals(expected, actual);
	}

	@Test
	public void test1() {
		final CSVWriter cw = new CSVWriter();
		Object[] oa = new Object[] { "A", "B" };
		assertEquals("\"A\",\"B\"", cw.asString(oa));
		assertEquals("\"A\",\"B\"", cw.asString("A", "B"));

		oa = new Object[] { "A", null };
		assertEquals("\"A\"", cw.asString(oa));

		oa = new Object[] { "\"A", null };
		assertEquals("\"\"\"A\"", cw.asString(oa));
	}

	@Test(expected = IllegalArgumentException.class)
	public void test2() {
		final CSVWriter cw = new CSVWriter();
		assertEquals("\"\"\"A\"", cw.asString(null));
	}
}
