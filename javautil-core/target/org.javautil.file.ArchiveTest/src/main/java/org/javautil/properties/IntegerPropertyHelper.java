package org.javautil.properties;

/**
 * todo expand to support min value, max value
 * 
 * @author jjs
 * 
 */
public class IntegerPropertyHelper {
	private String propertyName = "unspecified";

	private Integer minValue;

	private Integer maxValue;

	public IntegerPropertyHelper(final String propertyName,
			final Integer minValue, final Integer maxValue) {
		super();
		this.propertyName = propertyName;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public Integer parse(final String text) {
		Integer val = null;

		if (text != null && text.length() > 0) {
			// try {

			val = new Integer(text);
			// }
			// catch ( ParseException pe ) {
			// String message = " value " + text + " for " + propertyName +
			// " property cannot be parsed as an integer ";
			// throw new IllegalPropertyValueException(message);
			// }
			if (minValue != null && val < minValue) {
				final String message = " value " + text + " for "
						+ propertyName
						+ " property is less than the minimum allowed "
						+ minValue;
				throw new IllegalPropertyValueException(message);
			}
			if (maxValue != null && val > maxValue) {
				final String message = " value " + text + " for "
						+ propertyName
						+ " property is greater than the maximum allowed "
						+ maxValue;
				throw new IllegalPropertyValueException(message);
			}

		}
		return val;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(final Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(final Integer maxValue) {
		this.maxValue = maxValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}
}
