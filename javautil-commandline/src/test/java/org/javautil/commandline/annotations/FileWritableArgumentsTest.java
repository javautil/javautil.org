package org.javautil.commandline.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import jcmdline.CmdLineException;
import jcmdline.Parameter;

import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.junit.Ignore;
import org.junit.Test;

public class FileWritableArgumentsTest extends BaseTest {

	private final FileWritableArguments argumentBean = new FileWritableArguments();

	private final CommandLineHandler clh = new CommandLineHandler(argumentBean);

	@Ignore
	@Test
	public void testExistingFile() throws CmdLineException {
		final String argString = "-outputFile pom.xml ";
		clh.evaluateArgumentsString(argString);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testNonExistingFile() throws CmdLineException {
		final String argString = "-outputFile noSuchFile.xml ";
		clh.setDieOnParseError(false);
		clh.evaluateArgumentsString(argString);
		final Parameter p = clh.getParameter("outputFile");
		assertNotNull(p);
		assertTrue(p.isSet());
		assertEquals(p.getValue(), "noSuchFile.xml");
	}
}
