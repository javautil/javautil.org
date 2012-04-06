package org.javautil.jfm;

import java.lang.reflect.Field;

import org.springframework.beans.factory.InitializingBean;

/**
 * Implementation of PropertyMetadata backed by a java.lang.reflect.Field.
 * 
 * @author bcm-javautil
 */
public class IntrospectivePropertyMetadata implements InitializingBean,
		PropertyMetadata {

	private Field field;

	private String label;

	private String heading;

	private String description;

	public IntrospectivePropertyMetadata() {
	}

	public IntrospectivePropertyMetadata(final Field field) {
		this.field = field;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (field == null) {
			throw new IllegalStateException("field is null");
		}
	}

	@Override
	public String getName() {
		return field.getName();
	}

	@Override
	public String getLabel() {
		return label == null ? getName() : label;
	}

	@Override
	public String getHeading() {
		return heading == null ? getName() : heading;
	}

	public Field getField() {
		return field;
	}

	public void setField(final Field field) {
		this.field = field;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
