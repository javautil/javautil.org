package org.javautil.address.parser;

import org.apache.log4j.Logger;
import org.javautil.address.standardname.UspsUnitAbbreviation;
import org.javautil.address.standardname.UspsUnitAbbreviations;

/**
 * Parses an address line looking for subunits.
 * 
 * A subunit is a location within an address, such as an apartment,building or
 * mail stop.
 * 
 * @author jjs
 * 
 */
/**
 * <p>SubunitParser class.</p>
 *
 * @author jjs
 * @version $Id: SubunitParser.java,v 1.5 2012/03/04 12:31:12 jjs Exp $
 */
public class SubunitParser implements SubunitComponents {

	private String subunitType;

	private String subunitCode;

	private String nonUnit;

	private static UspsUnitAbbreviations abbrevs = new UspsUnitAbbreviations();
	private UspsUnitAbbreviation uspsAbbrev;
	private String[] words;
	private String line;
	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * <p>parse.</p>
	 *
	 * @param _line
	 *            Line 1 or Line 2 from an address.
	 * @see UspsUnitAbbreviation
	 * @return true if subunit found.
	 */
	public boolean parse(final String _line) {
		subunitCode = null;
		subunitType = null;

		boolean retval = false;
		if (_line != null) {

			this.line = _line.replace(".", "");
			subunitCode = null;
			words = line.split(" ");
			retval = checkQualified() || checkUnqualified();
			logger.debug(this.toString());
		}

		return retval;
	}

	private void setNonUnit(final int trailingCount) {
		final StringBuilder b = new StringBuilder();
		final int lastIndex = words.length - trailingCount - 2;
		for (int i = 0; i <= lastIndex; i++) {
			b.append(words[i]);
			b.append(" ");
		}
		if (trailingCount > words.length) {
			final String lastWord = words[lastIndex + 1];
			logger.debug("lastWord is '" + lastWord + "'");
			final int li = lastWord.length() - 1;
			while (li > 0) {
				final char c = lastWord.charAt(li);
				if (Character.isLetterOrDigit(c)) {
					break;
				}
			}
			final String trimmedLast = lastWord.substring(0, li + 1);
			if (logger.isDebugEnabled()) {
				logger.debug("trimmedLast '" + trimmedLast + "'");
				b.append(trimmedLast);
			}
		}
		nonUnit = b.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("nonUnit '" + nonUnit + "'");
		}
	}

	/**
	 * Checks the second from the last word and sets the subunitType to the
	 * standard abbreviation for the second to last word and the subunit code to
	 * the last word if the second to the last word is a known variant that
	 * takes a qualifier.
	 */
	private boolean checkQualified() {
		boolean retval = false;

		if (words.length > 1) {
			final String secondFromLast = words[words.length - 2];
			logger.debug("secondFromLast " + secondFromLast);
			uspsAbbrev = abbrevs.getUspsUnitAbbreviation(secondFromLast);
			logger.debug("secondFromLast " + secondFromLast + " usps abbrev '"
					+ uspsAbbrev + "'");

			if (uspsAbbrev != null && uspsAbbrev.isQualifierRequired()) {
				subunitType = uspsAbbrev.getStandardAbbreviation();
				subunitCode = words[words.length - 1];
				setNonUnit(2);
				retval = true;

			}
		}
		return retval;
	}

	// todo need to handle qualifier is present but not required
	private boolean checkUnqualified() {
		boolean retval = false;

		if (words.length > 0) {
			final String last = words[words.length - 1];
			uspsAbbrev = abbrevs.getUspsUnitAbbreviation(last);
			if (uspsAbbrev != null && !uspsAbbrev.isQualifierRequired()) {
				subunitType = uspsAbbrev.getStandardAbbreviation();
				subunitCode = null;
				setNonUnit(1);
				retval = true;
			}
		}

		return retval;
	}

	/** {@inheritDoc} */
	@Override
	public String getSubunitType() {
		return subunitType;
	}

	/**
	 * <p>getSubunitTypeStandardName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSubunitTypeStandardName() {
		final String retval = uspsAbbrev == null ? null : uspsAbbrev
				.getStandardName();
		return retval;
	}

	/** {@inheritDoc} */
	@Override
	public String getSubunitCode() {
		return subunitCode;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 */
	@Override
	public String toString() {
		final String TAB = "    ";

		final StringBuilder retValue = new StringBuilder();

		retValue.append("SubunitParser ( ").append(super.toString())
				.append(TAB).append("subunitCode = ").append(this.subunitCode)
				.append(TAB).append("subunitType = ").append(this.subunitType)
				.append(TAB).append("words =");

		for (final String word : words) {
			retValue.append(" '");
			retValue.append(word);
			retValue.append("'");
		}
		retValue.append(TAB);
		retValue.append("'");
		retValue.append(nonUnit);
		retValue.append("' )");

		return retValue.toString();
	}

	/**
	 * <p>Getter for the field <code>nonUnit</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getNonUnit() {
		return nonUnit;
	}

	/**
	 * <p>getCodes.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCodes() {
		return abbrevs.getCodes();
	}
}
