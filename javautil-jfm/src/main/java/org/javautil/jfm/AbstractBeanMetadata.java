package org.javautil.jfm;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

/**
 * An abstract implementation of BeanMetadata. Implementors should call the set
 * name and propertyMetadataFactory properties prior to initializing the bean.
 * 
 * @author bcm-javautil
 */
public abstract class AbstractBeanMetadata implements InitializingBean,
		BeanMetadata {

	private String name;

	private PropertyMetadataFactory<? extends PropertyMetadata> propertyMetadataFactory;

	private List<? extends PropertyMetadata> properties;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (propertyMetadataFactory == null) {
			throw new IllegalStateException("propertyMetadataFactory is null");
		}
		propertyMetadataFactory.afterPropertiesSet();
		properties = propertyMetadataFactory.getProperties();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public PropertyMetadataFactory<? extends PropertyMetadata> getPropertyMetadataFactory() {
		return propertyMetadataFactory;
	}

	public void setPropertyMetadataFactory(
			final PropertyMetadataFactory<? extends PropertyMetadata> propertyMetadataFactory) {
		this.propertyMetadataFactory = propertyMetadataFactory;
	}

	@Override
	public List<? extends PropertyMetadata> getProperties() {
		return properties;
	}

}
