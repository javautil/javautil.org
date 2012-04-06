package org.javautil.address.formatters;

import static org.junit.Assert.assertEquals;

import org.javautil.test.StandardTest;
import org.junit.Test;

/**
 * <p>CapitalizerTest class.</p>
 *
 * @author jjs
 * @version $Id: CapitalizerTest.java,v 1.2 2012/03/04 12:31:20 jjs Exp $
 * @since 0.11.0
 */
public class CapitalizerTest extends StandardTest {

	private final Capitalizer capitalizer = new Capitalizer();

	/**
	 * <p>test.</p>
	 */
	@Test
	public void test() {
		assertEquals("King of England", Capitalizer.initCaps("king of england"));
		assertEquals("Toad", Capitalizer.initCaps("toad"));
		assertEquals("Toad", Capitalizer.initCaps("TOAD"));
		assertEquals("", Capitalizer.initCaps(null));

	}

	/**
	 * <p>testStreetCap.</p>
	 */
	@Test
	public void testStreetCap() {
		assertEquals("Burney Lane", Capitalizer.initCapsStreet("Burney Lane"));
		assertEquals("Burney Lane", Capitalizer.initCapsStreet("burney lane"));

	}

}
