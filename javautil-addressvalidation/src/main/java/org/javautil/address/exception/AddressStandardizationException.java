package org.javautil.address.exception;

import org.javautil.address.interfaces.Address;

// todo wtf?
/**
 * <p>AddressStandardizationException class.</p>
 *
 * @author jjs
 * @version $Id: AddressStandardizationException.java,v 1.1 2012/03/04 12:31:16 jjs Exp $
 */
public class AddressStandardizationException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 2846976005288800447L;

	/**
	 * <p>Constructor for AddressStandardizationException.</p>
	 *
	 * @param error
	 * @param e a {@link java.lang.String} object.
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	protected AddressStandardizationException(final Address address,
			final String e) {
		super(e);
	}

	/**
	 * <p>Constructor for AddressStandardizationException.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	protected AddressStandardizationException(final Address address) {
	}

	/**
	 * <p>Constructor for AddressStandardizationException.</p>
	 *
	 * @param e a {@link java.lang.String} object.
	 */
	public AddressStandardizationException(final String e) {
		super(e);
	}

	// TODO delete
	// public AddressStandardizationException(final Exception e) {
	// super(e);
	// }
	//
	// public AddressStandardizationException() {
	//
	// }

	// @Override
	// public String getMessage() {
	// String message = null;
	// if (error == null) {
	// message = super.getMessage();
	// } else {
	// message = error.toString()
	// + (address != null ? address.getFormatted() : "");
	// }
	// return message;
	// }

	// public boolean isValidMessageNumber(final int nbr) {
	// boolean retval = false;
	// switch (nbr) {
	// case NO_STREET_NUMBER_OR_RURAL_ROUTE_FOUND:
	// retval = true;
	// break;
	// default:
	// retval = false;
	// }
	// return retval;
	// }
}
