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
 *         TODO jjs more evilness has been incorporated
 * 
 */
public class IntrospectionHelper {

	// TODO jjs
	// AAAAAAAAAAAAAARRRRRRRRRRRRRRRGGGGGGGGGGGGGGGGGHHHHHHHHHHHHHHHHHHHHHHHHHhhhh!!!!!!!!!!!!!!!!!1

	private final Log logger = LogFactory.getLog(this.getClass());

	private String propertyName = null;

	private Class<? extends Object> propertyType = null;

	// TODO jjs - really? carrying state way too far
	private Method getterMethod = null;

	private Method setterMethod = null;

	private Object introspectionBean = null;

	private Class<? extends Object> introspectionClass = null;

	private boolean invokeNonPublicMethods = false;

	public IntrospectionHelper() {
	}

	public IntrospectionHelper(final Object introspectionBean) {
		this(introspectionBean, null, null);
	}

	public IntrospectionHelper(final Object introspectionBean,
			final String propertyName,
			final Class<? extends Object> propertyType) {
		super();
		setIntrospectionBean(introspectionBean);
		if (introspectionBean != null) {
			setIntrospectionClass(introspectionBean.getClass());
		}
		setPropertyName(propertyName);
		setPropertyType(propertyType);
	}

	public IntrospectionHelper(final Object introspectionBean,
			final String propertyName) {
		this(introspectionBean, propertyName, null);
	}

	public IntrospectionHelper(
			final Class<? extends Object> introspectionClass,
			final String propertyName,
			final Class<? extends Object> propertyType) {
		super();
		if (introspectionBean != null) {
			setIntrospectionClass(introspectionBean.getClass());
		}
		setPropertyName(propertyName);
		setPropertyType(propertyType);
	}

	// TODO what is this
	// public IntrospectionHelper(Class<? extends Object> introspectionClass,
	// String propertyName) {
	// this(introspectionClass, propertyName, null);
	// }

	public Object invokeGetter() {
		preparePropertyAccess(true, false);
		try {
			final Object returnValue = getterMethod.invoke(
					getIntrospectionBean(), (Object[]) null);
			return returnValue;
		} catch (final Exception e) {
			throw new IllegalStateException(
					"Error invoking property getter method", e);
		}
	}

	public boolean hasGetter() {
		preparePropertyAccess(false, false);
		return getterMethod != null;
	}

	public boolean hasSetter() {
		preparePropertyAccess(false, false);
		return setterMethod != null;
	}

	public void invokeSetter(final String value) {
		preparePropertyAccess(false, true);
		if (logger.isDebugEnabled()) {
			logger.debug("assigning type '"
					+ propertyType.getName()
					+ "' using method "
					+ setterMethod.getName()
					+ " from value: '"
					+ (value == null ? "null" : value.toString() + "', '"
							+ value.getClass().getName()) + "'");
		}
		try {
			if (value == null) {
				if (!propertyType.isPrimitive()) {
					setterMethod.invoke(getIntrospectionBean(),
							new Object[] { value });
				} else {
					throw new IllegalStateException(
							"Cannot assign primitive type '"
									+ propertyType.getName()
									+ "\' a value of null");
				}
			} else if (String.class.isAssignableFrom(propertyType)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { value });
			} else if (Boolean.class.isAssignableFrom(propertyType)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { Boolean.parseBoolean(value) });
			} else if (Integer.class.isAssignableFrom(propertyType)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { Integer.parseInt(value) });
			} else if (Long.class.isAssignableFrom(propertyType)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { Long.parseLong(value) });
			} else {
				throw new IllegalStateException(
						"No implementation for parsing values type: '"
								+ propertyType.getName() + "'");
			}
		} catch (final Exception e) {
			throw new IllegalStateException("Error assigning type '"
					+ propertyType.getName() + "\' from value of type: '"
					+ (value == null ? "null" : value.getClass().getName())
					+ "'", e);
		}
	}

	public void invokeSetter(final Object value) {
		preparePropertyAccess(false, true);
		try {
			if (value == null
					|| propertyType.isAssignableFrom(value.getClass())) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { value });
			} else if (propertyType.isAssignableFrom(Boolean.TYPE)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { ((Boolean) value).booleanValue() });
			} else if (propertyType.isAssignableFrom(Long.TYPE)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { ((Long) value).longValue() });
			} else if (propertyType.isAssignableFrom(Integer.TYPE)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { ((Integer) value).intValue() });
			} else if (propertyType.isAssignableFrom(Float.TYPE)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { ((Float) value).floatValue() });
			} else if (propertyType.isAssignableFrom(Double.TYPE)) {
				setterMethod.invoke(getIntrospectionBean(),
						new Object[] { ((Double) value).doubleValue() });
			} else {
				throw new IllegalStateException(
						"No implementation for parsing values type: "
								+ propertyType.getName());
			}
		} catch (final Exception e) {
			throw new IllegalStateException("Error assigning type '"
					+ propertyType.getName() + "\' from value of type: "
					+ (value == null ? "null" : value.getClass().getName()), e);
		}
	}

	protected void preparePropertyAccess(
			final boolean throwExceptionForMissingGetter,
			final boolean throwExceptionForMissingSetter) {
		if (getterMethod == null || setterMethod == null) {
			Method[] methods = null;
			if (isInvokeNonPublicMethods()) {
				methods = getIntrospectionClass().getDeclaredMethods();
			} else {
				methods = getIntrospectionClass().getMethods();
			}
			final String capitalizedPropertyName = StringUtils
					.capitalize(propertyName);
			String getterMethodName = "get" + capitalizedPropertyName;
			if (this.introspectionClass != null && this.propertyName != null) {
				Field field = null;
				try {
					field = this.introspectionClass
							.getDeclaredField(this.propertyName);
				} catch (final Exception e) {
				}
				if (field != null) {
					this.propertyType = field.getType();
				}
			}
			if (this.propertyType != null
					&& this.propertyType.equals(Boolean.TYPE)) {
				getterMethodName = "is" + capitalizedPropertyName;
			}
			final String setterMethodName = "set" + capitalizedPropertyName;
			for (final Method method : methods) {
				if (isInvokeNonPublicMethods()) {
					method.setAccessible(true);
				}
				if (method.getName().equals(getterMethodName)
						&& method.getParameterTypes().length == 0) {
					getterMethod = method;
					getterMethod.setAccessible(true);
					if (propertyType == null) {
						propertyType = getterMethod.getReturnType();
					}
					break;
				}
			}
			for (final Method method : methods) {
				if (isInvokeNonPublicMethods()) {
					method.setAccessible(true);
				}
				if (propertyType != null) {
					if (method.getName().equals(setterMethodName)
							&& method.getParameterTypes().length == 1
							&& propertyType.isAssignableFrom(method
									.getParameterTypes()[0])) {
						setterMethod = method;
						setterMethod.setAccessible(true);
						break;
					}
				}
			}
			final String access = isInvokeNonPublicMethods() ? "" : "public ";
			if (throwExceptionForMissingGetter && getterMethod == null
					&& throwExceptionForMissingSetter && setterMethod == null) {
				throw new IllegalStateException("Property '" + propertyName
						+ "' does not have expected " + access
						+ "getter/setter" + " methods on the bean "
						+ getIntrospectionClass().getName());
			} else if (throwExceptionForMissingGetter && getterMethod == null) {
				throw new IllegalStateException("Property '" + propertyName
						+ "' does not have a " + access + "getter method '"
						+ getterMethodName + "()'" + " on the bean "
						+ getIntrospectionClass().getName());
			} else if (throwExceptionForMissingSetter && setterMethod == null) {
				throw new IllegalStateException("Property '" + propertyName
						+ "' does not have a " + access + "setter method '"
						+ setterMethodName + "(" + propertyType.getSimpleName()
						+ " " + propertyName + ")'" + " on the bean "
						+ getIntrospectionClass().getName());
			}
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(final String propertyName) {
		if (propertyName != this.propertyName) {
			this.propertyType = null;
			this.getterMethod = null;
			this.setterMethod = null;
		}
		this.propertyName = propertyName;
	}

	public Class<? extends Object> getPropertyType() {
		preparePropertyAccess(true, true);
		return propertyType;
	}

	public void setPropertyType(final Class<? extends Object> propertyType) {
		this.propertyType = propertyType;
	}

	public Object getIntrospectionBean() {
		return introspectionBean;
	}

	public void setIntrospectionBean(final Object introspectionBean) {
		this.introspectionBean = introspectionBean;
		if (introspectionBean != null) {
			setIntrospectionClass(introspectionBean.getClass());
		}
	}

	public Class<? extends Object> getIntrospectionClass() {
		return introspectionClass;
	}

	public void setIntrospectionClass(
			final Class<? extends Object> introspectionClass) {
		if ((this.introspectionClass == null && introspectionClass != null)
				|| (this.introspectionClass != null && introspectionClass == null)
				|| !this.introspectionClass.equals(introspectionClass)) {
			setterMethod = null;
			getterMethod = null;
			setPropertyType(null);
		}
		this.introspectionClass = introspectionClass;
	}

	public boolean isInvokeNonPublicMethods() {
		return invokeNonPublicMethods;
	}

	public void setInvokeNonPublicMethods(final boolean invokeNonPublicMethods) {
		this.invokeNonPublicMethods = invokeNonPublicMethods;
		// TODO WTF?
		setterMethod = null;
		getterMethod = null;
	}
}
