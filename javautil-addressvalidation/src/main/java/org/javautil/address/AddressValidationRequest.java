package org.javautil.address;

import org.javautil.address.beans.AddressBean;
import org.javautil.address.beans.AuthoritativeAddressBean;
import org.javautil.address.beans.StandardAddressBean;
import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.interfaces.StandardizedAddress;

/**
 *
 * TODO make UtAddressValidate extend this class TODO document every field
 *
 * @author jjs
 * @version $Id: AddressValidationRequest.java,v 1.2 2012/03/04 12:31:14 jjs Exp $
 */
public class AddressValidationRequest {

	private AuthoritativeAddress authoritativeAddress = new AuthoritativeAddressBean();
	// private String name;
	private final String newline = System.getProperty("line.separator");

	/**
	 * The unprocessed input address.
	 */
	private Address rawAddress = new AddressBean();
	/**
	 * The rawAddress after standardization logic has been applied.
	 */
	private StandardizedAddress standardAddress = new StandardAddressBean();
	/**
	 * The authoritative address that has been standardized to make it pretty.
	 * 
	 * Usually that is the reverse of standardization names are expanded etc.
	 */
	private StandardizedAddress standardizedAuthoritativeAddress = new StandardAddressBean();

	/**
	 * <p>Getter for the field <code>authoritativeAddress</code>.</p>
	 *
	 * @return a {@link org.javautil.address.interfaces.AuthoritativeAddress} object.
	 */
	public AuthoritativeAddress getAuthoritativeAddress() {
		return authoritativeAddress;
	}

	/**
	 * <p>Setter for the field <code>authoritativeAddress</code>.</p>
	 *
	 * @param authoritativeAddress a {@link org.javautil.address.interfaces.AuthoritativeAddress} object.
	 */
	public void setAuthoritativeAddress(
			final AuthoritativeAddress authoritativeAddress) {
		this.authoritativeAddress = authoritativeAddress;
	}

	/**
	 * <p>Getter for the field <code>rawAddress</code>.</p>
	 *
	 * @return a {@link org.javautil.address.interfaces.Address} object.
	 */
	public Address getRawAddress() {
		return rawAddress;
	}

	/**
	 * <p>Setter for the field <code>rawAddress</code>.</p>
	 *
	 * @param rawAddress a {@link org.javautil.address.interfaces.Address} object.
	 */
	public void setRawAddress(final Address rawAddress) {
		this.rawAddress = rawAddress;
	}

	/**
	 * <p>Getter for the field <code>standardAddress</code>.</p>
	 *
	 * @return a {@link org.javautil.address.interfaces.StandardizedAddress} object.
	 */
	public StandardizedAddress getStandardAddress() {
		return standardAddress;
	}

	/**
	 * <p>setStandardizedAddress.</p>
	 *
	 * @param standardAddress a {@link org.javautil.address.interfaces.StandardizedAddress} object.
	 */
	public void setStandardizedAddress(final StandardizedAddress standardAddress) {
		this.standardAddress = standardAddress;
	}

	/**
	 * <p>Getter for the field <code>standardizedAuthoritativeAddress</code>.</p>
	 *
	 * @return a {@link org.javautil.address.interfaces.StandardizedAddress} object.
	 */
	public StandardizedAddress getStandardizedAuthoritativeAddress() {
		return standardizedAuthoritativeAddress;
	}

	/**
	 * <p>Setter for the field <code>standardizedAuthoritativeAddress</code>.</p>
	 *
	 * @param standardizedAuthoritativeAddress a {@link org.javautil.address.interfaces.StandardizedAddress} object.
	 */
	public void setStandardizedAuthoritativeAddress(
			final StandardizedAddress standardizedAuthoritativeAddress) {
		this.standardizedAuthoritativeAddress = standardizedAuthoritativeAddress;
	}

	/**
	 * <p>Getter for the field <code>newline</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getNewline() {
		return newline;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("raw:");
		b.append(newline);// TODO Auto-generated method stub
		b.append(rawAddress.getFormatted());
		b.append(newline);
		b.append("standardized:");
		b.append(newline);
		b.append(standardAddress.getFormatted());
		b.append(newline);
		b.append("validated:");
		b.append(newline);
		b.append(authoritativeAddress.getFormatted());
		b.append(newline);
		b.append("standardize authoritative addresss:");
		b.append(newline);
		b.append(standardizedAuthoritativeAddress.getFormatted());
		b.append(newline);
		final String retval = b.toString();
		return retval;
	}

	/**
	 * <p>setStandardizationErrorMessage.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public void setStandardizationErrorMessage(final String message) {

	}

}
