package org.javautil.commandline.annotations;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Date;

import jcmdline.CmdLineException;

import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.javautil.commandline.ParamType;
import org.javautil.util.Day;
import org.junit.Test;

/**
 * DateParam.dateFormat: MM/dd/yy from string.property in
 * 
 * @author jjs
 * 
 */
public class DateArgumentsTest extends BaseTest {

	@Optional
	@MultiValue(type = ParamType.DATE)
	private Collection<Date> dates;

	@Optional
	private Date date;

	public Collection<Date> getDates() {
		return dates;
	}

	public void setDates(final Collection<Date> dates) {
		this.dates = dates;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	@Test
	public void testDate() throws CmdLineException {
		final CommandLineHandler clh = new CommandLineHandler(this);
		final String argString = "-date 08/07/10 -dates 08/09/10 -dates 08/10/10";
		clh.evaluateArgumentsString(argString);
		final Day day = new Day(2010, 8, 7);
		assertEquals(day, this.getDate());
	}

}
