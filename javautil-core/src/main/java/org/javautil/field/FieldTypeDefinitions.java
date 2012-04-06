package org.javautil.field;

import java.util.HashMap;

import org.javautil.recordvalidation.fieldtype.AlphaFieldType;
import org.javautil.recordvalidation.fieldtype.AlphanumericFieldType;
import org.javautil.recordvalidation.fieldtype.NumericFieldType;
import org.javautil.recordvalidation.fieldtype.NumericLeadingOrTrailingSpacesFieldType;
import org.javautil.recordvalidation.fieldtype.NumericLeadingSpacesFieldType;
import org.javautil.recordvalidation.fieldtype.PrintFieldType;
import org.javautil.recordvalidation.fieldtype.YNFieldType;

public class FieldTypeDefinitions extends HashMap<String, FieldTypeDefinition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
		add(new AlphaFieldType());
		add(new NumericFieldType());
		add(new AlphanumericFieldType());
		add(new NumericLeadingSpacesFieldType());
		add(new NumericLeadingOrTrailingSpacesFieldType());
		add(new PrintFieldType());
		add(new YNFieldType());

	}

	public void add(final FieldTypeDefinition fieldTypeDefinition) {
		if (fieldTypeDefinition == null) {
			throw new IllegalArgumentException("fieldTypeDefinition is null");
		}
		put(fieldTypeDefinition.getTypeName(), fieldTypeDefinition);
	}
}
