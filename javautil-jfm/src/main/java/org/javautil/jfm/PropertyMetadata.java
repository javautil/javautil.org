package org.javautil.jfm;

/**
 * Provides information about a bean property. The source of the meta data is
 * not defined but should be derived from to a field on a java class.
 * 
 * @author bcm-javautil
 */
public interface PropertyMetadata {

	/**
	 * Returns the name of the property.
	 * 
	 * @return name
	 */
	public String getName();

	/**
	 * Returns the column heading for the property when it is displayed in a
	 * tabular format.
	 * 
	 * @return heading
	 */
	public String getHeading();

	/**
	 * Returns a field label to be displayed for the property when it is
	 * displayed in a form.
	 * 
	 * @return label
	 */
	public String getLabel();

	/**
	 * Returns the description for the property. Typically not displayed by
	 * default, but may be shown using tool tips or using additional help text.
	 * 
	 * @return description
	 */
	public String getDescription();

}
