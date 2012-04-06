package org.javautil.commandline;

import org.apache.log4j.BasicConfigurator;
import org.javautil.test.NoExitUnitTest;
import org.junit.BeforeClass;

/**
 * Declared abstract so the test runners don't try to test this class.
 * 
 * @author jjs
 * 
 */
public abstract class BaseTest extends NoExitUnitTest {
	@BeforeClass
	public static void beforeClass() {
		BasicConfigurator.configure();
	}

}
