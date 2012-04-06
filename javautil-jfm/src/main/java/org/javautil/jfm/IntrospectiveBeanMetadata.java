package org.javautil.jfm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Encapsulates a java.lang.Class for use with freemarker templates.
 * 
 * @author bcm
 */
public class IntrospectiveBeanMetadata<T> extends AbstractBeanMetadata {

	private Class<T> javaClass;

	private final Log logger = LogFactory.getLog(getClass());

	public IntrospectiveBeanMetadata() {
	}

	public IntrospectiveBeanMetadata(final Class<T> javaClass) {
		this.javaClass = javaClass;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (javaClass == null) {
			throw new IllegalStateException("clazz is null");
		}
		if (getPropertyMetadataFactory() == null) {
			final PropertyMetadataFactory<IntrospectivePropertyMetadata> factory = new IntrospectivePropertyMetadataFactory(
					javaClass);
			logger.debug("setting propertyMetadataFactory to " + factory);
			setPropertyMetadataFactory(factory);
		}
		super.afterPropertiesSet();
	}

	public Class<T> getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(final Class<T> javaClass) {
		this.javaClass = javaClass;
	}

	@Override
	public String getName() {
		return javaClass.getName();
	}

	public String getSimpleName() {
		return javaClass.getSimpleName();
	}

	public String getPackageName() {
		return javaClass.getPackage() == null ? null : javaClass.getPackage()
				.getName();
	}

}
