package org.javautil.commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation type to indicate a parameter is required by another parameter. The
 * argument property should be set to a string with the name of the property on
 * the same class that requires it.
 * 
 * example:
 * 
 * The property "schemaName" is required by the property "xsd"
 * 
 * code:
 * 
 * @Requires("schemaName") String xsd;
 * 
 * @author jjs
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Requires {
	public String property();
}
