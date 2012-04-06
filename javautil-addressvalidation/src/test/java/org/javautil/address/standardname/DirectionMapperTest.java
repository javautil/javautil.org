package org.javautil.address.standardname;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * <p>DirectionMapperTest class.</p>
 *
 * @author jjs
 * @version $Id: DirectionMapperTest.java,v 1.2 2012/03/04 12:31:18 jjs Exp $
 * @since 0.11.0
 */
public class DirectionMapperTest {

	/**
	 * <p>test.</p>
	 */
	@Test
	public void test() {
		final DirectionMapper dm = new DirectionMapper();
		assertEquals("NW", dm.map("NW "));
		assertEquals("NW", dm.map(" NW "));
		assertEquals("NW", dm.map(" nw "));
		assertEquals("NW", dm.map(" n.w. "));

	}
}
