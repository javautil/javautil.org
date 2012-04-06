package org.javautil.commandline;

import static org.junit.Assert.assertEquals;
import jcmdline.CmdLineException;

import org.javautil.commandline.annotations.IntegerArguments;
import org.junit.Test;

public class ReadFromPropertiesFile extends BaseTest {
	// TODO make this work

	@Test
	public void testEquals() throws CmdLineException {
		final IntegerArguments argumentBean = new IntegerArguments();
		final CommandLineHandler clh = new CommandLineHandler(argumentBean);
		clh.evaluateArgumentsString("--from-properties=src/test/resources/org/javautil/commandline/ReadFromPropertiesFile.properties");
		assertEquals(new Integer(1), argumentBean.getIntValue());
	}

	@Test
	public void test2() throws CmdLineException {
		final IntegerArguments argumentBean = new IntegerArguments();
		final CommandLineHandler clh = new CommandLineHandler(argumentBean);
		clh.evaluateArgumentsString("--from-properties src/test/resources/org/javautil/commandline/ReadFromPropertiesFile.properties");
		assertEquals(new Integer(1), argumentBean.getIntValue());
	}
}
