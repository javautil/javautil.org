package org.javautil.jfm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation of PropertyMetadataFactory that uses reflection to inspect
 * fields.
 * 
 * @author bcm-javautil
 */
public class IntrospectivePropertyMetadataFactory implements
		PropertyMetadataFactory<IntrospectivePropertyMetadata> {

	private final Log logger = LogFactory.getLog(getClass());

	private Class<? extends Object> javaClass;

	private List<IntrospectivePropertyMetadata> properties;

	private Map<String, IntrospectivePropertyMetadata> propertiesByName;

	public IntrospectivePropertyMetadataFactory() {
	}

	public IntrospectivePropertyMetadataFactory(
			final Class<? extends Object> javaClass) {
		this.javaClass = javaClass;
	}

	@Override
	public IntrospectivePropertyMetadata getProperty(final String propertyName) {
		return propertiesByName.get(propertyName);
	}

	@Override
	public List<IntrospectivePropertyMetadata> getProperties() {
		return properties;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() {
		if (javaClass == null) {
			throw new IllegalStateException("javaClass is null");
		}
		properties = new ArrayList<IntrospectivePropertyMetadata>();
		propertiesByName = new LinkedHashMap<String, IntrospectivePropertyMetadata>();
		try {
			final Set<Class> processed = new HashSet<Class>();
			final Stack<Class> stack = new Stack<Class>();
			stack.add(javaClass);
			while (stack.size() > 0) {
				final Class clazz = stack.pop();
				if (clazz.getInterfaces() != null) {
					for (final Class otherClazz : clazz.getInterfaces()) {
						if (!processed.contains(clazz)) {
							stack.add(otherClazz);
						}
					}
				}
				if (clazz.getSuperclass() != null) {
					if (!processed.contains(clazz)) {
						stack.add(clazz.getSuperclass());
					}
				}
				for (final IntrospectivePropertyMetadata property : loadPropertiesFromClass(clazz)) {
					properties.add(property);
					propertiesByName.put(property.getName(), property);
				}
				processed.add(clazz);
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private Set<IntrospectivePropertyMetadata> loadPropertiesFromClass(
			final Class javaClass) throws Exception {
		final Field[] fields = javaClass.getDeclaredFields();
		final HashSet<IntrospectivePropertyMetadata> properties = new LinkedHashSet<IntrospectivePropertyMetadata>();
		for (final Field field : fields) {
			final Class<? extends Object> clazz = field.getDeclaringClass();
			if (logger.isDebugEnabled()) {
				logger.debug("declaring class is " + clazz.getName()
						+ " for field " + field.getName());
			}
			if (!ClassUtils.isJavaStandardEditionOrExtensionClass(clazz)) {
				final IntrospectivePropertyMetadata pojoProperty = new IntrospectivePropertyMetadata(
						field);
				pojoProperty.afterPropertiesSet(); // initializing bean
				if (logger.isDebugEnabled()) {
					logger.debug("adding property " + field.getName());
				}
				properties.add(pojoProperty);
			}
		}
		return properties;
	}

}
