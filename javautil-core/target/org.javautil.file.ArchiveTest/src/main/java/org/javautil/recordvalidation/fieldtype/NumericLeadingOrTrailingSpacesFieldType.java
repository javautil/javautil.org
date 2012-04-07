package org.javautil.recordvalidation.fieldtype;

import org.javautil.field.FieldTypeDefinition;

public class NumericLeadingOrTrailingSpacesFieldType extends
		FieldTypeDefinition {

	public NumericLeadingOrTrailingSpacesFieldType() {
		super("NUMERIC_LEADING_OR_TRAILING_SPACES", " *[0-9]+ *");
	}

}
