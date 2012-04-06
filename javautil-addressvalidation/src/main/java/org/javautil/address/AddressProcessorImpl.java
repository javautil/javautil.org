package org.javautil.address;

import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AddressStandardizer;
import org.javautil.address.interfaces.AddressValidator;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.interfaces.StandardizedAddress;
import org.javautil.address.service.usps.UspsAddressVerificationRequest;
import org.javautil.address.usps.AddressValidationException;

/**
 * <p>AddressProcessorImpl class.</p>
 *
 * @author jjs
 * @version $Id: AddressProcessorImpl.java,v 1.3 2012/03/06 12:26:19 jjs Exp $
 */
public class AddressProcessorImpl {

	private final AddressStandardizer standardizer = new USAddressStandardizer();

	private final AddressValidator validator = new UspsAddressVerificationRequest();

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
