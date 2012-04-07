package org.javautil.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author jjs
 * 
 */
public class FieldCacheEntry {

	private final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * The name of the inspected field on the bean.
	 */
	private final String fieldName = null;

	/**
	 * The class of the inspected field on the bean.
	 */
	private Class<? extends Object> fieldClass = null;

	/**
	 * The method used to "get" the value of the field.
	 */
	private Method getterMethod = null;

	/**
	 * The method used to "set" the value of the field.
	 */
	private ArrayList<Method> setterMethods = null;

	// /**
	// * The class that will be used to introspect the bean.
	// */
	// private Class<? extends Object> introspectionClass = null;

	/**
	 * When true, an exception will be thrown when a getter is missing.
	 */
	private boolean requireGetter = false;

	/**
	 * When true, an exception will be thrown when a setter is missing.
	 */
	private boolean requireSetter = false;

	private final Field field;

	private final ClassCacheEntry classInfo;

	/**
	 * 
	 * @param containingClass
	 *            the class that contains the Field
	 * @param field
	 */
	public FieldCacheEntry(ClassCacheEntry classInfo, final Field field) {
		if (classInfo == null) {
			throw new IllegalArgumentException("containingClass is null");
		}
		if (field == null) {
			throw new IllegalArgumentException("field is null");
		}
		this.field = field;
		this.classInfo = classInfo;
		preparePropertyAccess();
	}

	// /**
	// * Invokes the getter method on the bean
	// *
	// * @return the result of the getter method invocation
	// */
	// public Object invokeGetter() {
	// preparePropertyAccess();
	// try {
	// return getterMethod.invoke(getIntrospectionBean(), (Object[]) null);
	// } catch (final Exception e) {
	// throw new IllegalStateException(
	// "Error invoking property gettor method", e);
	// }
	// }

	/**
	 * True when the getter method exists.
	 * 
	 * @return
	 */
	public boolean hasGetter() {
		preparePropertyAccess();
		return getterMethod != null;
	}

	/**
	 * True when the setter method exists.
	 * 
	 * @return
	 */
	public boolean hasSetter() {
		return setterMethods != null;
	}

	/**
	 * Set the value of the bean's field, invoking the setter method. TODO move
	 * to FieldCacheEntryHelper
	 * 
	 * @param value
	 */
	// public void invokeSetter(final String value) {
	// preparePropertyAccess();
	// if (logger.isDebugEnabled()) {
	// logger.debug("assigning type '"
	// + fieldClass.getName()
	// + "' using method "
	// + setterMethod.getName()
	// + " from value: '"
	// + (value == null ? "null" : value.toString() + "', '"
	// + value.getClass().getName()) + "'");
	// }
	// try {
	// if (value == null) {
	// if (!fieldClass.isPrimitive()) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { value });
	// } else {
	// throw new IllegalArgumentException(
	// "Cannot assign primitive type '"
	// + fieldClass.getName()
	// + "\' a value of null");
	// }
	// } else if (String.class.isAssignableFrom(fieldClass)) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { value });
	// } else if (Boolean.class.isAssignableFrom(fieldClass)) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { Boolean.parseBoolean(value) });
	// } else if (Integer.class.isAssignableFrom(fieldClass)) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { Integer.parseInt(value) });
	// } else if (Long.class.isAssignableFrom(fieldClass)) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { Long.parseLong(value) });
	// } else {
	// throw new IllegalStateException(
	// "No implementation for parsing values type: '"
	// + fieldClass.getName() + "'");
	// }
	// } catch (final Exception e) {
	// throw new IllegalArgumentException("Error assigning type '"
	// + fieldClass.getName() + "\' from value of type: '"
	// + (value == null ? "null" : value.getClass().getName())
	// + "'", e);
	// }
	// }

	// public void invokeSetter(final Object value) {
	// preparePropertyAccess();
	// try {
	// if (value == null || fieldClass.isAssignableFrom(value.getClass())) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { value });
	// } else if (fieldClass.isAssignableFrom(Boolean.TYPE)) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { ((Boolean) value).booleanValue() });
	// } else if (fieldClass.isAssignableFrom(Long.TYPE)) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { ((Long) value).longValue() });
	// } else if (fieldClass.isAssignableFrom(Integer.TYPE)) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { ((Integer) value).intValue() });
	// } else if (fieldClass.isAssignableFrom(Float.TYPE)) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { ((Float) value).floatValue() });
	// } else if (fieldClass.isAssignableFrom(Double.TYPE)) {
	// setterMethod.invoke(getIntrospectionBean(),
	// new Object[] { ((Double) value).doubleValue() });
	// } else {
	// throw new IllegalStateException(
	// "No implementation for parsing values type: "
	// + fieldClass.getName());
	// }
	// } catch (final Exception e) {
	// throw new IllegalStateException("Error assigning type '"
	// + fieldClass.getName() + "\' from value of type: "
	// + (value == null ? "null" : value.getClass().getName()), e);
	// }
	// }

	/**
	 * Resolve the getter and setter methods for the field.
	 * 
	 * Uses standard Bean conventions the setter is "set" + ObjectName
	 * 
	 * TODO jjs "is" and "get" are acceptable variants for boolean
	 * 
	 * 
	 * The getter is "get" + ObjectName (first letter uppercase) and "is" +
	 * ObjectName (first letter uppsercase) for booleans
	 * 
	 * Complains if there is not both a setter and a getter, but this might be
	 * an immutable bean with only getters.
	 * 
	 * @param throwException
	 */
	protected void preparePropertyAccess() {
		// TODO pass ClassCacheEntry so this isn't done every time
		Map<String, ArrayList<Method>> methods = classInfo
				.getAccessorMethodsByName();
		final String capitalizedPropertyName = StringUtils
				.capitalize(fieldName);
		String getterMethodName = "get" + capitalizedPropertyName;
		this.fieldClass = field.getType();
		if (this.fieldClass.equals(Boolean.TYPE)) {
			getterMethodName = "is" + capitalizedPropertyName;
		}
		List<Method> getterMethods = methods.get(getterMethodName);
		if (getterMethods != null) {
			getterMethod = methods.get(getterMethodName).get(0);
			getterMethod.setAccessible(true);
			fieldClass = getterMethod.getReturnType();
		}
		String setterMethodName = "set" + capitalizedPropertyName;
		setterMethods = methods.get(setterMethodName);
		if (setterMethods != null) {
			for (Method method : setterMethods) {
				method.setAccessible(true);
			}
		}
		//

		final boolean getterFailure = requireGetter && getterMethod == null;
		final boolean setterFailure = requireSetter && setterMethods == null;
		if (getterFailure && setterFailure) {
			throw new IllegalArgumentException("Property '" + fieldName
					+ "' does not have expected"
					+ " getter and setter methods on the bean "
					+ classInfo.getClazz().getName());
		} else if (getterFailure) {
			throw new IllegalArgumentException("Property '" + fieldName
					+ "' does not have a getter method" + " '"
					+ getterMethodName + "()'" + " on the bean "
					+ classInfo.getClazz().getName());
		} else if (setterFailure) {
			throw new IllegalArgumentException("Property '" + fieldName
					+ "' does not have a setter method" + " '"
					+ setterMethodName + "(" + fieldClass.getSimpleName() + " "
					+ fieldName + ")'" + " on the bean "
					+ classInfo.getClazz().getName());
		}
	}

	public String getPropertyName() {
		return fieldName;
	}

	public Class<? extends Object> getPropertyType() {
		preparePropertyAccess();
		return fieldClass;
	}

	public void setPropertyType(final Class<? extends Object> propertyType) {
		this.fieldClass = propertyType;
	}

	public boolean isRequireGetter() {
		return requireGetter;
	}

	public void setRequireGetter(final boolean requireGetter) {
		this.requireGetter = requireGetter;
	}

	public boolean isRequireSetter() {
		return requireSetter;
	}

	public void setRequireSetter(final boolean requireSetter) {
		this.requireSetter = requireSetter;
	}

	public Log getLogger() {
		return logger;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Class<? extends Object> getFieldClass() {
		return fieldClass;
	}

	public Method getGetterMethod() {
		return getterMethod;
	}

	public ArrayList<Method> getSetterMethods() {
		return setterMethods;
	}
}
