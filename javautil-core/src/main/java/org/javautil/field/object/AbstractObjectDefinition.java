package org.javautil.field.object;

import org.javautil.field.FieldTypeDefinition;
import org.javautil.recordvalidation.fieldtype.NumericFieldType;

/**
 * Defines a single field in a fixed length octet array
 * 
 * @author jjs
 * 
 */
public abstract class AbstractObjectDefinition implements ObjectDefinition {
	// TODO add ranges for validation
	@Override
	public abstract Object getObject(final String externalRepresentation) throws ObjectParseException;

	public boolean validate(final String externalRepresentation) {
		boolean retval = true;
		try {
			getObject(externalRepresentation);
		} catch (final ObjectParseException ope) {
			retval = false;
		}
		return retval;
	}
}
