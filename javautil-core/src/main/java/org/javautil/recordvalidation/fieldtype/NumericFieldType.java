package org.javautil.recordvalidation.fieldtype;

import org.javautil.field.FieldTypeDefinition;

public class NumericFieldType extends FieldTypeDefinition {

	public NumericFieldType() {
		super("NUMERIC", "[0-9]+");
	}
}
