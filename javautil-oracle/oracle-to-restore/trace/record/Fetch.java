/**
* @(#) Fetch.java
*/

package com.dbexperts.oracle.trace.record;

/**
*
*/
public class Fetch extends CursorOperation
{
	public Fetch(final String record, final int lineNbr) {
		super(record);
		setLineNumber(lineNbr);
	}

	@Override
	public RecordType getRecordType() {
		return RecordType.FETCH;
	}
}
