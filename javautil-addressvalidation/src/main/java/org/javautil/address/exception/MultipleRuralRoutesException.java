/**
 * 
 */
package org.javautil.address.exception;

import org.javautil.address.interfaces.Address;

/**
 * <p>MultipleRuralRoutesException class.</p>
 *
 * @author jjs
 * @version $Id: MultipleRuralRoutesException.java,v 1.1 2012/03/04 12:31:17 jjs Exp $
 */
public class MultipleRuralRoutesException extends
		AddressStandardizationException {
	/**
* 
*/
	private static final long serialVersionUID = 2416664254389941059L;

	/**
	 * Multiple subunits were found in the address.
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public MultipleRuralRoutesException(final Address address) {
		super(address);

	}
}
