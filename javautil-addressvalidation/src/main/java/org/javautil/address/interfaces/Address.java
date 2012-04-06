package org.javautil.address.interfaces;

/**
 * <p>Address interface.</p>
 *
 * @author jjs
 * @version $Id: Address.java,v 1.2 2012/03/04 12:31:12 jjs Exp $
 */
public interface Address {

	/**
	 * <p>getAddress1.</p>
	 *
	 * @return the address1
	 */
	public abstract String getAddress1();

	/**
	 * <p>getAddress2.</p>
	 *
	 * @return the address2
	 */
	public abstract String getAddress2();

	/**
	 * <p>getAddressHash.</p>
	 *
	 * @return the addressHash based on the Formatted address.
	 */
	public abstract int getAddressHash();

	/**
	 * <p>getCity.</p>
	 *
	 * @return the city
	 */
	public abstract String getCity();

	/**
	 * <p>getCountryCode.</p>
	 *
	 * @return the countryCode
	 */
	public abstract String getCountryCode();

	/**
	 * <p>getFormatted.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public abstract String getFormatted();

	/**
	 * <p>getId.</p>
	 *
	 * @return the id
	 */
	public abstract Long getId();

	/**
	 * <p>getPostalCode.</p>
	 *
	 * @return the postalCode
	 */
	public abstract String getPostalCode();

	/**
	 * <p>getState.</p>
	 *
	 * @return the state
	 */
	public abstract String getState();

	/**
	 * <p>setAddress1.</p>
	 *
	 * @param address1
	 *            the address1 to set
	 */
	public abstract void setAddress1(final String address1);

	/**
	 * <p>setAddress2.</p>
	 *
	 * @param address2
	 *            the address2 to set
	 */
	public abstract void setAddress2(final String address2);

	/**
	 * <p>setCity.</p>
	 *
	 * @param city
	 *            the city to set
	 */
	public abstract void setCity(final String city);

	/**
	 * <p>setCountryCode.</p>
	 *
	 * @param countryCode
	 *            the countryCode to set
	 */
	public abstract void setCountryCode(final String countryCode);

	/**
	 * <p>setId.</p>
	 *
	 * @param id
	 *            the id to set
	 */
	public abstract void setId(final Long id);

	/**
	 * <p>setPostalCode.</p>
	 *
	 * @param postalCode
	 *            the postalCode to set
	 */
	public abstract void setPostalCode(final String postalCode);

	/**
	 * <p>setState.</p>
	 *
	 * @param state
	 *            the state to set
	 */
	public abstract void setState(final String state);

}
