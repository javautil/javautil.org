package org.javautil.oracle.stats;

public class SessionStatFormatter {

	private final StatNames statNames;

	private final String newline = System.getProperty("line.separator");

	public SessionStatFormatter(final StatNames statNames) {
		this.statNames = statNames;
	}

	public String format(final SessionStats stats) {
		final StringBuilder sb = new StringBuilder();
		for (final StatName statName : statNames) {
			final Long value = stats.getStatValue(statName.getStatisticNbr());
			if (value != null) {
				// todo need to pad out
				sb.append(statName.getName());
				sb.append(" ");
				sb.append(value);
				sb.append(newline);
			}
		}
		return sb.toString();
	}
}
