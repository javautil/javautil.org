package org.javautil.field.object;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

public class DateObjectDefinitionTest {
	private Logger logger = Logger.getLogger(getClass());
	@Test
	public void testDate() throws ObjectParseException {
		DateObjectDefinition def = new DateObjectDefinition("yyyy/MM/dd");
		Date date = def.getObject("2010/04/28");
		logger.debug(date);
	}
}
