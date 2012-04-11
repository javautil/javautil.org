package org.javautil.oracle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javautil.oracle.stats.SessionStat;
import org.javautil.oracle.stats.SessionStatDeltaFormatter;
import org.javautil.oracle.stats.SessionStatFormatter;
import org.javautil.oracle.stats.SessionStats;
import org.javautil.oracle.stats.SessionStatsDAO;
import org.javautil.oracle.stats.StatNames;
import org.javautil.oracle.stats.StaticStatName;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class SessionStatsTest {

	@Autowired
	private DataSource ds;

	private Connection conn;

	private final Logger logger = Logger.getLogger(getClass());

	private StatNames statNames;

	private static StatNames fakeStatNames = StaticStatName.getStatNames();

	private final String newline = System.getProperty("line.separator");

	@Before
	public void before() throws SQLException {
		conn = ds.getConnection();
		statNames = new StatNames();
		statNames.populate(conn);
	}

	// TODO fix this test
	@Ignore
	@Test
	public void getStats() throws SQLException {
		new SessionStatsDAO();
		SessionStatsDAO.getMySessionStats(conn);
	}

	// TODO fix this test
	@Ignore
	@Test
	public void getStatsDelta() throws SQLException {
		logger.debug("getStatsDelta");
		new SessionStatsDAO();
		final SessionStats stats = SessionStatsDAO.getMySessionStats(conn);
		final SessionStats stats2 = SessionStatsDAO.getMySessionStats(conn);
		final SessionStats delta = stats2.getDelta(stats);
		final SessionStatFormatter ssf = new SessionStatFormatter(fakeStatNames);
		logger.debug(ssf.format(delta));
	}

	// TODO fix this test
	@Ignore
	@Test
	public void testDelta() {
		final int sid = 1;
		final SessionStats old = new SessionStats(sid);
		final SessionStats newStats = new SessionStats(sid);
		newStats.add(new SessionStat(sid, 2, 4));
		final SessionStats delta = newStats.getDelta(old);
		assertEquals(4L, delta.getStatValue(2).longValue());

		final SessionStatFormatter ssf = new SessionStatFormatter(fakeStatNames);
		logger.debug(ssf.format(delta));
	}

	// TODO fix this test
	@Ignore
	@Test
	public void testStatsDelta2() throws SQLException {
		logger.debug("testStatsDelta2");
		new SessionStatsDAO();
		final SessionStats stats = SessionStatsDAO.getMySessionStats(conn);
		final SessionStats stats2 = SessionStatsDAO.getMySessionStats(conn);
		stats2.getDelta(stats);
		final SessionStatDeltaFormatter ssf = new SessionStatDeltaFormatter(
				statNames);
		logger.debug(newline + ssf.format(stats, stats2));
	}

	/**
	 * 
	 * Ignore the fact that some of these values could only decrease in value on
	 * an overflow of a 64 bit int.
	 */
	// TODO fix this test
	@Ignore
	@Test
	public void testDelta3() {
		logger.debug("getStatsDelta3");
		// 2 "opened cursors cumulative"
		// 3,"opened cursors current"
		final int sid = 1;
		final SessionStats old = new SessionStats(sid);
		old.add(new SessionStat(sid, 2, 4));
		old.add(new SessionStat(sid, 3, 5));
		old.add(new SessionStat(sid, 4, 6));
		old.add(new SessionStat(sid, 5, 6));

		final SessionStats newStats = new SessionStats(sid);
		newStats.add(new SessionStat(sid, 2, 4));

		newStats.add(new SessionStat(sid, 4, 7));
		newStats.add(new SessionStat(sid, 5, 1));

		final SessionStats delta = newStats.getDelta(old);

		assertNull(delta.getStatValue(2)); // in both unchanged
		assertEquals(-5L, delta.getStatValue(3).longValue()); // in old , not
																// new

		assertEquals(1L, delta.getStatValue(4).longValue()); // in both went up
		assertEquals(-5L, delta.getStatValue(5).longValue()); // in both went
																// down

		final SessionStatDeltaFormatter ssf = new SessionStatDeltaFormatter(
				fakeStatNames);
		logger.debug(ssf.format(old, newStats));

	}

	public static void main(final String[] args) throws SQLException {
		// todo get Spring context
		final SessionStatsTest sst = new SessionStatsTest();
		sst.testDelta3();
	}

}
