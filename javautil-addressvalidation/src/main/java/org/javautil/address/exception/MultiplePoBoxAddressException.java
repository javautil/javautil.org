package org.javautil.address.exception;

import org.javautil.address.interfaces.Address;

/**
 * More than one Post office box was found in the address.
 *
 * @author jjs
 * @version $Id: MultiplePoBoxAddressException.java,v 1.1 2012/03/04 12:31:16 jjs Exp $
 */
public class MultiplePoBoxAddressException extends
		AddressStandardizationException {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for MultiplePoBoxAddressException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public MultiplePoBoxAddressException(final Address address) {
		super(address);
	}

	/**
	 * <p>Constructor for MultiplePoBoxAddressException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 * @param e a {@link java.lang.String} object.
	 */
	public MultiplePoBoxAddressException(final Address address, final String e) {
		super(address, e);
	}

}
