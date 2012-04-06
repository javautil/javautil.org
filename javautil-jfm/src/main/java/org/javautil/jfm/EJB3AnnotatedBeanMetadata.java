package org.javautil.jfm;

import javax.persistence.Entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An implementation of BeanMetadata that derives its metadata properties from
 * EJB3 annotations.
 * 
 * @author bcm-javautil
 */
public class EJB3AnnotatedBeanMetadata extends AbstractBeanMetadata {

	private final Log logger = LogFactory.getLog(getClass());

	private Class<? extends Object> javaClass;

	protected Entity findEntityAnnotation() {
		Entity entity = javaClass.getAnnotation(Entity.class);
		final Class<? extends Object> javaClass = getJavaClass();
		while (entity == null && javaClass.getSuperclass() != null) {
			entity = javaClass.getAnnotation(Entity.class);
		}
		return entity;
	}

	public Class<? extends Object> getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(final Class<? extends Object> javaClass) {
		this.javaClass = javaClass;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// // TODO: write these missing classes?
		// if (getPropertyMetadataFactory() == null) {
		// PropertyMetadataFactory<EJB3PropertyMetadata> factory = new
		// EJB3AnnotatedPropertyMetadataFactory(
		// javaClass);
		// logger.debug("setting propertyMetadataFactory to " + factory);
		// setPropertyMetadataFactory(factory);
		// }
		// super.afterPropertiesSet();
		final Entity entityAnnotation = findEntityAnnotation();
		setName(entityAnnotation.name());
	}

}
