package org.javautil.address.service;

import org.apache.log4j.Logger;
import org.javautil.address.AddressValidationRequest;
import org.javautil.address.USAddressStandardizer;
import org.javautil.address.exception.AddressStandardizationException;
import org.javautil.address.interfaces.AddressStandardizer;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.service.usps.UspsAddressValidationRequest;
import org.javautil.address.usps.AddressValidationException;

/**
 * <p>SingleAddressValidationService class.</p>
 *
 * @author jjs
 * @version $Id: SingleAddressValidationService.java,v 1.6 2012/04/09 01:29:06 jjs Exp $
 */
public class SingleAddressValidationService {

	private final static Logger logger = Logger
			.getLogger(SingleAddressValidationService.class);

	private Exception exception;

	/**
	 * 
	 */
	private final AddressStandardizer standardizer = new USAddressStandardizer();

	private String uspsURL;

	/**
	 * This is the account number provided by the USPS for their online
	 * validation service
	 */
	private String uspsAcctCode;

	/**
	 * 
	 */

	private UspsAddressValidationRequest validator;

	/**
	 * <p>Getter for the field <code>uspsURL</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUspsURL() {
		return uspsURL;
	}

	/**
	 * <p>Setter for the field <code>uspsURL</code>.</p>
	 *
	 * @param uspsURL a {@link java.lang.String} object.
	 */
	public void setUspsURL(final String uspsURL) {
		this.uspsURL = uspsURL;
	}

	/**
	 * <p>Getter for the field <code>validator</code>.</p>
	 *
	 * @return a {@link org.javautil.address.service.usps.UspsAddressValidationRequest} object.
	 */
	public UspsAddressValidationRequest getValidator() {
		return validator;
	}

	/**
	 * <p>Setter for the field <code>validator</code>.</p>
	 *
	 * @param validator a {@link org.javautil.address.service.usps.UspsAddressValidationRequest} object.
	 */
	public void setValidator(final UspsAddressValidationRequest validator) {
		this.validator = validator;
	}

	/**
	 * <p>Getter for the field <code>logger</code>.</p>
	 *
	 * @return a {@link org.apache.log4j.Logger} object.
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * <p>Getter for the field <code>exception</code>.</p>
	 *
	 * @return a {@link java.lang.Exception} object.
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * <p>Getter for the field <code>standardizer</code>.</p>
	 *
	 * @return a {@link org.javautil.address.interfaces.AddressStandardizer} object.
	 */
	public AddressStandardizer getStandardizer() {
		return standardizer;
	}

	private void afterPropertiesSet() {
		if (getUspsAcctCode() == null) {
			throw new IllegalStateException("uspsAcct is null");
		}
		if (uspsURL == null) {
			throw new IllegalStateException("uspsURL is null");
		}
		validator = new UspsAddressValidationRequest(uspsURL,
				getUspsAcctCode());
	}

	/**
	 * <p>Getter for the field <code>exception</code>.</p>
	 *
	 * @param e a {@link java.lang.Exception} object.
	 * @return a {@link java.lang.Exception} object.
	 */
	public Exception getException(final Exception e) {
		return exception;
	}

	private String getUspsAcctCode() {
		return uspsAcctCode;
	}

	/**
	 * <p>process.</p>
	 *
	 * @param arguments a {@link org.javautil.address.AddressValidationRequest} object.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 */
	public void process(final AddressValidationRequest arguments)
			throws AddressValidationException {
		afterPropertiesSet();
		try {
			standardizer.standardize(arguments.getRawAddress(),
					arguments.getStandardAddress());
		} catch (final AddressStandardizationException msae) {
			arguments.setStandardizationErrorMessage(msae.getMessage());
		}
		if (logger.isDebugEnabled()) {
			final String addresses = "raw:\n" + arguments.getRawAddress()
					+ "\n" + "standardized:\n" + arguments.getStandardAddress();
			logger.debug(addresses);
		}
		final AuthoritativeAddress authoritativeAddress = validator
				.validate(arguments.getStandardAddress());
		arguments.setAuthoritativeAddress(authoritativeAddress);
	}

	/**
	 * <p>Setter for the field <code>uspsAcctCode</code>.</p>
	 *
	 * @param uspsAcctCode a {@link java.lang.String} object.
	 */
	public void setUspsAcctCode(final String uspsAcctCode) {
		this.uspsAcctCode = uspsAcctCode;
	}

}
