package com.dbexperts.oracle.trace.aggregation;

import org.apache.log4j.Logger;

import com.dbexperts.misc.EventHelper;
import com.dbexperts.oracle.trace.record.CursorOperation;
import com.dbexperts.oracle.trace.record.RecordType;

public class CursorOperationAggregation {



	private static EventHelper events = new EventHelper();

	private static final Integer LOG_AGGREGATE_CURSOR_OPERATION = Integer.valueOf(1);

	private static final Integer 	LOG_CURSOR_OPERATION_AGGREGATE = Integer.valueOf(2);

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private RecordType recordType = null;

	private long minimumTime;

	private long maximumTime;

	private int  eventCount;

	/**
	 * Total cpu consumption for call.
	 * Represented in trace file as c=%d
	 * c       CPU time (100th's of a second in Oracle7 ,8 and 9).
	 */
	private int		cpu;
	/**
	 * Elapsed duration of the call.
	 * In trace file as "e".
	 */
	/**
	 * Elapsed curation of timed event;
	 */
	private long	elapsedMicroSeconds;
	//     private long completionTime;
	/**
	 * Number of database blocks obtained by O.S. read calls.
	 *
	 * In trace file as "p".
	 */
	private int		physicalBlocksRead;
	/**
	 * Number of blocks read from cache in consistent mode.
	 *
	 * In trace file as "cr".
	 */
	private int		consistentReadBlocks;
	/**
	 * Number of blocks retrieved from cache in current mode.
	 * In trace file as "cu".
	 */
	private int		currentModeBlocks;
	/**
	 * Number of misses on the library cache.
	 */
	private int		libraryCacheMissCount;
	/**
	 * Number of rows returned.  In trace file as "r".
	 */
	private int		rowCount;
	/**
	 * Recursive depth.
	 * In trace file as "dep".
	 */
	static {
		events.addEvent(LOG_AGGREGATE_CURSOR_OPERATION);
		events.addEvent(LOG_CURSOR_OPERATION_AGGREGATE);
	}

//	private int		depth;
//	/**
//	 * Optimizer goal. In trace file as "og".
//	 * og      Optimizer goal: 1=All_Rows, 2=First_Rows, 3=Rule, 4=Choose
//	 */
//	private int		optimizerGoal;
//	private int		cursorNumber;

	/**
	 * Total cpu consumption for call.
	 * Represented in trace file as c=%d
	 * c       CPU time (100th's of a second in Oracle7 ,8 and 9).
	 */
	public void aggregate(final CursorOperation o) {
		if (events.exists(LOG_AGGREGATE_CURSOR_OPERATION)) {
			logger.info("aggregate CursorOperation before " + toString());
			logger.info("operation: " + o.toString());
		}
		if (recordType == null) {
			recordType = o.getRecordType();
		} else {
			if (! recordType.equals(o.getRecordType())) {
				logger.warn("record types don't match " + toString() + " " + o.toString());
			}
		}
		eventCount++;
		cpu += o.getCpu();
		elapsedMicroSeconds += o.getElapsedMicroSeconds();
		physicalBlocksRead += o.getPhysicalBlocksRead();
		consistentReadBlocks += o.getConsistentReadBlocks();
		currentModeBlocks += o.getCurrentModeBlocks();
		libraryCacheMissCount += o.getLibraryCacheMissCount();
		setMinimumTime(o.getTime());
		setMaximumTime(o.getTime());
		rowCount += o.getRowCount();
		if (events.exists(LOG_AGGREGATE_CURSOR_OPERATION)) {
			logger.info("after: " + toString());
		}
	}
	public void aggregate(final CursorOperationAggregation o) {
		StringBuilder b = null;
		if (events.exists(LOG_CURSOR_OPERATION_AGGREGATE )) {
			b = new StringBuilder();
			b.append("aggregate CursorOperationAggregation");
			b.append("\nbefore: ");
			b.append(toString());
			b.append("\n");
			b.append("other: ");
			if (o == null) {
				b.append("null");
			} else {
			b.append(o.toString());
			}
			if (o == null) {
				logger.info("CursorOperationAggregation.aggregate o is null");
			}
		}
		if (!( o == null)) {
		eventCount++;
		cpu += o.getCpu();
		final long elapsed = o.getElapsedMicroSeconds();
		if (events.exists(LOG_CURSOR_OPERATION_AGGREGATE)) {
			if (b == null) {
				b = new StringBuilder();
			}
			b.append("elapsed: " + elapsed + "\n");
		}
		elapsedMicroSeconds += o.getElapsedMicroSeconds();
		physicalBlocksRead += o.getPhysicalBlocksRead();
		consistentReadBlocks += o.getConsistentReadBlocks();
		currentModeBlocks += o.getCurrentModeBlocks();
		libraryCacheMissCount += o.getLibraryCacheMissCount();
		setMinimumTime(o.getMinimumTime());
		setMaximumTime(o.getMaximumTime());
		rowCount += o.getRowCount();
		}
		if (events.exists(LOG_CURSOR_OPERATION_AGGREGATE )) {
			if (b == null) {
				b = new StringBuilder();
			}
			b.append("\nafter: ");
			b.append(toString());
			logger.info(b.toString());
		}
	}

	public int getConsistentReadBlocks() {
		return consistentReadBlocks;
	}

	//	public void setConsistentReadBlocks(int consistentReadBlocks) {
//		this.consistentReadBlocks = consistentReadBlocks;
//	}
	public int getCpu() {
		return cpu;
	}
 	//	public void setCpu(int cpu) {
//		this.cpu = cpu;
//	}
	public int getCurrentModeBlocks() {
		return currentModeBlocks;
	}
//	public void setCurrentModeBlocks(int currentModeBlocks) {
//		this.currentModeBlocks = currentModeBlocks;
//	}
	public long getElapsedMicroSeconds() {
		return elapsedMicroSeconds;
	}
//	public void setElapsedMicroSeconds(long elapsedMicroSeconds) {
//		this.elapsedMicroSeconds = elapsedMicroSeconds;
//	}
	public int getEventCount() {
		return eventCount;
	}
//	public void setEventCount(int eventCount) {
//		this.eventCount = eventCount;
//	}
	public int getLibraryCacheMissCount() {
		return libraryCacheMissCount;
	}
public long getMaximumTime() {
		return maximumTime;
	}
public long getMinimumTime() {
		return minimumTime;
	}
//	public void setLibraryCacheMissCount(int libraryCacheMissCount) {
//		this.libraryCacheMissCount = libraryCacheMissCount;
//	}
	public int getPhysicalBlocksRead() {
		return physicalBlocksRead;
	}
//	public void setPhysicalBlocksRead(int physicalBlocksRead) {
//		this.physicalBlocksRead = physicalBlocksRead;
//	}
	public int getRowCount() {
		return rowCount;
	}
//	public void setRowCount(int rowCount) {
//		this.rowCount = rowCount;
//	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
        b.append("type: " + recordType + " ");
        b.append("count " + eventCount + " ");
		b.append("cpu: " + cpu + " ");
		b.append("elapsed: " + elapsedMicroSeconds + " ");
		b.append("pr: " + physicalBlocksRead + " ");
		b.append("pw: " + consistentReadBlocks + " ");
		b.append("cu: " + currentModeBlocks + " ");
		b.append("mis: " + libraryCacheMissCount + " ");
	//	b.append("time: " + time);
	//	b.append("dep" + depth + " ");
	//	b.append("og: " + optimizerGoal + " ");
		return b.toString();
	}

	private void setMaximumTime(final long time) {
		if (time > maximumTime) {
			maximumTime = time;
		}
	}

	private void setMinimumTime(final long time) {
		if (minimumTime == 0 || time < minimumTime) {
			minimumTime = time;
		}
	}
}
