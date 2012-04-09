package org.javautil.address;

import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AddressStandardizer;
import org.javautil.address.interfaces.AddressValidator;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.interfaces.StandardizedAddress;
import org.javautil.address.service.usps.UspsAddressValidationRequest;
import org.javautil.address.usps.AddressValidationException;

/**
 * <p>AddressProcessorImpl class.</p>
 *
 * @author jjs
 * @version $Id: AddressProcessorImpl.java,v 1.4 2012/04/09 01:29:07 jjs Exp $
 */
public class AddressProcessorImpl {

	private final AddressStandardizer standardizer = new USAddressStandardizer();

	private final AddressValidator validator = new UspsAddressValidationRequest();

	/**
	 * <p>processAddress.</p>
	 *
	 * @param rawAddress a {@link org.javautil.address.interfaces.Address} object.
	 * @param standardizedAddress a {@link org.javautil.address.interfaces.StandardizedAddress} object.
	 * @param authoritativeAddress a {@link org.javautil.address.interfaces.AuthoritativeAddress} object.
	 * @return a {@link org.javautil.address.interfaces.AuthoritativeAddress} object.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 */
	public AuthoritativeAddress processAddress(final Address rawAddress,
			final StandardizedAddress standardizedAddress,
			final AuthoritativeAddress authoritativeAddress)
			throws AddressValidationException {

		standardizer.standardize(rawAddress, standardizedAddress);
		return validator.validate(standardizedAddress);
	}

}
