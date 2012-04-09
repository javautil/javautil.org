package org.javautil.recordvalidation.fieldtype;

import org.javautil.field.FieldTypeDefinition;

public class AlphanumericFieldType extends FieldTypeDefinition {
	public AlphanumericFieldType() {
		// TODO document
		super("ALPHANUMERIC", "[\\w\\d]*");
	}

}
