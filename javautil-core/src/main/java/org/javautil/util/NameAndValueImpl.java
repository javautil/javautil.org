package org.javautil.util;

/**
 * 
 * @author bcm
 */
public class NameAndValueImpl<T> implements NameAndValue<T>,
		Comparable<NameAndValueImpl<?>> {

	private String name;

	private T value;

	public NameAndValueImpl(final String name, final T value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(final T value) {
		this.value = value;
	}

	@Override
	public int compareTo(final NameAndValueImpl<?> o) {
		return this.getName().compareTo(o.getName());
	}
}
