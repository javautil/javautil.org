package org.javautil.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Can be extended by classes designed for working with models.
 * 
 * @author bcm
 */
public class ModelHelper {

	private static Map<String, Object> EMPTY_MODEL = Collections
			.unmodifiableMap(new HashMap<String, Object>());

	/**
	 * Convenience method for creating a NameAndValue.
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	protected NameAndValue<Integer> asNameAndValue(String name, Integer value) {
		return new NameValueMap<Integer>(name, value);
	}

	/**
	 * Convenience method for creating a NameAndValue.
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	protected NameAndValue<String> asNameAndValue(String name, String value) {
		return new NameValueMap<String>(name, value);
	}

	/**
	 * Convenience method for getting names from a NameAndValueList.
	 * 
	 * @param <T>
	 * @param list
	 * @return List<String>
	 */
	protected <T> List<String> getNamesFromList(List<NameAndValue<T>> list) {
		List<String> names = new ArrayList<String>();
		for (NameAndValue<T> nameAndValue : list) {
			names.add(nameAndValue.getName());
		}
		return names;
	}

	/**
	 * Convenience method for getting values from a NameAndValueList.
	 * 
	 * @param <T>
	 * @param list
	 * @return List<T>
	 */
	protected <T> List<T> getValuesFromList(List<NameAndValue<T>> list) {
		List<T> values = new ArrayList<T>();
		for (NameAndValue<T> nameAndValue : list) {
			values.add(nameAndValue.getValue());
		}
		return values;
	}

	/**
	 * Represents a single key and value as a map for convenience use as a
	 * model.
	 * 
	 * @param key
	 * @param value
	 * @return model map
	 */
	protected Map<String, Object> asModel(String attributeName,
			Object attributeValue) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(attributeName, attributeValue);
		return model;
	}

	/**
	 * An empty model, used often, so a convenience methods is available here.
	 * 
	 * @return model map
	 */
	protected Map<String, Object> getEmptyModel() {
		return EMPTY_MODEL;
	}

}
