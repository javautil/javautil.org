package org.javautil.reflect;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javautil.spring.PropertyValuesHelper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;

public class SpringFieldHelper {

	private static final Logger logger = Logger.getLogger(FieldHelper.class);

	public List<FieldError> set(Object object, Map<String, String> nameValue) {
		PropertyValuesHelper propertyValues = new PropertyValuesHelper(
				nameValue);
		MutablePropertyValues values = propertyValues.getPropertyValues();
		DataBinder binder = new DataBinder(object);
		binder.bind(values);
		if (logger.isDebugEnabled()) {
			logger.debug("\nnameValue\n" + nameValue + "\nObject\n" + object);
		}
		List<FieldError> fieldErrors = null;
		try {
			binder.close();
		} catch (BindException bindException) {
			fieldErrors = bindException.getAllErrors();
			if (logger.isDebugEnabled()) {
				for (FieldError error : fieldErrors) {

					logger.error(error);
					logger.error("cannot bind " + error.getRejectedValue()
							+ " to the field " + error.getField());
				}
			}
		}
		return fieldErrors;
	}

}
