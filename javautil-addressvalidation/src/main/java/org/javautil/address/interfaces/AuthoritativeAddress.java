package org.javautil.address.interfaces;

/**
 * <p>AuthoritativeAddress interface.</p>
 *
 * @author jjs
 * @version $Id: AuthoritativeAddress.java,v 1.2 2012/03/04 12:31:12 jjs Exp $
 */
public interface AuthoritativeAddress extends Address, GeoAddress {

	/**
	 * <p>setAuthoritativeErrorMessage.</p>
	 *
	 * @param string a {@link java.lang.String} object.
	 */
	public abstract void setAuthoritativeErrorMessage(String string);

	/**
	 * <p>getAuthoritativeErrorMessage.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public abstract String getAuthoritativeErrorMessage();

	/** {@inheritDoc} */
	@Override
	public Double getLatitude();

	/** {@inheritDoc} */
	@Override
	public Double getLongitude();

	/** {@inheritDoc} */
	@Override
	public void setLatitude(Double d);

	/** {@inheritDoc} */
	@Override
	public void setLongitude(Double d);

}
