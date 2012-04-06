package org.javautil.oracle.stats;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SessionStatsDAO {

	private static final String message = " did you 'grant select on sys.v_$mystat to public' ?";
	private static final String newline = System.getProperty("line.separator");

	/**
	 * grant select on v_$mystat to public;
	 */

	public static SessionStats getMySessionStats(final Connection conn)
			throws SQLException {
		final String sql = "select sid, statistic#, value from v$mystat where value > 0";
		final Statement s = conn.createStatement();
		ResultSet rset;
		try {
			rset = s.executeQuery(sql);
		} catch (final SQLException sqe) {
			throw new SQLException(message + " " + sqe.getMessage());
		}
		SessionStats stats = null;
		while (rset.next()) {
			final int sid = rset.getInt(1);
			final int statNbr = rset.getInt(2);
			final long value = rset.getLong(3);
			final SessionStat ss = new SessionStat(sid, statNbr, value);
			if (stats == null) {
				stats = new SessionStats(sid);
			}
			stats.add(ss);
		}
		return stats;
	}
}
