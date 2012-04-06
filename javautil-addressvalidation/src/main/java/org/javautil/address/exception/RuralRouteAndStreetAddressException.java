/**
 * 
 */
package org.javautil.address.exception;

import org.javautil.address.interfaces.Address;

/**
 * <p>RuralRouteAndStreetAddressException class.</p>
 *
 * @author jjs
 * @version $Id: RuralRouteAndStreetAddressException.java,v 1.1 2012/03/04 12:31:17 jjs Exp $
 */
public class RuralRouteAndStreetAddressException extends
		AddressStandardizationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5823726569969878250L;

	/**
	 * <p>Constructor for RuralRouteAndStreetAddressException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 * @param e a {@link java.lang.String} object.
	 */
	public RuralRouteAndStreetAddressException(final Address address,
			final String e) {
		super(address, e);
	}

	/**
	 * <p>Constructor for RuralRouteAndStreetAddressException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public RuralRouteAndStreetAddressException(final Address address) {
		super(address);
	}

}
