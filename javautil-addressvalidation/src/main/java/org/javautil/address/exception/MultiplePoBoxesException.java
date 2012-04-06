/**
 * 
 */
package org.javautil.address.exception;

import org.javautil.address.interfaces.Address;

/**
 * <p>MultiplePoBoxesException class.</p>
 *
 * @author jjs
 * @version $Id: MultiplePoBoxesException.java,v 1.1 2012/03/04 12:31:16 jjs Exp $
 */
public class MultiplePoBoxesException extends AddressStandardizationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5823726569969878250L;

	/**
	 * <p>Constructor for MultiplePoBoxesException.</p>
	 *
	 * @param e a {@link java.lang.String} object.
	 */
	public MultiplePoBoxesException(final String e) {
		super(e);
	}

	/**
	 * <p>Constructor for MultiplePoBoxesException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 * @param e a {@link java.lang.String} object.
	 */
	public MultiplePoBoxesException(final Address address, final String e) {
		super(address, e);
	}

	/**
	 * <p>Constructor for MultiplePoBoxesException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public MultiplePoBoxesException(final Address address) {
		super(address);
	}

}
