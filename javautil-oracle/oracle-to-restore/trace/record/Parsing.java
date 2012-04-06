/**
 * @(#) Parsing.java
 */
package com.dbexperts.oracle.trace.record;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.dbexperts.misc.string.StringBuilderHelper;

/**
 * PARSING IN CURSOR #<CURSOR> len=X dep=X uid=X oct=X lid=X tim=X hv=X ad='X'
 * <statement> END OF STMT
 * ----------------------------------------------------------------------------
 * <CURSOR> Cursor number.
 *
 * len Length of SQL statement.
 * dep Recursive depth of the cursor.
 * uid Schema user id of parsing user.
 * oct Oracle command type.
 * lid Privilege user id.
 * tim Timestamp. Pre-Oracle9i, the times recorded by Oracle only have a resolution
 * of 1/100th of a second (10mS). As of Oracle9i some times are available to
 * microsecond accuracy (1/1,000,000th of a second). The timestamp can be used
 * to determine times between points in the trace file. The value is the value
 * in V$TIMER when the line was written. If there are TIMESTAMPS in the file you
 * can use the difference between 'tim' values to determine an absolute time.
 * hv Hash id.
 * ad SQLTEXT address (see <View:V$SQLAREA> and <View:V$SQLTEXT>).
 *
 * <statement> The actual SQL statement being parsed.
 */
public class Parsing extends AbstractCursorTraceEvent {
	private static final Pattern	cursorNumberPattern	= Pattern.compile("^PARSING IN CURSOR #(\\d*) ");
	private static final Pattern	lenPattern			= Pattern.compile(" len=(\\d*) ");
	private static final Pattern	depPattern			= Pattern.compile(" dep=(\\d*) ");
	private static final Pattern	uidPattern			= Pattern.compile(" uid=(\\d*) ");
	private static final Pattern	octPattern			= Pattern.compile(" oct=(\\d*) ");
	private static final Pattern	lidPattern			= Pattern.compile(" lid=(\\d*) ");
	private static final Pattern	timPattern			= Pattern.compile(" tim=(\\d*) ");
	private static final Pattern	hvPattern			= Pattern.compile(" hv=(\\d*) ");
	private static final Pattern	adPattern			= Pattern.compile(" ad='([^']*)");
	// private StringTokenizer tokenizer;
	private static Logger			logger				= Logger.getLogger(Parsing.class.getName());
	/**
	 * Length of SQL statement.
	 *
	 * @todo should probably not keep this around.
	 */
	private final int				sqlTextLength;
	/**
	 * Recursion depth
	 */
	private final int				recursionDepth;
	private final int				uid;
	/*
	 * oct Oracle command type.
	 */
	private final int				oracleCommandType;
	private final int				lid;
	private final long			sqlTextHashValue;
	private final long			timestamp;
	/**
	 * ad SQLTEXT address (see <View:V$SQLAREA> and <View:V$SQLTEXT>).
	 */
	private final String			address;
	private StringBuilder	lineBuffer			= new StringBuilder();

	// private ArrayList<String> lines = new ArrayList();
	// @todo convert to regex
	public Parsing(final String stmt, final int lineNumber) {
		final Matcher cursorNumberMatcher = cursorNumberPattern.matcher(stmt);
		final Matcher lenMatcher = lenPattern.matcher(stmt);
		final Matcher depMatcher = depPattern.matcher(stmt);
		final Matcher uidMatcher = uidPattern.matcher(stmt);
		final Matcher octMatcher = octPattern.matcher(stmt);
		final Matcher lidMatcher = lidPattern.matcher(stmt);
		final Matcher timMatcher = timPattern.matcher(stmt);
		final Matcher hvMatcher = hvPattern.matcher(stmt);
		final Matcher adMatcher = adPattern.matcher(stmt);
		if (!cursorNumberMatcher.find()) {
			throw new IllegalStateException("cursorNumberMatcher failed on " + stmt);
		}
		if (!lenMatcher.find()) {
			throw new IllegalStateException("lenMatcher failed ");
		}
		if (!depMatcher.find()) {
			throw new IllegalStateException("depMatcher failed ");
		}
		if (!uidMatcher.find()) {
			throw new IllegalStateException("uidMatcher failed ");
		}
		if (!octMatcher.find()) {
			throw new IllegalStateException("octMatcher failed ");
		}
		if (!lidMatcher.find()) {
			throw new IllegalStateException("lidMatcher failed ");
		}
		if (!timMatcher.find()) {
			throw new IllegalStateException("timMatcher failed ");
		}
		if (!hvMatcher.find()) {
			throw new IllegalStateException("hvMatcher failed ");
		}
		if (!adMatcher.find()) {
			throw new IllegalStateException("adMatcher failed ");
		}

		int cursorNumber;
		setCursorNumber(cursorNumber = Integer.parseInt(cursorNumberMatcher.group(1)));
		sqlTextLength = Integer.parseInt(lenMatcher.group(1));
		recursionDepth = Integer.parseInt(depMatcher.group(1));
		uid = Integer.parseInt(uidMatcher.group(1));
		oracleCommandType = Integer.parseInt(octMatcher.group(1));
		timestamp = Long.parseLong(timMatcher.group(1));
		lid = Integer.parseInt(lidMatcher.group(1));
		sqlTextHashValue = Long.parseLong(hvMatcher.group(1));
		address = adMatcher.group(1);
		setLineNumber(lineNumber);
	}

	public void addLine(final String line) {
		if (lineBuffer.length() > 0) {
			lineBuffer.append("\n");
		}
		lineBuffer.append(line);
	}

	// @todo ensure called
	public void clean() {
		lineBuffer = null;
	}

	/**
	 * @return Returns the address in hex at which the cursor was located in the
	 *         SGA.
	 *
	 * Identified by ad in the trace file.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return Returns the dep.
	 *
	 * dep is the tag for recursion depth.
	 */
	public int getDepth() {
		return recursionDepth;
	}

	/**
	 * @return The hash value of the sqlText as store in the SGA.
	 *
	 * Not really useful here other than for checking duplicate statements.
	 */
	public long getHashValue() {
		return sqlTextHashValue;
	}

	/**
	 * @return Returns the lid.
	 */
	public int getLid() {
		return lid;
	}

	/**
	 * @return Returns the oct.
	 *
	 * Oct is the Oracle Command Type.
	 *
	 * @todo document what the value means
	 */
	public int getOracleCommandType() {
		return oracleCommandType;
	}

	public RecordType getRecordType() {
		return RecordType.PARSING;
	}

	public String getSqlText() {
		return lineBuffer.toString();
	}

	/**
	 * @return Returns the len.
	 *
	 * Len is the length of the statement text.
	 */
	public int getSqlTextLength() {
		return sqlTextLength;
	}

	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @return Returns the uid.
	 */
	public int getUid() {
		return uid;
	}

	@Override
	public String toString() {
//		StringBuilder b = new StringBuilder();
		final StringBuilderHelper b = new StringBuilderHelper();
		b.setPairSeparator(" ");
		b.setValueSeparator(":");
		b.addNameValue("type", "PARSE");
		b.addNameValue("depth",recursionDepth);
		b.addNameValue("uid", uid);
		b.addNameValue("oct",oracleCommandType);
		b.addNameValue("lid",lid);
		b.addNameValue("tim",timestamp);
		b.addNameValue("address", address);
		b.addNameValue("hash",sqlTextHashValue);
		b.addNameValue("commandType", oracleCommandType);


		b.addNameValue("sql",lineBuffer.toString());

		return b.toString();
	}
}
