package org.javautil.address.beans;

import org.apache.log4j.Logger;

/**
 * <p>SimpleAddress class.</p>
 *
 * @author jjs
 * @version $Id: SimpleAddress.java,v 1.3 2012/03/04 12:31:19 jjs Exp $
 */
public class SimpleAddress {

	/** Constant <code>PO_BOX_FOUND_ON_STREET_LINE=1</code> */
	public static final int PO_BOX_FOUND_ON_STREET_LINE = 1;
	private static final int ADDR_LENGTH = 40;
	private Long id;
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String postalCode;
	private String countryCode;

	private int standardizationFlags;
	private static final String newline = System.getProperty("line.separator");

	/**
	 * <p>Constructor for SimpleAddress.</p>
	 */
	public SimpleAddress() {

	}

	/**
	 * <p>Constructor for SimpleAddress.</p>
	 *
	 * @param address1 a {@link java.lang.String} object.
	 * @param address2 a {@link java.lang.String} object.
	 * @param city a {@link java.lang.String} object.
	 * @param state a {@link java.lang.String} object.
	 * @param postalCode a {@link java.lang.String} object.
	 */
	public SimpleAddress(final String address1, final String address2,
			final String city, final String state, final String postalCode) {
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
	}

	/**
	 * <p>Getter for the field <code>standardizationFlags</code>.</p>
	 *
	 * @return a int.
	 */
	public int getStandardizationFlags() {
		return standardizationFlags;
	}

	/**
	 * <p>Setter for the field <code>standardizationFlags</code>.</p>
	 *
	 * @param standardizationFlags a int.
	 */
	public void setStandardizationFlags(final int standardizationFlags) {
		this.standardizationFlags = standardizationFlags;
	}

	/**
	 * <p>getFormatted.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getFormatted() {
		String retval = "";

		if (address1 != null || address2 != null || city != null
				|| state != null || postalCode != null) {
			final StringBuilder sb = new StringBuilder();
			sb.append(address1 == null ? "" : address1);
			sb.append(newline);
			sb.append(address2 == null ? "" : address2);
			sb.append(newline);
			sb.append(city + ", " + state + "   " + postalCode);
			retval = sb.toString();
		}
		return retval;
	}

	/**
	 * <p>Getter for the field <code>address1</code>.</p>
	 *
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * <p>Getter for the field <code>address2</code>.</p>
	 *
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append(address1 + "\n");
		b.append(address2 + "\n");
		b.append(city + ", " + state + "  " + postalCode);
		return b.toString();
	}

	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return a {@link java.lang.Long} object.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * <p>Getter for the field <code>city</code>.</p>
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * <p>Setter for the field <code>city</code>.</p>
	 *
	 * @param city
	 *            the city to set
	 */
	public void setCity(final String city) {
		this.city = city;
	}

	/**
	 * <p>Getter for the field <code>state</code>.</p>
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * <p>Setter for the field <code>state</code>.</p>
	 *
	 * @param state
	 *            the state to set
	 */
	public void setState(final String state) {
		this.state = state;
	}

	/**
	 * <p>Getter for the field <code>postalCode</code>.</p>
	 *
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * <p>Setter for the field <code>postalCode</code>.</p>
	 *
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * <p>setLocatorSuccess.</p>
	 *
	 * @param locatorSuccess
	 *            the locatorSuccess to set
	 */
	public void setLocatorSuccess(final boolean locatorSuccess) {
	}

	/**
	 * <p>getADDR_LENGTH.</p>
	 *
	 * @return the aDDR_LENGTH
	 */
	public static int getADDR_LENGTH() {
		return ADDR_LENGTH;
	}

	/**
	 * <p>Getter for the field <code>logger</code>.</p>
	 *
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * <p>Getter for the field <code>newline</code>.</p>
	 *
	 * @return the newline
	 */
	public static String getNewline() {
		return newline;
	}

	/**
	 * <p>Setter for the field <code>id</code>.</p>
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * <p>Setter for the field <code>address1</code>.</p>
	 *
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(final String address1) {
		this.address1 = address1;
	}

	/**
	 * <p>Setter for the field <code>address2</code>.</p>
	 *
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(final String address2) {
		this.address2 = address2;
	}

	/**
	 * <p>Getter for the field <code>countryCode</code>.</p>
	 *
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * <p>Setter for the field <code>countryCode</code>.</p>
	 *
	 * @param countryCode
	 *            the countryCode to set
	 */
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * <p>getAddressHash.</p>
	 *
	 * @return a int.
	 */
	public int getAddressHash() {

		return getFormatted().hashCode();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (address1 == null ? 0 : address1.hashCode());
		result = prime * result + (address2 == null ? 0 : address2.hashCode());
		result = prime * result + (city == null ? 0 : city.hashCode());
		result = prime * result
				+ (countryCode == null ? 0 : countryCode.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result
				+ (postalCode == null ? 0 : postalCode.hashCode());
		result = prime * result + standardizationFlags;
		result = prime * result + (state == null ? 0 : state.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * tb this is a really nasty hack so I can call this from StandardAddress
	 */

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SimpleAddress)) {
			return false;
		}
		final SimpleAddress other = (SimpleAddress) obj;
		if (address1 == null) {
			if (other.address1 != null) {
				return false;
			}
		} else if (!address1.equals(other.address1)) {
			return false;
		}
		if (address2 == null) {
			if (other.address2 != null) {
				return false;
			}
		} else if (!address2.equals(other.address2)) {
			return false;
		}
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		// if (countryCode == null) {
		// if (other.countryCode != null)
		// return false;
		// } else if (!countryCode.equals(other.countryCode))
		// return false;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (postalCode == null) {
			if (other.postalCode != null) {
				return false;
			}
		} else if (!postalCode.equals(other.postalCode)) {
			return false;
		}
		if (standardizationFlags != other.standardizationFlags) {
			return false;
		}
		if (state == null) {
			if (other.state != null) {
				return false;
			}
		} else if (!state.equals(other.state)) {
			return false;
		}
		return true;
	}
}
