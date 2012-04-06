package org.javautil.commandline.annotations;

import jcmdline.CmdLineException;

import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.junit.Test;

public class RequiredUnlessTest extends BaseTest {

	private final RequiredUnlessArguments argumentBean = new RequiredUnlessArguments();
	private final CommandLineHandler clh = new CommandLineHandler(argumentBean);

	public void test1() throws CmdLineException {
		final String argString = "-frog 1 -toad 2 ";
		clh.evaluateArgumentsString(argString);
	}

	@Test
	public void testUnlessField() throws CmdLineException {
		final String argString = "-toad 2";
		clh.evaluateArgumentsString(argString);
	}

	@Test
	public void testRequiredUnlessOnly() throws CmdLineException {
		final String argString = "-frog 2";
		clh.evaluateArgumentsString(argString);
	}

	/**
	 * As not frog or toad is provided.
	 * 
	 * @throws CmdLineException
	 */
	@Test(expected = org.javautil.security.NoExitException.class)
	public void testMissingRequiredUnless() throws CmdLineException {
		final String argString = "-snake Hognose";
		clh.evaluateArgumentsString(argString);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testMissingRequiredValue() throws CmdLineException {
		final String argString = "";
		clh.setDieOnParseError(false);
		clh.evaluateArgumentsString(argString);
	}
}
