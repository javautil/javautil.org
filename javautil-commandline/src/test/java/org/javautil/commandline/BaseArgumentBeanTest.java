package org.javautil.commandline;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BaseArgumentBeanTest extends BaseTest {
	BaseArgumentBean argumentBean = new BaseArgumentBean();

	@Test
	public void testVerbose() {
		argumentBean.setVerbose(true);
		assertTrue(argumentBean.isVerbose());
		argumentBean.setVerbose(false);
		assertFalse(argumentBean.isVerbose());
	}

}
