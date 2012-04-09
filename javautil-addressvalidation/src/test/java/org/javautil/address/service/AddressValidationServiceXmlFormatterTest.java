/**
 * 
 */
package org.javautil.address.service;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.javautil.address.AddressValidationRequest;
import org.javautil.address.interfaces.Address;
import org.javautil.address.service.usps.UspsAddressValidationRequest;
import org.javautil.address.usps.UspsValidationServicePropertyHelper;
import org.javautil.address.usps.UspsValidationTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>AddressValidationServiceXmlFormatterTest class.</p>
 *
 * @author jjs
 * @version $Id: AddressValidationServiceXmlFormatterTest.java,v 1.5 2012/04/09 01:29:07 jjs Exp $
 * @since 0.11.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class AddressValidationServiceXmlFormatterTest {

	private static UspsValidationServicePropertyHelper helper;

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * <p>setUpBeforeClass.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		helper = new UspsValidationServicePropertyHelper();
		helper.setTestUrl("http://SERVERNAME/ShippingAPITest.dll");
		helper.setProductionUrl(null);
		helper.setUserId("xxxxxxx");
	}

	/**
	 * <p>tearDownAfterClass.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	@After
	public void tearDownAfterClass() throws Exception {
	}

	/**
	 * <p>setUp.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * <p>testFormat1.</p>
	 */
	@Test
	public void testFormat1() {
		final Address address = UspsValidationTestData.getAddress1();
		final AddressValidationRequest request = new AddressValidationRequest();
		request.setRawAddress(address);
		final UspsAddressValidationRequest validator = new UspsAddressValidationRequest(
				helper.getTestUrl(), helper.getUserId());
		validator.addAddress(address);
		final String rawUrl = validator.asRawURLText();
		final String expected = UspsValidationTestData.getAddress1xml();
		logger.debug("expected: " + expected);
		logger.debug("actual  : " + rawUrl);
		assertEquals(expected, rawUrl);
	}
}
