package org.javautil.jdbc.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.BasicConfigurator;
import org.javautil.jdbc.datasources.SimpleDatasourcesFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataSourceFactoryTest {

	@BeforeClass
	public static void beforeClass() {
		BasicConfigurator.configure();
	}

	public void testConnection(final Connection conn) throws SQLException {
		final Statement stmt = conn.createStatement();
		stmt.execute("create table a (b number(9))");
		stmt.execute("drop table a");
		stmt.close();
	}

	@Test
	public void test1() throws SQLException {
		System.setProperty("DATASOURCES_FILE",
				"src/test/resources/DataSourceH2.xml");
		final Connection conn = SimpleDatasourcesFactory.getDataSource("h2")
				.getConnection();
		testConnection(conn);
		conn.close();
	}

	@Test
	public void test2() throws SQLException {
		System.setProperty("DATASOURCES_FILE",
				"src/test/resources/DataSourceH2_2.xml");
		final Connection conn = SimpleDatasourcesFactory.getDataSource("h2")
				.getConnection();
		testConnection(conn);
		conn.close();
	}

	@Test
	public void test3() throws SQLException {
		System.setProperty("DATASOURCES_FILE",
				"src/test/resources/DataSourceH2C3P0.xml");
		final Connection conn = SimpleDatasourcesFactory.getDataSource("h2")
				.getConnection();
		testConnection(conn);
		conn.close();
	}
}
