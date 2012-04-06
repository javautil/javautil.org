package org.javautil.address;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.javautil.address.beans.AddressBean;
import org.javautil.address.interfaces.StandardizedAddress;
import org.junit.Ignore;
import org.junit.Test;

/**
 * <p>USAddressStandardizerTest class.</p>
 *
 * @author jjs
 * @version $Id: USAddressStandardizerTest.java,v 1.3 2012/03/04 12:31:11 jjs Exp $
 * @since 0.11.0
 */
public class USAddressStandardizerTest {

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * <p>noProblemTest.</p>
	 */
	@Test
	public void noProblemTest() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("410 South Main Street");
		raw.setAddress2(null);
		raw.setCity("Romeo");
		raw.setState("MI");
		raw.setPostalCode("48065");
		raw.setCountryCode("USA");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		final StandardizedAddress std = standardizer.standardize(raw);
		assertEquals("410", std.getStreetNumber());
		assertEquals("South Main", std.getStreetName());
		assertEquals("STREET", std.getStreetType());
		assertEquals("Romeo", std.getCity());
		assertEquals("MI", std.getState());
		assertEquals("48065", std.getPostalCode());
		assertEquals("USA", std.getCountryCode());
	}

	/**
	 * <p>subUnitTestNoPeriod.</p>
	 */
	@Test
	public void subUnitTestNoPeriod() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("410 South Main Street");
		raw.setAddress2("Apt 310");
		raw.setCity("Romeo");
		raw.setState("MI");
		raw.setPostalCode("48065");
		raw.setCountryCode("USA");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		final StandardizedAddress std = standardizer.standardize(raw);
		assertEquals("410", std.getStreetNumber());
		assertEquals("South Main", std.getStreetName());
		assertEquals("STREET", std.getStreetType());
		assertEquals("APT", std.getSubunitType());
		assertEquals("310", std.getSubunitCode());
		assertEquals("Romeo", std.getCity());
		assertEquals("MI", std.getState());
		assertEquals("48065", std.getPostalCode());
		assertEquals("USA", std.getCountryCode());
	}

	/**
	 * <p>subUnitTest.</p>
	 */
	@Test
	public void subUnitTest() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("410 South Main Street");
		raw.setAddress2("Apt. 310");
		raw.setCity("Romeo");
		raw.setState("MI");
		raw.setPostalCode("48065");
		raw.setCountryCode("USA");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		final StandardizedAddress std = standardizer.standardize(raw);
		assertEquals("410", std.getStreetNumber());
		assertEquals("South Main", std.getStreetName());
		assertEquals("STREET", std.getStreetType());
		assertEquals("APT", std.getSubunitType());
		assertEquals("310", std.getSubunitCode());
		assertEquals("Romeo", std.getCity());
		assertEquals("MI", std.getState());
		assertEquals("48065", std.getPostalCode());
		assertEquals("USA", std.getCountryCode());
	}

	/**
	 * <p>multipleSubUnitTest.</p>
	 */
	@Test(expected = org.javautil.address.exception.MultipleSubunitException.class)
	public void multipleSubUnitTest() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("1600 Pennsylvania Avenue Apt. 310");
		raw.setAddress2("Apt. 420");
		raw.setCity("Washington");
		raw.setState("DC");
		raw.setPostalCode("20500");
		raw.setCountryCode("USA");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		standardizer.standardize(raw);

	}

	/**
	 * <p>multipleStreetTest.</p>
	 */
	@Test(expected = org.javautil.address.exception.AddressStandardizationException.class)
	public void multipleStreetTest() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("1600 Pennsylvania Avenue Apt. 310");
		raw.setAddress2("301 Buckingham Road");
		raw.setCity("Washington");
		raw.setState("DC");
		raw.setPostalCode("20500");
		raw.setCountryCode("USA");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		standardizer.standardize(raw);

	}

	/**
	 * <p>twoPoBoxTest.</p>
	 */
	@Test(expected = org.javautil.address.exception.MultiplePoBoxAddressException.class)
	public void twoPoBoxTest() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("PO Box 1701");
		raw.setAddress2("PO Box 3025");
		raw.setCity("Romeo");
		raw.setState("MI");
		raw.setPostalCode("48065");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		final StandardizedAddress std = standardizer.standardize(raw);
		assertEquals("410", std.getStreetNumber());
		assertEquals("South Main", std.getStreetName());
		assertEquals("STREET", std.getStreetType());
		assertEquals("3025", std.getPoBox());
		assertEquals("Romeo", std.getCity());
		assertEquals("MI", std.getState());
		assertEquals("48065", std.getPostalCode());
	}

	/**
	 * <p>twoPoBoxOneLineTest.</p>
	 */
	@Test(expected = org.javautil.address.exception.AddressStandardizationException.class)
	public void twoPoBoxOneLineTest() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("PO Box 1701 PO Box 3025");
		// raw.setAddress2("PO Box 3025");
		raw.setCity("Romeo");
		raw.setState("MI");
		raw.setPostalCode("48065");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		final StandardizedAddress std = standardizer.standardize(raw);
		assertEquals(null, std.getStreetNumber());
		assertEquals("1701", std.getPoBox());
		assertEquals(null, std.getStreetType());
		assertEquals(null, std.getPoBox());
		assertEquals("Romeo", std.getCity());
		assertEquals("MI", std.getState());
		assertEquals("48065", std.getPostalCode());
	}

	// rural route address info http://pe.usps.gov/text/pub28/28c2_004.html

	/**
	 * <p>poBoxAndRuralRoute1Test.</p>
	 */
	@Test
	public void poBoxAndRuralRoute1Test() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("PO Box 3025");
		raw.setAddress2("RR 2 Box 342");
		raw.setCity("Romeo");
		raw.setState("MI");
		raw.setPostalCode("48065");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		final StandardizedAddress std = standardizer.standardize(raw);
		assertEquals("3025", std.getPoBox());
		assertEquals("RR 2 Box 342", std.getAddress2());
		assertEquals("Romeo", std.getCity());
		assertEquals("MI", std.getState());
		assertEquals("48065", std.getPostalCode());
	}

	/**
	 * <p>poBoxAndRuralRoute2Test.</p>
	 */
	@Test
	public void poBoxAndRuralRoute2Test() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("PO Box 3025");
		raw.setAddress2("RR 2 Box 34A");
		raw.setCity("Romeo");
		raw.setState("MI");
		raw.setPostalCode("48065");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		final StandardizedAddress std = standardizer.standardize(raw);
		assertEquals("3025", std.getPoBox());
		assertEquals("RR 2 Box 34A", std.getAddress2());
		assertEquals("Romeo", std.getCity());
		assertEquals("MI", std.getState());
		assertEquals("48065", std.getPostalCode());
	}

	// TODO fix this test
	/**
	 * <p>poBoxAndRuralRoute3Test.</p>
	 */
	@Ignore
	@Test
	public void poBoxAndRuralRoute3Test() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("PO Box 3025");
		raw.setAddress2("RR02 Box 34D");
		raw.setCity("Romeo");
		raw.setState("MI");
		raw.setPostalCode("48065");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		final StandardizedAddress std = standardizer.standardize(raw);
		assertEquals("3025", std.getPoBox());
		assertEquals("RR02 Box 34D", std.getAddress2());
		assertEquals("Romeo", std.getCity());
		assertEquals("MI", std.getState());
		assertEquals("48065", std.getPostalCode());
	}

	// todo this should throw an exception
	/**
	 * <p>poBoxAndRuralRoute4Test.</p>
	 */
	@Test
	public void poBoxAndRuralRoute4Test() {
		final AddressBean raw = new AddressBean();
		raw.setAddress1("PO Box 3025");
		raw.setAddress2("RR 2 Box 34-1D");
		raw.setCity("Romeo");
		raw.setState("MI");
		raw.setPostalCode("48065");
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		final StandardizedAddress std = standardizer.standardize(raw);
		assertEquals("3025", std.getPoBox());
		assertEquals("RR 2 Box 34-1D", std.getAddress2());
		assertEquals("Romeo", std.getCity());
		assertEquals("MI", std.getState());
		assertEquals("48065", std.getPostalCode());
	}

	/**
	 * <p>poBoxAndRuralRoute4Test2.</p>
	 */
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void poBoxAndRuralRoute4Test2() {
		final USAddressStandardizer standardizer = new USAddressStandardizer();
		standardizer.standardize(null);

	}

}
