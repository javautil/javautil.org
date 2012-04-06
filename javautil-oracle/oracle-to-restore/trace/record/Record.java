package com.dbexperts.oracle.trace.record;

public interface Record {

	public int getLineNumber();
	public RecordType getRecordType();
	public void setLineNumber(int lineNumber);

}
