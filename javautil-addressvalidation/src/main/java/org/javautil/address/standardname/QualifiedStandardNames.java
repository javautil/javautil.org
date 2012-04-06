package org.javautil.address.standardname;

/**
 * <p>QualifiedStandardNames interface.</p>
 *
 * @author jjs
 * @version $Id: QualifiedStandardNames.java,v 1.2 2012/03/04 12:31:11 jjs Exp $
 */
public interface QualifiedStandardNames extends StandardNames {
	/**
	 * <p>requiresQualifier.</p>
	 *
	 * @param name
	 *            Any variant, the standard name or the standard abbreviation
	 * @return a boolean.
	 */
	public boolean requiresQualifier(String name);
}
