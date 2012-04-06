package org.javautil.address.formatters;

import org.javautil.address.parser.PoBoxComponent;

/**
 * <p>PoBoxFormatter class.</p>
 *
 * @author jjs
 * @version $Id: PoBoxFormatter.java,v 1.2 2012/03/04 12:31:18 jjs Exp $
 */
public class PoBoxFormatter {

	/**
	 * <p>format.</p>
	 *
	 * @param poBox a {@link org.javautil.address.parser.PoBoxComponent} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String format(final PoBoxComponent poBox) {
		return "PO Box " + poBox.getPoBox();
	}
}
