package org.javautil.util;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author jjs at dbexperts dot com
 */
@SuppressWarnings({ "serial", "synthetic-access" })
public class ConfigurableToStringTestData {

	@SuppressWarnings("unused")
	transient private Logger logger = Logger.getLogger(getClass());

	transient DateFactory dateFactory = new DateFactory();

	@SuppressWarnings("unused")
	private final Date mjs = dateFactory.getDate(1990, 4, 28);

	@SuppressWarnings("unused")
	private final ArrayList<Date> dateList = new ArrayList<Date>() {
		{
			add(dateFactory.getDate(2008, 5, 6, 13, 27, 32));
		}
	};

}
