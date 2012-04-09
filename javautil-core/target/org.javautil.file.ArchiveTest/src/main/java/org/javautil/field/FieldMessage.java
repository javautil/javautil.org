package org.javautil.field;

/**
 * 
 * @author jjs
 * 
 */
public class FieldMessage {
	private FieldDefinition fieldDefinition;

	private String message = "";

	public FieldMessage(final FieldDefinition fieldDefinition) {
		super();
		this.fieldDefinition = fieldDefinition;
	}

	public FieldMessage(final FieldDefinition fieldDefinition,
			final String message) {
		super();
		this.fieldDefinition = fieldDefinition;
		this.message = message;
	}

	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}

	public void setFieldDefinition(final FieldDefinition fieldDefinition) {
		this.fieldDefinition = fieldDefinition;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	};

	@Override
	public String toString() {
		return fieldDefinition.toString() + " " + message;
	}
}
