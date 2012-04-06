/**
 * 
 */
package org.javautil.address.exception;

import org.javautil.address.interfaces.Address;

/**
 * <p>NoDeliveryLocationFoundException class.</p>
 *
 * @author jjs
 * @version $Id: NoDeliveryLocationFoundException.java,v 1.1 2012/03/04 12:31:16 jjs Exp $
 */
public class NoDeliveryLocationFoundException extends
		AddressStandardizationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5823726569969878250L;

	/**
	 * <p>Constructor for NoDeliveryLocationFoundException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 * @param e a {@link java.lang.String} object.
	 */
	public NoDeliveryLocationFoundException(final Address address,
			final String e) {
		super(address, e);
	}

	/**
	 * <p>Constructor for NoDeliveryLocationFoundException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public NoDeliveryLocationFoundException(final Address address) {
		super(address);
	}

}
