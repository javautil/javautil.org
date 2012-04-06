package org.javautil.jfm;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

public interface PropertyMetadataFactory<T extends PropertyMetadata> extends
		InitializingBean {

	public T getProperty(String propertyName);

	public List<T> getProperties();

}
