package org.javautil.recordvalidation.fieldtype;

import org.javautil.field.FieldTypeDefinition;

public class PrintFieldType extends FieldTypeDefinition {

	public PrintFieldType() {
		super("PRINT", "[\\p{Graph}| ]*");

	}

}
