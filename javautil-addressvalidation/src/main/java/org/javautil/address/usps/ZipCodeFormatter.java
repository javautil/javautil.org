package org.javautil.address.usps;

import org.javautil.address.exception.AddressStandardizationException;

/**
 * <p>ZipCodeFormatter class.</p>
 *
 * @author jjs
 * @version $Id: ZipCodeFormatter.java,v 1.2 2012/03/04 12:31:15 jjs Exp $
 */
public class ZipCodeFormatter {

	private static final String fiveDigits = "expected 5 digit zip code";

	private static final String nineDigits = "expected 9 digit zip code";

	private static final String notUS = "not a valid US zip code format";

	/**
	 * <p>formatUSPostalCode.</p>
	 *
	 * @param zip a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	public String formatUSPostalCode(final String zip)
			throws AddressStandardizationException {
		String retval = null;
		if (zip != null) {
			final String trimmed = zip.trim();
			switch (trimmed.length()) {
			case 5:
				retval = formatZip5(trimmed);
				break;
			case 9:
				retval = formatZip9(trimmed);
				break;
			case 10:
				retval = formatZip10(trimmed);
				break;
			default:
				throw new ZipCodeFormatException(notUS);
			}
		}
		return retval;
	}

	private boolean isAllDigits(final String in) {
		boolean retval = true;
		if (in == null) {
			retval = false;
		} else {
			for (int i = 0; i < in.length(); i++) {
				final char c = in.charAt(i);
				if (!Character.isDigit(c)) {
					retval = false;
					break;
				}
			}
		}
		return retval;
	}

	private String formatZip5(final String in)
			throws AddressStandardizationException {
		if (in.length() != 5) {
			throw new ZipCodeFormatException(fiveDigits);
		}

		if (!isAllDigits(in)) {
			throw new ZipCodeFormatException(fiveDigits);
		}

		return in.length() == 5 ? in : null;
	}

	private String formatZip9(final String in)
			throws AddressStandardizationException {
		if (in == null) {
			throw new ZipCodeFormatException("in is null");
		}
		if (in.length() != 9) {
			throw new ZipCodeFormatException(nineDigits);
		}

		if (!isAllDigits(in)) {
			throw new ZipCodeFormatException(nineDigits);
		}
		final String trimmed = in.trim();
		final String zip5 = trimmed.substring(0, 5);
		final String zip4 = trimmed.substring(5);
		final String retval = zip5 + "-" + zip4;

		return retval;
	}

	private String formatZip10(final String in) {
		String retval = null;
		if (in != null && in.length() == 10) {
			final String zip5 = in.substring(0, 5);
			final String zip4 = in.substring(6);
			final char separator = in.charAt(5);
			if (isAllDigits(zip5) && isAllDigits(zip4)) {
				if (separator == ' ' || separator == '-') {
					retval = zip5 + "-" + zip4;
				}
			}
		}
		return retval;
	}
}
