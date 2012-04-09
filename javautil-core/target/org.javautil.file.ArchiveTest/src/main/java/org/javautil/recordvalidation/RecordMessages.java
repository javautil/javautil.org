package org.javautil.recordvalidation;

import java.util.ArrayList;
import java.util.List;

import org.javautil.field.FieldDefinition;
import org.javautil.field.FieldMessage;
import org.javautil.lang.SystemProperties;

/**
 * 
 * @author jjs
 * 
 */
public class RecordMessages {
	private int recordNumber;

	private String recordText;

	private List<FieldMessage> fieldMessages = new ArrayList<FieldMessage>();

	public RecordMessages(final int recordNumber, final String recordText) {
		super();
		this.recordNumber = recordNumber;
		this.recordText = recordText;
	}

	public void addFieldMessage(final FieldDefinition fieldDefinition) {

		this.fieldMessages.add(new FieldMessage(fieldDefinition));
	}

	public void addFieldMessage(final FieldMessage fieldMessage) {
		this.fieldMessages.add(fieldMessage);
	}

	public int getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(final int recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getRecordText() {
		return recordText;
	}

	public void setRecordText(final String recordText) {
		this.recordText = recordText;
	}

	public List<FieldMessage> getFieldMessages() {
		return fieldMessages;
	}

	public void setFieldMessages(final List<FieldMessage> fieldMessages) {
		this.fieldMessages = fieldMessages;
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append(recordNumber + ": ");
		b.append(recordText + SystemProperties.newline);
		for (final FieldMessage field : fieldMessages) {
			b.append(field.toString());
			b.append(SystemProperties.newline);
		}
		return b.toString();
	}
}
