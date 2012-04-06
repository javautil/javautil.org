package org.javautil.address.interfaces;

/**
 * <p>GeoAddress interface.</p>
 *
 * @author jjs
 * @version $Id: GeoAddress.java,v 1.2 2012/03/04 12:31:12 jjs Exp $
 */
public interface GeoAddress extends Address {

	/**
	 * <p>getLatitude.</p>
	 *
	 * @return the latitude
	 */
	public abstract Double getLatitude();

	/**
	 * <p>getLongitude.</p>
	 *
	 * @return the longitude
	 */
	public abstract Double getLongitude();

	/**
	 * <p>setLongitude.</p>
	 *
	 * @param longitude a {@link java.lang.Double} object.
	 */
	public abstract void setLongitude(Double longitude);

	/**
	 * <p>setLatitude.</p>
	 *
	 * @param latitude a {@link java.lang.Double} object.
	 */
	public abstract void setLatitude(Double latitude);
}
