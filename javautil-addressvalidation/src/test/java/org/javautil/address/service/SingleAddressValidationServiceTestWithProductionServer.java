package org.javautil.address.service;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.javautil.address.AddressValidationRequest;
import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.usps.AddressValidationException;
import org.javautil.address.usps.UspsValidationServicePropertyHelper;
import org.javautil.persistence.PersistenceException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
/**
 * Verification per USPS.
 *
 * TODO nuke?
 *
 * https://www.usps.com/webtools/htm/Address-Information-v3-1a.htm#_Toc131231398
 * Section 2.2
 *
 * @author jjs
 * @version $Id: SingleAddressValidationServiceTestWithProductionServer.java,v 1.3 2012/03/04 12:31:16 jjs Exp $
 * @since 0.11.0
 */
public class SingleAddressValidationServiceTestWithProductionServer {

	private SingleAddressValidationService validator = new SingleAddressValidationService();

	private String uspsAcctCode = new UspsValidationServicePropertyHelper().getUserId();

	private String uspsUrl = new UspsValidationServicePropertyHelper().getTestUrl();

	private final Logger logger = Logger.getLogger(getClass());

	// TODO
	/**
	 * <p>processRaw.</p>
	 *
	 * @param address1 a {@link java.lang.String} object.
	 * @param address2 a {@link java.lang.String} object.
	 * @param city a {@link java.lang.String} object.
	 * @param state a {@link java.lang.String} object.
	 * @param zip a {@link java.lang.String} object.
	 * @return a {@link org.javautil.address.interfaces.AuthoritativeAddress} object.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 */
	public AuthoritativeAddress processRaw(final String address1,
			final String address2, final String city, final String state,
			final String zip) throws AddressValidationException {
		validator = new SingleAddressValidationService();
		validator.setUspsAcctCode(getUspsAcctCode());
		validator.setUspsURL(getUspsUrl());
		final AddressValidationRequest request = new AddressValidationRequest();
		final Address raw = request.getRawAddress();
		raw.setAddress1(address1);
		raw.setAddress2(address2);
		raw.setCity(city);
		raw.setState(state);
		raw.setPostalCode(zip);
		final String rawFormatted = raw.toString();
		logger.debug(rawFormatted);
		request.setRawAddress(raw);
		validator.process(request);
		final AuthoritativeAddress returnValue = request
				.getAuthoritativeAddress();
		logger.debug("Authoritative:\n" + returnValue);
		return returnValue;
	}

	// TODO use UspsValidationTestData

	/**
	 * <p>test.</p>
	 *
	 * @throws org.javautil.persistence.PersistenceException if any.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 * @throws java.sql.SQLException if any.
	 */
	@Ignore
	// doesnt work on test server
	@Test
	public void test() throws PersistenceException, AddressValidationException,
			SQLException {
		final AuthoritativeAddress whiteHouse = processRaw("West Wing",
				"1600 Pennsylvania Avenue", "Washington", "DC", null);
		assertEquals("20500-0003", whiteHouse.getPostalCode());
	}

	/**
	 * <p>test3.</p>
	 *
	 * @throws org.javautil.persistence.PersistenceException if any.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 * @throws java.sql.SQLException if any.
	 */
	@Ignore
	// doesn't work on test server
	@Test
	public void test3() throws PersistenceException,
			AddressValidationException, SQLException {
		final AuthoritativeAddress whiteHouse = processRaw(
				"1600 Pennsylvania Avenue", "West Wing", "Washington", "DC",
				null);
		assertEquals("20500-0003", whiteHouse.getPostalCode());
	}

	/**
	 * <p>test5.</p>
	 *
	 * @throws org.javautil.persistence.PersistenceException if any.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 * @throws java.sql.SQLException if any.
	 */
	@Ignore
	// doesn't work on test server
	// TODO why
	@Test
	public void test5() throws PersistenceException,
			AddressValidationException, SQLException {
		final AuthoritativeAddress whiteHouse = processRaw(
				"Office of the President", "1600 Pennsylvania Avenue NW",
				"Washington", "DC", null);

		assertEquals("Multiple responses found.  No default address",
				whiteHouse.getAuthoritativeErrorMessage());
	}

	/**
	 * <p>test2.</p>
	 *
	 * @throws org.javautil.persistence.PersistenceException if any.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 * @throws java.sql.SQLException if any.
	 */
	@Ignore
	@Test
	public void test2() throws PersistenceException,
			AddressValidationException, SQLException {
		validator = new SingleAddressValidationService();
		validator.setUspsAcctCode(getUspsAcctCode()); // TODO get a javautil
														// address
		validator.setUspsURL(getUspsUrl());
		final AddressValidationRequest request = new AddressValidationRequest();
		final Address raw = request.getRawAddress();
		raw.setAddress1("West Wing");
		raw.setAddress2("1600 Pennsylvania Avenue");
		raw.setCity("Washington");
		raw.setState("D.C.");
		validator.process(request);
		final AuthoritativeAddress whiteHouse = request
				.getAuthoritativeAddress();
		logger.info(whiteHouse);
		assertEquals("Invalid State Code.  ",
				whiteHouse.getAuthoritativeErrorMessage());
	}

	/**
	 * <p>Getter for the field <code>uspsAcctCode</code>.</p>
	 *
	 * @return the uspsAcctCode
	 */
	public String getUspsAcctCode() {
		return uspsAcctCode;
	}

	/**
	 * <p>Setter for the field <code>uspsAcctCode</code>.</p>
	 *
	 * @param uspsAcctCode
	 *            the uspsAcctCode to set
	 */
	public void setUspsAcctCode(final String uspsAcctCode) {
		this.uspsAcctCode = uspsAcctCode;
	}

	/**
	 * <p>Getter for the field <code>uspsUrl</code>.</p>
	 *
	 * @return the uspsUrl
	 */
	public String getUspsUrl() {
		return uspsUrl;
	}

	/**
	 * <p>Setter for the field <code>uspsUrl</code>.</p>
	 *
	 * @param uspsUrl
	 *            the uspsUrl to set
	 */
	public void setUspsUrl(final String uspsUrl) {
		this.uspsUrl = uspsUrl;
	}
}
