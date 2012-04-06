package org.javautil.address.interfaces;

import org.javautil.address.usps.AddressValidationException;

/**
 * <p>AddressValidator interface.</p>
 *
 * @author jjs
 * @version $Id: AddressValidator.java,v 1.2 2012/03/04 12:31:12 jjs Exp $
 */
public interface AddressValidator {

	/**
	 * <p>validate.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 * @return a {@link org.javautil.address.interfaces.AuthoritativeAddress} object.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 */
	public AuthoritativeAddress validate(Address address)
			throws AddressValidationException;
}
