package org.javautil.commandline.annotations;

import static org.junit.Assert.assertEquals;
import jcmdline.CmdLineException;

import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.junit.Test;

/**
 * DateParam.dateFormat: MM/dd/yy from string.property in
 * 
 * @author jjs
 * 
 */
public class BooleanArgumentsTest extends BaseTest {

	// TODO multi-valued boolean doesn't make sense should blow up when parsing
	// bean
	// @Test
	// public void testBools() throws CmdLineException
	// {
	// BooleanArguments bools = new BooleanArguments();
	// CommandLineHandler clh = new CommandLineHandler(bools);
	// String argString = "-bean -flags=false -flags=true";
	// clh.evaluateArgumentsString(argString);
	//
	// assertEquals(true,bools.getBean());
	// Collection<Boolean> values = bools.getFlags();
	// // assertEquals(2,values.size());
	// assertTrue(values.contains(Boolean.TRUE));
	// assertTrue(values.contains(Boolean.FALSE));
	//
	// }

	@Test
	public void testBools() throws CmdLineException {
		final BooleanArguments bools = new BooleanArguments();
		final CommandLineHandler clh = new CommandLineHandler(bools);
		final String argString = "-bean ";
		clh.evaluateArgumentsString(argString);

		assertEquals(true, bools.getBean());
		// Collection<Boolean> values = bools.getFlags();
		// // assertEquals(2,values.size());
		// assertTrue(values.contains(Boolean.TRUE));
		// assertTrue(values.contains(Boolean.FALSE));

	}

}
