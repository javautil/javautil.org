package org.javautil.recordvalidation;

import java.util.Collection;

import org.javautil.field.FieldDefinition;

/**
 * 
 * @author jjs
 * 
 */
public interface RecordDefinition {

	public Collection<FieldDefinition> getFields();

	public FieldDefinition getField(int fieldIndex);
}