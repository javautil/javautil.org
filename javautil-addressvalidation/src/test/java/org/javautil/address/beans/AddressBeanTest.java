/**
 * 
 */
package org.javautil.address.beans;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * <p>
 * AddressBeanTest class.
 * </p>
 * 
 * @author siyer
 * @version $Id: AddressBeanTest.java,v 1.3 2012/03/05 19:54:02 jjs Exp $
 * @since 0.11.0
 */
public class AddressBeanTest {

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * <p>
	 * test1.
	 * </p>
	 */
	@Test
	public void test1() {
		final String address1 = "410 S Main";
		final String address2 = "";
		final String city = "Romeo";
		final String st = "MI";
		final String postalCode = "48065";
		final AddressBean addr = new AddressBean(address1, address2, city, st,
				postalCode);
		Assert.assertNotNull(addr);
		Assert.assertNotNull(addr.getFormatted());
	}

	/**
	 * <p>
	 * getAddressHashTest.
	 * </p>
	 */
	@Test
	public void getAddressHashTest() {
		final String address1 = "410 S Main";
		final String address2 = "";
		final String city = "Romeo";
		final String st = "MI";
		final String postalCode = "48065";
		final AddressBean addr = new AddressBean(address1, address2, city, st,
				postalCode);
		Assert.assertNotNull(addr);
		final int hash = addr.getAddressHash();
		Assert.assertTrue(hash != 0);
		logger.info("Hash = " + hash);
	}

	/**
	 * <p>
	 * getIdTest.
	 * </p>
	 */
	@Test
	public void getIdTest() {
		final Long id = new Long(2038290348);
		final AddressBean addr = new AddressBean();
		addr.setId(id);
		Assert.assertTrue(addr.getId() != null);
	}

	/**
	 * <p>
	 * setPostalCodeSet.
	 * </p>
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setPostalCodeSet() {
		final String postalCode = "1934303493-111";
		final AddressBean addr = new AddressBean();
		addr.setPostalCode(postalCode);
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
		final String streetName = "410 S Main";
		final AddressBean addr = new AddressBean();

		addr.setLatitude(latitude);
		addr.setLongitude(longitude);
		addr.setStreetName(streetName);

		Assert.assertNotNull(addr.getLatitude());
		Assert.assertNotNull(addr.getLongitude());
		Assert.assertNotNull(addr.getStreetName());
	}

}
