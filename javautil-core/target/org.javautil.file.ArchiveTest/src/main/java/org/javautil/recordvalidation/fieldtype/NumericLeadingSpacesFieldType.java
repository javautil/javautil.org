package org.javautil.recordvalidation.fieldtype;

import org.javautil.field.FieldTypeDefinition;

public class NumericLeadingSpacesFieldType extends FieldTypeDefinition {

	public NumericLeadingSpacesFieldType() {
		super("NUMERIC_LEADING_SPACES", " *[0-9]+");
	}

}
