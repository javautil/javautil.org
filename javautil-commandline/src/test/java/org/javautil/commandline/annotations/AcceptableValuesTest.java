package org.javautil.commandline.annotations;

import jcmdline.CmdLineException;

import org.javautil.commandline.CommandLineHandler;
import org.javautil.security.NoExitSecurityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AcceptableValuesTest {

	@Before
	public void setUp() throws Exception {
		System.setSecurityManager(new NoExitSecurityManager());
	}

	@After
	public void tearDown() throws Exception {
		System.setSecurityManager(null);
	}

	private final CommandLineHandler clh = new CommandLineHandler(this);

	@Required
	@AcceptableValues(values = { "a", "b" })
	private String text;

	@Test
	public void test() throws CmdLineException {
		final String argString = "-text b ";
		clh.evaluateArgumentsString(argString);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testFail() throws CmdLineException {
		final String argString = "-text c ";
		clh.setDieOnParseError(false);
		clh.evaluateArgumentsString(argString);
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

}
