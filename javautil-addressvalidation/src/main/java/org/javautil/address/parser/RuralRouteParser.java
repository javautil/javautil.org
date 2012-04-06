package org.javautil.address.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javautil.address.AddressResourceBundle;

/**
 * Looks at an address line and looks for a Rural Route and a box.
 *
 * The first non blank characters of the line must be 'RR'
 *
 * usage: String addressLine;
 * <ul>
 * <li>RuralRouteParser parser = new RuralRouteParser();
 * <li>boolean rrFound = parser.parse(addressLine);
 * <li>String ruralRound = parser.getRuralRoute();
 *
 * @author jjs
 * @version $Id: RuralRouteParser.java,v 1.4 2012/03/04 12:31:12 jjs Exp $
 */
public class RuralRouteParser {
	/**
	 * 
	 */
	private static final String RRP = "RuralRouteParser.";
	/**
	 * Optional leading spaces
	 */

	private static final String ruralRouteExpression = AddressResourceBundle
			.getString(RRP + "RouteRegularExpression"); //$NON-NLS-1$
	private static final int ROUTE_GROUP = Integer
			.parseInt(AddressResourceBundle.getString(RRP + "RouteGroup"));
	private static final int BOX_GROUP = Integer.parseInt(AddressResourceBundle
			.getString(RRP + "BoxGroup"));

	private final Pattern ruralRoutePattern = Pattern
			.compile(ruralRouteExpression);

	private String ruralRoute;

	private String box;

	private String beforeBox;

	private String afterBox;

	// private static final String RURAL_FOUND_NO_BOX = AddressResourceBundle
	//			.getString("RuralRouteParser.RRFoundButBoxNotFound"); //$NON-NLS-1$

	/*
	 * returns true if this appears to be a street address, todo weakly enough
	 * just means it starts with digits
	 */

	/**
	 * <p>parse.</p>
	 *
	 * @param in a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean parse(final String in) {

		clear();
		if (in != null) {
			final String upperIn = in.toUpperCase();

			final Matcher matcher = ruralRoutePattern.matcher(upperIn);
			if (matcher.lookingAt()) {
				ruralRoute = matcher.group(ROUTE_GROUP);
				beforeBox = matcher.group(2);
				box = matcher.group(BOX_GROUP);
				// afterBox = matcher.group(4);
			}
		}
		final boolean retval = ruralRoute != null || box != null;
		return retval;
	}

	private void clear() {

		ruralRoute = null;
		ruralRoute = null;
		beforeBox = null;
		box = null;
		afterBox = null;
	}

	/**
	 * <p>Getter for the field <code>ruralRoute</code>.</p>
	 *
	 * @return the ruralRoute
	 */
	public String getRuralRoute() {
		return ruralRoute;
	}

	/**
	 * <p>Getter for the field <code>box</code>.</p>
	 *
	 * @return the box
	 */
	public String getBox() {
		return box;
	}

	/**
	 * <p>getBefore.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getBefore() {
		return beforeBox;
	}

	/**
	 * <p>Getter for the field <code>afterBox</code>.</p>
	 *
	 * @return the afterBox
	 */
	public String getAfterBox() {
		return afterBox;
	}

}
