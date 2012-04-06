package org.javautil.address.usps;

import org.javautil.address.exception.AddressStandardizationException;

/**
 * <p>ZipCodeFormatException class.</p>
 *
 * @author jjs
 * @version $Id: ZipCodeFormatException.java,v 1.4 2012/03/04 12:31:15 jjs Exp $
 */
public class ZipCodeFormatException extends AddressStandardizationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7060049932536601899L;

	/**
	 * <p>Constructor for ZipCodeFormatException.</p>
	 *
	 * @param zip a {@link java.lang.String} object.
	 */
	public ZipCodeFormatException(final String zip) {
		super(zip);
	}

}
