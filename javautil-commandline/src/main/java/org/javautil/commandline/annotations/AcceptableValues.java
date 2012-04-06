package org.javautil.commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation type to indicate a list of values that may be assumed by a String,
 * usually used as a parameter to the command line.
 * 
 * To support a list of strings as an argument. TODO write tests
 * 
 * @author jjs@javautil.org
 * 
 *         example
 * 
 * 
 @Required
 * @AcceptableValues(values = {"a", "b"}) private String text;
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface AcceptableValues {
	public String[] values();

	// String[] getValues() {
	// return value;
	// }
}
