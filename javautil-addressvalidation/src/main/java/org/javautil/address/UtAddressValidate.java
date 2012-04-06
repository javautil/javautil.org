package org.javautil.address;

//import java.sql.SQLException;
import java.sql.Timestamp;

import org.javautil.address.beans.AddressBean;
import org.javautil.address.beans.AuthoritativeAddressBean;
import org.javautil.address.beans.StandardAddressBean;
import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.interfaces.StandardizedAddress;
import org.javautil.persistence.PersistenceAction;

/**
 * Contains a transient representation of the data persisted in a tuple of
 * UT_ADDR_VALIDATE .
 * 
 * @author jjs
 * @version $Id: UtAddressValidate.java,v 1.4 2012/03/06 14:17:11 jjs Exp $
 */
public class UtAddressValidate {

	private Long utAddrValidateNbr = null;

	private Integer dataSrcNbr = null;
	private Long dataSrcPk = null;

	private Integer runNbr;

	private Address rawAddress = new AddressBean();

	private StandardizedAddress standardAddress = new StandardAddressBean();

	private AuthoritativeAddress authoritativeAddress = new AuthoritativeAddressBean();

	private StandardizedAddress standardizedAuthoritativeAddress = new StandardAddressBean();

	private Timestamp stdTime;

	private Timestamp stdTs = null;

	private String authRqstCd;
	private String name;
	private final String newline = System.getProperty("line.separator");
	// private SQLException persistenceException;
	private PersistenceAction persistenceAction;
	private boolean getAuthoritative;

	/**
	 * <p>
	 * getAuthAddress.
	 * </p>
	 * 
	 * @return the authoritativeAddress
	 */
	public AuthoritativeAddress getAuthAddress() {
		return authoritativeAddress;
	}

	/**
	 * <p>
	 * setAuthAddress.
	 * </p>
	 * 
	 * @param authoritativeAddress
	 *            the authoritativeAddress to set
	 */
	public void setAuthAddress(final AuthoritativeAddress authoritativeAddress) {
		this.authoritativeAddress = authoritativeAddress;
	}

	/**
	 * <p>
	 * Getter for the field <code>dataSrcNbr</code>.
	 * </p>
	 * 
	 * @return the dataSrcNbr
	 */
	public Integer getDataSrcNbr() {
		return dataSrcNbr;
	}

	/**
	 * <p>
	 * Setter for the field <code>dataSrcNbr</code>.
	 * </p>
	 * 
	 * @param dataSrcNbr
	 *            the dataSrcNbr to set
	 */
	public void setDataSrcNbr(final Integer dataSrcNbr) {
		this.dataSrcNbr = dataSrcNbr;
	}

	/**
	 * <p>
	 * Getter for the field <code>dataSrcPk</code>.
	 * </p>
	 * 
	 * @return the dataSrcPk
	 */
	public Long getDataSrcPk() {
		return dataSrcPk;
	}

	/**
	 * <p>
	 * Setter for the field <code>dataSrcPk</code>.
	 * </p>
	 * 
	 * @param dataSrcPk
	 *            the dataSrcPk to set
	 */
	public void setDataSrcPk(final Long dataSrcPk) {
		this.dataSrcPk = dataSrcPk;
	}

	/**
	 * <p>
	 * Getter for the field <code>rawAddress</code>.
	 * </p>
	 * 
	 * @return the rawAddress
	 */
	public Address getRawAddress() {
		return rawAddress;
	}

	/**
	 * <p>
	 * Setter for the field <code>rawAddress</code>.
	 * </p>
	 * 
	 * @param rawAddress
	 *            the rawAddress to set
	 */
	public void setRawAddress(final Address rawAddress) {
		this.rawAddress = rawAddress;
	}

	/**
	 * <p>
	 * Getter for the field <code>runNbr</code>.
	 * </p>
	 * 
	 * @return the runNbr
	 */
	public Integer getRunNbr() {
		return runNbr;
	}

	/**
	 * <p>
	 * Setter for the field <code>runNbr</code>.
	 * </p>
	 * 
	 * @param runNbr
	 *            the runNbr to set
	 */
	public void setRunNbr(final Integer runNbr) {
		this.runNbr = runNbr;
	}

	/**
	 * <p>
	 * getStdAddress.
	 * </p>
	 * 
	 * @return the standardAddress
	 */
	public StandardizedAddress getStdAddress() {
		return standardAddress;
	}

	/**
	 * <p>
	 * setStdAddress.
	 * </p>
	 * 
	 * @param standard
	 *            the standardAddress to set
	 */
	public void setStdAddress(final StandardizedAddress standard) {
		this.standardAddress = standard;
	}

	/**
	 * <p>
	 * Getter for the field <code>stdTime</code>.
	 * </p>
	 * 
	 * @return the stdTime
	 */
	public Timestamp getStdTime() {
		return stdTime;
	}

	/**
	 * <p>
	 * Setter for the field <code>stdTime</code>.
	 * </p>
	 * 
	 * @param stdTime
	 *            the stdTime to set
	 */
	public void setStdTime(final Timestamp stdTime) {
		this.stdTime = stdTime;
	}

	/**
	 * <p>
	 * Getter for the field <code>stdTs</code>.
	 * </p>
	 * 
	 * @return the stdTs
	 */
	public Timestamp getStdTs() {
		return stdTs;
	}

	/**
	 * <p>
	 * Setter for the field <code>stdTs</code>.
	 * </p>
	 * 
	 * @param stdTs
	 *            the stdTs to set
	 */
	public void setStdTs(final Timestamp stdTs) {
		this.stdTs = stdTs;
	}

	/**
	 * <p>
	 * Getter for the field <code>utAddrValidateNbr</code>.
	 * </p>
	 * 
	 * @return the utAddrValidateNbr
	 */
	public Long getUtAddrValidateNbr() {
		return utAddrValidateNbr;
	}

	/**
	 * <p>
	 * Setter for the field <code>utAddrValidateNbr</code>.
	 * </p>
	 * 
	 * @param utAddrValidateNbr
	 *            the utAddrValidateNbr to set
	 */
	public void setUtAddrValidateNbr(final Long utAddrValidateNbr) {
		this.utAddrValidateNbr = utAddrValidateNbr;
	}

	/**
	 * <p>
	 * getStdAuthAddress.
	 * </p>
	 * 
	 * @return the standardizedAuthoritativeAddress
	 */
	public StandardizedAddress getStdAuthAddress() {
		return standardizedAuthoritativeAddress;
	}

	/**
	 * <p>
	 * setStdAuthAddress.
	 * </p>
	 * 
	 * @param standardizedAuthoritativeAddress
	 *            the standardizedAuthoritativeAddress to set
	 */
	public void setStdAuthAddress(
			final StandardizedAddress standardizedAuthoritativeAddress) {
		this.standardizedAuthoritativeAddress = standardizedAuthoritativeAddress;
	}

	/**
	 * <p>
	 * Getter for the field <code>authRqstCd</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getAuthRqstCd() {
		return authRqstCd;
	}

	/**
	 * <p>
	 * Setter for the field <code>name</code>.
	 * </p>
	 * 
	 * @param name
	 *            a {@link java.lang.String} object.
	 */
	public void setName(final String name) {
		this.name = name;

	}

	/**
	 * <p>
	 * Setter for the field <code>authRqstCd</code>.
	 * </p>
	 * 
	 * @param authRqstCd
	 *            a {@link java.lang.String} object.
	 */
	public void setAuthRqstCd(final String authRqstCd) {
		this.authRqstCd = authRqstCd;

	}

	/**
	 * <p>
	 * Getter for the field <code>name</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("ut_addr_validate_nbr: ");
		b.append(getUtAddrValidateNbr());
		b.append(newline);
		b.append("raw:");
		b.append(newline);// TODO Auto-generated method stub
		b.append(rawAddress.getFormatted());
		b.append(newline);
		b.append("standardized:");
		b.append(newline);
		b.append(standardAddress.getFormatted());
		b.append(newline);
		b.append(newline);
		b.append("validated:");
		b.append(newline);
		b.append(authoritativeAddress.getFormatted());
		b.append(newline);
		b.append(newline);
		b.append("standardized authoritative addresss:");
		b.append(newline);
		b.append(standardizedAuthoritativeAddress.getFormatted());
		b.append(newline);
		final String retval = b.toString();
		return retval;
	}

	// /**
	// * <p>
	// * Setter for the field <code>persistenceException</code>.
	// * </p>
	// *
	// * @param e1
	// * a {@link java.sql.SQLException} object.
	// */
	// public void setPersistenceException(final SQLException e1) {
	// persistenceException = e1;
	//
	// }
	//
	// /**
	// * <p>
	// * Getter for the field <code>persistenceException</code>.
	// * </p>
	// *
	// * @return a {@link java.lang.Throwable} object.
	// */
	// public Throwable getPersistenceException() {
	// // TODO Auto-generated method stub
	// return persistenceException;
	// }

	/**
	 * <p>
	 * Getter for the field <code>persistenceAction</code>.
	 * </p>
	 * 
	 * @return the persistenceAction
	 */
	public PersistenceAction getPersistenceAction() {
		return persistenceAction;
	}

	/**
	 * <p>
	 * Setter for the field <code>persistenceAction</code>.
	 * </p>
	 * 
	 * @param persistenceAction
	 *            the persistenceAction to set
	 */
	public void setPersistenceAction(final PersistenceAction persistenceAction) {
		this.persistenceAction = persistenceAction;
	}

	/**
	 * Need to make a call to the Address Authority for this address if true;
	 * 
	 * @param b
	 *            a boolean.
	 */
	public void setGetAuthoritive(final boolean b) {
		getAuthoritative = b;

	}

	/**
	 * <p>
	 * getGetAuthoritive.
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public boolean getGetAuthoritive() {
		return getAuthoritative;
	}
}
