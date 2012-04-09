package org.javautil.recordvalidation.fieldtype;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.apache.log4j.Logger;
import org.javautil.util.Day;
import org.junit.Test;

public class DateFieldTypeTest {

	private Logger logger = Logger.getLogger(getClass());
	
	@Test
	public void test1() throws FieldParseException {
		DateFieldType field = new DateFieldType("yyyyMMdd");
		Date date = field.getObject("20100401".getBytes());
		logger.debug(date);
		logger.debug("date " + date);
		Day day = new Day(2010,04,01);
		logger.debug("day " + day);
		assertEquals(day,new Day(date));
		
		date = field.getObject("20100408".getBytes());
		logger.debug(date);
		logger.debug("date " + date);
		day = new Day(2010,4,8);
		logger.debug("day " + day);
		assertEquals(day,new Day(date));
	}
}
