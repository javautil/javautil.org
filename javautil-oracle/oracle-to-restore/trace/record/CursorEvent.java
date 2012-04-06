package com.dbexperts.oracle.trace.record;

public interface CursorEvent {
	public int getCursorNumber();
	public int getLineNumber();
	public RecordType getRecordType();
	public void setCursorNumber(int cursorNumber);
}
