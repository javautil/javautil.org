package org.javautil.commandline.annotations;

import static org.junit.Assert.assertEquals;

import java.io.File;

import jcmdline.CmdLineException;

import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.junit.Test;

public class InheritenceArgumentsTest extends BaseTest {

	private final InheritenceArguments argumentBean = new InheritenceArguments();
	private final CommandLineHandler clh = new CommandLineHandler(argumentBean);

	@Test
	public void testInheritence() throws CmdLineException {
		final String argString = "-intValue 3 -outputFile pom.xml ";
		clh.evaluateArgumentsString(argString);
		assertEquals(new Integer(3), argumentBean.getIntValue());
		new File("pom.xml");
		assertEquals("pom.xml", argumentBean.getOutputFile().getName());
	}

}
