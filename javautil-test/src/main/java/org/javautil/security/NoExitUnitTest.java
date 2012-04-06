package org.javautil.security;

import org.junit.After;
import org.junit.Before;

/**
 * Although this is only used for testing it is in the main tree so that it can
 * be deployed in the jar for use in other packages.
 * 
 * @author jjs
 * 
 */
public class NoExitUnitTest {

	@Before
	public void setUp() throws Exception {
		System.setSecurityManager(new NoExitSecurityManager());
	}

	@After
	public void tearDown() throws Exception {
		System.setSecurityManager(null);
	}

}
