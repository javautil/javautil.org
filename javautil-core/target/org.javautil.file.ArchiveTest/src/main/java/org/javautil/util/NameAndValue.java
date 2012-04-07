package org.javautil.util;

/**
 * 
 * @author bcm
 * 
 */
public interface NameAndValue<T> {

	public String getName();

	public void setName(String name);

	public T getValue();

	public void setValue(T value);
}
