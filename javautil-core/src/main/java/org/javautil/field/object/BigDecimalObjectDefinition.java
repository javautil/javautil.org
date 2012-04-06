package org.javautil.field.object;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.javautil.field.FieldTypeDefinition;
import org.javautil.recordvalidation.fieldtype.FieldParseException;

public class BigDecimalObjectDefinition implements ObjectDefinition {

	private final Logger logger = Logger.getLogger(getClass());

	public BigDecimalObjectDefinition() {
	}

	// TODO add ranges for validation 
	@Override
	public BigDecimal
	
	getObject(String externalRepresentation) throws ObjectParseException {
			try {
				return new BigDecimal(externalRepresentation);
			} catch (Exception e) {
				throw new ObjectParseException("error while parsing '" + externalRepresentation + "' ", e);
			}
	}

	// TODO move to abstract class
	@Override
	public boolean validate(String externalRepresentation) {
		boolean retval = true;
		try {
			getObject(externalRepresentation);
		} catch (ObjectParseException ope) {
			retval = false;
		}
		return retval;
	}

}
