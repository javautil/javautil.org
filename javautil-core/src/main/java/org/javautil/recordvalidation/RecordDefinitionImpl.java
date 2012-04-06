package org.javautil.recordvalidation;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.javautil.field.FieldDefinition;

public class RecordDefinitionImpl extends
		LinkedHashMap<String, FieldDefinition> implements RecordDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger(getClass());

	@Override
	public Collection<FieldDefinition> getFields() {
		return values();
	}

	public void addField(final FieldDefinition definition) {
		if (logger.isTraceEnabled()) {
			logger.trace("adding field " + definition.getFieldName());
		}
		put(definition.getFieldName(), definition);
	}

	@Override
	public FieldDefinition getField(final int fieldIndex) {
		final FieldDefinition definition = get(fieldIndex);
		return definition;
	}
}
