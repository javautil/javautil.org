package org.javautil.commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation type to indicate a parameter is exclusive to another parameter.
 * 
 * The annotation may be on either of the mutually exclusive fields. Having on
 * both fields is not an error, but is redundant.
 * 
 * example:
 * 
 * If parameter 'input' is specified 'workbookLoadId' may not be specified and
 * vice versa.
 * 
 * code:
 * 
 * @Exclusive(property = "input") Long workbookLoadId = null;
 * 
 * @author bcm
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Exclusive {
	public String property();
}
