package org.javautil.jfm;

import java.util.List;

/**
 * Implemented by beans that represent meta data for a pojo.
 * 
 * @author bcm-javautil
 */
public interface BeanMetadata {

	/**
	 * This should return the simple class name of the bean.
	 * 
	 * For example a bean my.super.dto.CustomerInfo this would return
	 * CustomerInfo and probably be stored in the table CUSTOMER_INFO.
	 * 
	 * 
	 * @return name
	 */
	public String getName();

	/**
	 * 
	 * @return
	 */
	public List<? extends PropertyMetadata> getProperties();

}
