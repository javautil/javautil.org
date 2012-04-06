/**
* @(#) ParseError.java
*/

package com.dbexperts.oracle.trace.record;

/**
* PARSE ERROR #%d:len=%ld dep=%d uid=%ld oct=%d lid=%ld tim=%lu err=%d
* <statement> ...
* ----------------------------------------------------------------------------
*
* PARSE ERROR In Oracle 7.2+ parse errors are reported to the trace file.
*
* len         Length of SQL statement.
* dep         Recursive depth of the statement
* uid         User id.
* oct         Oracle command type (if known).
* lid         Privilege user id.
* tim         Timestamp.
* err         Oracle error code (e.g. ORA-XXXXX) reported
*
* <statement> The SQL statement that errored.  If this contains a password,
* the statement is truncated as indicated by '...' at the end.
*/
public class ParseError extends AbstractRecord implements Record
{

	public RecordType getRecordType() {

		return RecordType.PARSE_ERROR;
	}




}
