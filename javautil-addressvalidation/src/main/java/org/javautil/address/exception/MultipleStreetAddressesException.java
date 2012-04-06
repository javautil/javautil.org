package org.javautil.address.exception;

import org.javautil.address.interfaces.Address;

/**
 * <p>MultipleStreetAddressesException class.</p>
 *
 * @author jjs
 * @version $Id: MultipleStreetAddressesException.java,v 1.1 2012/03/04 12:31:17 jjs Exp $
 */
public class MultipleStreetAddressesException extends
		AddressStandardizationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2416664254389941059L;

	/**
	 * Multiple subunits were found in the address.
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public MultipleStreetAddressesException(final Address address) {
		super(address);

	}
}
