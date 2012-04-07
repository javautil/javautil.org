package org.javautil.recordvalidation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.log4j.Logger;
import org.javautil.field.FieldDefinition;
import org.javautil.field.FieldDefinitionException;
import org.javautil.field.FieldTypeDefinition;
import org.javautil.recordvalidation.fieldtype.DateFieldType;
import org.javautil.recordvalidation.fieldtype.FieldParseException;
import org.javautil.recordvalidation.fieldtype.NumericFieldType;

public class RecordParser {
	public static final NumericFieldType numericFieldType = new NumericFieldType();

	private final Logger logger = Logger.getLogger(getClass());
	private byte[] bytes;
	private final RecordDefinitionImpl recordDefinition = new RecordDefinitionImpl();

	public void setRecord(final byte[] bytes) {
		if (bytes == null) {
			throw new IllegalArgumentException("bytes is null");
		}
		this.bytes = bytes;
	}

	public byte[] getRecord() {
		return bytes;
	}

	public void addField(final FieldDefinition fieldDefinition) {
		recordDefinition.addField(fieldDefinition);
	}

	public String getString(final String fieldName) {
		final byte[] fieldBytes = getBytes(fieldName);
		final String fieldString = new String(fieldBytes);
		if (logger.isDebugEnabled()) {
			logger.debug("fieldName '" + fieldName + "' string '" + fieldString
					+ "'");
		}
		return fieldString;
	}

	public byte[] getBytes(final String fieldName)
			throws FieldDefinitionException {
		if (bytes == null) {
			throw new IllegalStateException("setRecord has not been called");
		}
		final FieldDefinition fieldDefinition = recordDefinition.get(fieldName);
		if (fieldDefinition == null) {
			throw new IllegalArgumentException("no such field: " + fieldName
					+ " in " + recordDefinition.keySet());
		}
		if (fieldDefinition.getOffset() > bytes.length) {
			throw new IllegalArgumentException(fieldDefinition.toString()
					+ " record length " + bytes.length
					+ " ends before start of field "
					+ fieldDefinition.getOffset());
		}
		final int endIndex = fieldDefinition.getOffset()
				+ fieldDefinition.getLength();
		if (endIndex > bytes.length) {
			throw new IllegalArgumentException(fieldDefinition.toString()
					+ " record length " + bytes.length
					+ " ends before end of field " + endIndex);
		}
		final int startPosition = fieldDefinition.getOffset();
		final byte[] fieldBytes = new byte[fieldDefinition.getLength()];

		System.arraycopy(bytes, startPosition, fieldBytes, 0,
				fieldDefinition.getLength());
		if (logger.isDebugEnabled()) {
			final StringBuilder sb = new StringBuilder();
			sb.append("fieldBytes.length " + fieldBytes.length);
			sb.append(" srcPosition " + startPosition);
			sb.append(" bytes.length " + bytes.length);
			sb.append(" fieldDefinition.length " + fieldDefinition.getLength());
			sb.append(" bytes as String '" + new String(bytes) + "'");
			logger.debug(sb.toString());
		}
		return fieldBytes;

	}

	public BigDecimal getBigDecimal(final String fieldName) {
		final String fieldText = getString(fieldName);
		final BigDecimal returnValue = new BigDecimal(fieldText);
		return returnValue;
	}

	public Integer getInteger(final String fieldName)
			throws FieldParseException {
		final String fieldText = getString(fieldName);
		final FieldDefinition definition = recordDefinition.get(fieldName);
		if (logger.isDebugEnabled()) {
			logger.debug("getting value for field '" + fieldName + "'");
		}
		Integer returnValue = null;
		try {
			returnValue = new Integer(fieldText);
		} catch (final Exception e) {
			throw new FieldParseException(definition, e);
		}
		return returnValue;
	}

	/**
	 * TODO for some strange reason this returns the date in standard time, not
	 * daylight savings time so for some times of the year this works at other
	 * times it is off by an hour
	 * 
	 * @param string
	 * @return
	 * @throws FieldParseException
	 */
	public Date getDate(final String string) throws FieldParseException {
		final FieldDefinition definition = recordDefinition.get(string);
		final byte[] fieldText = getBytes(string);
		final FieldTypeDefinition fieldType = definition
				.getFieldTypeDefinition();
		final DateFieldType dateFieldType = (DateFieldType) fieldType;
		Date date = null;

		try {
			date = dateFieldType.getObject(fieldText);
			showDebugInfo(string, dateFieldType, date);
		} catch (final FieldParseException e) {
			throw new FieldParseException(definition, e);
		}
		return date;
	}

	public BigInteger getBigInteger(final String string) {
		final String fieldText = getString(string);
		final BigInteger returnValue = new BigInteger(fieldText);
		showDebugInfo(string, returnValue);
		return returnValue;
	}

	void showDebugInfo(String fieldDate, FieldTypeDefinition definition,
			Object dataObject) {
		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("getting: ");
			sb.append(fieldDate);
			sb.append(" ");
			sb.append(definition.toString());
			sb.append(" ");
			sb.append("returning '");
			sb.append(dataObject.toString());
			logger.debug(sb);

		}
	}

	void showDebugInfo(String fieldText, Object dataObject) {
		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("getting: ");
			sb.append(fieldText);
			sb.append(" ");
			sb.append("returning '");
			sb.append(dataObject.toString());
			logger.debug(sb);

		}
	}

	public RecordDefinition getRecordDefinition() {
		return recordDefinition;
	}

	public Long getLong(String string) {
		final String fieldText = getString(string);
		final Long returnValue = new Long(fieldText);
		showDebugInfo(string, returnValue);
		return returnValue;
	}
}
