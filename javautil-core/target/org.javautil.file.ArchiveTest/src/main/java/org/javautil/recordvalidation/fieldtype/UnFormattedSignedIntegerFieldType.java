package org.javautil.recordvalidation.fieldtype;

import org.javautil.field.FieldTypeDefinition;

public class UnFormattedSignedIntegerFieldType extends FieldTypeDefinition {

	public UnFormattedSignedIntegerFieldType() {
		super("UnFormattedSignedInteger", " *-{0,1}[0-9]+");

	}
}
