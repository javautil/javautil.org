package org.javautil.address.beans;

import org.javautil.address.interfaces.AuthoritativeAddress;

/**
 * <p>
 * AuthoritativeAddressBean class.
 * </p>
 * 
 * @author jjs
 * @version $Id: AuthoritativeAddressBean.java,v 1.3 2012/03/04 12:31:19 jjs Exp
 *          $
 */
public class AuthoritativeAddressBean extends SimpleAddress implements
		AuthoritativeAddress {

	private String authoritativeErrorMessage;

	private Double longitude;

	private Double latitude;

	/** {@inheritDoc} */
	@Override
	public String getAuthoritativeErrorMessage() {
		return authoritativeErrorMessage;
	}

	/** {@inheritDoc} */
	@Override
	public String getFormatted() {
		String retval = null;
		if (getAuthoritativeErrorMessage() != null) {
			final StringBuilder b = new StringBuilder();
			b.append(super.getFormatted());
			b.append("error: " + getAuthoritativeErrorMessage());
			retval = b.toString();
		} else {
			final StringBuilder b = new StringBuilder();
			b.append(super.getFormatted());
			b.append("longitude: " + longitude + " latitude " + latitude);
			retval = b.toString();
		}
		return retval;
	}

	/** {@inheritDoc} */
	@Override
	public void setAuthoritativeErrorMessage(final String string) {
		this.authoritativeErrorMessage = string;

	}

	/** {@inheritDoc} */
	@Override
	public Double getLatitude() {

		return latitude;
	}

	/** {@inheritDoc} */
	@Override
	public Double getLongitude() {

		return longitude;
	}

	/** {@inheritDoc} */
	@Override
	public void setLongitude(final Double d) {
		this.longitude = d;

	}

	/** {@inheritDoc} */
	@Override
	public void setLatitude(final Double d) {
		this.latitude = d;
	}

}
