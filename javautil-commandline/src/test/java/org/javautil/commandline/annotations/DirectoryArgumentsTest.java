package org.javautil.commandline.annotations;

import jcmdline.CmdLineException;

import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.junit.Test;

public class DirectoryArgumentsTest extends BaseTest {

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void optionalDirectory() throws CmdLineException {
		final DirectoryArguments da = new DirectoryArguments();
		final CommandLineHandler clh = new CommandLineHandler(da);
		final String argString = "-optionalDirectory ";
		clh.setDieOnParseError(false);
		clh.evaluateArgumentsString(argString);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void test2() throws CmdLineException {
		final DirectoryArguments da = new DirectoryArguments();
		final CommandLineHandler clh = new CommandLineHandler(da);
		final String argString = "-yo ";
		clh.setDieOnParseError(false);
		clh.evaluateArgumentsString(argString);
	}

	// TODO this should have failed as there is no such directory
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void optionalNonExistentDirectory() throws CmdLineException {
		// todo ensure that this directory doesn't exist
		final DirectoryArguments da = new DirectoryArguments();
		final CommandLineHandler clh = new CommandLineHandler(da);
		clh.setDieOnParseError(false);
		final String argString = "-optionalReadableDirectory swizzle ";
		clh.evaluateArgumentsString(argString);
	}

	@Test
	public void test6() throws CmdLineException {
		// todo ensure that this directory doesn't exist
		final DirectoryArguments da = new DirectoryArguments();
		final CommandLineHandler clh = new CommandLineHandler(da);
		final String argString = "-requiredDirectory src ";
		clh.evaluateArgumentsString(argString);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void optionalExistingReadableDirectoryTest() throws CmdLineException {
		// todo ensure that this directory doesn't exist
		final DirectoryArguments da = new DirectoryArguments();
		final CommandLineHandler clh = new CommandLineHandler(da);
		clh.setDieOnParseError(false);
		final String argString = "-requiredDirectory src -optionalExistingReadableDirectory swizzle";
		clh.evaluateArgumentsString(argString);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void optionalReadableDirectoryTest() throws CmdLineException {
		// todo ensure that this directory doesn't exist
		final DirectoryArguments da = new DirectoryArguments();
		final CommandLineHandler clh = new CommandLineHandler(da);
		clh.setDieOnParseError(false);
		final String argString = "-requiredDirectory src -optionalReadableDirectory swizzle";
		clh.evaluateArgumentsString(argString);
	}

	// todo this should have failed as there is no such directory
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void test5() throws CmdLineException {
		// todo ensure that this directory doesn't exist
		final DirectoryArguments da = new DirectoryArguments();
		final CommandLineHandler clh = new CommandLineHandler(da);
		clh.setDieOnParseError(false);
		final String argString = "-requiredDirectory swizzle ";
		clh.evaluateArgumentsString(argString);
	}
}
