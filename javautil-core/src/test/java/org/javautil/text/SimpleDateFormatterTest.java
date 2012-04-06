package org.javautil.text;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.javautil.util.Day;
import org.junit.Test;

public class SimpleDateFormatterTest {

	private final Date date = new Day(2009, 7, 27);
	private SimpleDateFormatter formatter;

	@Test
	public void test() {
		formatter = new SimpleDateFormatter("yyyy-MM-dd");
		assertEquals("2009-07-27", formatter.format(date));
		formatter = new SimpleDateFormatter("yyyy-Q");
		assertEquals("2009-1", formatter.format(new Day(2009, 1, 1)));
		assertEquals("2009-1", formatter.format(new Day(2009, 2, 1)));
		assertEquals("2009-1", formatter.format(new Day(2009, 3, 1)));
		assertEquals("2009-2", formatter.format(new Day(2009, 4, 1)));
		assertEquals("2009-2", formatter.format(new Day(2009, 5, 1)));
		assertEquals("2009-2", formatter.format(new Day(2009, 6, 1)));
		assertEquals("2009-3", formatter.format(new Day(2009, 7, 1)));
		assertEquals("2009-3", formatter.format(new Day(2009, 8, 1)));
		assertEquals("2009-3", formatter.format(new Day(2009, 9, 1)));
		assertEquals("2009-4", formatter.format(new Day(2009, 10, 1)));
		assertEquals("2009-4", formatter.format(new Day(2009, 11, 1)));
		assertEquals("2009-4", formatter.format(new Day(2009, 12, 1)));
	}

}
