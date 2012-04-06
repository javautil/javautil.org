package org.javautil.test;

import org.javautil.security.NoExitSecurityManager;
import org.junit.After;
import org.junit.Before;

public abstract class NoExitUnitTest extends StandardTest {

	@Before
	public void before() throws Exception {
		System.setSecurityManager(new NoExitSecurityManager());
	}

	@After
	public void after() throws Exception {
		System.setSecurityManager(null);
	}

}
