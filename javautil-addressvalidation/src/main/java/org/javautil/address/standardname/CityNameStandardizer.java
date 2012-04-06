package org.javautil.address.standardname;

import org.javautil.address.formatters.Capitalizer;

/**
 * <p>CityNameStandardizer class.</p>
 *
 * @author jjs
 * @version $Id: CityNameStandardizer.java,v 1.2 2012/03/04 12:31:11 jjs Exp $
 */
public class CityNameStandardizer {

	/**
	 * <p>getStandardCityName.</p>
	 *
	 * @param cityName a {@link java.lang.String} object.
	 * @param stateCode a {@link java.lang.String} object.
	 * @param countryCode a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String getStandardCityName(final String cityName,
			final String stateCode, final String countryCode) {
		final String retval = cityName == null ? null : Capitalizer
				.initCaps(cityName.trim());
		return retval;
	}
}
