package org.javautil.address.parser;

/**
 * <p>StreetComponents interface.</p>
 *
 * @author jjs
 * @version $Id: StreetComponents.java,v 1.2 2012/03/04 12:31:12 jjs Exp $
 */
public interface StreetComponents {

	//
	// results accessors
	//
	/**
	 * <p>getStreetNumber.</p>
	 *
	 * @return the streetNumber
	 */
	public abstract String getStreetNumber();

	/**
	 * <p>getStreetName.</p>
	 *
	 * @return the streetName
	 */
	public abstract String getStreetName();

	/**
	 * <p>getStreetType.</p>
	 *
	 * @return the streetType
	 */
	public abstract String getStreetType();

}
