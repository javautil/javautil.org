package org.javautil.jdbc;

public class BindVariableValue {
	private String variableName;
	private JdbcType jdbcType;
	private String value;
	private final Object valueObject;

	public BindVariableValue(final String varName, final JdbcType type,
			final String value) {
		this.setVariableName(varName.toUpperCase());
		this.jdbcType = type;
		this.value = value;
		this.valueObject = jdbcType.getAsObject(value);
	}

	public BindVariableValue(final String varName, final Object valueObject) {
		if (varName == null) {
			throw new IllegalArgumentException("varName is null");
		}
		if (valueObject == null) {
			throw new IllegalArgumentException("valueObject is null");
		}
		if (valueObject instanceof java.util.Date) {
			jdbcType = JdbcType.DATE;
		}
		this.setVariableName(varName.toUpperCase());
		this.valueObject = valueObject;
	}

	public BindVariableValue(final String varName, final String typeName,
			final String value) {
		this.setVariableName(varName.toUpperCase());
		this.value = value;
		this.jdbcType = JdbcType.getJdbcType(typeName);
		this.valueObject = jdbcType.getAsObject(value);
	}

	/**
	 * @return Returns the varName.
	 */
	// public String getVariableName() {
	// return variableName;
	// }

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	// @todo review and clean this up with respect to getValueObject
	public String getValue() {
		String returnValue = null;
		if (value == null && valueObject == null) {
			throw new IllegalStateException("value and valueObject both null");
		} else if (value == null && valueObject != null) {
			returnValue = valueObject.toString();
		} else if (value != null && valueObject == null) {
			returnValue = value;
		} else if (value != null && valueObject != null) {
			returnValue = value;
		}
		return returnValue;
	}

	public Object getValueObject() {
		return valueObject;
	}

	/**
	 * @return the variableName
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * @param variableName
	 *            the variableName to set
	 */
	private void setVariableName(final String variableName) {
		if (variableName == null) {
			throw new IllegalArgumentException("variableName is null");
		}
		if (variableName.trim().length() == 0) {
			throw new IllegalArgumentException(
					"variableName contains all white characters");
		}
		this.variableName = variableName.trim();
	}

}
