package com.dbexperts.oracle.trace;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dbexperts.misc.EventHelper;
import com.dbexperts.oracle.trace.aggregation.CursorOperationAggregation;
import com.dbexperts.oracle.trace.record.Parsing;
import com.dbexperts.oracle.trace.record.Stat;
import com.dbexperts.oracle.trace.record.Wait;

public class CursorXmlFormatter implements CursorFormatter {
	private static EventHelper		events						= new EventHelper();

	private static final Integer	LOG_NO_AGGREGATION			= 1;

	private Element					root;

	private Element					waits;

	private Element					parsing;

	private final AggregateCursor			cursor;

	private final Logger					logger						= Logger.getLogger(this.getClass().getName());

	//private boolean					enumerateChildren			= true;

	private boolean					showRecursiveStatistics		= true;

	private boolean					showAggregateTimestamps		= false;

	private final boolean					showHashValue				= true;
	private int						rootChildCount				= 0;

	/**
	 * If true only shows non zero statistics.
	 *
	 * A value of true reduces the size of the output. A value of true may
	 * facilitate some XSLT models.
	 */
	private boolean					sparseAggregateStatistics	= true;

	static {
		events.addEvent(LOG_NO_AGGREGATION);
	}

	public CursorXmlFormatter(final AggregateCursor cursor) {
		this.cursor = cursor;
	}

	public Element asElement() {
		root = DocumentHelper.createElement("cursor");
		root.addAttribute("cursorId", Integer.toString(cursor.getCursorId()));
		formatOperations();
		formatStats();
		if (cursor instanceof AggregateCursor) {
			final AggregateCursor ag = cursor;
			ag.getExecAggregation();
		}
		if (cursor.getWaits() != null) {
			addToRoot(formatWaits());
		}
		// enumerateChildren();
		if (showRecursiveStatistics && hasRecursiveOperations()) {
			formatRecursives();
		}
		if (cursor.getWaits() != null) {
			addToRoot(formatWaits());
		}
		return root;
	}

	public String asString() {
		throw new UnsupportedOperationException();
	}

	public Element formatOperationAggregation(final CursorOperationAggregation ag, final String nm) {
		final Element el = DocumentHelper.createElement(nm);
		addAttribute(el, "cr", ag.getConsistentReadBlocks());
		addAttribute(el, "cpu", ag.getCpu());
		addAttribute(el, "count", ag.getEventCount());
		addAttribute(el, "ela", ag.getElapsedMicroSeconds());
		addAttribute(el, "current", ag.getCurrentModeBlocks());
		addAttribute(el, "mis", ag.getLibraryCacheMissCount());
		addAttribute(el, "disk", ag.getPhysicalBlocksRead());
		addAttribute(el, "cr", ag.getConsistentReadBlocks());
		if (showAggregateTimestamps) {
			el.addAttribute("earliest", Long.toString(ag.getMinimumTime()));
			el.addAttribute("latest", Long.toString(ag.getMaximumTime()));
		}
		return el;
	}

	// @todo is tim the time?
	/**
	 * @todo it's possible that there is no parsing record, but highly unlikely
	 */
	// private void enumerateChildren() {
	// if (enumerateChildren) {
	// Collection<Cursor> children = cursor.getChildren();
	// TreeMap<Long, Cursor> byParsingTimestamp = new TreeMap<Long, Cursor>();
	// for (Cursor child : children) {
	// Long parsingTimestamp = child.getParsing().getTimestamp();
	// byParsingTimestamp.put(parsingTimestamp, child);
	// }
	// logger.info("child count for " + cursor.getKey() + " " +
	// children.size());
	// if (children.size() > 0) {
	// Element kids = root.addElement("children");
	// for (Cursor child : byParsingTimestamp.values()) {
	// CursorXmlFormatter cxf = new CursorXmlFormatter(child);
	// kids.add(cxf.asElement());
	// }
	// }
	// }
	// }
	/**
	 * @todo resolve the lid - Logical id to the user name.
	 * @return
	 */
	public Element formatParsing() {
		final Parsing rec = cursor.getParsing();
		parsing = DocumentHelper.createElement("parsing");
		final String sqlText = rec.getSqlText();
		parsing.addAttribute("sql", sqlText);
		if (showHashValue) {
			parsing.addAttribute("hv", Long.toString(rec.getHashValue()));
		}
		parsing.addAttribute("lid", Integer.toString(rec.getLid()));
		return parsing;
	}

	public Element formatWaits() {
		final ArrayList<Wait> w = cursor.getWaits();
		if (w != null) {
			waits = DocumentHelper.createElement("waits");
			for (final Wait wr : w) {
				final Element wre = waits.addElement("wait");
				wre.addAttribute("waitType", wr.getWaitType());
				wre.addAttribute("count", Long.toString(wr.getElapsed()));
			}
		}
		return waits;
	}

	/**
	 * @return the showAggregateTimestamps
	 */
	public boolean isShowAggregateTimestamps() {
		return showAggregateTimestamps;
	}

	/**
	 * @return the showRecursiveStatistics
	 */
	public boolean isShowRecursiveStatistics() {
		return showRecursiveStatistics;
	}

	/**
	 * @param showAggregateTimestamps
	 *            the showAggregateTimestamps to set
	 *
	 * If true timestamps for the first and last time the operation was
	 * encountered in the trace file is reported.
	 */
	public void setShowAggregateTimestamps(final boolean showAggregateTimestamps) {
		this.showAggregateTimestamps = showAggregateTimestamps;
	}

	/**
	 * @param showRecursiveStatistics
	 *            If true, the statistics for recursive sql is added to the log.
	 *            Almost useless without this.
	 *
	 */
	public void setShowRecursiveStatistics(final boolean showRecursiveStatistics) {
		this.showRecursiveStatistics = showRecursiveStatistics;
	}

	private void addAttribute(final Element el, final String attributeName, final long statistic) {
		if (statistic != 0 || !sparseAggregateStatistics) {
			el.addAttribute(attributeName, Long.toString(statistic));
		}
	}

	private void addToRoot(final Element el) {
		rootChildCount++;
		if (rootChildCount % 1000 == 0) {
			logger.warn("root child count is " + rootChildCount);
		}
		root.add(el);
	}

	private void formatOperations() {
		if (cursor.getParsing() != null) {
			root.add(formatParsing());
		}

		if (cursor.getParseAggregation() != null) {
			addToRoot(formatOperationAggregation(cursor.getParseAggregation(), "parse"));
		} else {
			if (events.exists(LOG_NO_AGGREGATION)) {
				logger.warn("cursor " + cursor.getCursorId() + " has no parse aggregation");
			}
		}

		if (cursor.getExecAggregation() != null) {
			addToRoot(formatOperationAggregation(cursor.getExecAggregation(), "exec"));
		} else {
			if (events.exists(LOG_NO_AGGREGATION)) {
				logger.warn("cursor " + cursor.getCursorId() + " has no exec aggregation");
			}
		}

		if (cursor.getFetchAggregation() != null) {
			addToRoot(formatOperationAggregation(cursor.getFetchAggregation(), "fetches"));
		} else {
			if (events.exists(LOG_NO_AGGREGATION)) {
				logger.warn("cursor " + cursor.getCursorId() + " has no fetch aggregation");
			}
		}
	}

	private void formatRecursives() {
		final Element recursiveEl = root.addElement("recursive");
		CursorOperationAggregation oa = null;
		oa = cursor.getRecursiveParseAggregation();
		if (oa != null && oa.getEventCount() > 0) {
			recursiveEl.add(formatOperationAggregation(oa, "parse"));
		} else {
			logger.info("no recursiveParse");
		}
		oa = cursor.getRecursiveExecAggregation();
		if (oa != null && oa.getEventCount() > 0) {
			recursiveEl.add(formatOperationAggregation(oa, "exec"));
		}
		oa = cursor.getRecursiveFetchAggregation();
		if (oa != null && oa.getEventCount() > 0) {
			recursiveEl.add(formatOperationAggregation(oa, "fetch"));
		}
	}

	private void formatStats() {
		final Collection<Stat> stats = cursor.getStats();
		final Element elStats = root.addElement("stats");
		for (final Stat stat : stats) {
			final Element statEl = elStats.addElement("stat");
			statEl.addAttribute("pos", Integer.toString(stat.getPosition()));
			statEl.addAttribute("op", stat.getOperation());
			addAttribute(statEl, "pr", stat.getPhysicalReads());
			addAttribute(statEl, "pw", stat.getPhysicalWrites());
			addAttribute(statEl, "cr", stat.getCr());
			addAttribute(statEl, "obj", stat.getObjectNumber());
		}
	}

	private boolean hasRecursiveOperations() {
		final boolean returnValue = cursor.getRecursiveFetchAggregation() != null || cursor.getRecursiveExecAggregation() != null
				|| cursor.getRecursiveParseAggregation() != null || cursor.getRecursiveUnmapAggregation() != null;
		return returnValue;
	}
}
