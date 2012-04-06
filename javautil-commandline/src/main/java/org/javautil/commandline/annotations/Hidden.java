package org.javautil.commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation type to indicate that an argument is hidden; that is, not
 * displayed on help messages.
 * 
 * TODO this is not implemented in the CommandLineHandler
 * 
 * @author jjs
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Hidden {
}
