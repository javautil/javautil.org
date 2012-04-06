/**
* @(#) Bind.java
*/

package com.dbexperts.oracle.trace.record;

/**
* ----------------------------------------------------------------------------
* BINDS #%d:
* bind 0: dty=2 mxl=22(22) mal=00 scl=00 pre=00 oacflg=03 oacfl2=0 size=24 offset=0
* bfp=02fedb44 bln=22 avl=00 flg=05
* value=10
* ----------------------------------------------------------------------------
*
* BIND        Variables bound to a cursor.
*
* bind N      The bind position being bound.
* dty         Data type (see <Glossary:DataTypes>).
* mxl         Maximum length of the bind variable (private max len in paren).
* mal         Array length.
* scl         Scale.
* pre         Precision.
* oacflg      Special flag indicating bind options
* oacflg2     Continuation of oacflg
* size        Amount of memory to be allocated for this chunk
* offset      Offset into this chunk for this bind buffer
*
* bfp         Bind address.
* bln         Bind buffer length.
* avl         Actual value length (array length too).
* flg         Special flag indicating bind status
* value       The actual value of the bind variable.
* Numbers show the numeric value, strings show the string etc...
*
* It is also possible to see "bind 6: (No oacdef for this bind)", if no
* separate bind buffer exists.
*/
public class BindVariable extends AbstractCursorTraceEvent implements Record
{

	public RecordType getRecordType() {
		return RecordType.BIND;
	}


}
