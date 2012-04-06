package org.javautil.address.usps;

/**
 * <p>AddressValidationException class.</p>
 *
 * @author jjs
 * @version $Id: AddressValidationException.java,v 1.2 2012/03/04 12:31:15 jjs Exp $
 */
public class AddressValidationException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 2947159599571447279L;

	/**
	 * <p>Constructor for AddressValidationException.</p>
	 *
	 * @param e a {@link java.lang.Exception} object.
	 */
	public AddressValidationException(final Exception e) {
		super(e);
	}

	/**
	 * <p>Constructor for AddressValidationException.</p>
	 *
	 * @param s a {@link java.lang.String} object.
	 */
	public AddressValidationException(final String s) {
		super(s);
	}
}
