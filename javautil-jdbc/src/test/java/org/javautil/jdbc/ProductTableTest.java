package org.javautil.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class ProductTableTest {

	@Test
	public void test() throws SQLException {
		final ProductTable productTable = new ProductTable();
		final Connection conn = productTable
				.getConnectionToDatabaseWithProductTable();

		final String countTable = "select count(*) from product";
		final Statement statement = conn.createStatement();
		final ResultSet resultSet = statement.executeQuery(countTable);
		resultSet.next();
		final int count = resultSet.getInt(1);
		assertEquals(2, count);
		conn.close();

	}
}
