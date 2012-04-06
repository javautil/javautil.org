package org.javautil.commandline;

import jcmdline.CmdLineException;

import org.javautil.commandline.annotations.DirectoryArguments;
import org.junit.Test;

public class CommandLineHandlerTest extends BaseTest {
	// TODO clean up

	// /**
	// * Ensures that the CommandLineHandler(ResourceBundle, Object) constructor
	// * is still around.
	// */
	// @Test
	// public void miscellaneous() {
	// ResourceBundle resources = ResourceBundle
	// .getBundle("org.javautil.commandline.annotations.DirectoryArguments");
	// DirectoryArguments argumentBean = new DirectoryArguments();
	// CommandLineHandler clh = new CommandLineHandler(resources, argumentBean);
	// clh.setDieOnParseError(true);
	// assertTrue(clh.isDieOnParseError());
	// clh.setDieOnParseError(false);
	// assertFalse(clh.isDieOnParseError());
	// // TODO need to ensure that it does die on parse error
	// }

	// @Test(expected = java.lang.IllegalArgumentException.class)
	// public void testNullArguments() throws CmdLineException {
	// // ResourceBundle resources =
	// //
	// ResourceBundle.getBundle("org.javautil.commandline.annotations.DirectoryArguments");
	// DirectoryArguments argumentBean = new DirectoryArguments();
	// CommandLineHandler clh = new CommandLineHandler(argumentBean);
	// clh.evaluateArguments(null);
	// }

	@Test(expected = java.lang.IllegalArgumentException.class)
	// No such directory
	public void testArguments() throws CmdLineException {
		// ResourceBundle resources =
		// ResourceBundle.getBundle("org.javautil.commandline.annotations.DirectoryArguments");
		final DirectoryArguments argumentBean = new DirectoryArguments();
		final CommandLineHandler clh = new CommandLineHandler(argumentBean);
		clh.setDieOnParseError(false);
		clh.evaluateArgumentsString("-required twit");
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testIllegal() throws CmdLineException {
		// ResourceBundle resources =
		// ResourceBundle.getBundle("org.javautil.commandline.annotations.DirectoryArguments");
		final DirectoryArguments argumentBean = new DirectoryArguments();
		final CommandLineHandler clh = new CommandLineHandler(argumentBean);
		clh.throwIllegalArgumentException();
		clh.setDieOnParseError(false);
		clh.evaluateArgumentsString("-requires twit");
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	// null bean not allowed.
	public void testNullBean() {
		new CommandLineHandler(null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	// null bean not allowed.
	public void testNullArguments() {
		final DirectoryArguments argumentBean = new DirectoryArguments();
		final CommandLineHandler clh = new CommandLineHandler(argumentBean);
		clh.evaluateArguments(null);
	}

	// @Test // null bean not allowed.
	// public void testSetters() throws CmdLineException {
	// DirectoryArguments argumentBean = new DirectoryArguments();
	// CommandLineHandler clh = new CommandLineHandler(argumentBean);
	// String appDescription = "testApp";
	// clh.setApplicationDescription(appDescription);
	// String applicationVersion = "av";
	// clh.setApplicationVersion(applicationVersion);
	// assertEquals(applicationVersion,clh.getApplicationVersion());
	// assertEquals(appDescription,clh.getApplicationDescription());
	// clh.evaluateArgumentsString("-requires src");
	// }

	// @Test
	// //(expected = java.lang.IllegalArgumentException.class)
	// public void testMonlothicArguments() throws CmdLineException {
	// // ResourceBundle resources =
	// //
	// ResourceBundle.getBundle("org.javautil.commandline.annotations.DirectoryArguments");
	// DirectoryArguments argumentBean = new DirectoryArguments();
	// MonolithicCommandLineHandler clh = new
	// MonolithicCommandLineHandler(argumentBean);
	// clh.evaluateArgumentsString("-required twat");
	// }
}
