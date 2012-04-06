package org.javautil.address.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.javautil.address.AddressResourceBundle;
import org.javautil.address.exception.AddressStandardizationException;
import org.javautil.address.exception.MultiplePoBoxesException;

/**
 * <p>POBoxParser class.</p>
 *
 * @author jjs
 * @version $Id: POBoxParser.java,v 1.3 2012/03/04 12:31:12 jjs Exp $
 */
public class POBoxParser implements PoBoxComponent {

	private final Logger logger = Logger.getLogger(getClass());

	private static String poBoxExpression = AddressResourceBundle
			.getString("POBoxParser.PoBoxRegularExpression"); //$NON-NLS-1$
	/**
	 * In case "PO Box " was abbreviated with out the box
	 */
	private static String poExpression = AddressResourceBundle
			.getString("POBoxParser.PoRegularExpression"); //$NON-NLS-1$

	private final Pattern poBoxPattern = Pattern.compile(poBoxExpression);
	private final Pattern poPattern = Pattern.compile(poExpression);

	private String poBox = null;

	/**
	 * <p>Constructor for POBoxParser.</p>
	 */
	public POBoxParser() {

	}

	/**
	 * Parse an address line for a Post Office Box
	 *
	 * @param line
	 *            either line 1 or line 2 of an address.
	 * @return true if a post office box is found.
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	public synchronized boolean parse(final String line)
			throws AddressStandardizationException {
		poBox = null;
		boolean foundPoBox = false;
		if (line != null) {
			Matcher m = poBoxPattern.matcher(line.toUpperCase());
			if (m.matches()) {
				poBox = m.group(1);
				foundPoBox = true;
			} else {
				m = poPattern.matcher(line.toUpperCase());
				if (m.matches()) {
					poBox = m.group(1);
					foundPoBox = true;

				}

			}
			if (foundPoBox) {
				final String afterBox = m.group(2);
				logger.debug("afterBox " + afterBox);
				if (afterBox != null && afterBox.trim().length() > 0) {
					throw new MultiplePoBoxesException(line);

				}
			}
		}
		return foundPoBox;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Returns the post office box found during {@link #parse(String)}.
	 */
	@Override
	public String getPoBox() {
		return poBox;
	}

}
