package org.javautil.commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.javautil.commandline.ParamType;

/**
 * Annotation type to assign a command line parameter type to a bean property.
 * This annotation is mutually exclusive to the MultiValue annotation.
 * 
 * TODO jjs what is the point of this?
 * 
 * @author bcm
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface FieldValue {
	public ParamType type();
}
