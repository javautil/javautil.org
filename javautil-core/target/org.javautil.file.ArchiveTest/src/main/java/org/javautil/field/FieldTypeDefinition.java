package org.javautil.field;

import java.util.regex.Pattern;

public abstract class FieldTypeDefinition {
	private final String typeName;
	private final String regularExpression;
	private final Pattern pattern;

	public FieldTypeDefinition(final String typeName,
			final String regularExpression) {
		super();
		this.typeName = typeName;
		this.regularExpression = regularExpression;
		pattern = Pattern.compile(regularExpression);
	}

	
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @return the regularExpression
	 */
	public String getRegularExpression() {
		return regularExpression;
	}

	public boolean isMatch(final String text) {
		return pattern.matcher(text).matches();
	}

	@Override
	public String toString() {
		return "typeName: " + typeName + " regularExpression: '"
				+ regularExpression + "'";
	}
}
