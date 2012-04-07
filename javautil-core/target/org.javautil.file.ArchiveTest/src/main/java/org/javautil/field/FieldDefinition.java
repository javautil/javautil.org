package org.javautil.field;

/**
 * Defines a single field in a fixed length octet array
 * 
 * @author jjs
 * TODO this should extend or encapsulate ObjectDefinition 
 */
public class FieldDefinition {
	/**
	 * Offset from beginning of record relative 0.
	 */
	private int offset;
	/**
	 * Length of this field in bytes
	 */
	private int length;
	/**
	 * The FieldType.
	 */
	private FieldTypeDefinition fieldTypeDefinition;
	/**
	 * The Field Name.
	 * 
	 * Used only in error message reporting.
	 */
	private String fieldName;

	public FieldDefinition(final int offset, final int length,
			final FieldTypeDefinition fieldType, final String fieldName) {
		super();
		this.offset = offset;
		this.length = length;
		this.fieldTypeDefinition = fieldType;
		this.fieldName = fieldName;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(final int offset) {
		this.offset = offset;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(final int length) {
		this.length = length;
	}

	/**
	 * @return the fieldType
	 */
	public FieldTypeDefinition getFieldTypeDefinition() {
		return fieldTypeDefinition;
	}

	/**
	 * @param fieldType
	 *            the fieldType to set
	 */
	public void setFieldTypeDefinition(final FieldTypeDefinition fieldType) {
		this.fieldTypeDefinition = fieldType;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 */
	public void setFieldName(final String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String toString() {
		return fieldName + " " + offset + " "
				+ String.valueOf(fieldTypeDefinition);
	}
}
