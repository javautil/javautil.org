package org.javautil.commandline.annotations;

import jcmdline.CmdLineException;

import org.apache.log4j.Logger;
import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.junit.Test;

public class ExclusiveArgumentsTest extends BaseTest {

	private final ExclusiveArguments argumentBean = new ExclusiveArguments();
	private final CommandLineHandler clh = new CommandLineHandler(argumentBean);
	private final Logger logger = Logger.getLogger(getClass());

	@Test
	public void testToad() throws CmdLineException {
		final String argString = "-toad bufo ";
		clh.evaluateArgumentsString(argString);
	}

	@Test
	public void testFrog() throws CmdLineException {
		final String argString = "-frog rana";
		clh.evaluateArgumentsString(argString);
	}

	// TODO should have a way to run and throw a InvalidRuntimeArgumentException
	@Test(expected = org.javautil.security.NoExitException.class)
	public void testExclusive() throws CmdLineException {
		final String argString = "-frog rana -toad bufo";
		final boolean result = clh.evaluateArgumentsString(argString);
		logger.debug("result " + result);
	}
}
