package org.javautil.address.geo;

/**
 * <p>GeoProcessingException class.</p>
 *
 * @author jjs
 * @version $Id: GeoProcessingException.java,v 1.2 2012/03/04 12:31:17 jjs Exp $
 */
public class GeoProcessingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1911504522659643200L;

	/**
	 * <p>Constructor for GeoProcessingException.</p>
	 *
	 * @param string a {@link java.lang.String} object.
	 */
	public GeoProcessingException(final String string) {
		super(string);
	}

}
