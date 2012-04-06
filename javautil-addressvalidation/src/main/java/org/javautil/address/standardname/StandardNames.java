package org.javautil.address.standardname;

import java.util.Collection;

/**
 * <p>StandardNames interface.</p>
 *
 * @author jjs
 * @version $Id: StandardNames.java,v 1.3 2012/03/04 12:31:11 jjs Exp $
 */
public interface StandardNames {

	/**
	 * <p>getVariants.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return Variants of a given name. TODO provide examples and where
	 *         resources are stored.
	 */
	public Collection<String> getVariants(String name);

	/**
	 * <p>getStandardName.</p>
	 *
	 * @param variant a {@link java.lang.String} object.
	 * @return String the standard name for the variant if known else null
	 */
	public String getStandardName(String variant);

	/**
	 * If a variant is known return the associated standard abbreviation
	 *
	 * @param variant a {@link java.lang.String} object.
	 * @return String the standard abbreviation for the variant.
	 */
	public String getStandardAbbreviation(String variant);

}
