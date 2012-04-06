package org.javautil.address.interfaces;

import java.sql.Timestamp;

/**
 * <p>
 * StandardizedAddress interface.
 * </p>
 * 
 * @author jjs
 * @version $Id: StandardizedAddress.java,v 1.3 2012/03/05 19:54:02 jjs Exp $
 */
public interface StandardizedAddress extends Address {

	/**
	 * <p>
	 * getStandardizationErrorMessage.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public abstract String getStandardizationErrorMessage();

	/**
	 * The po box number without an "PO" prefix e.g.
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public abstract String getPoBox();

	/**
	 * <p>
	 * getStandardizationTime.
	 * </p>
	 * 
	 * @return a {@link java.sql.Timestamp} object.
	 */
	public abstract Timestamp getStandardizationTime();

	/**
	 * <p>
	 * getStreetName.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public abstract String getStreetName();

	/**
	 * <p>
	 * getStreetNumber.
	 * </p>
	 * 
	 * @return the streetNumber
	 */
	public abstract String getStreetNumber();

	/**
	 * <p>
	 * getStreetType.
	 * </p>
	 * 
	 * @return the streetType
	 */
	public abstract String getStreetType();

	/**
	 * <p>
	 * getSubunitCode.
	 * </p>
	 * 
	 * @return the subunitCode
	 */
	public abstract String getSubunitCode();

	/**
	 * <p>
	 * getSubunitType.
	 * </p>
	 * 
	 * @return the unitType
	 */
	public abstract String getSubunitType();

	/**
	 * <p>
	 * setPoBox.
	 * </p>
	 * 
	 * @param string
	 *            a {@link java.lang.String} object.
	 */
	public abstract void setPoBox(String string);

	/**
	 * <p>
	 * setStandardizationErrorMessage.
	 * </p>
	 * 
	 * @param string
	 *            a {@link java.lang.String} object.
	 */
	public abstract void setStandardizationErrorMessage(String string);

	/**
	 * <p>
	 * setStreetName.
	 * </p>
	 * 
	 * @param streetName
	 *            a {@link java.lang.String} object.
	 */
	public abstract void setStreetName(final String streetName);

	/**
	 * <p>
	 * setStreetNumber.
	 * </p>
	 * 
	 * @param streetNumber
	 *            the streetNumber to set
	 */
	public abstract void setStreetNumber(final String streetNumber);

	/**
	 * <p>
	 * setStreetType.
	 * </p>
	 * 
	 * @param streetType
	 *            the streetType to set
	 */
	public abstract void setStreetType(final String streetType);

	/**
	 * <p>
	 * setSubunitCode.
	 * </p>
	 * 
	 * @param subunitCode
	 *            the subunitCode to set
	 */
	public abstract void setSubunitCode(final String subunitCode);

	/**
	 * <p>
	 * setSubunitType.
	 * </p>
	 * 
	 * @param unitType
	 *            the unitType to set
	 */
	public abstract void setSubunitType(final String unitType);

	public abstract Double getLongitude();

	public abstract Double getLatitude();

}
