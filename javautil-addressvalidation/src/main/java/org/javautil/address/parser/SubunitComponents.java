package org.javautil.address.parser;

/**
 * <p>SubunitComponents interface.</p>
 *
 * @author jjs
 * @version $Id: SubunitComponents.java,v 1.3 2012/03/04 12:31:12 jjs Exp $
 */
public interface SubunitComponents {

	/**
	 * Subunits are such things as "FLOOR" "APT"
	 *
	 * @return The type of subunit if one is found.
	 */
	public String getSubunitType();

	/**
	 * <p>getSubunitCode.</p>
	 *
	 * @return The subunit code (apt #, floor etc.) if found else null.
	 */
	public String getSubunitCode();
}
