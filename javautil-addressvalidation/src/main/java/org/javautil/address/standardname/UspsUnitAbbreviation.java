package org.javautil.address.standardname;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.javautil.properties.BooleanPropertyHelper;

/**
 * <p>UspsUnitAbbreviation class.</p>
 *
 * @author jjs
 * @version $Id: UspsUnitAbbreviation.java,v 1.2 2012/03/04 12:31:11 jjs Exp $
 */
public class UspsUnitAbbreviation {
	private final String standardName;
	private final String standardAbbreviation;
	private final boolean qualifierRequired;
	private final TreeSet<String> variants = new TreeSet<String>();

	/**
	 * <p>Constructor for UspsUnitAbbreviation.</p>
	 *
	 * @param standardName a {@link java.lang.String} object.
	 * @param uspsAbbreviation a {@link java.lang.String} object.
	 * @param requiresQualifier a boolean.
	 * @param variants an array of {@link java.lang.String} objects.
	 */
	public UspsUnitAbbreviation(final String standardName,
			final String uspsAbbreviation, final boolean requiresQualifier,
			final String[] variants) {
		if (standardName == null) {
			throw new IllegalArgumentException("standardName is null");
		}
		if (uspsAbbreviation == null) {
			throw new IllegalArgumentException("uspsAbbreviation is null");
		}

		this.standardName = standardName;
		this.standardAbbreviation = uspsAbbreviation;
		this.qualifierRequired = requiresQualifier;
		if (variants != null) {
			for (final String variant : variants) {
				if (variant == null) {
					throw new IllegalArgumentException("null variant");
				}
				this.variants.add(variant.toUpperCase());

			}
		}
	}

	/**
	 * The
	 *
	 * @param args a {@link java.util.List} object.
	 */
	public UspsUnitAbbreviation(final List<String> args) {
		final BooleanPropertyHelper boolHelper = new BooleanPropertyHelper();
		if (args.size() < 3) {
			throw new IllegalArgumentException(
					"insufficient number of arguments");
		}
		standardName = args.get(0);
		standardAbbreviation = args.get(1);
		qualifierRequired = boolHelper.parse(args.get(2));
		variants.addAll(args.subList(4, args.size() - 1));

	}

	/**
	 * <p>Getter for the field <code>variants</code>.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<String> getVariants() {
		return variants;
	}

	/**
	 * <p>Getter for the field <code>standardName</code>.</p>
	 *
	 * @return the standardName
	 */
	public String getStandardName() {
		return standardName;
	}

	/**
	 * <p>Getter for the field <code>standardAbbreviation</code>.</p>
	 *
	 * @return the uspsAbbreviation
	 */
	public String getStandardAbbreviation() {
		return standardAbbreviation;
	}

	/**
	 * <p>isQualifierRequired.</p>
	 *
	 * @return the requiresQualifier
	 */
	public boolean isQualifierRequired() {
		return qualifierRequired;
	}

}
