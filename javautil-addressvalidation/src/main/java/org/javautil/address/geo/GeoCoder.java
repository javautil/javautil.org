package org.javautil.address.geo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.GeoLocator;

/**
 * <p>GeoCoder class.</p>
 *
 * @author jjs
 * @version $Id: GeoCoder.java,v 1.2 2012/03/04 12:31:17 jjs Exp $
 */
public class GeoCoder implements GeoLocator {

	private Double longitude;
	private Double latitude;
	private Exception exception;

	/**
	 * <p>setAddress.</p>
	 *
	 * @param address a {@link org.javautil.address.interfaces.Address} object.
	 */
	public void setAddress(final Address address) {
		if (address == null) {
			throw new IllegalArgumentException("address is null");
		}
		throw new UnsupportedOperationException();

	}

	/** {@inheritDoc} */
	@Override
	public boolean processGeoRequest() {
		boolean retval = true;
		try {
			final String webAddr = getEncodedLocator();
			final URL url = new URL(webAddr);
			final String response = (String) url.getContent();
			final String imageUrl = getImageUrl(response);
			final Map<String, String> parms = getParms(imageUrl);
			final String lat = parms.get("lat");
			final String lon = parms.get("lon");
			latitude = Double.valueOf(lat);
			longitude = Double.valueOf(lon);
		} catch (final Exception e) {
			exception = e;
			retval = false;
		}
		return retval;
	}

	private Map<String, String> getParms(final String webAddr)
			throws MalformedURLException {
		final HashMap<String, String> parms = new HashMap<String, String>();
		final URL url = new URL(webAddr);
		final String q = url.getQuery();
		q.split("&");
		if (parms.size() == 0) {
			throw new UnsupportedOperationException();
		}
		return parms;

	}

	private String getImageUrl(final String pageSource) {
		return null;
	}

	private String getEncodedLocator() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Double getLatitude() {

		processGeoRequest();

		return latitude;

	}

	/** {@inheritDoc} */
	@Override
	public Double getLongitude() {
		if (longitude == null) {
			processGeoRequest();
		}
		return longitude;
	}

	/**
	 * <p>getProcessException.</p>
	 *
	 * @return a {@link java.lang.Exception} object.
	 */
	public Exception getProcessException() {
		return exception;
	}
}
