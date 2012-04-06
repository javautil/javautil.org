package org.javautil.commandline.annotations;

import jcmdline.CmdLineException;

import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.junit.Test;

public class OptionalRequiredArgumentsTest extends BaseTest {
	private final OptionalRequiredArguments argumentBean = new OptionalRequiredArguments();

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEtc() throws CmdLineException {
		final CommandLineHandler clh = new CommandLineHandler(argumentBean);
		final String argString = "-requiredDirectory /etc";
		clh.evaluateArgumentsString(argString);
	}
}
