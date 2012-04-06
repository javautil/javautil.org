package org.javautil.jdbc.datasources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;
import org.javautil.oracle.datasources.ODSWrapperInterface;
import org.javautil.oracle.datasources.OracleDataSourceWrapper;
import org.javautil.oracle.datasources.OracleDataSourceWrapperArguments;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//
// TODO need to create various configurations on the fly using spring
// and test 
//TODO write or use as data or delete
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:OracleDataSourceWrapperArgumentsContext.xml" })



public class OracleDataSourceWrapperTest {


	private final Logger logger = Logger.getLogger(getClass());
//	public static final String DATABASE_NAME = "DEV11F";  // TODO externalize
//	private final String serverName = "localhost";        // TODO externalize 
//	private final String user = "oe";  // TODO externalize
//
//	private final String password = "oe"; // todo externalize

	@Autowired
	private OracleDataSourceWrapperArguments arguments ;
	@Before
	public  void setDataSourcesLocation() {
		
		System.setProperty(SimpleDatasourcesFactory.DATASOURCES_FILE,
				"./src/test/resources/oracleDataSources.xml");
	}

	// TODO fix this test
	@Ignore
	@Test
	public void testToast() throws SQLException {
		final DataSource ds = SimpleDatasourcesFactory.getDataSource("toast");

		final Connection conn = ds.getConnection();

		final PreparedStatement pstat = conn
				.prepareStatement("select 'x' from dual");
		final ResultSet rset = pstat.executeQuery();
		rset.next();
		rset.close();
		pstat.close();
		conn.close();
	}

	// TODO fix this test
	@Ignore
	@Test
	public void spongeBob() throws SQLException {
		final DataSource ds = SimpleDatasourcesFactory.getDataSource("spongebob");

		final Connection conn = ds.getConnection();

		final PreparedStatement pstat = conn
				.prepareStatement("select 'x' from dual");
		final ResultSet rset = pstat.executeQuery();
		rset.next();
		rset.close();
		pstat.close();
		conn.close();
	}

	@Ignore
	@org.junit.Test
	public void propertyPropagationTest5() throws SQLException {
		final Properties cachePropertiesIn = new Properties();
		final Properties connectionPropertiesIn = new Properties();
		final OracleDataSource ods = new OracleDataSource();
		ods.setUser(arguments.getUser());
		connectionPropertiesIn.setProperty("ServerName", arguments.getServerName());
		connectionPropertiesIn.setProperty(
				ODSWrapperInterface.ConnectionCachingEnabled, "true");
		ods.setConnectionCacheProperties(connectionPropertiesIn);
		ods.setConnectionProperties(connectionPropertiesIn);
		//
		System.out.println(OracleDataSourceWrapper.asString(ods));

		cachePropertiesIn.setProperty(ODSWrapperInterface.InitialLimit, "1");

		ods.getConnectionProperties();
		System.out.println(OracleDataSourceWrapper.asString(ods));
	}
	

	/**
	 * don't set the server name, should get a useful error message
	 * 
	 * @throws SQLException
	 */
	// TODO make this a test
	@Test
	@Ignore
	// TODO figure why this just hangs, this is an oracle bug 
	public void test4() throws SQLException {
		try {
			final OracleDataSourceWrapper ods = new OracleDataSourceWrapper();
			final String description = "OracleDataSourceWrapper + no server";
			logger.info(description);

			// Set DataSource properties
			ods.setUser("test");
			ods.setPassword("test");
			ods.setUseThinConnectionType(true);

			ods.setPortNumber(1521);
			ods.setServiceName(arguments.getServiceName());
			final Connection conn = ods.getConnection();
			conn.close(); // return connection to the cache
			ods.close(); // close datasource and clean up the cache
			logger.info("success");
		} catch (final SQLException sqe) {
			// sqe.printStackTrace();
			logger.error(sqe.getMessage());
			throw sqe;
		} catch (final InvalidDataSourceException idse) {
			logger.error(idse.getMessage());
			throw idse;
		}
	}

	/**
	 * TODO this looks like it is trying to test statement caching
	 * @throws SQLException 
	 * 
	 */
	@Ignore
	@Test
	public void test5() throws SQLException {
		try {
			final OracleDataSourceWrapper ods = new OracleDataSourceWrapper();
			final String description = "OracleDataSourceWrapper + no server";
			logger.info(description);

			// Set DataSource properties
			ods.setUser("test");
			ods.setPassword("test");
			ods.setUseThinConnectionType(true);

			ods.setPortNumber(1521);
			ods.setServerName("localhost");
			ods.setServiceName(arguments.getServiceName());
			final OracleConnection conn = ods.getConnection();
			conn.setImplicitCachingEnabled(true);
			conn.setStatementCacheSize(100);

			for (int i = 1; i < 1000; i++) {
				final PreparedStatement ps = conn
						.prepareStatement("select 'x' from sys.dual");
				ps.executeQuery();
				// we really don't need to read it
				ps.close();
			}
			conn.close(); // return connection to the cache
			ods.close(); // close datasource and clean up the cache
			logger.info("success");
		} catch (final SQLException sqe) {
			logger.error(sqe.getMessage());
			throw sqe;
		} catch (final InvalidDataSourceException idse) {
			logger.error(idse.getMessage());
			throw idse;
		}
	}
	
	// TODO delete

//	// need to test using set properties on OracleDataSource
//	public static void main(final String[] args) throws SQLException {
//		final OracleDataSourceWrapperTest test = new OracleDataSourceWrapperTest();
//		OracleDataSourceWrapperTest.setDataSourcesLocation();
//		// test.testToast();
//		test.spongeBob();
//	}

}
