package org.javautil.recordvalidation;

import java.util.LinkedHashMap;

import org.javautil.field.FieldDefinition;

public class RecordSplitter {

	/**
	 * 
	 * @param text
	 * @param recordDefinition
	 * @return a map of fields keyed on field name
	 */
	public LinkedHashMap<String, String> parse(final String text,
			final RecordDefinition recordDefinition) {
		final LinkedHashMap<String, String> retval = new LinkedHashMap<String, String>();
		for (final FieldDefinition field : recordDefinition.getFields()) {
			final String fieldName = field.getFieldName();
			final String fieldText = getField(text, field);
			retval.put(fieldName, fieldText);
		}
		return retval;
	}

	String getField(final String record, final FieldDefinition fieldDefinition) {

		final String fieldValueStr = record.substring(
				fieldDefinition.getOffset(), fieldDefinition.getOffset()
						+ fieldDefinition.getLength());
		return fieldValueStr;
	}
}
