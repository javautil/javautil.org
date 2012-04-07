package org.javautil.recordvalidation.fieldtype;

import org.javautil.field.FieldTypeDefinition;

public class AlphaFieldType extends FieldTypeDefinition {

	public AlphaFieldType() {
		super("ALPHA", "[a-zA-Z]*");
	}

}
