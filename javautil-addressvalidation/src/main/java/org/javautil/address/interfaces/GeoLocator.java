package org.javautil.address.interfaces;

/**
 * <p>GeoLocator interface.</p>
 *
 * @author jjs
 * @version $Id: GeoLocator.java,v 1.2 2012/03/04 12:31:12 jjs Exp $
 */
public interface GeoLocator {
	/**
	 * <p>getLongitude.</p>
	 *
	 * @return a {@link java.lang.Double} object.
	 */
	public Double getLongitude();

	/**
	 * <p>getLatitude.</p>
	 *
	 * @return a {@link java.lang.Double} object.
	 */
	public Double getLatitude();

	/**
	 * <p>processGeoRequest.</p>
	 *
	 * @return a boolean.
	 */
	public boolean processGeoRequest();

}
