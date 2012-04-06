package org.javautil.recordvalidation.fieldtype;

import org.javautil.field.FieldTypeDefinition;

public class ZonedDecimalFieldType extends FieldTypeDefinition {

	public ZonedDecimalFieldType() {
		super("ZonedDecimal", " *-{0,1}[0-9]+");
	}

}
