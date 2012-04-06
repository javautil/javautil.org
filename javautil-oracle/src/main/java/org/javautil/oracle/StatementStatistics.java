package org.javautil.oracle;

import java.sql.Connection;
import java.sql.SQLException;

import org.javautil.oracle.stats.SessionStatDeltaFormatter;
import org.javautil.oracle.stats.SessionStats;
import org.javautil.oracle.stats.SessionStatsDAO;
import org.javautil.oracle.stats.StatNames;

public class StatementStatistics {

	private SessionStats before;

	private SessionStats after;

	// private String deltaStats;

	private String explainPlan;

	private StatNames statNames;

	private final String newline = System.getProperty("line.separator");

	public StatementStatistics() {

	}

	public StatementStatistics(final StatNames statNames) {
		this.statNames = statNames;
	}

	public SessionStats before(final Connection conn) throws SQLException {
		if (statNames == null) {
			statNames = new StatNames();
			statNames.populate(conn);
		}
		before = SessionStatsDAO.getMySessionStats(conn);
		return before;
	}

	public String after(final Connection conn) throws SQLException {
		after = SessionStatsDAO.getMySessionStats(conn);
		final DbmsXplan dx = new DbmsXplan();
		explainPlan = dx.getExplainPlanForLastStatement(conn);

		final SessionStatDeltaFormatter fmt = new SessionStatDeltaFormatter(
				statNames);
		final String deltaStats = fmt.format(before, after);

		return explainPlan + newline + deltaStats;

	}
}
