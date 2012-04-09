package org.javautil.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class FieldHelper {

	private static final Logger logger = Logger.getLogger(FieldHelper.class);
	/**
	 * Always true, but maybe in the future I will support idiots.
	 */
	private final boolean ignoreCase = true;

	public void set(Object object, Map<String, String> nameValue) {
		Map<String, Field> fieldsByUpperName = getFields(object);

		for (String fieldName : nameValue.keySet()) {
			String value = nameValue.get(fieldName);
			if (logger.isDebugEnabled()) {
				logger.debug("the value for '" + fieldName + " is '" + value
						+ "'");
			}
			Field field = fieldsByUpperName.get(fieldName.toUpperCase());
			if (field == null) {
				throw new IllegalStateException("no such field '"
						+ fieldName.toUpperCase() + "'" + " in\n"
						+ getNameValueAsString(nameValue));
			}
			set(object, field, value);
		}
	}

	public String getNameValueAsString(Map<String, String> nameValue) {
		StringBuilder sb = new StringBuilder(128);
		for (Entry<String, String> entry : nameValue.entrySet()) {
			sb.append(entry);
			sb.append("\n");
		}
		return sb.toString();
	}

	private HashMap<String, Field> getFields(Object object) {
		if (object == null) {
			throw new IllegalArgumentException("object is null");
		}

		HashMap<String, Field> fieldsByUpperName = new HashMap<String, Field>();
		Field fields[] = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			String upperName = field.getName().toUpperCase();
			Field oldField = fieldsByUpperName.put(upperName, field);

			if (logger.isDebugEnabled()) {
				logger.debug("added field '" + upperName + "'");
			}
			if (ignoreCase) {
				if (oldField != null) {
					throw new IllegalArgumentException(
							"field names collide when case insensitive '"
									+ upperName + "' and '"
									+ oldField.getName());
				}
			}
		}

		return fieldsByUpperName;
	}

	public void set(Object object, String fieldName, String value) {
		if (object == null) {
			throw new IllegalArgumentException("object is null");
		}
		if (fieldName == null) {
			throw new IllegalArgumentException("fieldName is null");
		}
		if (value == null) {
			throw new IllegalArgumentException("value is null");
		}
		Method setter = getSetter(object, fieldName);
		if (setter != null) {
			getValueAsObject(object, fieldName, value);
		}
	}

	private void getValueAsObject(Object object, String fieldName, String value) {
		// TODO Auto-generated method stub

	}

	/**
	 * Searches in the object and all superclasses for a setter. The setter is
	 * assumed to be named "set" prepended to the fieldName with the first
	 * letter capitalized.
	 * 
	 * @param object
	 *            The object from which the setter should be found.
	 * @param fieldName
	 *            the name of the field for which the setter should be found.
	 * @return
	 */
	private Method getSetter(Object object, String fieldName) {
		if (object == null) {
			throw new IllegalArgumentException("object is null");
		}
		if (fieldName == null) {
			throw new IllegalArgumentException("fieldName is null");
		}
		if (fieldName.length() > 1) {
			fieldName.substring(1);
		}
		Field field;
		try {
			field = object.getClass().getDeclaredField(fieldName);
		} catch (SecurityException e) {
			throw new RuntimeException("security exception on '" + fieldName
					+ "' in class '" + object.getClass(), e);
		} catch (NoSuchFieldException e) {
			// TODO should look through super classes
			throw new RuntimeException("no field named '" + fieldName
					+ "' in class '" + object.getClass(), e);
		}
		Type type = field.getType();
		Method method = null;
		Object currentObject = object;
		while (method == null & currentObject != null) {
			try {
				method = object.getClass().getDeclaredMethod(fieldName,
						type.getClass());
			} catch (SecurityException e) {
				throw new RuntimeException(
						"unable to get setter for field named '" + fieldName
								+ "'");

			} catch (NoSuchMethodException e) {
				currentObject = object.getClass().getSuperclass();
			}
		}
		return method;
	}

	public void set(Object object, Field field, String value) {
		if (object == null) {
			throw new IllegalArgumentException("object is null");
		}
		if (field == null) {
			throw new IllegalArgumentException("field is null");
		}
		if (value == null) {
			throw new IllegalArgumentException("value is null");
		}
		Class<?> type = field.getType();
		logger.debug("processing type " + type);
		try {
			while (true) {
				if (type.isAssignableFrom(String.class)) {
					field.set(object, value);
					break;
				}
				if (type.isAssignableFrom(Long.class)) {
					field.set(object, new Long(value));
					break;
				}
				if (type.isAssignableFrom(Integer.class)) {
					field.set(object, new Integer(value));
					break;
				}

				if (type.isAssignableFrom(Short.class)) {
					field.set(object, new Short(value));
					break;
				}

				if (type.isAssignableFrom(Byte.class)) {
					field.set(object, new Byte(value));
					break;
				}
				if (type.isAssignableFrom(Double.class)) {
					field.set(object, new Double(value));
					break;
				}
				if (type.isAssignableFrom(Float.class)) {
					field.set(object, new Float(value));

					break;
				}
				// TODO support Date with Mask
				throw new IllegalArgumentException("unsupported type "
						+ field.getType() + " while processing "
						+ field.getName() + " with value '" + value + "'");
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Unable to populate " + object
					+ " Field: " + field + " with value: " + value, e);
		}

		catch (IllegalAccessException e) {

			throw new RuntimeException("Unable to populate " + object
					+ " Field: " + field + " with value: " + value, e);
		}

	}
}
