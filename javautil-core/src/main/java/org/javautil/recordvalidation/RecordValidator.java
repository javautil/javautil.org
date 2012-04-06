package org.javautil.recordvalidation;

/**
 * 
 * @author jjs
 * 
 */
public interface RecordValidator {

	public abstract boolean validateRecord(int recordNumber, String input);

	public RecordMessages getRecordMessages();

	public void setRecordType(String recordType);

	public String getRecordType();

	public RecordDefinition getDefinition();

	public String getText();

	public void setText(String text);
}