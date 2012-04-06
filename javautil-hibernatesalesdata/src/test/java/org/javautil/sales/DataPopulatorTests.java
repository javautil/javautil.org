package org.javautil.sales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javautil.hibernate.persist.Persistence;
import org.javautil.hibernate.persist.TransactionHelper;
import org.javautil.jdbc.h2.H2Helper;
import org.javautil.jdbc.metadata.Schema;
import org.javautil.jdbc.metadata.dao.DatabaseMetadataHelper;
import org.javautil.jdbc.metadata.dao.SchemaDaoJdbc;
import org.javautil.jdbc.metadata.renderer.SchemaToXml;
import org.javautil.sales.database.schema.CreateSalesDatabaseObjects;
import org.javautil.sales.dataservice.SalesServicesJDBC;
import org.javautil.sales.examples.CreateDatabaseObjects;
import org.javautil.sales.populate.DataPopulator;
import org.javautil.sales.populate.DataPopulatorArguments;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

// TODO make this some real unit test
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class DataPopulatorTests {

	@Autowired
	private SessionFactory sessionFactory;

	private TransactionHelper transactionHelper;

	private final Logger logger = Logger.getLogger(getClass());

	private DataPopulatorArguments counts;

	public void init(final ClassPathXmlApplicationContext context)
			throws SQLException {
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		transactionHelper = new TransactionHelper(sessionFactory);
		transactionHelper.startTransaction();
		dumpSchema();
	}

	@Ignore
	@Before
	public void Before() throws HibernateException, SQLException {
		new CreateDatabaseObjects();
	}

	DataPopulatorArguments getCounts() {
		counts = new DataPopulatorArguments();
		counts.setSalespersonsCount(5);
		counts.setCustomerCount(100);
		counts.setProductCount(100);
		counts.setManufacturerCount(71);
		counts.setSalesCount(1000);
		return counts;
	}

	@After
	public void After() {
	}

	void dumpSchema() throws SQLException {
		H2Helper h2 = new H2Helper();
		String h2Info = h2.getInfo(getConnection());
		logger.warn(h2Info);

		new DatabaseMetadataHelper();
		String metaInfo = DatabaseMetadataHelper.toString(getConnection());
		logger.info(metaInfo);
		String schemaName = "%"; // TODO should be pluggable
		String catalog = "%";
		SchemaDaoJdbc dao = new SchemaDaoJdbc(getConnection(), schemaName,
				catalog, "%");
		dao.populateTables();
		Schema schema = dao.getSchema();
		SchemaToXml renderer = new SchemaToXml(schema, true);
		// TODO get schema name from connection
		String xml = renderer.getAsString(schema, schemaName, false);
		logger.error(xml);
	}

	public void createDatabaseSchema() {
		CreateSalesDatabaseObjects.createDatabaseObjects();
		final SalesServicesJDBC ss = new SalesServicesJDBC();
		ss.setConnection(getConnection());
		assertEquals(0, ss.getSalespersonCount());
		releaseConnection();
	}

	//
	void testObjectsExistWithNoRows() {
		final SalesServicesJDBC ss = new SalesServicesJDBC();
		ss.setConnection(getConnection());
		assertEquals(0, ss.getSalespersonCount());
		releaseConnection();
	}

	void populate() {
		final DataPopulator populator = new DataPopulator();
		populator.setArguments(getCounts());
		populator.setSessionFactory(sessionFactory);
		populator.populate();
	}

	@Test
	public void testArguments() throws SQLException {
		createDatabaseSchema();
		populate();
		final SalesServicesJDBC ss = new SalesServicesJDBC();
		ss.setConnection(getConnection());
		getCounts();
		// TODO ensure connection is closed
		assertNotNull(counts);
		assertNotNull(ss);
		assertEquals(counts.getSalespersonsCount(),
				new Integer(ss.getSalespersonCount()));
		assertEquals(counts.getCustomerCount(),
				new Integer(ss.getCustomerCount()));
		assertEquals(counts.getSalesCount(), new Integer(ss.getSalesCount()));
		assertEquals(counts.getProductCount(),
				new Integer(ss.getProductCount()));
	}

	Connection getConnection() {
		final Session session = Persistence.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		final Connection connection = Persistence.getSessionFactory()
				.getCurrentSession().connection();
		return connection;
	}

	void releaseConnection() {
		final Session session = Persistence.getSessionFactory()
				.getCurrentSession();
		session.close();
	}

	// TODO there is a way to read arguments as properties
	/**
	 * What are we measuring here?
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(final String[] args) throws SQLException {

		final ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(
				"application-context.xml");
		final DataPopulatorTests dp = new DataPopulatorTests();
		dp.init(springContext);
		dp.testArguments();
	}
}
