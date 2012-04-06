package org.javautil.address.beans;

import org.javautil.address.interfaces.Address;

// todo use AddressBean or SimpleAddress
/**
 * <p>AddressBean class.</p>
 *
 * @author jjs
 * @version $Id: AddressBean.java,v 1.3 2012/03/04 12:31:19 jjs Exp $
 */
/**
 * @author jjs
 * 
 */
public class AddressBean implements Address {
	private static final String newline = System.getProperty("line.separator");
	private Long id;
	private final int maxPoCodeLength = 10;
	private String address1;
	private String address2;
	private String streetName;
	private String city;
	private String state;
	private String postalCode;
	private String countryCode;
	private Double latitude;
	private Double longitude;
	private int addressHash = 0;

	/**
	 * <p>
	 * Constructor for AddressBean.
	 * </p>
	 */
	public AddressBean() {

	}

	/**
	 * <p>
	 * Constructor for AddressBean.
	 * </p>
	 * 
	 * @param address1
	 *            a {@link java.lang.String} object.
	 * @param address2
	 *            a {@link java.lang.String} object.
	 * @param city
	 *            a {@link java.lang.String} object.
	 * @param state
	 *            a {@link java.lang.String} object.
	 * @param postalCode
	 *            a {@link java.lang.String} object.
	 */
	public AddressBean(final String address1, final String address2,
			final String city, final String state, final String postalCode) {
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
	}

	@Override
	/** {@inheritDoc} */
	public int getAddressHash() {
		if (addressHash == 0) {
			addressHash = getFormatted().hashCode();
		}
		return addressHash;
	}

	@Override
	/** {@inheritDoc} */
	public Long getId() {
		return id;
	}

	@Override
	/** {@inheritDoc} */public void setId(final Long id) {
		this.id = id;
	}

	/** {@inheritDoc} */
	@Override
	public String getAddress1() {
		return address1;
	}

	/** {@inheritDoc} */
	@Override
	public void setAddress1(final String address1) {
		addressHash = 0;
		this.address1 = address1;
	}

	/** {@inheritDoc} */
	@Override
	public String getAddress2() {
		addressHash = 0;
		return address2;
	}

	/** {@inheritDoc} */
	@Override
	public void setAddress2(final String address2) {
		addressHash = 0;
		this.address2 = address2;
	}

	/** {@inheritDoc} */
	@Override
	public String getCity() {
		addressHash = 0;
		return city;
	}

	/** {@inheritDoc} */
	@Override
	public void setCity(final String city) {
		addressHash = 0;
		this.city = city;
	}

	/** {@inheritDoc} */
	@Override
	public String getState() {
		addressHash = 0;
		return state;
	}

	/** {@inheritDoc} */
	@Override
	public void setState(final String state) {
		addressHash = 0;
		this.state = state;
	}

	/** {@inheritDoc} */
	@Override
	public String getPostalCode() {
		addressHash = 0;
		return postalCode;
	}

	/** {@inheritDoc} */
	@Override
	public void setPostalCode(final String postalCode) {
		if (postalCode != null && postalCode.length() > maxPoCodeLength) {
			throw new IllegalArgumentException(
					"postal code exceeds max length " + maxPoCodeLength);
		}
		this.postalCode = postalCode;
	}

	/**
	 * <p>
	 * Getter for the field <code>latitude</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.Double} object.
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * <p>
	 * Setter for the field <code>latitude</code>.
	 * </p>
	 * 
	 * @param latitude
	 *            a {@link java.lang.Double} object.
	 */
	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * <p>
	 * Getter for the field <code>longitude</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.Double} object.
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * <p>
	 * Setter for the field <code>longitude</code>.
	 * </p>
	 * 
	 * @param longitude
	 *            a {@link java.lang.Double} object.
	 */
	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * <p>
	 * Getter for the field <code>streetName</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * <p>
	 * Setter for the field <code>streetName</code>.
	 * </p>
	 * 
	 * @param streetName
	 *            a {@link java.lang.String} object.
	 */
	public void setStreetName(final String streetName) {
		this.streetName = streetName;
	}

	/** {@inheritDoc} */
	@Override
	public String getCountryCode() {
		return countryCode;
	}

	/** {@inheritDoc} */
	@Override
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	/** {@inheritDoc} */
	@Override
	public String getFormatted() {
		final StringBuilder b = new StringBuilder();

		b.append("Address1:    "
				+ (getAddress1() == null ? "" : "'" + getAddress1() + "'")
				+ newline);
		b.append("Address2:    "
				+ (getAddress2() == null ? "" : "'" + getAddress2() + "'")
				+ newline);
		b.append("City:        "
				+ (getCity() == null ? "" : "'" + getCity() + "'") + newline);
		b.append("State:       "
				+ (getState() == null ? "" : "'" + getState() + "'") + newline);
		b.append("Postal Code: "
				+ (getPostalCode() == null ? "" : "'" + getPostalCode() + "'")
				+ newline);

		return b.toString();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getFormatted();
	}
}
