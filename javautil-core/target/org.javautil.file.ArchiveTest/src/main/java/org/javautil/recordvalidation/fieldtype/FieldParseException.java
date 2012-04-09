package org.javautil.recordvalidation.fieldtype;

import org.javautil.field.FieldDefinition;

public class FieldParseException extends Exception {
	
	private FieldDefinition fieldDefinition;


	/**
	 * @return the fieldDefinition
	 */
	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}

	/**
	 * @author jjs TODO provide documentation on when this should be thrown
	 * 
	 *         <ul>
	 *         <li>
	 */
	private static final long serialVersionUID = 4717657643244704278L;


//	public FieldParseException(final String message, final Throwable cause) {
//		super(message, cause);
//	}

	
	public FieldParseException(final Throwable cause) {
		super(cause);
	}

	
	// TODO this seems like noise
	public FieldParseException(FieldDefinition definition, FieldParseException e) {
		super(e);
		this.fieldDefinition = definition;
	}

	public FieldParseException(FieldDefinition definition, Throwable cause) {
		super(cause);
		this.fieldDefinition = definition;
	}

	

}
