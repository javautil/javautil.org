package org.javautil.address.usps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.service.usps.UspsServiceRequestHelper;
import org.javautil.persistence.PersistenceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
/**
 * Verification per USPS.
 *
 * https://www.usps.com/webtools/htm/Address-Information-v3-1a.htm#_Toc131231398
 * Section 2.2
 *
 * @author jjs
 * @version $Id: SingleAddressValidationServiceTest.java,v 1.5 2012/03/05 19:54:01 jjs Exp $
 * @since 0.11.0
 */
public class SingleAddressValidationServiceTest {

	private final UspsServiceRequestHelper helper = new UspsServiceRequestHelper();

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * <p>
	 * testRequest1.
	 * </p>
	 * */
	@Test
	public void testRequest1() throws PersistenceException,
			AddressValidationException, SQLException {
		new UspsValidationTestData();
		final Address address = UspsValidationTestData.getAddress1();
		final AuthoritativeAddress authoritative = helper.process(address);
		assertNull(authoritative.getAddress1());
		assertEquals("6406 IVY LN", authoritative.getAddress2());
		assertEquals("GREENBELT", authoritative.getCity());
		assertEquals("MD", authoritative.getState());
		assertEquals("20770-1440", authoritative.getPostalCode());
	}

	/**
	 * <p>
	 * testRequest2.
	 * </p>
	 * 
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 * @throws org.javautil.address.usps.AddressValidationException
	 *             if any.
	 * @throws java.sql.SQLException
	 *             if any.
	 */
	@Test
	public void testRequest2() throws PersistenceException,
			AddressValidationException, SQLException {
		new UspsValidationTestData();
		final Address address = UspsValidationTestData.getAddress2();
		final AuthoritativeAddress authoritative = helper.process(address);
		logger.debug("\n" + authoritative);
		assertNull(authoritative.getAddress1());
		assertEquals("8 WILDWOOD DR", authoritative.getAddress2());
		assertEquals("OLD LYME", authoritative.getCity());
		assertEquals("CT", authoritative.getState());
		assertEquals("06371-1844", authoritative.getPostalCode());
		assertNull(authoritative.getAuthoritativeErrorMessage());

	}

}
