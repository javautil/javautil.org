package org.javautil.address.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.javautil.address.AddressResourceBundle;
import org.javautil.address.standardname.StreetTypeSuffix;
import org.javautil.address.standardname.StreetTypeSuffixes;

/**
 * <p>
 * StreetParser class.
 * </p>
 * 
 * @author jjs
 * @version $Id: StreetParser.java,v 1.4 2012/03/04 12:31:12 jjs Exp $
 */
public class StreetParser implements StreetComponents {
	private final Logger logger = Logger.getLogger(getClass());

	private final StreetTypeSuffixes streetTypes = new StreetTypeSuffixes();

	public boolean initted = false;
	/**
	 * Leading digits on the line
	 */
	private String streetNumber;

	/*
	 * Everything following the leading digits on the line
	 */
	private String nonStreetNumber;
	//
	//
	// Split the street into leading digits and everything else
	//
	//
	//
	private static String streetNumberExpression = AddressResourceBundle
			.getString("StreetParser.StreetNumberRegularExpression"); //$NON-NLS-1$

	/*
	 * returns true if this appears to be a street address, todo weakly enough
	 * just means it starts with digits
	 */

	private final Pattern streetNumberPattern = Pattern
			.compile(streetNumberExpression);

	//
	//
	//
	private static String directionalPrefixExpression = AddressResourceBundle
			.getString("StreetParser.DirectionalPrefixRegularExpression"); //$NON-NLS-1$
	private static Pattern directionalPrefixPattern = Pattern
			.compile(directionalPrefixExpression);
	private String rawDirectionalPrefix;

	//
	//
	//
	private static String streetTypeExpression = AddressResourceBundle
			.getString("StreetParser.StreetTypeRegularExpression"); //$NON-NLS-1$

	private final Pattern streetTypePattern = Pattern
			.compile(streetTypeExpression);
	private String streetName;
	private String streetType;

	private void clear() {
		streetNumber = null;
		nonStreetNumber = null;
		rawDirectionalPrefix = null;
		streetName = null;
		streetType = null;
	}

	private String getRawDirectionalPrefix(final String line) {
		final Matcher m = directionalPrefixPattern.matcher(line);

		if (m.matches()) {
			rawDirectionalPrefix = m.group(1);
		}
		return rawDirectionalPrefix;
	}

	/** {@inheritDoc} */
	@Override
	public String getStreetName() {
		return streetName;
	}

	//
	// results accessors
	//
	/** {@inheritDoc} */
	@Override
	public String getStreetNumber() {
		return streetNumber;
	}

	/** {@inheritDoc} */
	@Override
	public String getStreetType() {
		return streetType;
	}

	/**
	 * <p>
	 * init.
	 * </p>
	 */
	public void init() {
		if (!initted) {
			streetTypes.init();
			initted = true;
		}
	}

	/**
	 * Parse the address line into components.
	 * 
	 * @param in
	 *            a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean parse(final String in) {
		init();
		clear();
		boolean retval = false;
		if (in != null) {
			retval = splitStreetNumber(in);
			if (retval) {
				rawDirectionalPrefix = getRawDirectionalPrefix(nonStreetNumber);
				processStreetType(nonStreetNumber);
			}
		}
		return retval;
	}

	private void processStreetType(final String in) {
		String streetNameSuspect;

		String streetTypeSuspect;
		final Matcher streetTypeMatcher = streetTypePattern.matcher(in);
		if (streetTypeMatcher.matches()) {
			streetNameSuspect = streetTypeMatcher.group(1);
			streetTypeSuspect = streetTypeMatcher.group(2);

			if (logger.isDebugEnabled()) {
				logger.debug("streetTypeSuspect " + streetTypeSuspect);
			}
			final StreetTypeSuffix typeSuffix = streetTypes
					.getStreetTypeSuffix(streetTypeSuspect);
			if (typeSuffix != null) {
				streetType = typeSuffix.getPrimarySuffix();
			}

			if (streetType == null) {
				streetName = nonStreetNumber;
			} else {
				streetName = streetNameSuspect;
			}
		} else {
			streetName = in;
		}
	}

	/**
	 * Splits the line into streetNumber and nonStreetNumber.
	 * 
	 * @param line
	 * @return
	 */
	private boolean splitStreetNumber(final String line) {
		final Matcher streetNumberMatcher = streetNumberPattern.matcher(line);
		final boolean matches = streetNumberMatcher.matches();
		boolean foundStreet = false;
		if (matches) {

			final String myStreetNumber = streetNumberMatcher.group(1);
			if (myStreetNumber != null && myStreetNumber.length() > 0) {
				foundStreet = true;
				streetNumber = myStreetNumber;

				nonStreetNumber = streetNumberMatcher.group(2);
			}
		}
		return foundStreet;
	}

}
