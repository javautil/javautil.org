package org.javautil.recordvalidation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.javautil.field.FieldDefinition;
import org.javautil.field.FieldTypeDefinition;
import org.javautil.recordvalidation.fieldtype.AlphaFieldType;
import org.javautil.recordvalidation.fieldtype.AlphanumericFieldType;
import org.javautil.recordvalidation.fieldtype.NumericFieldType;
import org.javautil.recordvalidation.fieldtype.NumericLeadingOrTrailingSpacesFieldType;
import org.javautil.recordvalidation.fieldtype.NumericLeadingSpacesFieldType;
import org.javautil.recordvalidation.fieldtype.PrintFieldType;
import org.javautil.recordvalidation.fieldtype.UnFormattedSignedIntegerFieldType;
import org.javautil.recordvalidation.fieldtype.YNFieldType;

/**
 * 
 * @author jjs
 * 
 */
public abstract class AbstractRecordDefinition implements RecordDefinition {
	private String text;

	public static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyMMDD");
	public static FieldTypeDefinition ALPHA = new AlphaFieldType();
	public static FieldTypeDefinition NUMERIC = new NumericFieldType();
	public static FieldTypeDefinition ALPHANUMERIC = new AlphanumericFieldType();
	public static FieldTypeDefinition YN = new YNFieldType();
	public static FieldTypeDefinition PRINT = new PrintFieldType();
	public static FieldTypeDefinition NUMERIC_LEADING_SPACES = new NumericLeadingSpacesFieldType();
	public static FieldTypeDefinition NUMERIC_LEADING_OR_TRAILING_SPACES = new NumericLeadingOrTrailingSpacesFieldType();
	public static FieldTypeDefinition UN_FORMATTED_SIGNED_INTEGER = new UnFormattedSignedIntegerFieldType();

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(final String text) {
		this.text = text;
	}

	public String getFieldText(final FieldDefinition fieldDefinition) {
		String result = null;
		if (text != null) {
			result = text.substring(fieldDefinition.getOffset(),
					fieldDefinition.getOffset() + fieldDefinition.getLength());
		}
		return result;
	}

	public BigDecimal getBigDecimal(final FieldDefinition fieldDefinition) {
		return new BigDecimal(getFieldText(fieldDefinition));
	}

	public double getDouble(final FieldDefinition fieldDefinition) {
		double result = 0.0;
		final String text = getFieldText(fieldDefinition);
		if (text != null) {
			result = Double.parseDouble(text);
		}
		return result;
	}

	public Date getDate(final FieldDefinition fieldDefinition) {
		Date date = null;
		final String fieldText = getFieldText(fieldDefinition);
		if (fieldText != null) {
			try {
				date = getDateFormatter().parse(fieldText);
			} catch (final ParseException e) {
				// TODO not rethrown
			}
		}
		return date;
	}

	public SimpleDateFormat getDateFormatter() {
		return dateFormatter;
	}

}
