package org.javautil.address.exception;

import org.javautil.address.interfaces.Address;

/**
 * <p>MultipleSubunitException class.</p>
 *
 * @author jjs
 * @version $Id: MultipleSubunitException.java,v 1.1 2012/03/04 12:31:16 jjs Exp $
 */
public class MultipleSubunitException extends AddressStandardizationException {

	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 733475165599365342L;

	/**
	 * Multiple subunits were found in the address.
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public MultipleSubunitException(final Address address) {
		super(address);

	}

}
