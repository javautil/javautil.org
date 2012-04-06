/**
* @(#) Stat.java
*/

package com.dbexperts.oracle.trace.record;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.dbexperts.oracle.trace.RegularExpressionHelper;

/**
* STAT #<CURSOR> id=N cnt=0  [pid=0 pos=0 obj=0 op='SORT AGGREGATE ']
* ----------------------------------------------------------------------------
*
* STAT        Lines report explain plan statistics for the numbered <CURSOR>.
*
* <CURSOR>    Cursor which the statistics apply to.
*
* id          Line of the explain plan which the row count applies to (starts at line 1).
* This is effectively the row source row count for all row sources in the execution tree.
* cnt         Number of rows for this row source.
*
* As of 7.3.3 the items in '[...]' are also reported:
*
* pid         Parent id of this row source.
* pos         Position in explain plan.
* obj         Object id of row source (if this is a base object).
* op='...'    The row source access operation.
*
* In reality we find this
 STAT #3 id=1 cnt=188220 pid=0 pos=1 obj=77538 op='INDEX FAST FULL SCAN FC_FCST_NDX_02 (cr=2366 pr=0 pw=0 time=1129914 us)'
*/
public class Stat extends AbstractCursorTraceEvent implements Record
{
//	private StringTokenizer	tokenizer;
	private static Logger					logger		= Logger.getLogger(Stat.class.getName());
	private static Pattern    cursorNumberPattern = Pattern.compile("^STAT #(\\d*) ");
	private static Pattern	idPattern	= Pattern.compile("id= (\\d*)");
	private static Pattern	cntPattern	= Pattern.compile("cnt= (\\d*)");
	private static Pattern	pidPattern	= Pattern.compile("pid= (\\d*)");
	private static Pattern	posPattern	= Pattern.compile("pos=(\\d*).*");
	private static Pattern	objPattern	= Pattern.compile("obj=(\\d*)");
	private static Pattern	opPattern	= Pattern.compile("op='(.*) \\(");
	private static Pattern	crPattern	= Pattern.compile("\\(cr=(\\d*)");
	private static Pattern	prPattern	= Pattern.compile("pr=(\\d*)");
	private static Pattern	pwPattern	= Pattern.compile("pw=(\\d*)");
	private static Pattern	timePattern	= Pattern.compile("time='(\\d*)");
	private final int				id;
	private final int				cnt;
	private final int				pid;
	private final int				pos;
	private final int				obj;
	private final String			op;
	private final int				cr;
	private final int				pr;
	private final int				pw;
	private final int				time;

	public static Pattern getCntPattern() {
		return cntPattern;
	}


	public static Pattern getCrPattern() {
		return crPattern;
	}

	public static Pattern getIdPattern() {
		return idPattern;
	}

	public static Pattern getObjPattern() {
		return objPattern;
	}

	public static Pattern getOpPattern() {
		return opPattern;
	}

	public static Pattern getPidPattern() {
		return pidPattern;
	}

	public static Pattern getPosPattern() {
		return posPattern;
	}

	public static Pattern getPrPattern() {
		return prPattern;
	}

	public static Pattern getPwPattern() {
		return pwPattern;
	}

	public static Pattern getTimePattern() {
		return timePattern;
	}

	public Stat(final String stmt, final int lineNumber) {
		//recordText = stmt;
		setLineNumber(lineNumber);
		id = RegularExpressionHelper.getInt(stmt,idPattern);
		cnt = RegularExpressionHelper.getInt(stmt,cntPattern);
		pid = RegularExpressionHelper.getInt(stmt,pidPattern);
		pos = RegularExpressionHelper.getInt(stmt,posPattern);
		obj = RegularExpressionHelper.getInt(stmt,objPattern);
		op = RegularExpressionHelper.getString(stmt,opPattern);
		cr = RegularExpressionHelper.getInt(stmt,crPattern);
		pr = RegularExpressionHelper.getInt(stmt,prPattern);
		pw = RegularExpressionHelper.getInt(stmt,pwPattern);
		time = RegularExpressionHelper.getInt(stmt,timePattern);
		final int cursorNumber = RegularExpressionHelper.getInt(stmt,cursorNumberPattern);
		setCursorNumber(cursorNumber);
	}

	public int getCnt() {
		return cnt;
	}

	public int getCr() {
		return cr;
	}

	public int getId() {
		return id;
	}

	public Logger getLogger() {
		return logger;
	}

	/**
	 * @return the database object number
	 */
	public int getObjectNumber() {
		return obj;
	}

	/**
	 * The op value in the trace log.
	 * Describes the operation.
	 * @return the operation description.
	 */
	public String getOperation() {
		return op;
	}

	/**
     *
     * @return the number of blocks read via operating system calls.
     */
	public int getPhysicalReads() {
		return pr;
	}

	public int getPhysicalWrites() {
		return pw;
	}

	public int getPid() {
		return pid;
	}

    /**
	 * The position in the explain plan.
	 * @return
	 */
	public int getPosition() {
		return pos;
	}

	public RecordType getRecordType() {
		return RecordType.STAT;
	}

	public int getTime() {
		return time;
	}

//	public StringTokenizer getTokenizer() {
//		return tokenizer;
//	}

}
