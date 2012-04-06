/**
 * @(#) Wait.java
 */
package com.dbexperts.oracle.trace.record;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * WAIT #<CURSOR>: nam="<event name>" ela=0 p1=0 p2=0 p3=0
 * ----------------------------------------------------------------------------
 *
 * WAIT An event that we waited for.
 *
 * nam What was being waited for . The wait events here are the same as are seen
 * in <View:V$SESSION_WAIT>. For any Oracle release a full list of wait events
 * and the values in P1, P2 and P3 below can be seen in <View:V$EVENT_NAME> ela
 * Elapsed time for the operation. p1 P1 for the given wait event. p2 P2 for the
 * given wait event. p3 P3 for the given wait event.
 *
 * Example (Full Table Scan):
 *
 * WAIT #1: nam="db file scattered read" ela= 5 p1=4 p2=1435 p3=25
 *
 * WAITing under CURSOR no 1 for "db file scattered read" We waited 0.05 seconds
 * For a read of: File 4, start block 1435, for 25 Oracle blocks
 *
 * Example (Index Scan):
 *
 * WAIT #1: nam="db file sequential read" ela= 4 p1=4 p2=1224 p3=1
 *
 * WAITing under CURSOR no 1 for "db file sequential read" We waited 0.04
 * seconds for a single block read (p3=1) from file 4, block 1224
 */
public class Wait extends AbstractCursorTraceEvent implements Record {
	private static Logger	logger		= Logger.getLogger(Wait.class.getName());
	// private long tim;
	private static Pattern    cursorNumberPattern = Pattern.compile("^WAIT #(\\d*):");
	private static Pattern	elaPattern	= Pattern.compile("ela= (\\d*)");
	// private static Pattern driverPattern = Pattern.compile("driver
	// id=(\\d*)");
	// private static Pattern bytesPattern = Pattern.compile("#bytes=(\\d*)");
	private static Pattern	p1Pattern	= Pattern.compile("p1=(\\d*)");
	private static Pattern	p2Pattern	= Pattern.compile("p2=(\\d*)");
	private static Pattern	p3Pattern	= Pattern.compile("p3=(\\d*)");
	private static Pattern	timPattern	= Pattern.compile("tim=(\\d*)");
	private static Pattern	namPattern	= Pattern.compile("nam='(.*)'");
	private int				statementNumber;
	private final String			waitType;
	private final long			ela;
	private long			driverId;
	private int				bytes;
	private final long			p1;
	private final long			p2;
	private final long			p3;
	private int				objNbr;
	public Wait(String stmt, final int lineNumber) {
		if (logger.isDebugEnabled()) {
			logger.debug("evaluating " + stmt);
		}

		final Matcher cursorNumberMatcher = cursorNumberPattern.matcher(stmt);
		if (!cursorNumberMatcher.find()) {
			throw new IllegalStateException("can't find cursor number for wait " + stmt);
		}
		final Matcher namMatcher = namPattern.matcher(stmt);
		final Matcher elaMatcher = elaPattern.matcher(stmt);
		final Matcher p1Matcher = p1Pattern.matcher(stmt);
		final Matcher p2Matcher = p2Pattern.matcher(stmt);
		final Matcher p3Matcher = p3Pattern.matcher(stmt);
		final Matcher timMatcher = timPattern.matcher(stmt);
		setLineNumber(lineNumber);
		final String cursorNumberText = cursorNumberMatcher.group(1);
		if (cursorNumberText == null) {
			throw new IllegalStateException("cant find cursorNumberText");
		}
		setCursorNumber(Integer.parseInt(cursorNumberText));
		ela = elaMatcher.find() ? Integer.parseInt(elaMatcher.group(1)) : -1;
		p1 = p1Matcher.find() ? Integer.parseInt(p1Matcher.group(1)) : -1;
		p2 = p2Matcher.find() ? Integer.parseInt(p2Matcher.group(1)) : -1;
		p3 = p3Matcher.find() ? Integer.parseInt(p3Matcher.group(1)) : -1;
		// super.setCompletionTime(timMatcher.find() ?
		// Long.parseLong(timMatcher.group(1)) : -1);
		waitType = namMatcher.find() ? namMatcher.group(1) : null;
		stmt = null;
	}

	public void clean() {
	}

	/**
	 * @return Returns the bytes.
	 */
	public int getBytes() {
		return bytes;
	}

	/**
	 * @return Returns the driverId.
	 */
	public long getDriverId() {
		return driverId;
	}

	/**
	 * @return Returns the ela.
	 */
	public long getElapsed() {
		return ela;
	}

	/**
	 * @return Returns the objNbr.
	 */
	public int getObjNbr() {
		return objNbr;
	}

	public long getP1() {
		return p1;
	}

	public long getP2() {
		return p2;
	}

	/**
	 * @return Returns the p3.
	 */
	public long getP3() {
		return p3;
	}

	public RecordType getRecordType() {
		return RecordType.WAIT;
	}

	/**
	 * @return Returns the nam.
	 */
	public String getWaitType() {
		return waitType;
	}

	public void setWaitType(final String waitType2) {
		// TODO Auto-generated method stub
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("id: ");
		b.append(statementNumber);
		b.append(" ");
		b.append(waitType);
		b.append(" elapsed ");
		b.append(ela);
		return b.toString();
	}
}
