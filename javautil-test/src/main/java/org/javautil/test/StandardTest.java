package org.javautil.test;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;

/**
 * This is intended to be the base class for Junit Tests.
 * 
 * @author jjs
 * 
 */
public abstract class StandardTest {

	@BeforeClass
	public static void beforeClass() {
		BasicConfigurator.configure();
	}

}
