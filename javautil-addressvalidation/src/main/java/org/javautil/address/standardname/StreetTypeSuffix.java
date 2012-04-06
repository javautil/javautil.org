package org.javautil.address.standardname;

import java.util.Set;
import java.util.TreeSet;

import org.javautil.text.CSVWriter;

/**
 * <p>StreetTypeSuffix class.</p>
 *
 * @author jjs
 * @version $Id: StreetTypeSuffix.java,v 1.3 2012/03/04 12:31:10 jjs Exp $
 */
public class StreetTypeSuffix {
	private final String primarySuffix;
	private final String standardAbbreviation;

	private TreeSet<String> variants = new TreeSet<String>();

	/**
	 * <p>Constructor for StreetTypeSuffix.</p>
	 *
	 * @param variant a {@link java.lang.String} object.
	 */
	public StreetTypeSuffix(final String... variant) {
		if (variant.length < 1) {
			throw new IllegalArgumentException("no values");
		}
		if (variant[0] == null || variant[0].trim().length() == 0) {
			throw new IllegalArgumentException("primary suffix is null or ");
		}
		primarySuffix = variant[0];
		standardAbbreviation = variant[1];
		for (final String element : variant) {
			addVariant(element);
		}

	}

	/**
	 * <p>getVariantsArray.</p>
	 *
	 * @return an array of {@link java.lang.String} objects.
	 */
	@SuppressWarnings("unchecked")
	public String[] getVariantsArray() {
		final Set<String> s = (Set<String>) variants.clone();

		final int variantsSize = variants.size();
		final String[] vars = new String[variantsSize];

		int i = 0;
		for (final String v : s) {
			if (v == null) {
				throw new IllegalStateException("contains null variant");
			}
			vars[i++] = v;
		}
		for (final String var : vars) {
			if (var == null) {
				throw new IllegalStateException("null variant found");
			}
		}
		return vars;

	}

	/**
	 * <p>check.</p>
	 */
	public void check() {
		if (primarySuffix == null) {
			throw new IllegalStateException("primarySuffix is null");
		}
		if (standardAbbreviation == null) {
			throw new IllegalStateException("standardAbbreviation is null");
		}
	}

	/**
	 * <p>addVariant.</p>
	 *
	 * @param variant a {@link java.lang.String} object.
	 */
	public void addVariant(final String variant) {
		if (variant == null) {
			throw new IllegalArgumentException("variant may not be null");
		}
		this.variants.add(variant.toUpperCase());
	}

	/**
	 * <p>Getter for the field <code>variants</code>.</p>
	 *
	 * @return a {@link java.util.TreeSet} object.
	 */
	public TreeSet<String> getVariants() {
		return variants;
	}

	/**
	 * <p>Setter for the field <code>variants</code>.</p>
	 *
	 * @param variants a {@link java.util.TreeSet} object.
	 */
	public void setVariants(final TreeSet<String> variants) {
		this.variants = variants;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("primary " + primarySuffix + " ");
		b.append("standard " + standardAbbreviation + " : ");
		for (final String variant : variants) {
			b.append(variant);
			b.append(" ");
		}
		return b.toString();
	}

	/**
	 * <p>toCSVString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toCSVString() {
		final CSVWriter cw = new CSVWriter();
		return cw.asString((Object[]) getVariantsArray());
	}

	/**
	 * <p>Getter for the field <code>standardAbbreviation</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getStandardAbbreviation() {
		return standardAbbreviation;
	}

	/**
	 * <p>Getter for the field <code>primarySuffix</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPrimarySuffix() {
		return primarySuffix;
	}

}
