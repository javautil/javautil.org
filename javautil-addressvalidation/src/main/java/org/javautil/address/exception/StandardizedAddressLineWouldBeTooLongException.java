package org.javautil.address.exception;

import org.javautil.address.interfaces.Address;

/**
 * More than one Post office box was found in the address.
 *
 * @author jjs
 * @version $Id: StandardizedAddressLineWouldBeTooLongException.java,v 1.1 2012/03/04 12:31:16 jjs Exp $
 */
public class StandardizedAddressLineWouldBeTooLongException extends
		AddressStandardizationException {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for StandardizedAddressLineWouldBeTooLongException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public StandardizedAddressLineWouldBeTooLongException(final Address address) {
		super(address);
	}

	/**
	 * <p>Constructor for StandardizedAddressLineWouldBeTooLongException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 * @param e a {@link java.lang.String} object.
	 */
	public StandardizedAddressLineWouldBeTooLongException(
			final Address address, final String e) {
		super(address, e);
	}

}
