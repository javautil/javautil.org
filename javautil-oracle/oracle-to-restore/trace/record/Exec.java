/**
* @(#) Exec.java
*/

package com.dbexperts.oracle.trace.record;

/**
*
*/
public class Exec extends CursorOperation
{
	public Exec(final String record, final int lineNumber) {
		super(record);
		setLineNumber(lineNumber);
	}

	@Override
	public RecordType getRecordType() {
		return RecordType.EXEC;
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("recordType: " + getRecordType() + " ");
		b.append("lineNumber: " + getLineNumber() + " ");
		b.append(super.toString());
		return b.toString();
	}
}
