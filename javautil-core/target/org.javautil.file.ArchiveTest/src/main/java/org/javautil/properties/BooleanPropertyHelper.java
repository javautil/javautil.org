package org.javautil.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO reduce cost of construction
 * 
 * TODO allow null default for true or false
 * 
 * @author jjs
 * 
 */
public class BooleanPropertyHelper {
	private String propertyName = "unspecified";

	private boolean caseSensitive = false;

	private String[] trueValues = new String[] { "1", "true", "y", "yes" };

	private String[] falseValues = new String[] { "0", "false", "n", "no" };

	private final Map<String, Boolean> truthMap = new HashMap<String, Boolean>();

	private final boolean initted = false;

	public BooleanPropertyHelper() {

	}

	public BooleanPropertyHelper(final String propertyName) {
		this.propertyName = propertyName;

	}

	public BooleanPropertyHelper(final boolean caseSensitive,
			final String[] trueValues, final String[] falseValues) {
		super();

		this.caseSensitive = caseSensitive;
		this.trueValues = trueValues;
		this.falseValues = falseValues;
	}

	public BooleanPropertyHelper(final String propertyName,
			final boolean caseSensitive, final String[] trueValues,
			final String[] falseValues) {
		super();
		this.propertyName = propertyName;
		this.caseSensitive = caseSensitive;
		this.trueValues = trueValues;
		this.falseValues = falseValues;
	}

	private void init() {
		if (!initted) {
			for (final String val : trueValues) {
				if (val == null) {
					throw new IllegalArgumentException(
							"null value for trueValue found");
				}
				final String key = caseSensitive ? val : val.toUpperCase();
				truthMap.put(key, Boolean.TRUE);
			}
			for (final String val : falseValues) {
				if (val == null) {
					throw new IllegalArgumentException(
							"null value for falseValue found");
				}
				final String key = caseSensitive ? val : val.toUpperCase();
				truthMap.put(key, Boolean.FALSE);
			}
		}
	}

	public boolean parse(final String text) {
		if (text == null) {
			throw new IllegalArgumentException("text is null");
		}
		init();
		Boolean retval = null;
		// todo doesn't support null
		if (caseSensitive) {
			retval = truthMap.get(text);
		} else {
			retval = truthMap.get(text.toUpperCase());
		}
		if (retval == null) {
			// todo show values
			throw new IllegalPropertyValueException(
					"cannot determine value of input text '" + text + "' "
							+ truthMap);
		}
		return retval;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}
}
