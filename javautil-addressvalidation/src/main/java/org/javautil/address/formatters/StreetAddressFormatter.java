package org.javautil.address.formatters;

import org.javautil.address.parser.StreetComponents;

/**
 * <p>StreetAddressFormatter class.</p>
 *
 * @author jjs
 * @version $Id: StreetAddressFormatter.java,v 1.3 2012/03/04 12:31:18 jjs Exp $
 */
public class StreetAddressFormatter {

	/**
	 * Constructs a standard format street name from the components.
	 *
	 * @param components a {@link org.javautil.address.parser.StreetComponents} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String format(final StreetComponents components) {
		final StringBuilder b = new StringBuilder();
		b.append(components.getStreetNumber());
		b.append(" ");
		b.append(components.getStreetName());
		b.append(components.getStreetType() == null ? "" : " "
				+ components.getStreetType());
		final String retval = b.toString();
		return retval;
	}
}
