/**
 * 
 */
package org.javautil.address.beans;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * <p>
 * AuthoritativeAddressBeanTest class.
 * </p>
 * 
 * @author siyer
 * @version $Id: AuthoritativeAddressBeanTest.java,v 1.2 2012/03/04 12:31:18 jjs
 *          Exp $
 * @since 0.11.0
 */
public class AuthoritativeAddressBeanTest {

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * <p>
	 * test1.
	 * </p>
	 */
	@Test
	public void test1() {
		final AuthoritativeAddressBean bean = new AuthoritativeAddressBean();
		bean.setAuthoritativeErrorMessage("Error Message");
		Assert.assertNotNull(bean.getAuthoritativeErrorMessage());
		Assert.assertNotNull(bean.getFormatted());
	}

	/**
	 * <p>
	 * test2.
	 * </p>
	 */
	@Test
	public void test2() {
		final AuthoritativeAddressBean bean = new AuthoritativeAddressBean();
		// bean.setAuthoritativeErrorMessage("Error Message");
		Assert.assertNull(bean.getAuthoritativeErrorMessage());
		Assert.assertNotNull(bean.getFormatted());
	}

	/**
	 * <p>
	 * test10.
	 * </p>
	 */
	@Test
	public void test10() {
		final Double latitude = new Double(45.66834);
		final Double longitude = new Double(34.123223);
		final AuthoritativeAddressBean addr = new AuthoritativeAddressBean();

		addr.setLatitude(latitude);
		addr.setLongitude(longitude);

		Assert.assertNotNull(addr.getLatitude());
		Assert.assertNotNull(addr.getLongitude());
	}

}
