package org.javautil.address.service.usps;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AddressValidator;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.usps.AddressValidationException;
import org.javautil.xml.ElementHelper;

/**
 * Creates a url of the form (with no line returns of course) where XXXXXX is
 * the USPS assigned account
 *
 * <pre>
 * http://Production.ShippingAPIs.com/ShippingAPI.dll?API=Verify&XML=
 * <AddressValidateRequest USERID="XXXXXX">
 * 		<Address>
 *        	<Address1/>
 *     		<Address2>1600 Pennsylvania Ave</Address2>
 *     		<City>Washington</City><State>DC</State><Zip5>20500</Zip5><Zip4/>
 * 		</Address>
 * </AddressValidateRequest>
 * </pre>
 *
 * @author jjs
 * @version $Id: UspsAddressValidationRequest.java,v 1.1 2012/04/09 01:29:05 jjs Exp $
 */
public class UspsAddressValidationRequest implements AddressValidator {
	private final Logger logger = Logger.getLogger(this.getClass());
	private Collection<Address> addresses = new ArrayList<Address>();
	private static final String newline = System.getProperty("line.separator");
	private String userId;
	private int requestCount = 0;
	private long requestMillis = 0;
	private int addressCount = 0;
	private String url;
	private final boolean logRequest = true; // TODO restore
												// AddressResourceBundle.getBoolean("USPSAddressValidationRequest.logRequest");
	private final boolean logRawUrlText = true; // TOD restore
												// AddressResourceBundle.getBoolean("USPSAddressValidationRequest.logRawUrlText");

	/**
	 * <p>Constructor for UspsAddressVerificationRequest.</p>
	 */
	public UspsAddressValidationRequest() {

	}

	/**
	 * <p>Constructor for UspsAddressVerificationRequest.</p>
	 *
	 * @param url
	 *            the url for the USPS validation service. For production this
	 *            should be
	 *            <code>http://production.shippingapis.com/ShippingAPI.dll?API=Verify&XML=</code>
	 * @param userId
	 *            The USPS assigned user id. There is no password
	 */
	public UspsAddressValidationRequest(final String url, final String userId) {
		if (url == null) {
			throw new IllegalArgumentException("url is null");
		}
		if (userId == null) {
			throw new IllegalArgumentException("userId is null");
		}

		this.url = url;
		this.userId = userId;
	}

	/**
	 * <p>validate.</p>
	 *
	 * @param standards a {@link java.util.Collection} object.
	 * @return a {@link java.util.List} object.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 */
	public List<AuthoritativeAddress> validate(
			final Collection<Address> standards)
			throws AddressValidationException {
		addresses = standards;
		List<AuthoritativeAddress> retval = new ArrayList<AuthoritativeAddress>();
		if (standards.size() > 0) {
			final String response = getResponse();
			retval = UspsValidationResponse.parseResponse(response);
		}
		return retval;
	}

	/**
	 * <p>addAddress.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public void addAddress(final Address address) {
		addresses.add(address);
	}

	/**
	 * <p>getPrefix.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPrefix() {
		final String prefix = url + "?API=Verify&XML=";
		return prefix;
	}

	/**
	 * <p>asRawURLText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String asRawURLText() {
		final String retval = getPrefix() + asXML();
		if (logger.isDebugEnabled()) {
			logger.debug("raw '" + retval + "'");
		}
		return retval;
	}

	/**
	 * <p>asURLText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 * @throws java.io.UnsupportedEncodingException if any.
	 */
	public String asURLText() throws UnsupportedEncodingException {
		final String retval = getPrefix() + URLEncoder.encode(asXML(), "UTF-8");
		if (logger.isDebugEnabled()) {
			logger.debug("asURLText '" + retval + "'");
		}
		return retval;
	}

	private void addToElement(final Element el, final String elementName,
			final String value) {
		final Element component = el.addElement(elementName);
		if (value != null) {
			component.setText(value);
		}
	}

	private Element getAsElement(final Address addr) {
		final Element addrEl = DocumentHelper.createElement("Address");
		addToElement(addrEl, "Address1", addr.getAddress1());
		addToElement(addrEl, "Address2", addr.getAddress2());
		addToElement(addrEl, "City", addr.getCity());
		addToElement(addrEl, "State", addr.getState());
		addToElement(addrEl, "Zip5", addr.getPostalCode());
		addToElement(addrEl, "Zip4", null);
		return addrEl;
	}

	/**
	 * <p>asXML.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String asXML() {
		final Element request = DocumentHelper
				.createElement("AddressValidateRequest");
		request.addAttribute("USERID", userId);

		for (final Address address : addresses) {
			if (logger.isDebugEnabled()) {
				logger.debug("before adding: " + newline
						+ ElementHelper.getPretty(request));
			}
			final Element newAddr = getAsElement(address);
			request.add(getAsElement(address));
			if (logger.isDebugEnabled()) {
				logger.debug("adding address: " + newline
						+ address.getFormatted());
				logger.debug("new element is " + newline
						+ ElementHelper.getPretty(newAddr));
				logger.debug("request is now: " + newline
						+ ElementHelper.getPretty(request));
			}

		}
		return request.asXML();
	}

	private URL toURL() throws MalformedURLException,
			UnsupportedEncodingException {
		final String urlText = asURLText();
		if (logRawUrlText) {
			final String raw = asRawURLText();
			// "http://testing.shippingapis.com/ShippingAPITest.dll?API=[API_Name]&XML=[XML_String_containing_User_ID] "
			logger.debug("raw request: '" + raw + "'");

		}
		if (logRequest) {
			logger.debug(newline + urlText);
		}
		return new URL(urlText);
	}

	/**
	 * <p>getResponse.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 */
	public String getResponse() throws AddressValidationException {
		final long beforeRqst = System.currentTimeMillis();
		final StringBuilder response = new StringBuilder();
		try {
			final URL url = toURL();
			final InputStream in = url.openStream();
			final BufferedInputStream bufIn = new BufferedInputStream(in);

			int data;
			if (logger.isDebugEnabled()) {
				logger.debug("using url: '" + url.toString());
			}
			while ((data = bufIn.read()) != -1) {
				response.append((char) data);

			}
		} catch (final IOException io) {
			throw new AddressValidationException(io.getClass().getName() + " "
					+ io.getMessage());
		}
		final long afterRqst = System.currentTimeMillis();
		final long requestDurationMillis = afterRqst - beforeRqst;
		addressCount += addresses.size();
		requestCount++;
		requestMillis += requestDurationMillis;
		if (logger.isDebugEnabled()) {
			final String message = "request size " + addresses.size()
					+ " in millis " + requestDurationMillis;
			logger.debug(message);
		}
		return response.toString();
	}

	/**
	 * <p>clear.</p>
	 */
	public void clear() {
		addresses.clear();
	}

	/**
	 * <p>getSize.</p>
	 *
	 * @return a int.
	 */
	public int getSize() {
		return addresses.size();
	}

	/**
	 * <p>Getter for the field <code>requestMillis</code>.</p>
	 *
	 * @return The total number of milliseconds spent in getResponse method.
	 */
	public long getRequestMillis() {
		return requestMillis;
	}

	/**
	 * <p>Getter for the field <code>requestCount</code>.</p>
	 *
	 * @return the requestCount, the number of times getResponse was called.
	 */
	public int getRequestCount() {
		return requestCount;
	}

	/**
	 * <p>Getter for the field <code>addressCount</code>.</p>
	 *
	 * @return the number of addresses processed
	 */
	public int getAddressCount() {
		return addressCount;
	}

	/**
	 * <p>getStatisticsMessage.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getStatisticsMessage() {
		String message;
		if (addressCount > 0) {
			final double millisPerAddress = requestMillis / addressCount;
			message = "requestCount: " + requestCount + //
					" requestMillis: " + requestMillis + //
					" addressCount: " + addressCount + //
					" millisPerAddress: " + millisPerAddress;
		} else {
			message = " no addresses request";
		}
		return message;
	}

	/**
	 * <p>Getter for the field <code>url</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <p>Setter for the field <code>url</code>.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * <p>Getter for the field <code>userId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * <p>Setter for the field <code>userId</code>.</p>
	 *
	 * @param userId a {@link java.lang.String} object.
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/** {@inheritDoc} */
	@Override
	public AuthoritativeAddress validate(final Address standardAddress)
			throws AddressValidationException {
		final List<Address> addresses = new ArrayList<Address>();
		addresses.add(standardAddress);
		final List<AuthoritativeAddress> authoritative = validate(addresses);
		return authoritative.get(0);
	}

}
