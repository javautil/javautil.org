package org.javautil.recordvalidation;

import org.apache.log4j.Logger;
import org.javautil.field.FieldDefinition;

/**
 * 
 * @author jjs
 * 
 */

public class RecordValidatorImpl implements RecordValidator {

	private final Logger logger = Logger.getLogger(getClass());
	private RecordDefinition definition;

	private RecordMessages recordMessages;

	private String recordType;

	private String text;

	/*
	 */
	@Override
	public boolean validateRecord(final int recordNumber, final String input) {
		boolean rc = true;
		logger.info("Input is " + input);
		recordMessages = new RecordMessages(recordNumber, input);
		// TODO who wrote this?
		logger.trace("Step 1");

		if (input == null) {
			logger.debug("input was null");
			return false;
		}

		if (definition == null) {
			throw new IllegalStateException("definition is null");
		}

		if (definition.getFields() == null) {
			throw new IllegalStateException("definition has no fields");
		}

		FieldDefinition fieldDefinition = null;
		String fieldValueStr = null;

		logger.debug("Before Loop");
		for (int i = 0; i < definition.getFields().size(); i++) {
			fieldDefinition = definition.getField(i);

			fieldValueStr = input.substring(fieldDefinition.getOffset(),
					fieldDefinition.getOffset() + fieldDefinition.getLength());
			if (logger.isDebugEnabled()) {
				logger.debug("Field definition is "
						+ fieldDefinition.getFieldName());
				logger.debug("Field Value Str is " + fieldValueStr);
			}
			if (fieldDefinition.getFieldTypeDefinition() != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Field Type is not null, Trying to match '"
							+ fieldDefinition.getFieldTypeDefinition()
							+ "' to '" + fieldValueStr + "'");
				}
				if (!fieldDefinition.getFieldTypeDefinition().isMatch(
						fieldValueStr)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Not a match so setting return to false");
					}
					rc = false;
					recordMessages.addFieldMessage(fieldDefinition);
				}
			}
		}

		// return true;
		return rc;
	}

	@Override
	public RecordMessages getRecordMessages() {
		return recordMessages;
	}

	/**
	 * @return the definition
	 */
	@Override
	public RecordDefinition getDefinition() {
		return definition;
	}

	/**
	 * @param definition
	 *            the definition to set
	 */
	public void setDefinition(final RecordDefinition definition) {
		this.definition = definition;
	}

	/**
	 * @return the recordType
	 */
	@Override
	public String getRecordType() {
		return recordType;
	}

	/**
	 * @param recordType
	 *            the recordType to set
	 */
	@Override
	public void setRecordType(final String recordType) {
		this.recordType = recordType;
	}

	public RecordValidatorImpl(final RecordDefinition recordDefinition) {
		this.definition = recordDefinition;
	}

	/**
	 * @return the text
	 */
	@Override
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	@Override
	public void setText(final String text) {
		this.text = text;
	}
}
