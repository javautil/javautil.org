package org.javautil.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
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
public class DbmsXplanTest {

	@Autowired
	private DataSource datasource;

	private Connection conn;
	private final Logger logger = Logger.getLogger(getClass());
	private final String newline = System.getProperty("line.separator");

	@Before
	public void before() throws SQLException {
		// System.setProperty("DATASOURCES_FILE","etc/DataSources.xml");
		// conn = DatasourcesFactory.getDataSource("sales").getConnection();
		// logger.debug("conn is " + conn);
		conn = datasource.getConnection();
		logger.info("conn is " + conn);
	}

	// @After
	// public void after() throws SQLException {
	// conn.close();
	// }
	// TODO fix this test
	@Ignore
	@Test
	public void runStmt1() throws SQLException {
		final String sql = "select count(*) from all_objects";
		final Statement s = conn.createStatement();
		final ResultSet rset = s.executeQuery(sql);
		rset.next();
		final DbmsXplan explainer = new DbmsXplan();
		final String plan = explainer.getExplainPlanForLastStatement(conn);
		System.out.println("************" + newline + plan + newline
				+ "****************");

	}

	// TODO fix this test
	@Ignore
	@Test
	public void runStmt2() throws SQLException {
		final String sql = "select * from all_tab_columns";
		final Statement s = conn.createStatement();
		final ResultSet rset = s.executeQuery(sql);
		while (rset.next()) {

		}
		final DbmsXplan explainer = new DbmsXplan();
		final String plan = explainer.getExplainPlanForLastStatement(conn);
		System.out.println("************" + newline + plan + newline
				+ "****************");

	}

	public static void main(final String[] args) throws SQLException {
		final DbmsXplanTest dxt = new DbmsXplanTest();
		dxt.logger.debug("starting");
		dxt.before();
		dxt.runStmt1();
		// dxt.after();
	}
}
