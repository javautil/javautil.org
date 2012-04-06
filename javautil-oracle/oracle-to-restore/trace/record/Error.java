/**
* @(#) Error.java
*/

package com.dbexperts.oracle.trace.record;

/**
* ERROR #%d:err=%d tim=%lu
* ----------------------------------------------------------------------------
* SQL         Error shown after an execution or fetch error.
*
* err         Oracle error code (e.g. ORA-XXXXX) at the top of the stack.
* tim         Timestamp.
*/
public class Error extends AbstractRecord implements Record
{



	public RecordType getRecordType() {
		return RecordType.ERROR;
	}
}
