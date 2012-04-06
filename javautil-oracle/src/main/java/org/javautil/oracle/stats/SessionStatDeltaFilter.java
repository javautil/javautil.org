package org.javautil.oracle.stats;

public class SessionStatDeltaFilter {
	/**
	 * Returns true if this is worthy of reporting.
	 * 
	 * You might want to suppress an entry if there is no change, so we need
	 * both values. You might want to suppress some stats, so we need the
	 * StatName,
	 * 
	 * In this case if they are not the same we return it and we ignore the stat
	 * itself.
	 * 
	 * @param stat
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	boolean reportAble(final StatName stat, final Long oldValue,
			final Long newValue) {

		return oldValue != null && !oldValue.equals(newValue);
	}
}
