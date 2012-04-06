package org.javautil.oracle.stats;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SessionStatDeltaFormatter {

	private final StatNames statNames;
	private final Logger logger = Logger.getLogger(getClass());
	private final String newline = System.getProperty("line.separator");
	private final SessionStatDeltaFilter filter = new SessionStatDeltaFilter();
	private final DecimalFormat nf = new DecimalFormat("###,###,###,###");

	public SessionStatDeltaFormatter(final StatNames statNames) {
		this.statNames = statNames;

	}

	public String format(final SessionStats oldStats,
			final SessionStats newStats) {
		final StringBuilder sb = new StringBuilder();
		final StatNames reportableStats = new StatNames();
		for (final StatName statName : statNames) {
			final Long oldStat = oldStats.getStatValue(statName
					.getStatisticNbr());
			final Long newStat = newStats.getStatValue(statName
					.getStatisticNbr());
			if (filter.reportAble(statName, oldStat, newStat)) {
				reportableStats.add(statName);
			}
		}

		final int padTo = reportableStats.getMaxStatNameLength() + 1;
		final int padStatLength = 11;
		sb.append(StringUtils.rightPad("Statistic Name", padTo));
		sb.append(StringUtils.leftPad("old", padStatLength));
		sb.append(StringUtils.leftPad("new", padStatLength));
		sb.append(StringUtils.leftPad("difference", padStatLength));
		sb.append(newline);
		for (final StatName statName : reportableStats) {
			final Long oldStat = oldStats.getStatValue(statName
					.getStatisticNbr());
			final Long newStat = newStats.getStatValue(statName
					.getStatisticNbr());
			sb.append(formatDelta(statName, oldStat, newStat, padTo,
					padStatLength));
			sb.append(newline);
		}
		return sb.toString();
	}

	/**
	 * Format the data based on the presumption that null values represent zero.
	 * 
	 * In the interest of reducing.. todo explain why a null represents a zero
	 * as it was never stored if it was zero // todo this sucks
	 * 
	 * @param stat
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	String formatDelta(final StatName stat, final Long oldValue,
			final Long newValue, final int padToLength, final int padStatLength) {

		final long oldV = oldValue != null ? oldValue.longValue() : 0;
		final long newV = newValue != null ? newValue.longValue() : 0;
		final String retval = //
		StringUtils.rightPad(stat.getName(), padToLength) + //
				StringUtils.leftPad(nf.format(oldV), padStatLength) + //
				StringUtils.leftPad(nf.format(newV), padStatLength) + //
				StringUtils.leftPad(nf.format(newV - oldV), padStatLength);
		return retval;
	}
}
