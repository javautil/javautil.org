package org.javautil.address;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

import org.junit.Test;

/**
 * <p>AddressBundleTest class.</p>
 *
 * @author jjs
 * @version $Id: AddressBundleTest.java,v 1.2 2012/03/04 12:31:11 jjs Exp $
 * @since 0.11.0
 */
public class AddressBundleTest {
	private final String name = "RuralRouteParser.RRFoundButBoxNotFound";
	private final String r = AddressResourceBundle.getString(name);

	/**
	 * <p>testMissingProperty.</p>
	 */
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testMissingProperty() {
		AddressResourceBundle.getString("dog");
	}

	/**
	 * <p>testPresent.</p>
	 */
	@Test
	public void testPresent() {
		assertEquals("RR found but box not found",
				AddressResourceBundle.getString(name));
	}

	/**
	 * <p>testNotNumber.</p>
	 */
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testNotNumber() {
		assertEquals("RR found but box not found",
				AddressResourceBundle.getInt(r));
	}

	/**
	 * <p>getBooleanTest.</p>
	 */
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void getBooleanTest() {
		Assert.assertEquals(true, AddressResourceBundle.getBoolean(r));
	}
}
