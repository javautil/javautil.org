package org.javautil.commandline;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import jcmdline.CmdLineException;
import jcmdline.Parameter;

import org.javautil.commandline.annotations.IntegerArguments;
import org.junit.Test;

public class ParameterCreatorTest extends BaseTest {
	@Test
	public void test() throws CmdLineException {
		Map<String, Parameter> parmsByTag = null;
		final IntegerArguments args = new IntegerArguments();
		final ParameterCreator creator = new ParameterCreator(args);
		parmsByTag = creator.generateParametersForArgumentBean();
		final Parameter p = parmsByTag.get("intValue");
		assertNotNull(p);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testTwo() {
		Map<String, Parameter> parmsByTag = null;
		final IntegerArguments args = new IntegerArguments();
		final ParameterCreator creator = new ParameterCreator(args, null, "");
		parmsByTag = creator.generateParametersForArgumentBean();
		final Parameter p = parmsByTag.get("intValue");
		assertNotNull(p);
	}
}
