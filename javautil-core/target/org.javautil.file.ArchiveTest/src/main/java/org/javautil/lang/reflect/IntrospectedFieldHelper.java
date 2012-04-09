package org.javautil.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author bcm
 * 
 */
public class IntrospectedFieldHelper {

	private final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * The name of the inspected field on the bean.
	 */
	private String fieldName = null;

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
	private Method setterMethod = null;

	/**
	 * The bean that will be inspected for fields.
	 */
	private Object introspectedBean = null;

	/**
	 * The class that will be used to introspect the bean.
	 */
	private Class<? extends Object> introspectionClass = null;

	/**
	 * When true, an exception will be thrown when a getter is missing.
	 */
	private boolean requireGetter = false;

	/**
	 * When true, an exception will be thrown when a setter is missing.
	 */
	private boolean requireSetter = false;

	/**
	 * Creates an InstrospectedFieldHelper for a given object, requiring a
	 * property to exist having the given name and type.
	 * 
	 * @param introspectedObject
	 * @param propertyName
	 * @param propertyType
	 */
	public IntrospectedFieldHelper(final Object introspectedObject,
			final String propertyName,
			final Class<? extends Object> propertyType) {
		setIntrospectedObject(introspectedObject);
		if (introspectedObject != null) {
			setIntrospectionClass(introspectedObject.getClass());
		}
		setPropertyName(propertyName);
		setPropertyType(propertyType);
	}

	/**
	 * Creates an InstrospectedFieldHelper for a given object, requiring a
	 * property to exist having the given name.
	 * 
	 * @param introspectedObject
	 * @param propertyName
	 */
	public IntrospectedFieldHelper(final Object introspectedObject,
			final String propertyName) {
		this(introspectedObject, propertyName, null);
	}

	/**
	 * Invokes the getter method on the bean
	 * 
	 * @return the result of the getter method invocation
	 */
	public Object invokeGetter() {
		preparePropertyAccess();
		try {
			return getterMethod.invoke(getIntrospectionBean(), (Object[]) null);
		} catch (final Exception e) {
			throw new IllegalStateException(
					"Error invoking property gettor method", e);
		}
	}

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
		preparePropertyAccess();
		return setterMethod != null;
	}

	/**
	 * Set the value of the bean's field, invoking the setter method.
	 * 
	 * @param value
	 */
	public void invokeSetter(final String value) {
		preparePropertyAccess();
		if (logger.isDebugEnabled()) {
			logger.debug("assigning type '"
					+ fieldClass.getName()
					+ "' using method "
					+ setterMethod.getName()
					+ " from value: '"
					+ (value == null ? "null" : value.toString() + "', '"
							+ value.getClass().getName()) + "'");
		}
		try {
			if (value == null) {
				if (!fieldClass.isPrimitive()) {
					setterMethod.invoke(getIntrospectionBean(),
							new Object[] { value });
				} else {
					throw new IllegalArgumentException(
							"Cannot assign primitive type '"
									+ fieldClass.getName()
									+ "\' a value of null");
				}
			} else if (String.class.isAssignableFrom(fieldClass)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { value });
			} else if (Boolean.class.isAssignableFrom(fieldClass)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { Boolean.parseBoolean(value) });
			} else if (Integer.class.isAssignableFrom(fieldClass)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { Integer.parseInt(value) });
			} else if (Long.class.isAssignableFrom(fieldClass)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { Long.parseLong(value) });
			} else {
				throw new IllegalStateException(
						"No implementation for parsing values type: '"
								+ fieldClass.getName() + "'");
			}
		} catch (final Exception e) {
			throw new IllegalArgumentException("Error assigning type '"
					+ fieldClass.getName() + "\' from value of type: '"
					+ (value == null ? "null" : value.getClass().getName())
					+ "'", e);
		}
	}

	public void invokeSetter(final Object value) {
		preparePropertyAccess();
		try {
			if (value == null || fieldClass.isAssignableFrom(value.getClass())) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { value });
			} else if (fieldClass.isAssignableFrom(Boolean.TYPE)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { ((Boolean) value).booleanValue() });
			} else if (fieldClass.isAssignableFrom(Long.TYPE)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { ((Long) value).longValue() });
			} else if (fieldClass.isAssignableFrom(Integer.TYPE)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { ((Integer) value).intValue() });
			} else if (fieldClass.isAssignableFrom(Float.TYPE)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { ((Float) value).floatValue() });
			} else if (fieldClass.isAssignableFrom(Double.TYPE)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { ((Double) value).doubleValue() });
			} else {
				throw new IllegalStateException(
						"No implementation for parsing values type: "
								+ fieldClass.getName());
			}
		} catch (final Exception e) {
			throw new IllegalStateException("Error assigning type '"
					+ fieldClass.getName() + "\' from value of type: "
					+ (value == null ? "null" : value.getClass().getName()), e);
		}
	}

	/**
	 * Resolve the getter and setter methods for the field.
	 * 
	 * Uses standard Bean conventions the setter is "set" + ObjectName
	 * 
	 * todo jjs "is" and "get" are acceptable variants for boolean
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
		if (getterMethod == null || setterMethod == null) {
			final Method[] methods = getIntrospectionClass().getMethods();
			final String capitalizedPropertyName = StringUtils
					.capitalize(fieldName);
			String getterMethodName = "get" + capitalizedPropertyName;
			if (this.introspectionClass != null && this.fieldName != null) {
				Field field = null;
				try {
					field = this.introspectionClass
							.getDeclaredField(this.fieldName);
				} catch (final Exception e) {
				}
				if (field != null) {
					this.fieldClass = field.getType();
				}
			}
			if (this.fieldClass != null && this.fieldClass.equals(Boolean.TYPE)) {
				getterMethodName = "is" + capitalizedPropertyName;
			}
			for (final Method method : methods) {
				if (method.getName().equals(getterMethodName)
						&& method.getParameterTypes().length == 0) {
					getterMethod = method;
					getterMethod.setAccessible(true);
					if (fieldClass == null) {
						fieldClass = getterMethod.getReturnType();
					}
					break;
				}
			}
			final String setterMethodName = "set" + capitalizedPropertyName;
			for (final Method method : methods) {
				if (fieldClass != null) {
					if (method.getName().equals(setterMethodName)
							&& method.getParameterTypes().length == 1
							&& fieldClass.isAssignableFrom(method
									.getParameterTypes()[0])) {
						setterMethod = method;
						setterMethod.setAccessible(true);
						break;
					}
				}
			}
			final boolean getterFailure = requireGetter && getterMethod == null;
			final boolean setterFailure = requireSetter && setterMethod == null;
			if (getterFailure && setterFailure) {
				throw new IllegalArgumentException("Property '" + fieldName
						+ "' does not have expected"
						+ " getter and setter methods on the bean "
						+ getIntrospectionClass().getName());
			} else if (getterFailure) {
				throw new IllegalArgumentException("Property '" + fieldName
						+ "' does not have a getter method" + " '"
						+ getterMethodName + "()'" + " on the bean "
						+ getIntrospectionClass().getName());
			} else if (setterFailure) {
				throw new IllegalArgumentException("Property '" + fieldName
						+ "' does not have a setter method" + " '"
						+ setterMethodName + "(" + fieldClass.getSimpleName()
						+ " " + fieldName + ")'" + " on the bean "
						+ getIntrospectionClass().getName());
			}
		}
	}

	public String getPropertyName() {
		return fieldName;
	}

	// todo jjs
	private void setPropertyName(final String propertyName) {
		if (propertyName != this.fieldName) {
			this.fieldClass = null;
		}
		this.fieldName = propertyName;
	}

	public Class<? extends Object> getPropertyType() {
		preparePropertyAccess();
		return fieldClass;
	}

	public void setPropertyType(final Class<? extends Object> propertyType) {
		this.fieldClass = propertyType;
	}

	public Object getIntrospectionBean() {
		return introspectedBean;
	}

	// todo jjs why in the world would we let somebody do this outside of of the
	// constructor?
	// todo and this would class with the behaviour of
	public void setIntrospectedObject(final Object introspectionBean) {
		this.introspectedBean = introspectionBean;
		setIntrospectionClass(introspectionBean.getClass());
	}

	public Class<? extends Object> getIntrospectionClass() {
		return introspectionClass;
	}

	public void setIntrospectionClass(
			final Class<? extends Object> introspectionClass) {
		if (this.introspectionClass != introspectionClass) {
			setterMethod = null;
			getterMethod = null;
			setPropertyType(null);
		}
		this.introspectionClass = introspectionClass;
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
}
