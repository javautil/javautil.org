package org.javautil.address.geo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.GeoAddress;
import org.javautil.address.usps.AddressValidationException;
import org.javautil.util.Base64Coder;

/**
 import org.javautil.address.interfaces.Address;
 import org.javautil.address.interfaces.GeoAddress;
 import org.javautil.address.usps.AddressValidationException;
 import org.javautil.util.Base64Coder;
 */
/**
 * takes an address encodes a url similar to the following
 * http://username:password
 *
 * @author jjs
 * @version $Id: AddressGeoRequest.java,v 1.3 2012/03/04 12:31:17 jjs Exp $
 */
public class AddressGeoRequest {

	private static final XPath pointPath = DocumentHelper
			.createXPath("/rdf:RDF/geo:Point");
	// private static final XPath descrPath =
	// DocumentHelper.createXPath("./dc:description");
	private static final XPath longitudePath = DocumentHelper
			.createXPath("./geo:long");
	private static final XPath latitudePath = DocumentHelper
			.createXPath("./geo:lat");
	// private Collection<GeoAddress> addresses;

	// private static final XPath descriptionPath =
	// DocumentHelper.createXPath("./Error/Description");

	private static Logger logger = Logger.getLogger(AddressGeoRequest.class);

	private org.javautil.address.interfaces.Address address;
	private static final String urlComponent = "geocoder.us/member/service/rest/geocode?address=";
	//
	// Service account info
	//
	// private String url;
	private String userId;
	private String password;

	/**
	 * <p>Constructor for AddressGeoRequest.</p>
	 */
	public AddressGeoRequest() {

	}

	/**
	 * <p>Constructor for AddressGeoRequest.</p>
	 *
	 * @param userId a {@link java.lang.String} object.
	 * @param password a {@link java.lang.String} object.
	 */
	public AddressGeoRequest(final String userId, final String password) {

		if (userId == null) {
			throw new IllegalArgumentException("userId is null");
		}
		if (password == null) {
			throw new IllegalArgumentException("password is null");
		}

		this.userId = userId;
		this.password = password;
	}

	/**
	 * <p>setGeoInfo.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.GeoAddress} object.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 * @throws org.javautil.address.geo.GeoProcessingException if any.
	 */
	public void setGeoInfo(final GeoAddress address)
			throws AddressValidationException, GeoProcessingException {
		setAddress(address);
		final long beforeResponseMillis = System.currentTimeMillis();
		final String response = getResponse();
		final long afterResponseMillis = System.currentTimeMillis();
		final long responseMillis = afterResponseMillis - beforeResponseMillis;

		// GeoCoderResponse responseHandler = new GeoCoderResponse();
		parseResponse(response, address);
		logger.debug("response Millis " + responseMillis + " "
				+ address.getFormatted());
	}

	/**
	 * <p>Setter for the field <code>address</code>.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public void setAddress(final Address address) {
		if (address == null) {
			throw new IllegalArgumentException("address is null");
		}
		this.address = address;
	}

	/**
	 * <p>asRawURLText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 * @throws java.io.UnsupportedEncodingException if any.
	 */
	public String asRawURLText() throws UnsupportedEncodingException {
		final StringBuilder b = new StringBuilder();
		b.append("http://");

		b.append(urlComponent);
		final String rawRestAddress = getAsRest(address);

		final String encodedAddress = URLEncoder
				.encode(rawRestAddress, "UTF-8");
		b.append(encodedAddress);
		final String retval = b.toString();
		logger.debug("request: " + retval);
		return b.toString();
	}

	/**
	 * <p>updateGeoSpatial.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.GeoAddress} object.
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 */
	public void updateGeoSpatial(final GeoAddress address)
			throws AddressValidationException {
		this.address = address;
		getResponse();
	}

	private String getAsRest(final Address addr) {
		if (addr == null) {
			throw new IllegalArgumentException("addr is null");
		}
		final StringBuilder b = new StringBuilder();
		b.append(addr.getAddress2());
		b.append(",");
		b.append(addr.getCity());
		b.append(" ");
		b.append(addr.getState());
		b.append(" ");
		b.append(addr.getPostalCode());
		return b.toString();
	}

	/**
	 * <p>toURL.</p>
	 *
	 * @return a {@link java.net.URL} object.
	 * @throws java.net.MalformedURLException if any.
	 * @throws java.io.UnsupportedEncodingException if any.
	 */
	public URL toURL() throws MalformedURLException,
			UnsupportedEncodingException {
		return new URL(asRawURLText());
	}

	/**
	 * <p>processGeoRequest.</p>
	 */
	public void processGeoRequest() {
		if (address == null) {
			throw new IllegalStateException("address has not been set");
		}
	}

	/**
	 * <p>encode.</p>
	 *
	 * @param source a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String encode(final String source) {

		return new String(Base64Coder.encode(source.getBytes()));
	}

	// http://www.w3.org/Protocols/HTTP/1.0/draft-ietf-http-spec.html#WWW-Authenticate
	// http://home.tiscali.nl/~bmc88/java/sbook/045.html

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
			logger.debug(url.toString());
			final URLConnection conn = url.openConnection();

			final String authString = "Basic "
					+ encode(userId + ":" + password);
			conn.setRequestProperty("Authorization", authString);
			final InputStream in = conn.getInputStream();
			final BufferedInputStream bufIn = new BufferedInputStream(in);
			int data;
			while ((data = bufIn.read()) != -1) {
				response.append((char) data);
			}
		} catch (final IOException io) {
			logger.error(io.getMessage());
			throw new AddressValidationException(io.getClass().getName() + " "
					+ io.getMessage());
		}
		final long afterRqst = System.currentTimeMillis();
		final long requestDurationMillis = afterRqst - beforeRqst;

		if (logger.isDebugEnabled()) {
			final String message = "request duration millis "
					+ requestDurationMillis;
			logger.debug(message);
		}
		final String retval = response.toString();
		logger.debug(retval);
		return retval;
	}

	/**
	 * <p>parseResponse.</p>
	 *
	 * @param response a {@link java.lang.String} object.
	 * @param address a {@link org.javautil.address.interfaces.GeoAddress} object.
	 * @return a {@link org.javautil.address.interfaces.GeoAddress} object.
	 * @throws org.javautil.address.geo.GeoProcessingException if any.
	 */
	@SuppressWarnings({ "cast", "unchecked" })
	public GeoAddress parseResponse(final String response,
			final GeoAddress address) throws GeoProcessingException {
		// this.addresses = addresses;
		if (response == null) {
			throw new IllegalArgumentException("response is null");
		}
		// The response is parsed into the document.
		Document doc;

		// Parse the response into the document
		try {
			doc = DocumentHelper.parseText(response);
		} catch (final DocumentException e) {
			throw new GeoProcessingException(
					"invalid response from GeoCoder: '" + response + "'");
		}

		// process each address

		final List<Element> points = (List<Element>) pointPath.selectNodes(doc);

		// Iterator<GeoAddress> geoIt = addresses.iterator();
		for (final Element point : points) {
			// GeoAddress addr = geoIt.next();

			address.setLongitude(new Double(getText(point, longitudePath)));
			address.setLatitude(new Double(getText(point, latitudePath)));

		}

		return address;
	}

	private static String getText(final Element parent, final XPath path) {
		String retval = null;
		final Element child = (Element) path.selectSingleNode(parent);
		if (child != null) {
			retval = child.getText();
		}
		return retval;
	}

}
