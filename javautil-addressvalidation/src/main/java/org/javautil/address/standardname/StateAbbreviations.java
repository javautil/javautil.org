package org.javautil.address.standardname;

import java.util.TreeMap;

/**
 * <p>StateAbbreviations class.</p>
 *
 * @author jjs
 * @version $Id: StateAbbreviations.java,v 1.2 2012/03/04 12:31:10 jjs Exp $
 */
public class StateAbbreviations {
	private static TreeMap<String, String> byAbbrv = new TreeMap<String, String>();

	static {
		byAbbrv.put("AL", "Alabama");
		byAbbrv.put("AK", "Alaska");
		byAbbrv.put("AS", "American Samoa");
		byAbbrv.put("AZ", "Arizona");
		byAbbrv.put("AR", "Arkansas");
		byAbbrv.put("CA", "California");
		byAbbrv.put("CO", "Colorado");
		byAbbrv.put("CT", "Connecticut");
		byAbbrv.put("DE", "Delaware");
		byAbbrv.put("DC", "District of Columbia");
		byAbbrv.put("FM", "Federated States of Micronesia");
		byAbbrv.put("FL", "Florida");
		byAbbrv.put("GA", "Georgia");
		byAbbrv.put("GU", "Guam");
		byAbbrv.put("HI", "Hawaii");
		byAbbrv.put("ID", "Idaho");
		byAbbrv.put("IL", "Illinois");
		byAbbrv.put("IN", "Indiana");
		byAbbrv.put("IA", "Iowa");
		byAbbrv.put("KS", "Kansas");
		byAbbrv.put("KY", "Kentucky");
		byAbbrv.put("LA", "Louisiana");
		byAbbrv.put("ME", "Maine");
		byAbbrv.put("MH", "Marshall Islands");
		byAbbrv.put("MD", "Maryland");
		byAbbrv.put("MA", "Massachusetts");
		byAbbrv.put("MI", "Michigan");
		byAbbrv.put("MN", "Minnesota");
		byAbbrv.put("MS", "Mississippi");
		byAbbrv.put("MO", "Missouri");
		byAbbrv.put("MT", "Montana");
		byAbbrv.put("NE", "Nebraska");
		byAbbrv.put("NV", "Nevada");
		byAbbrv.put("NH", "New Hampshire");
		byAbbrv.put("NJ", "New Jersey");
		byAbbrv.put("NM", "New Mexico");
		byAbbrv.put("NY", "New York");
		byAbbrv.put("NC", "North Carolina");
		byAbbrv.put("ND", "North Dakota");
		byAbbrv.put("MP", "Northern Mariana Islands");
		byAbbrv.put("OH", "Ohio");
		byAbbrv.put("OK", "Oklahoma");
		byAbbrv.put("OR", "Oregon");
		byAbbrv.put("PW", "Palau");
		byAbbrv.put("PA", "Pennsylvania");
		byAbbrv.put("PR", "Puerto Rico");
		byAbbrv.put("RI", "Rhode Island");
		byAbbrv.put("SC", "South Carolina");
		byAbbrv.put("SD", "South Dakota");
		byAbbrv.put("TN", "Tennessee");
		byAbbrv.put("TX", "Texas");
		byAbbrv.put("UT", "Utah");
		byAbbrv.put("VT", "Vermont");
		byAbbrv.put("VI", "Virgin Islands");
		byAbbrv.put("VA", "Virginia");
		byAbbrv.put("WA", "Washington");
		byAbbrv.put("WV", "West Virginia");
		byAbbrv.put("WI", "Wisconsin");
		byAbbrv.put("WY", "Wyoming");
		byAbbrv.put("AE", "Armed Forces");
		byAbbrv.put("AA", "Armed Forces Americas");
		byAbbrv.put("AP", "Armed Forces Pacific");
	}

	/**
	 * <p>getStandardAbbreviation.</p>
	 *
	 * @param abbrev a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getStandardAbbreviation(final String abbrev) {
		String retval = null;
		if (abbrev != null) {
			final String key = abbrev.toUpperCase().trim();
			if (byAbbrv.get(key) != null) {
				retval = key;
			}
		}
		return retval;
	}

	/**
	 * <p>getStandardName.</p>
	 *
	 * @param abbrv a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getStandardName(final String abbrv) {
		String retval = null;
		if (abbrv != null) {
			retval = byAbbrv.get(abbrv.toUpperCase());
		}
		return retval;
	}
}
