/**
 * 
 */
package org.javautil.address.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.javautil.address.UtAddrValidatePopulator;
import org.javautil.address.dao.UtAddrValidateJDBCPersister;
import org.javautil.address.dataservice.populate.ddl.CreateDatabaseObjects;
import org.javautil.address.usps.AddressValidationException;
import org.javautil.address.usps.UspsValidationServicePropertyHelper;
import org.javautil.commandline.CommandLineHandler;
import org.javautil.jdbc.datasources.DataSourceInstantiationException;
import org.javautil.persistence.PersistenceException;
import org.javautil.util.InvalidEnvironmentException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * AddressValidationServiceIT class.
 * </p>
 * 
 * @author jjs
 * @version $Id: AddressValidationServiceIT.java,v 1.3 2012/03/04 12:31:16 jjs
 *          Exp $
 * @since 0.11.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class AddressValidationServiceIT {

	private static Connection conn;

	@Autowired
	private DataSource datasource;

	/**
	 * Should be true for in memory database
	 */
	private final boolean populateDatabase = false;

	public void afterPropertiesSet() {
		if (datasource == null) {
			throw new IllegalStateException("datasource has not been set");
		}
	}

	/**
	 * <p>
	 * setUpBeforeClass.
	 * </p>
	 * 
	 * @throws java.lang.Exception
	 *             if any.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// System.setProperty("DATASOURCES_FILE", "datasources.xml");
		// System.setProperty("DATASOURCE", "h2");
		// conn = H2SingletonInstance.getConnection();

	}

	@Before
	public void before() throws SQLException {
		conn = datasource.getConnection();
		if (populateDatabase) {
			populateAddresses();
		}
	}

	/**
	 * <p>
	 * tearDownAfterClass.
	 * </p>
	 * 
	 * @throws java.lang.Exception
	 *             if any.
	 */
	@After
	public void tearDownAfterClass() throws Exception {
		// conn.close();
	}

	/**
	 * <p>
	 * setUp.
	 * </p>
	 * 
	 * @throws java.lang.Exception
	 *             if any.
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * <p>
	 * testConnection.
	 * </p>
	 * 
	 * @throws java.lang.Exception
	 *             if any.
	 */
	@Test
	public void testConnection() throws Exception {
		assertNotNull(conn);
	}

	// TODO this is not a standalone test
	/**
	 * <p>
	 * addressValidationTest.
	 * </p>
	 * 
	 * @throws java.sql.SQLException
	 *             if any.
	 * @throws org.javautil.jdbc.datasources.DataSourceInstantiationException
	 *             if any.
	 * @throws org.javautil.util.InvalidEnvironmentException
	 *             if any.
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 * @throws org.javautil.address.usps.AddressValidationException
	 *             if any.
	 */
	@Test
	public void addressValidationTest() throws SQLException,
			DataSourceInstantiationException, InvalidEnvironmentException,
			PersistenceException, AddressValidationException {

		final int n = getRecordCount();
		assertEquals(2, n);

		addressValidationServiceArgumentsTest();
		// TODO need to test something.
	}

	/**
	 * <p>
	 * populateAddresses.
	 * </p>
	 * 
	 * @throws java.sql.SQLException
	 *             if any.
	 */
	public static void populateAddresses() throws SQLException {
		new CreateDatabaseObjects(conn);
		final UtAddrValidatePopulator pop = new UtAddrValidatePopulator(conn);
		pop.populateAddresses();
	}

	/**
	 * <p>
	 * getRecordCount.
	 * </p>
	 * 
	 * @return a int.
	 * @throws java.sql.SQLException
	 *             if any.
	 */
	public int getRecordCount() throws SQLException {
		final Statement s = conn.createStatement();
		final ResultSet rset = s
				.executeQuery("select count(*) from ut_addr_validate");
		rset.next();
		final int n = rset.getInt(1);
		s.close();
		return n;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
		// AddressValidationServiceIT.datasource = datasource;
	}

	/**
	 * <p>
	 * addressValidationServiceArgumentsTest.
	 * </p>
	 * 
	 * @throws java.sql.SQLException
	 *             if any.
	 * @throws org.javautil.jdbc.datasources.DataSourceInstantiationException
	 *             if any.
	 * @throws org.javautil.util.InvalidEnvironmentException
	 *             if any.
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 * @throws org.javautil.address.usps.AddressValidationException
	 *             if any.
	 */
	@Test
	public void addressValidationServiceArgumentsTest() throws SQLException,
			DataSourceInstantiationException, InvalidEnvironmentException,
			PersistenceException, AddressValidationException {
		final String uspsAcct = new UspsValidationServicePropertyHelper()
				.getUserId();
		final String[] parms = { "-runNbr", "1", "-uspsAcct", uspsAcct,
				"-noDatasource" };
		final AddressValidationServiceJdbc avs = new AddressValidationServiceJdbc();
		final AddressValidationServiceArguments args = new AddressValidationServiceArguments();

		final CommandLineHandler clh = new CommandLineHandler(args);
		// TODO these should be injected
		UtAddrValidateJDBCPersister persister = new UtAddrValidateJDBCPersister();
		avs.setPersister(persister);
		persister.setConnection(conn);
		clh.evaluateArguments(parms);
		assertNotNull(conn);
		avs.setConnection(conn);
		avs.process(args);
	}

}
