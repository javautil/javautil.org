package org.javautil.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetIterator;
import org.javautil.io.ClassPathResourceResolver;
import org.javautil.jdbc.datasources.H2InMemoryDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ResourceLoader;

public abstract class QueryResourceImplTest {

	private static DataSource datasource = new H2InMemoryDataSource();
	
	private static ResourceLoader loader;

	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	private QueryResource resource;

	private Connection conn;
	
	private static Logger logger = Logger.getLogger(QueryResourceImplTest.class);
	
	private static void dropTable(Connection conn) throws SQLException {
		final String dropTable = "drop table friends";
		final Statement s = conn.createStatement();
		try {
		s.execute(dropTable);
		} catch (SQLException sqe) {
			logger.warn(sqe.getMessage());
		}
		s.close();
	}
	
	private static void createTable(Connection conn) throws SQLException {
		final String createTable = "create table friends (phone number(9) not null, name varchar2(32), birthday date)";
		final Statement s = conn.createStatement();
		s.execute(createTable);
		s.close();
	}
	
	private static void populateTable(Connection conn) throws SQLException, ParseException {
		//conn.commit();
		final String insert = "insert into friends (name, birthday, phone) values (?, ?, ?);";
		final PreparedStatement ps = conn.prepareStatement(insert);
		//
		ps.setString(1, "Jenny");
		ps.setDate(2, new java.sql.Date(sdf.parse("01/01/1984").getTime()));
		ps.setInt(3, 8675309);
		ps.executeUpdate();
		ps.setString(1, "Betty");
		ps.setDate(2, new java.sql.Date(sdf.parse("05/21/1983").getTime()));
		ps.setInt(3, 1234567);
		ps.executeUpdate();
		//
		ps.setString(1, "Suzy");
		ps.setDate(2, new java.sql.Date(sdf.parse("06/27/1985").getTime()));
		ps.setInt(3, 5554545);
		ps.executeUpdate();
		//
		conn.commit();
		ps.close();
	}
		
	@BeforeClass
	public static void setupClass() throws Exception {
		BasicConfigurator.configure();
		final Connection conn = datasource.getConnection();
	    dropTable(conn);
	    createTable(conn);
	    populateTable(conn);
		
		conn.close();
		loader = new ClassPathResourceResolver("query");
		((ClassPathResourceResolver) loader).afterPropertiesSet();
	}



	@After
	public void after() throws Exception {
		if (conn != null) {
			conn.close();
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		final Connection conn = datasource.getConnection();
		final String createTable = "drop table friends";
		final Statement s = conn.createStatement();
		s.execute(createTable);
		conn.close();
	}

	//
	//  Tests
	//
	
	@Test(expected = RuntimeException.class)
	public void testResourceDoesNotExist() throws Exception {
		resource.setQueryResourceName("non_existant_resource.sql");
		resource.getDataset();
	}

	@Test(expected = RuntimeException.class)
	public void testParameterNotSet() throws Exception {
		final Map<String, Object> parms = new HashMap<String, Object>();
		resource.setParameters(parms);
		resource.setQueryResourceName("friends_where_name.sql");
		Dataset dataset = resource.getDataset();
		
	}

	@Test
	public void testGetDataset() throws Exception {
		final Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("name", "Betty");
		resource.setParameters(parms);
		resource.setQueryResourceName("friends_where_name.sql");
		final Dataset dataset = resource.getDataset();
		Assert.assertNotNull(dataset);
		//Assert.assertEquals(1, dataset.getDatasetIterator().getRowCount());
		final DatasetIterator iterator = dataset.getDatasetIterator();
		iterator.next();
		final Map<String, Object> row = iterator.getRowAsMap();
		Assert.assertEquals(new Integer(1234567).toString(), row.get("PHONE")
				.toString());
		Assert.assertEquals("Betty", row.get("NAME").toString());
		Assert.assertEquals("05/21/1983",
				sdf.format((Date) row.get("BIRTHDAY")));
	}


	public static DataSource getDatasource() {
		return datasource;
	}

	public static void setDatasource(DataSource datasource) {
		QueryResourceImplTest.datasource = datasource;
	}

	public QueryResource getResource() {
		return resource;
	}

	public void setResource(QueryResource resource) {
		this.resource = resource;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}
