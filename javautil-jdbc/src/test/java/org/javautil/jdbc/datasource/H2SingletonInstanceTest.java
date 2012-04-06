package org.javautil.jdbc.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.javautil.jdbc.H2SingletonInstance;
import org.junit.Test;

public class H2SingletonInstanceTest {

	@Test
	public void test1() throws SQLException {
		new H2SingletonInstance();
		H2SingletonInstance.getConnection();
	}

	int getOne() throws SQLException {
		new H2SingletonInstance();
		final Statement s = H2SingletonInstance.getConnection()
				.createStatement();
		final ResultSet rset = s.executeQuery("select y from x");
		rset.next();
		final int z = rset.getInt(1);
		s.close();
		return z;
	}

	@Test
	public void test2() throws SQLException {
		new H2SingletonInstance();
		final Connection conn = H2SingletonInstance.getConnection();
		final Statement s = conn.createStatement();
		s.execute("create table x(y number(1))");
		s.execute("insert into x(y) values (1)");
		s.close();
		getOne();

	}
}
