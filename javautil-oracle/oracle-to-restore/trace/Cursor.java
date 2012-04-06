/**
 * @(#) Cursor.java
 */
package com.dbexperts.oracle.trace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.dbexperts.misc.EventHelper;
import com.dbexperts.oracle.trace.aggregation.CursorOperationAggregation;
import com.dbexperts.oracle.trace.record.CursorEvent;
import com.dbexperts.oracle.trace.record.CursorOperation;
import com.dbexperts.oracle.trace.record.Exec;
import com.dbexperts.oracle.trace.record.Fetch;
import com.dbexperts.oracle.trace.record.Parsing;
import com.dbexperts.oracle.trace.record.RecordType;
import com.dbexperts.oracle.trace.record.Stat;
import com.dbexperts.oracle.trace.record.Wait;

/**
 *
 * There is nothing to aggregate on a Parsing.
 * @todo fix adding 1 to fetch count even if there is no fetch, maybe just on aggregation.
 */
public class Cursor {
	private static ArrayList<Stat>		emptyStats				= new ArrayList<Stat>();
	private static Logger						logger					= Logger.getLogger(Cursor.class.getName());
	private static EventHelper events = new EventHelper();
	private static final Integer LOG_ADD_EVENT = 100;
	private static final Integer LOG_ADD_CHILD = 300;
	/**
	 *
	 */
	private String						key;

	/**
	 *
	 */
	private Parsing						parsing;

	/**
	 *
	 */
	private TreeMap<Integer, Stat>		stats;
	/**
	 *
	 */
	private ArrayList<Wait>				waits;
	/**
	 *
	 */
	/**
	 *
	 */
	private ArrayList<Fetch>			fetches;

	/**
	 *
	 */
	private ArrayList<Exec>				execs;
	private int cursorId;
	/**
	 *
	 */
	/**
	 * Depth, Cursor Key,
	 */
	private final TreeMap<String, Cursor>		childrenByKey			= new TreeMap<String, Cursor>();
	private final HashSet<Cursor>				children				= new HashSet<Cursor>();
	private boolean						aggregateOnly			= false;


	private CursorOperationAggregation	fetchAggregation;

	private CursorOperationAggregation	execAggregation;
	private CursorOperationAggregation	parseAggregation;

	private CursorOperationAggregation	unmapAggregation;
	private final String newline = System.getProperty("line.separator");
	private final boolean logConstructors = true;
	/**
	 * Keyed by wait event name
	 */
	private final HashMap<String, Object>		waitAggregations		= new HashMap<String, Object>();



	public Cursor(final int cursorId,final boolean warn) {
		if (cursorId < 1) {
			throw new IllegalArgumentException("cursor id must be > 0 ");
		}
		if (warn) {
			logger.warn("Cursor instantiated without a Parsing");
		}
		this.cursorId = cursorId;
		if (logConstructors) {
			logger.debug("creating cursor id " + this.cursorId);
		}
	}

	public Cursor(final Parsing p, final int cursorId) {
		if (cursorId < 1) {
			throw new IllegalArgumentException("cursor id must be > 0 ");
		}
		if (p == null) {
			logger.warn("cursor instantiated with a null parsing");
		} else {
			key = getKey();
		}
		this.cursorId = cursorId;
		parsing = p;
		if (logConstructors) {
			logger.debug("creating cursorId "  + this.cursorId);
		}
	}


	public void add(final CursorEvent rec) {
		if (events.exists(LOG_ADD_EVENT)) {
			logger.info("received " + rec + "\n");
			logger.info(" before:\n" + toString());
		}
		final RecordType r = rec.getRecordType();
		if (r == null) {
			throw new IllegalArgumentException("event has null TraceRecordType");
		}
		if (parsing == null && !rec.getRecordType().equals(RecordType.PARSING) && rec.getCursorNumber() != 0) {
			logger.warn("parsing record not found for this cursor  found at line # " + rec.getLineNumber());
		}
		if (!aggregateOnly) {
			addRecord(rec);
		}
		aggregate(rec);
		if (events.exists(LOG_ADD_EVENT)) {
			logger.info(" after:\n" + toString());
		}
	}

	public void add(final Exec event) {
		if (execs == null) {
			execs = new ArrayList<Exec>();
		}
		execs.add(event);
	}

	public void add(final Fetch event) {
		if (fetches == null) {
			fetches = new ArrayList<Fetch>();
		}
		fetches.add(event);
	}

	public void add(final Stat event) {
		if (stats == null) {
			stats = new TreeMap<Integer, Stat>();
		}
		stats.put(event.getPosition(), event);
	}

	public void add(final Wait event) {
		if (waits == null) {
			waits = new ArrayList<Wait>();
		}
		waits.add(event);
	}

	public void addChild(final Cursor child) {
		if (events.exists(LOG_ADD_CHILD)) {
		logger.info("adding child " + child.getKey() + " to " + getKey());
		}
		final String childKey = child.getKey();
		childrenByKey.put(childKey, child);
	}

	/**
	 * @param execAggregation the execAggregation to set
	 */
	public void addChildren(final Collection<Cursor> children) {
		this.children.addAll(children);
	}

	/**
	 * It is possible this returns null if tracing was turned on after the statement was parsed.
	 * @return
	 */
	public String getAddress() {
		return parsing == null ? null : parsing.getAddress();
	}

	public Collection<Cursor> getChildren() {
		return children;
	}

	public Collection<Cursor> getChildrenByKey() {
		return childrenByKey.values();
	}

	/**
	 * @return the cursorId
	 */
	public int getCursorId() {
		return cursorId;
	}

	/**
	 * @return the execAggregation
	 */
	public CursorOperationAggregation getExecAggregation() {
		return execAggregation;
	}

	/**
	 * @return the execs
	 */
	public ArrayList<Exec> getExecs() {
		return execs;
	}

	/**
	 * @return the fetchAggregation
	 */
	public CursorOperationAggregation getFetchAggregation() {
		return fetchAggregation;
	}

	public CursorOperationAggregation getFetchAggregation(final boolean createIfNull) {
		if (fetchAggregation == null) {
			fetchAggregation = new CursorOperationAggregation();
		}
		return fetchAggregation;
	}

	/**
	 * @return the fetches
	 */
	public ArrayList<Fetch> getFetches() {
		return fetches;
	}

	public Long getHashValue() {
		return parsing == null ? -1 : parsing.getHashValue();
	}

	public String getKey() {
		if (key == null) {
			if (getAddress() != null && getHashValue() != null) {
				key = getAddress() + "-" + getHashValue();
			}
		}
		return key;
	}

	/**
	 * @return the parseAggregation
	 */
	public CursorOperationAggregation getParseAggregation() {
		return parseAggregation;
	}

	/**
	 * @return the parsing
	 */
	public Parsing getParsing() {
		return parsing;
	}

	/**
	 * @return the stats
	 *
	 */
	public Collection<Stat> getStats() {
		Collection<Stat> returnValue;
		if (stats == null) {
			returnValue = emptyStats;
		} else {
			returnValue = stats.values();
		}
		return returnValue;
	}

	/**
	 * @return the unmapAggregation
	 */
	public CursorOperationAggregation getUnmapAggregation() {
		return unmapAggregation;
	}



	/**
	 * @return the waitAggregations
	 */
	public HashMap<String, Object> getWaitAggregations() {
		return waitAggregations;
	}

	/**
	 * @return the waits
	 */
	public ArrayList<Wait> getWaits() {
		return waits;
	}

	//	/**
	//	 * @param key the key to set
	//	 */
	//	public void setKey(String key) {
	//		this.key = key;
	//	}
	public boolean matches(final Cursor cursor) {
		boolean returnValue = false;
		if (cursor == null) {
			throw new IllegalArgumentException("cursor argument is null");
		}
		final String thisKey = getKey();
		final String otherKey = cursor.getKey();
		if (thisKey != null && otherKey != null) {
			returnValue = thisKey.equals(otherKey);
		}
		return returnValue;
	}
	/**
	 * Aggregation policy.
	 *
	 * If true (default) operations, (parse, exec, fetch) are aggregated and the individual
	 * trace records are not stored.
	 * @param value
	 */
	public void setAggregateOnly(final boolean value) {
		aggregateOnly = value;
	}

	/**
	 * @param cursorId the cursorId to set
	 */
	public void setCursorId(final int cursorId) {
		this.cursorId = cursorId;
	}

	/**
	 * @param execAggregation the execAggregation to set
	 */
	public void setExecAggregation(final CursorOperationAggregation execAggregation) {
		this.execAggregation = execAggregation;
	}

	/**
	 * @param execs the execs to set
	 */
	public void setExecs(final ArrayList<Exec> execs) {
		this.execs = execs;
	}

	/**
	 * @param fetchAggregation the fetchAggregation to set
	 */
	public void setFetchAggregation(final CursorOperationAggregation fetchAggregation) {
		this.fetchAggregation = fetchAggregation;
	}

	/**
	 * @param fetches the fetches to set
	 */
	public void setFetches(final ArrayList<Fetch> fetches) {
		this.fetches = fetches;
	}


	/**
	 * @param parseAggregation the parseAggregation to set
	 */
	public void setParseAggregation(final CursorOperationAggregation parseAggregation) {
		this.parseAggregation = parseAggregation;
	}

	/**
	 * @param parsing the parsing to set
	 */
	public void setParsing(final Parsing parsing) {
		if (this.parsing != null) {
			throw new IllegalStateException("may only call this once per cursor invoke from line number " + parsing.getLineNumber()
					+ " was declared from " + this.parsing.getLineNumber());
		}
		this.parsing = parsing;
	}

	/**
	 * @param waits the waits to set
	 */
	public void setWaits(final ArrayList<Wait> waits) {
		this.waits = waits;
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();

		if (parsing != null) {
			b.append(parsing.toString());
			b.append(newline);
		}
		appendAggregation(b, parseAggregation);
		appendAggregation(b, fetchAggregation);
		appendAggregation(b, execAggregation);
		final String retval = b.toString();
		return retval;
	}

	private void addRecord(final CursorEvent rec) {
		final RecordType r = rec.getRecordType();
		switch (r) {
			case EXEC:
				add((Exec) rec);
				break;
			case WAIT:
				add((Wait) rec);
				break;
			case STAT:
				add((Stat) rec);
				break;
			case PARSING:
				setParsing((Parsing) rec);
				break;
			case PARSE:
				break;  // @todo is this ok?
			case FETCH:
				add((Fetch) rec);
				break;
			default:
				logger.info("uncategorized event on line " + rec.getLineNumber() + " " + rec);
		}
	}

	private void aggregate(final CursorEvent rec) {
		final RecordType r = rec.getRecordType();
		switch (r) {
			case EXEC:
			case PARSE:
			case FETCH:
			case UNMAP:
				aggregateOperation((CursorOperation) rec);
				break;
			case WAIT:
				aggregateWait((Wait) rec);
				break;
			case STAT:
				add((Stat) rec);
				break;
			case PARSING:
				if (logger.isDebugEnabled()) {
					logger.debug("not aggregating parsing");
				}
				break;
			default:
				logger.info("uncategorized event " + rec);
		}
	}

	private void aggregateOperation(final CursorOperation event) {
		final RecordType r = event.getRecordType();
		switch (r) {
			case EXEC:
				if (execAggregation == null) {
					execAggregation = new CursorOperationAggregation();
				}
				execAggregation.aggregate(event);
				break;
			case PARSE:
				if (parseAggregation == null) {
					parseAggregation = new CursorOperationAggregation();
				}
				parseAggregation.aggregate(event);
				break;
			case FETCH:
				if (fetchAggregation == null) {
					fetchAggregation = new CursorOperationAggregation();
				}
				fetchAggregation.aggregate(event);
				break;
			case UNMAP:
				if (unmapAggregation == null) {
					unmapAggregation = new CursorOperationAggregation();
				}
				unmapAggregation.aggregate(event);
				break;
			default:
				throw new IllegalStateException("unmapped operation");
		}
	}

	private void aggregateWait(final Wait event) {
		// todo implement
	}

	private void appendAggregation(final StringBuilder b, final CursorOperationAggregation o) {
		if (o != null) {
			b.append(o.toString());
			b.append("\n");
		}
	}


}
