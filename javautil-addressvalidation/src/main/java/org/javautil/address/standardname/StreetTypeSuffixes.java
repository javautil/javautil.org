package org.javautil.address.standardname;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.javautil.text.CSVTokenizer;
import org.javautil.text.TokenizingException;

/**
 * <p>StreetTypeSuffixes class.</p>
 *
 * @see http://www.usps.com/ncsc/lookups/usps_abbreviations.html#suffix
 * @author jjs
 * @version $Id: StreetTypeSuffixes.java,v 1.2 2012/03/04 12:31:10 jjs Exp $
 */
public class StreetTypeSuffixes {
	private final TreeMap<String, StreetTypeSuffix> byVariant = new TreeMap<String, StreetTypeSuffix>();
	private boolean initted = false;

	/**
	 * <p>init.</p>
	 */
	public void init() {
		if (!initted) {
			populateVariants();
			initted = true;
		}
	}

	private void populateVariants() {
		for (final StreetTypeSuffix suffix : getStreetTypeSuffixes()) {
			for (final String variant : suffix.getVariantsArray()) {
				if (variant == null) {
					throw new IllegalStateException("null variant for "
							+ suffix);
				}
				StreetTypeSuffix existing;
				if ((existing = byVariant.put(variant, suffix)) != null) {
					if (existing != suffix) {
						throw new IllegalStateException(
								"duplicate variant found " + variant);
					}
				}
			}
		}
	}

	/**
	 * <p>getStreetTypeSuffixes.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<StreetTypeSuffix> getStreetTypeSuffixes() {
		final InputStream is = getClass().getResourceAsStream(
				"StreetSuffix.csv");
		final BufferedReader r = new BufferedReader(new InputStreamReader(is));
		String in;
		final CSVTokenizer tokenizer = new CSVTokenizer();
		final ArrayList<StreetTypeSuffix> suffixes = new ArrayList<StreetTypeSuffix>();
		try {
			while ((in = r.readLine()) != null) {
				final List<String> tokenList = tokenizer.parse(in);
				final String[] tokens = tokenList.toArray(new String[tokenList
						.size()]);
				final StreetTypeSuffix suffix = new StreetTypeSuffix(tokens);
				suffixes.add(suffix);
			}
		} catch (final TokenizingException e) {
			throw new RuntimeException(e);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return suffixes;
	}

	/**
	 * Returns the StreetTypeSuffix for the specified variant.
	 *
	 * @param val a {@link java.lang.String} object.
	 * @return a {@link org.javautil.address.standardname.StreetTypeSuffix} object.
	 */
	public StreetTypeSuffix getStreetTypeSuffix(final String val) {
		StreetTypeSuffix retval = null;
		if (val != null) {
			retval = byVariant.get(val.toUpperCase());
		}
		return retval;
	}

	/**
	 * <p>getKnownVariants.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getKnownVariants() {
		final StringBuilder b = new StringBuilder();

		for (final String variant : byVariant.keySet()) {
			b.append("'");
			b.append(variant);
			b.append("' ");

		}
		return b.toString();
	}

}
