package org.javautil.address.service;

import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.StandardizedAddress;

/**
 * <p>AddressProcessor interface.</p>
 *
 * @author jjs
 * @version $Id: AddressProcessor.java,v 1.2 2012/03/04 12:31:17 jjs Exp $
 */
public interface AddressProcessor {

	/**
	 * <p>getStandardizedAddress.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 * @return a {@link org.javautil.address.interfaces.StandardizedAddress} object.
	 */
	public StandardizedAddress getStandardizedAddress(Address address);
}
