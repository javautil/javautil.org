package org.javautil.address.interfaces;

import java.util.Collection;

import org.javautil.address.exception.AddressStandardizationException;

/**
 * <p>AddressStandardizer interface.</p>
 *
 * @author jjs
 * @version $Id: AddressStandardizer.java,v 1.2 2012/03/04 12:31:12 jjs Exp $
 */
public interface AddressStandardizer {
	// public Address getRawAddress();
	// public StandardizedAddress getStandardAddress();
	/**
	 * <p>getExceptions.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<AddressStandardizationException> getExceptions();

	/**
	 * <p>standardize.</p>
	 *
	 * @param raw a {@link org.javautil.address.interfaces.Address} object.
	 * @param std a {@link org.javautil.address.interfaces.StandardizedAddress} object.
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	public void standardize(final Address raw, final StandardizedAddress std)
			throws AddressStandardizationException;
}
