package com.dbexperts.oracle.trace.record;

public abstract class AbstractRecord implements Record {

	private static Boolean latch = Boolean.TRUE;

	private static long nextRecordNumber = 1;

	private int lineNumber;

	private long recordNumber;

	public final int getLineNumber() {
		return lineNumber;
	}

	public long getRecordNumber() {
		if (recordNumber == 0) {
			recordNumber = getNextRecordNumber();
		}
		return recordNumber;
	}

	public final void setLineNumber(final int lineNumber) {
		this.lineNumber = lineNumber;
	}

	long getNextRecordNumber() {
		synchronized(latch) {
			return nextRecordNumber++;
		}
	}
}
