package org.javautil.commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.javautil.commandline.ParamType;

/**
 * Annotation type to indicate a parameter is accepted multiple times. This
 * annotation is mutually exclusive to the FieldValue annotation.
 * 
 * TODO type must be specified what are the allowable values?
 * 
 * example:
 * 
 * The property "downloadUrls" is to be multiple urls to download
 * 
 * code:
 * 
 * MultiValue(type=ParamType.STRING) String downloadUrls;
 * 
 * @author bcm
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface MultiValue {
	ParamType type();
}
