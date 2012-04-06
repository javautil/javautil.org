package org.javautil.address.service.usps;

import org.apache.log4j.Logger;
import org.javautil.address.AddressValidationRequest;
import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.service.SingleAddressValidationService;
import org.javautil.address.usps.AddressValidationException;
import org.javautil.address.usps.UspsValidationServicePropertyHelper;

/**
 * <p>
 * UspsServiceRequestHelper class.
 * </p>
 * 
 * @author jjs
 * @version $Id: UspsServiceRequestHelper.java,v 1.1 2012/03/04 12:31:15 jjs Exp
 *          $
 */
public class UspsServiceRequestHelper {

	private SingleAddressValidationService validator = new SingleAddressValidationService();
	private final String uspsAcctCode = new UspsValidationServicePropertyHelper()
			.getUserId();

	private final String uspsUrl = new UspsValidationServicePropertyHelper()
			.getTestUrl();
	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * Simple way to process an address.
	 * 
	 * <pre>
	 * 
	 * 
	 * private final UspsServiceRequestHelper helper = new UspsServiceRequestHelper();
	 * 
	 * private final Logger logger = Logger.getLogger(getClass());
	 * 
	 * &#064;Test
	 * public void testRequest1() throws PersistenceException,
	 * 		AddressValidationException, SQLException {
	 * 	new UspsValidationTestData();
	 * 	final Address address = UspsValidationTestData.getAddress1();
	 * 	final AuthoritativeAddress authoritative = helper.processRaw(address);
	 * 	assertNull(authoritative.getAddress1());
	 * 	assertEquals(&quot;6406 IVY LN&quot;, authoritative.getAddress2());
	 * 	assertEquals(&quot;GREENBELT&quot;, authoritative.getCity());
	 * 	assertEquals(&quot;MD&quot;, authoritative.getState());
	 * 	assertEquals(&quot;20770-1440&quot;, authoritative.getPostalCode());
	 * }
	 * </pre>
	 * 
	 * @param address
	 *            a {@link org.javautil.address.interfaces.Address} object.
	 * @throws org.javautil.address.usps.AddressValidationException
	 *             if any.
	 * @return a {@link org.javautil.address.interfaces.AuthoritativeAddress}
	 *         object.
	 */
	public AuthoritativeAddress process(final Address address)
			throws AddressValidationException {
		validator = new SingleAddressValidationService();
		validator.setUspsAcctCode(uspsAcctCode);
		validator.setUspsURL(uspsUrl);
		final AddressValidationRequest request = new AddressValidationRequest();
		request.setRawAddress(address);
		final Address raw = request.getRawAddress();
		final String rawFormatted = raw.toString();
		if (logger.isDebugEnabled()) {
			logger.debug(rawFormatted);
		}
		request.setRawAddress(raw);
		validator.process(request);
		final AuthoritativeAddress returnValue = request
				.getAuthoritativeAddress();
		if (logger.isDebugEnabled()) {
			logger.debug("Authoritative:\n" + returnValue);
		}
		return returnValue;
	}
}
