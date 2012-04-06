package org.javautil.jdbc.datasources;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;
import org.javautil.oracle.datasources.ODSWrapperInterface;
import org.javautil.oracle.datasources.OracleDataSourceWrapperArguments;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//TODO write or use as data or delete
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:OracleDataSourceWrapperArgumentsContext.xml" })
public class OracleDataSourceTest {

	@Autowired
	private OracleDataSourceWrapperArguments arguments;

	private final Logger logger = Logger.getLogger(this.getClass().getName());


	public void before() {
		if (arguments == null) {
			throw new IllegalStateException("arguments is null");
		}
	}

	public void testConnection(final Connection conn) throws SQLException {
		assertNotNull(conn);

		final PreparedStatement ps = conn.prepareCall("select 'y' from dual");
		final ResultSet rs = ps.executeQuery();
		rs.next();
		final String txt = rs.getString(1);
		conn.close();
		System.out.println(txt);
		assertTrue("y".equals(txt));
		// logger.exiting(this.getClass().getName(), "testConnection");

	}

	/**
	 * <ul>
	 * <li>lowerThresholdLimit = 20</li>
	 * <li>maxLimit = 5</li>
	 * </ul>
	 * should be constrained to maxLimit
	 * 
	 * @throws SQLException
	 */
	// TODO fix this test
	@Ignore
	@org.junit.Test
	public void testWorthlessErrorMessageWhenUserAndPasswordNotProvided()
			throws SQLException {
		final OracleDataSource ods = new OracleDataSource();
		ods.setServerName(arguments.getServerName());
		ods.setPortNumber(arguments.getPortNumber());
		ods.setServiceName(arguments.getServiceName());
		ods.setDriverType("thin");
		try {
			ods.getConnection();
		} catch (final Exception e) {
			assertTrue("invalid arguments in call".equals(e.getMessage()));
		}
		ods.close();

	}

	/**
	 * <ul>
	 * <li>lowerThresholdLimit = 20</li>
	 * <li>maxLimit = 5</li>
	 * </ul>
	 * should be constrained to maxLimit
	 * 
	 * @throws SQLException
	 */
	// TODO fix this test
	@Ignore
	@org.junit.Test
	public void testConnection2() throws SQLException {
		final OracleDataSource ods = new OracleDataSource();
		ods.setServerName(arguments.getServerName());
		ods.setPortNumber(arguments.getPortNumber());
		ods.setServiceName(arguments.getServiceName());
		ods.setDriverType("thin");
		ods.setUser(arguments.getUser());
		ods.setPassword(arguments.getPassword());
		final Connection conn = ods.getConnection();
		assertNotNull("obtained connection", conn);
		testConnection(conn);
		conn.close();

	}

	@org.junit.Test
	public void propertyPropagationTest() throws SQLException {
		final OracleDataSource ods = new OracleDataSource();
		ods.setUser(arguments.getUser());
		final Properties p = ods.getConnectionCacheProperties();
		if (p == null) {
			System.out.println("p is null");
		} else {
			System.out.println("p is not null");
		}
	}

	@Ignore // WHY?
	@org.junit.Test
	public void propertyPropagationTest3() throws SQLException {
		final OracleDataSource ods = new OracleDataSource();
		final Properties pIn = new Properties();
		ods.setConnectionCacheProperties(pIn);
		ods.setUser(arguments.getUser());
		assertNotNull(pIn);
		pIn.setProperty("ServerName", arguments.getServiceName());
		pIn.setProperty(ODSWrapperInterface.ConnectionCachingEnabled, "true");
		Properties pOut = ods.getConnectionCacheProperties();
		if (pOut == null) {
			System.out.println("pOut is null");
		} else {
			System.out.println("pOut is not null");
			pOut.toString();
		}
		pIn.setProperty(ODSWrapperInterface.InitialLimit, "1");
		pOut = ods.getConnectionCacheProperties();
		System.out.println("pOut " + pOut);
		final Properties pConnP = ods.getConnectionProperties();
		System.out.println("pConnP " + pConnP);
	}

	// todo fix or purge
	// @org.junit.Test
	// public void propertyPropagationTest4() throws SQLException {
	// Properties cachePropertiesIn = new Properties();
	// Properties connectionPropertiesIn = new Properties();
	// Properties connectionPropertiesOut;
	// Properties cachePropertiesOut;
	//
	// OracleDataSource ods = new OracleDataSource();
	// ods.setUser(user);
	// connectionPropertiesIn.setProperty("ServerName", serverName);
	// connectionPropertiesIn.setProperty(ODSWrapperInterface.ConnectionCachingEnabled,"true");
	// ods.setConnectionCacheProperties(connectionPropertiesIn);
	// ods.setConnectionProperties(connectionPropertiesIn);
	// //
	// cachePropertiesOut = ods.getConnectionCacheProperties();
	// if (cachePropertiesOut == null) {
	// System.out.println("cachePropertiesOut is null");
	// } else {
	// System.out.println("cachePropertiesOut is not null");
	// cachePropertiesOut.toString();
	// }
	// connectionPropertiesOut = ods.getConnectionProperties();
	// if (connectionPropertiesOut == null) {
	// System.out.println("connectionPropertiesOut is null");
	// } else {
	// System.out.println("connectionPropertiesOut is not null");
	// System.out.println(PropertiesHelper.asText("connectionPropertiesOut " ,
	// connectionPropertiesOut));
	// }
	//
	// cachePropertiesIn.setProperty(ODSWrapperInterface.InitialLimit,"1");
	// ods.setConnectionCacheProperties(cachePropertiesIn);
	// cachePropertiesOut = ods.getConnectionCacheProperties();
	// if (cachePropertiesOut != null) {
	// System.out.println(PropertiesHelper.asText("cachePropertiesOut " ,
	// cachePropertiesOut));
	// }
	// connectionPropertiesOut = ods.getConnectionProperties();
	// System.out.println("connectionPropertiesOut " + connectionPropertiesOut
	// );
	// System.out.println(PropertiesHelper.asText("connectionPropertiesOut " ,
	// connectionPropertiesOut ));
	// }
	//
	// TODO fix this test
	
	@Test
	public void test1() throws SQLException {
		try {
			final String description = " simple test";
			logger.info(description);
			final OracleDataSource ods = new OracleDataSource();
			ods.setUser(arguments.getUser());
			ods.setPassword(arguments.getPassword());
			ods.setDriverType("thin");
			ods.setPortNumber(arguments.getPortNumber());
			ods.setServerName(arguments.getServerName());
			ods.setDatabaseName(arguments.getServiceName());
			final Connection conn = ods.getConnection();
			// connectionTimer.stop().toString();
			conn.close(); // return connection to the cache
			ods.close(); // close datasource and clean up the cache
			logger.info("success");
		} catch (final SQLException sqe) {
			logger.error(sqe.getMessage());
			throw sqe;
		}
	}

	@Test(expected=SQLException.class)
	public void test2() throws SQLException {
		try {
			final String message = "description: invalid driver type";
			logger.info(message);
			final OracleDataSource ods = new OracleDataSource();

			// Set DataSource properties
			ods.setUser(arguments.getUser());
			ods.setPassword(arguments.getPassword());
			ods.setDriverType("pizza");
			ods.setPortNumber(arguments.getPortNumber());
			ods.setServerName(arguments.getServerName());
			ods.setDatabaseName(arguments.getServiceName());
			final Connection conn = ods.getConnection();
			conn.close(); // return connection to the cache
			ods.close();
			logger.info("success");
		} catch (final SQLException sqe) {
			logger.error(sqe.getMessage());
			throw sqe;
		}
	}

	@Test(expected=SQLException.class)
	@Ignore
	// TODO report this to oracle it just hangs under oracle 11
	public void test3() throws SQLException {
		try {
			final OracleDataSource ods = new OracleDataSource();
			logger.info("OracleDataSource no server");

			// Set DataSource properties
			ods.setUser(arguments.getUser());
			ods.setPassword(arguments.getPassword());
			ods.setDriverType("thin");

			ods.setPortNumber(arguments.getPortNumber());
			ods.setServiceName(arguments.getServiceName());
			final Connection conn = ods.getConnection();
			// logger.info(connectionTimer.stop().toString());
			conn.close(); // return connection to the cache
			ods.close(); // close datasource and clean up the cache
			logger.info("success");
		} catch (final SQLException sqe) {
			logger.error(sqe.getMessage());
			throw sqe;
		}
	}

//	/**
//	 * @return the serverName
//	 */
//	protected String getServerName() {
//		return serverName;
//	}

//	/**
//	 * @param serverName the serverName to set
//	 */
//	protected void setServerName(String serverName) {
//		this.serverName = serverName;
//	}
}
