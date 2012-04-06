/**
 * 
 */
package org.javautil.dblogging.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.javautil.dblogging.dao.impl.ProcessLoggingImpl;
import org.javautil.dblogging.dto.UtProcessStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author siyer TODO cleanup
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "classpath:application-context.xml" })
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = true)
// @Transactional
public class ProcessLoggingTest {

	private static final Logger logger = Logger
			.getLogger(ProcessLoggingTest.class);
	// private static H2SingletonInstance h2 = new H2SingletonInstance();
	private Configuration cfg;
	private static SessionFactory sessionFactory;

	// TODO make these pluggable
	private final String dbUrl = "jdbc:h2:testdata/dblogging";
	private final String password = "toad";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void prepareClass() {
		System.setProperty("DATASOURCES_FILE", "datasources.xml");
		System.setProperty("DATASOURCE", "h2");
	}

	public void createSchema(final Configuration cfg) {

		try {
			logger.info("Starting process...");
			final SchemaExport schema = new SchemaExport(cfg); // .create(false,
																// true);
			schema.create(/* script */false, /* export */true);
			logger.info("Ending process...");

		} catch (final Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// TODO this doesn't create schema
		cfg = new Configuration();
		cfg.addResource("org/javautil/dblogging/dto/UtProcessStatus.hbm.xml");
		cfg.addResource("org/javautil/dblogging/dto/UtProcessLog.hbm.xml");
		cfg.addResource("org/javautil/dblogging/dto/UtProcessStat.hbm.xml");

		cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		cfg.setProperty("hibernate.connection.driver_class",
				"org.javautil.jdbc.datasources.NonConnectionPooledDatasource");
		cfg.setProperty("hibernate.connection.url", dbUrl);
		cfg.setProperty("hibernate.connection.username", "dblogging");
		cfg.setProperty("hibernate.connection.password", password);
		// cfg.setProperty("hbm2ddl.auto", "create");

		sessionFactory = cfg.buildSessionFactory();
		logger.info("setUp done");
	}

	void createTable() throws SQLException {
		Session sess = sessionFactory.openSession();
		Connection conn = sess.connection();
		Statement s = conn.createStatement();
		s.execute("create table noise(x varchar(1))");
		sess.close();
	}

	@Ignore
	@Test
	public void beginJobTest() {
		final ProcessLoggingImpl processLogging = new ProcessLoggingImpl();
		// createSchema(cfg);
		processLogging.setSessionFactory(sessionFactory);
		final UtProcessStatus status = processLogging.beginJob("Process Begin",
				"H2", 1, 1);
		Assert.assertNotNull(status.getUtProcessStatusNbr());
	}

	@Test
	public void testProcessStatus() {
		final ProcessLoggingImpl processLogging = new ProcessLoggingImpl();
		processLogging.setSessionFactory(sessionFactory);
		final UtProcessStatus status = processLogging.beginJob("Process Begin",
				"H2", 1, 1);
		Assert.assertNotNull(status.getUtProcessStatusNbr());
		processLogging.info("INFO", "Test Information Message");
		// TODO need to ensure inserts actually work
	}

	// TODO all of these tests need to make sure they write to the database in
	// integration testing
	@Test
	public void ProcessLogTest2() throws InterruptedException {
		final ProcessLoggingImpl processLogging = new ProcessLoggingImpl();
		processLogging.setSessionFactory(sessionFactory);
		final UtProcessStatus status = processLogging.beginJob("Process Test2",
				"H2", 1, 1);
		Assert.assertNotNull(status.getUtProcessStatusNbr());
		Thread.sleep(1000);
		processLogging.info("INFO", "Test Information Message");
		Thread.sleep(4500);
		processLogging.severe("Severe", "Severe Test Message");
		Thread.sleep(100);
		processLogging.warning("Warn", "Warning Test Message");
		processLogging.entering("Entering", "Entering Test Message");
		processLogging.exiting("Exiting", "Exiting Test Message");
		Thread.sleep(10000);
		processLogging.fine("FINE", "Fine Test Message");
		processLogging.finer("FINER", "Finer Test Message");
		processLogging.finest("FINEST", "Finest Test Message");
		Thread.sleep(1200);
		processLogging.action("Action", "Action Test Message");
		processLogging.snapStats("SNAP");
		Thread.sleep(2000);
		processLogging.none("NONE", "None Test Message");

	}

	@Test
	public void endJobTest() throws InterruptedException {
		final ProcessLoggingImpl processLogging = new ProcessLoggingImpl();
		processLogging.setSessionFactory(sessionFactory);
		final UtProcessStatus status = processLogging.beginJob("Process Begin",
				"H2", 1, 1);
		Assert.assertNotNull(status.getUtProcessStatusNbr());
		Thread.sleep(1000);
		processLogging.info("INFO", "Test Information Message");
		Thread.sleep(4500);
		processLogging.severe("Severe", "Severe Test Message");
		Thread.sleep(100);
		processLogging.endJob();
	}

	@Test
	public void abortJobTest() throws InterruptedException {
		final ProcessLoggingImpl processLogging = new ProcessLoggingImpl();
		processLogging.setSessionFactory(sessionFactory);
		final UtProcessStatus status = processLogging.beginJob(
				"Abort Process Begin", "H2", 1, 1);
		Assert.assertNotNull(status.getUtProcessStatusNbr());
		Thread.sleep(1600);
		processLogging.info("INFO", "Test Information Message");
		Thread.sleep(500);
		processLogging.severe("Severe", "Severe Test Message");
		Thread.sleep(100);
		processLogging.abortJob();
	}

	public static void main(String args[]) throws Exception {
		ProcessLoggingTest loggingTest = new ProcessLoggingTest();
		loggingTest.setUp();
		loggingTest.createTable();
		loggingTest.beginJobTest();
		logger.info("done");
	}
}
