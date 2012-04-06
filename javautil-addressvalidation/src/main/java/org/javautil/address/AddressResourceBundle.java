package org.javautil.address;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.javautil.properties.BooleanPropertyHelper;

/**
 * <p>AddressResourceBundle class.</p>
 *
 * @author jjs
 * @version $Id: AddressResourceBundle.java,v 1.2 2012/03/04 12:31:14 jjs Exp $
 */
public class AddressResourceBundle {
	private static final String BUNDLE_NAME = "org.javautil.address.addressProperties"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private AddressResourceBundle() {
	}

	/**
	 * <p>getString.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getString(final String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException e) {
			throw new IllegalArgumentException("key: '" + key
					+ "' not found in resource bundle " + BUNDLE_NAME);
		}
	}

	/**
	 * <p>getInt.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 * @return a int.
	 */
	public static int getInt(final String key) {

		final String string = getString(key);
		int retval;
		try {
			retval = Integer.parseInt(string);
		} catch (final NumberFormatException nfe) {
			throw new IllegalArgumentException("property with key '" + key
					+ "' in " + BUNDLE_NAME + " value: '" + string
					+ "' cannot be parsed as an int " + nfe.getMessage());
		}
		return retval;
	}

	/**
	 * <p>getBoolean.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean getBoolean(final String key) {
		final String value = getString(key);
		final BooleanPropertyHelper h = new BooleanPropertyHelper();
		final boolean retval = h.parse(value);
		return retval;
	}
}
