package org.javautil.spring;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;

public class PropertyValuesHelper {
	private final Map<String, String> nameValues;

	private MutablePropertyValues propertyValues;
	private final Logger logger = Logger.getLogger(this.getClass());

	public PropertyValuesHelper(Map<String, String> nameValues) {
		if (nameValues == null) {
			throw new IllegalArgumentException("nameValues is null");
		}
		this.nameValues = nameValues;
	}

	public MutablePropertyValues getPropertyValues() {
		propertyValues = new MutablePropertyValues();
		for (Entry<String, String> entry : nameValues.entrySet()) {
			PropertyValue pv = new PropertyValue(entry.getKey(),
					entry.getValue());
			if (logger.isDebugEnabled()) {
				logger.debug(toString());
			}
			propertyValues.addPropertyValue(pv);
		}
		return propertyValues;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("nameValues\n");
		sb.append(nameValues);
		sb.append("propertyValues\n");
		sb.append(propertyValues);
		return sb.toString();
	}
}
