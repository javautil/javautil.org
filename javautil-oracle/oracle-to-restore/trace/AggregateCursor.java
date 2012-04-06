package com.dbexperts.oracle.trace;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.dbexperts.misc.EventHelper;
import com.dbexperts.oracle.trace.aggregation.CursorOperationAggregation;

public class AggregateCursor extends Cursor {
	private static final Integer		AGGREGATING_TO		= 100;

	private static final Integer		AGGREGATION_CHANGE	= 200;

	private static EventHelper			events				= new EventHelper();

	private final Logger						logger				= Logger.getLogger(this.getClass().getName());

	private CursorOperationAggregation	recursiveFetchAggregation;

	private CursorOperationAggregation	recursiveExecAggregation;

	private CursorOperationAggregation	recursiveParseAggregation;

	private CursorOperationAggregation	recursiveUnmapAggregation;

	private final ArrayList<Cursor> cursors = new ArrayList<Cursor>();
	private boolean hasBeenAggregated = false;
	static {
		events.addEvent(AGGREGATING_TO);
	}

	public AggregateCursor(final Cursor c, final int cursorId) {
		super(cursorId, false);

	}

	public void addCursor(final Cursor c) {
		cursors.add(c);
	}
	public void aggregate(final Cursor o) {
		if (events.exists(AGGREGATING_TO)) {
			logger.info("aggregating to " + getKey() + " from " + o.getKey());
		}
		if (o.getFetchAggregation() != null) {
			if (getRecursiveFetchAggregation() == null) {
				setRecursiveFetchAggregation(new CursorOperationAggregation());
			}
		//	getRecursiveFetchAggregation().aggregate(o.getFetchAggregation());
			final CursorOperationAggregation agg = getFetchAggregation(true);
			agg.aggregate(o.getFetchAggregation());
		//	aggregateOperation(o.getFetchAggregation());
		}

		if (o.getExecAggregation() != null) {
			if (getRecursiveExecAggregation() == null) {
				setRecursiveExecAggregation(new CursorOperationAggregation());
			}
			if (events.exists(AGGREGATION_CHANGE)) {
				logger.info("recursiveExecAggregation before: " + getRecursiveExecAggregation().toString());
			}
			getRecursiveExecAggregation().aggregate(o.getExecAggregation());
			if (events.exists(AGGREGATION_CHANGE)) {
				logger.info("execAggregation after: " + getRecursiveExecAggregation().toString());
			}
		} else {
			logger.info("cursor " + o.getKey() + " has no ExecAggregation");
		}

		if (o.getParseAggregation() != null) {
			if (getRecursiveParseAggregation() == null) {
				setRecursiveParseAggregation(new CursorOperationAggregation());
			}
			getRecursiveParseAggregation().aggregate(o.getParseAggregation());
		}

		if (o.getUnmapAggregation() != null) {
			if (getRecursiveUnmapAggregation() == null) {
				setRecursiveUnmapAggregation(new CursorOperationAggregation());
			}
			getRecursiveUnmapAggregation().aggregate(o.getUnmapAggregation());
		}
	}
	public void aggregateNoRecurse(final Cursor o) {
		if (events.exists(AGGREGATING_TO)) {
			logger.info("aggregating to " + getKey() + " from " + o.getKey());
		}
		if (o.getFetchAggregation() != null) {

			final CursorOperationAggregation agg = getFetchAggregation(true);
			agg.aggregate(o.getFetchAggregation());
		//	aggregateOperation(o.getFetchAggregation());
		}


	}
	/**
	 * @return the recursiveExecAggregation
	 */
	public CursorOperationAggregation getRecursiveExecAggregation() {
		return recursiveExecAggregation;
	}

	/**
	 * @return the recursiveFetchAggregation
	 */
	public CursorOperationAggregation getRecursiveFetchAggregation() {
		return recursiveFetchAggregation;
	}

	/**
	 * @return the recursiveParseAggregation
	 */
	public CursorOperationAggregation getRecursiveParseAggregation() {
		return recursiveParseAggregation;
	}

	/**
	 * @return the recursiveUnmapAggregation
	 */
	public CursorOperationAggregation getRecursiveUnmapAggregation() {
		return recursiveUnmapAggregation;
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("Aggregation of " + cursors.size() + " cursors\n");
		for (int i = 0; i < cursors.size(); i++) {
			b.append("cursor # " + i + "\n");
			final Cursor c = cursors.get(i);
			b.append(c.toString());
		}
		if (! hasBeenAggregated) {
			aggregateCursors();
		}
		b.append("aggregate qtys");
		b.append("\nparse ");
		b.append(getParseAggregation().toString());
		b.append("\nexec ");
		b.append(getExecAggregation().toString());
		b.append("\nfetch ");
		b.append(getFetchAggregation().toString());
		return b.toString();
	}

	private void aggregateCursors() {
		final CursorOperationAggregation fetchAgg = new CursorOperationAggregation();
		final CursorOperationAggregation parseAgg = new CursorOperationAggregation();
		final CursorOperationAggregation execAgg = new CursorOperationAggregation();
	//	CursorOperationAggregation fetchAgg = new CursorOperationAggregation();
		for (final Cursor c : cursors) {
			fetchAgg.aggregate(c.getFetchAggregation());
			parseAgg.aggregate(c.getParseAggregation());
			execAgg.aggregate(c.getExecAggregation());
		}
		this.setFetchAggregation(fetchAgg);
		this.setParseAggregation(parseAgg);
		this.setExecAggregation(execAgg);
		hasBeenAggregated = true;
		//

	}

	/**
	 * @param recursiveExecAggregation
	 *            the recursiveExecAggregation to set
	 */
	protected void setRecursiveExecAggregation(final CursorOperationAggregation recursiveExecAggregation) {
		this.recursiveExecAggregation = recursiveExecAggregation;
	}

	/**
	 * @param recursiveFetchAggregation
	 *            the recursiveFetchAggregation to set
	 */
	protected void setRecursiveFetchAggregation(final CursorOperationAggregation recursiveFetchAggregation) {
		this.recursiveFetchAggregation = recursiveFetchAggregation;
	}

	/**
	 * @param recursiveParseAggregation
	 *            the recursiveParseAggregation to set
	 */
	protected void setRecursiveParseAggregation(final CursorOperationAggregation recursiveParseAggregation) {
		this.recursiveParseAggregation = recursiveParseAggregation;
	}

	/**
	 * @param recursiveUnmapAggregation
	 *            the recursiveUnmapAggregation to set
	 */
	protected void setRecursiveUnmapAggregation(final CursorOperationAggregation recursiveUnmapAggregation) {
		this.recursiveUnmapAggregation = recursiveUnmapAggregation;
	}
}
