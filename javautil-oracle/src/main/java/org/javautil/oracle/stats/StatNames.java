package org.javautil.oracle.stats;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatNames implements Iterable<StatName> {
	private Map<Integer, StatName> statsByNumber = new LinkedHashMap<Integer, StatName>();

	private static final String grant = "grant select on v_$statname to public";

	/**
	 * @return the statsByNumber
	 */
	public Map<Integer, StatName> getStatsByNumber() {
		return statsByNumber;
	}

	public void add(final StatName statName) {
		statsByNumber.put(statName.getStatisticNbr(), statName);
	}

	public Map<Integer, StatName> populate(final Connection conn)
			throws SQLException {
		final String sql = "select " + " STATISTIC#, " + " NAME, " + " CLASS, "
				+ " STAT_ID " + " from v$statname order by class, statistic#";

		statsByNumber = new LinkedHashMap<Integer, StatName>();

		Statement s = null;

		// todo test and review try
		s = conn.createStatement();
		ResultSet rset;
		try {
			rset = s.executeQuery(sql);
		} catch (final SQLException sqe) {
			if (s != null && !s.isClosed()) {
				s.close(); // todo test
			}
			throw new SQLException(grant + sqe.getMessage());
		}
		while (rset.next()) {
			final int statNbr = rset.getInt(1);
			final String name = rset.getString(2);
			final int statClass = rset.getInt(3);
			final long id = rset.getLong(4);
			final StatName sn = new StatName(statNbr, name, statClass, id);
			add(sn);
		}
		s.close();

		return statsByNumber;

	}

	public int getMaxStatNameLength() {
		int max = 0;
		for (final StatName statName : statsByNumber.values()) {
			if (statName.getName().length() > max) {
				max = statName.getName().length();
			}
		}
		return max;
	}

	@Override
	public Iterator<StatName> iterator() {
		if (statsByNumber == null) {
			throw new IllegalStateException("populate has not been called");
		}
		return statsByNumber.values().iterator();
	}

	public String getNameOfStatNumber(int i) {
		// TODO should check if null and throw nice Exception
		return statsByNumber.get(i).getName();
	}

}
