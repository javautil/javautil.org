package org.javautil.jfm.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation type to indicate that a field is to be added to the JFM model
 * 
 * @author bcm-javautil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface JFMModelProperty {

	/**
	 * The name of the jfm model path that will be used to access this object
	 * 
	 * @return name
	 */
	public String name();
}
