package org.javautil.commandline.annotations;

import static org.junit.Assert.assertEquals;
import jcmdline.CmdLineException;

import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.junit.Test;

public class RequiresTest extends BaseTest {

	private final RequiresArguments argumentBean = new RequiresArguments();
	private final CommandLineHandler clh = new CommandLineHandler(argumentBean);

	@Test
	public void test1() throws CmdLineException {
		final String argString = "-toad alice -numberOfWarts 2 ";
		clh.evaluateArgumentsString(argString);
		assertEquals("alice", argumentBean.getToad());
		assertEquals(new Integer(2), argumentBean.getNumberOfWarts());
	}

	// TODO should require numberOfWarts
	// @Test (expected=java.lang.IllegalArgumentException.class)
	@Test(expected = org.javautil.security.NoExitException.class)
	public void missingRequired() throws CmdLineException {
		final String argString = "-toad alice  ";
		clh.evaluateArgumentsString(argString);
	}
}
