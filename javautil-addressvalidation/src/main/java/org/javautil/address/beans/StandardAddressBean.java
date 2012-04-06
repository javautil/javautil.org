package org.javautil.address.beans;

import java.sql.Timestamp;

import org.javautil.address.interfaces.GeoAddress;
import org.javautil.address.interfaces.StandardizedAddress;

/**
 * <p>
 * StandardAddressBean class.
 * </p>
 * 
 * @author jjs
 * @version $Id: StandardAddressBean.java,v 1.4 2012/03/05 19:54:02 jjs Exp $
 */
public class StandardAddressBean extends SimpleAddress implements
		StandardizedAddress, GeoAddress {

	private String poBox;
	private String streetName;
	private String streetNumber;
	private String streetType;
	private String subunitCode;
	private String subunitType;
	private Timestamp standardizationTime;
	private String standardizationErrorMessage;
	private Double longitude;
	private Double latitude;

	/**
	 * Copy Constructor
	 * 
	 * @param standardAddressBean
	 *            a <code>StandardAddressBean</code> object
	 */
	public StandardAddressBean(final StandardizedAddress standardAddressBean) {
		this.poBox = standardAddressBean.getPoBox();
		this.streetName = standardAddressBean.getStreetName();
		this.streetNumber = standardAddressBean.getStreetNumber();
		this.streetType = standardAddressBean.getStreetType();
		this.subunitCode = standardAddressBean.getSubunitCode();
		this.subunitType = standardAddressBean.getSubunitType();
		this.standardizationTime = standardAddressBean.getStandardizationTime();
		this.standardizationErrorMessage = standardAddressBean
				.getStandardizationErrorMessage();
		this.longitude = standardAddressBean.getLongitude();
		this.latitude = standardAddressBean.getLatitude();
	}

	/**
	 * <p>
	 * Constructor for StandardAddressBean.
	 * </p>
	 */
	public StandardAddressBean() {

	}

	/**
	 * <p>
	 * Constructor for StandardAddressBean.
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
	public StandardAddressBean(final String address1, final String address2,
			final String city, final String state, final String postalCode) {
		super(address1, address2, city, state, postalCode);
	}

	/**
	 * <p>
	 * Constructor for StandardAddressBean.
	 * </p>
	 * 
	 * @param addr1
	 *            a {@link java.lang.String} object.
	 * @param addr2
	 *            a {@link java.lang.String} object.
	 * @param city
	 *            a {@link java.lang.String} object.
	 * @param state
	 *            a {@link java.lang.String} object.
	 * @param postalCd
	 *            a {@link java.lang.String} object.
	 * @param streetNbr
	 *            a {@link java.lang.String} object.
	 * @param streetName
	 *            a {@link java.lang.String} object.
	 * @param streetType
	 *            a {@link java.lang.String} object.
	 * @param poBox
	 *            a {@link java.lang.String} object.
	 * @param subunitType
	 *            a {@link java.lang.String} object.
	 * @param subunitCode
	 *            a {@link java.lang.String} object.
	 */
	public StandardAddressBean(final String addr1, final String addr2,
			final String city, final String state, final String postalCd,
			final String streetNbr, final String streetName,
			final String streetType, final String poBox,
			final String subunitType, final String subunitCode) {
		super(addr1, addr2, city, state, postalCd);
		this.streetNumber = streetNbr;
		this.streetName = streetName;
		this.streetType = streetType;
		this.poBox = poBox;
		this.subunitType = subunitType;
		this.subunitCode = subunitCode;
	}

	/** {@inheritDoc} */
	@Override
	public String getPoBox() {
		return poBox;
	}

	/** {@inheritDoc} */
	@Override
	public void setPoBox(final String poBox) {
		this.poBox = poBox;
	}

	/** {@inheritDoc} */
	@Override
	public String getStreetName() {
		return streetName;
	}

	/** {@inheritDoc} */
	@Override
	public void setStreetName(final String streetName) {
		this.streetName = streetName;
	}

	/** {@inheritDoc} */
	@Override
	public String getStreetNumber() {
		return streetNumber;
	}

	/** {@inheritDoc} */
	@Override
	public void setStreetNumber(final String streetNumber) {
		this.streetNumber = streetNumber;
	}

	/** {@inheritDoc} */
	@Override
	public String getStreetType() {
		return streetType;
	}

	/** {@inheritDoc} */
	@Override
	public void setStreetType(final String streetType) {
		this.streetType = streetType;
	}

	/** {@inheritDoc} */
	@Override
	public String getSubunitCode() {
		return subunitCode;
	}

	/** {@inheritDoc} */
	@Override
	public void setSubunitCode(final String subunitCode) {
		this.subunitCode = subunitCode;
	}

	/** {@inheritDoc} */
	@Override
	public String getSubunitType() {
		return subunitType;
	}

	/** {@inheritDoc} */
	@Override
	public void setSubunitType(final String subunitType) {
		this.subunitType = subunitType;
	}

	/** {@inheritDoc} */
	@Override
	public Timestamp getStandardizationTime() {
		return standardizationTime;
	}

	/**
	 * <p>
	 * setStdSubunitCode.
	 * </p>
	 * 
	 * @param subunitCode
	 *            a {@link java.lang.String} object.
	 */
	public void setStdSubunitCode(final String subunitCode) {
		this.subunitCode = subunitCode;

	}

	/**
	 * <p>
	 * Setter for the field <code>standardizationTime</code>.
	 * </p>
	 * 
	 * @param timestamp
	 *            a {@link java.sql.Timestamp} object.
	 */
	public void setStandardizationTime(final Timestamp timestamp) {
		this.standardizationTime = timestamp;

	}

	/** {@inheritDoc} */
	@Override
	public String getStandardizationErrorMessage() {
		return standardizationErrorMessage;
	}

	/** {@inheritDoc} */
	@Override
	public void setStandardizationErrorMessage(final String string) {
		this.standardizationErrorMessage = string;

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
	public void setLatitude(final Double latitude) {
		this.latitude = latitude;

	}

	/** {@inheritDoc} */
	@Override
	public void setLongitude(final Double longitude) {
		this.longitude = longitude;

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
		result = prime * result + (latitude == null ? 0 : latitude.hashCode());
		result = prime * result
				+ (longitude == null ? 0 : longitude.hashCode());
		result = prime * result + (poBox == null ? 0 : poBox.hashCode());
		result = prime
				* result
				+ (standardizationErrorMessage == null ? 0
						: standardizationErrorMessage.hashCode());
		result = prime
				* result
				+ (standardizationTime == null ? 0 : standardizationTime
						.hashCode());
		result = prime * result
				+ (streetName == null ? 0 : streetName.hashCode());
		result = prime * result
				+ (streetNumber == null ? 0 : streetNumber.hashCode());
		result = prime * result
				+ (streetType == null ? 0 : streetType.hashCode());
		result = prime * result
				+ (subunitCode == null ? 0 : subunitCode.hashCode());
		result = prime * result
				+ (subunitType == null ? 0 : subunitType.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
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
		if (!(obj instanceof StandardAddressBean)) {
			return false;
		}
		final StandardAddressBean other = (StandardAddressBean) obj;

		if (!super.equals(obj)) {
			return false;
		}
		if (latitude == null) {
			if (other.latitude != null) {
				return false;
			}
		} else if (!latitude.equals(other.latitude)) {
			return false;
		}
		if (longitude == null) {
			if (other.longitude != null) {
				return false;
			}
		} else if (!longitude.equals(other.longitude)) {
			return false;
		}
		if (poBox == null) {
			if (other.poBox != null) {
				return false;
			}
		} else if (!poBox.equals(other.poBox)) {
			return false;
		}
		if (standardizationErrorMessage == null) {
			if (other.standardizationErrorMessage != null) {
				return false;
			}
		} else if (!standardizationErrorMessage
				.equals(other.standardizationErrorMessage)) {
			return false;
		}
		if (standardizationTime == null) {
			if (other.standardizationTime != null) {
				return false;
			}
		} else if (!standardizationTime.equals(other.standardizationTime)) {
			return false;
		}
		if (streetName == null) {
			if (other.streetName != null) {
				return false;
			}
		} else if (!streetName.equals(other.streetName)) {
			return false;
		}
		if (streetNumber == null) {
			if (other.streetNumber != null) {
				return false;
			}
		} else if (!streetNumber.equals(other.streetNumber)) {
			return false;
		}
		if (streetType == null) {
			if (other.streetType != null) {
				return false;
			}
		} else if (!streetType.equals(other.streetType)) {
			return false;
		}
		if (subunitCode == null) {
			if (other.subunitCode != null) {
				return false;
			}
		} else if (!subunitCode.equals(other.subunitCode)) {
			return false;
		}
		if (subunitType == null) {
			if (other.subunitType != null) {
				return false;
			}
		} else if (!subunitType.equals(other.subunitType)) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 */
	@Override
	public String toString() {
		final String TAB = System.getProperty("line.separator");

		String retValue = "";

		retValue = "StandardAddressBean ( " + super.toString() + TAB
				+ "poBox = " + this.poBox + TAB + "streetName = "
				+ this.streetName + TAB + "streetNumber = " + this.streetNumber
				+ TAB + "streetType = " + this.streetType + TAB
				+ "subunitCode = " + this.subunitCode + TAB + "subunitType = "
				+ this.subunitType + TAB + "standardizationTime = "
				+ this.standardizationTime + TAB
				+ "standardizationErrorMessage = "
				+ this.standardizationErrorMessage + TAB + "longitude = "
				+ this.longitude + TAB + "latitude = " + this.latitude + TAB
				+ " )";

		return retValue;
	}

}
