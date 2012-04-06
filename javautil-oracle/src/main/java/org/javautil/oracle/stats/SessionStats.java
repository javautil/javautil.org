package org.javautil.oracle.stats;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class SessionStats {
	private final Integer sid;

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * Stats mapped by statistic number.
	 */
	private final Map<Integer, Long> statsByStatisticNbr = new HashMap<Integer, Long>();

	private final long snaptimeNanos = System.nanoTime(); // in millis

	public SessionStats(final int sid) {
		this.sid = sid;
	}

	public void add(final SessionStat stat) {
		statsByStatisticNbr.put(stat.getStatNbr(), stat.getValue());
	}

	public Long getStatValue(final Integer statNumber) {
		return statsByStatisticNbr.get(statNumber);
	}

	public SessionStats getDelta(final SessionStats oldValues) {
		return getDelta(oldValues, false);
	}

	public SessionStats getDelta(final SessionStats oldValues,
			final boolean showNoChange) {

		final SessionStats delta = new SessionStats(sid);
		for (final Integer statNbr : statsByStatisticNbr.keySet()) {
			final Long oldValue = oldValues.statsByStatisticNbr.get(statNbr);

			if (oldValue == null) {
				// logger.debug("no old value for stat " + statNbr + " using " +
				// statsByStatisticNbr.get(statNbr));
				delta.statsByStatisticNbr.put(statNbr,
						statsByStatisticNbr.get(statNbr));
			} else {
				final long diff = statsByStatisticNbr.get(statNbr) - oldValue;
				// logger.debug("stat exists twice for " + statNbr + " old " +
				// oldValue + " new " + statsByStatisticNbr.get(statNbr) +
				// " diff " + diff );

				if (diff > 0 || showNoChange) {
					delta.statsByStatisticNbr.put(statNbr, diff);
				}
			}
		}
		for (final Integer statNbr : oldValues.statsByStatisticNbr.keySet()) {
			if (statsByStatisticNbr.get(statNbr) == null) {
				final long deltaValue = -1
						* oldValues.statsByStatisticNbr.get(statNbr);
				// logger.debug("no  new Value for stat " + statNbr + " using "
				// + deltaValue);
				delta.statsByStatisticNbr.put(statNbr, deltaValue);
			}

		}
		return delta;
	}

}
