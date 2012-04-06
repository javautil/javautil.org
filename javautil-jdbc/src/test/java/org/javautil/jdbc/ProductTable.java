package org.javautil.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.javautil.util.DateFactory;

public class ProductTable {

	private final Logger logger = Logger.getLogger(getClass());

	public void createProductTable(final Connection conn) throws SQLException {
		final String createTable = "create table product (product_id number(9) not null, "
				+ " mfr_id varchar2(12), "
				+ "descr  varchar2(32), intro_dt date)";
		final String createPk = "alter table product add constraint product_pk primary key(product_id)";
		final Statement s = conn.createStatement();
		s.execute(createTable);
		s.execute(createPk);
		s.close();
	}

	public void populateProductTable(final Connection conn) throws SQLException {
		final DateFactory dateFactory = new DateFactory(new GregorianCalendar());
		final String insertText = "insert into product(product_id,mfr_id,descr, intro_dt) values (?,?,?,?)";
		final PreparedStatement ps = conn.prepareStatement(insertText);

		ps.setInt(1, 1);
		ps.setString(2, "123456000001");
		ps.setString(3, "Fried Squid");
		Date d = dateFactory.getDate(1988, 4, 8);
		java.sql.Date sd = new java.sql.Date(d.getTime());
		ps.setDate(4, sd);
		ps.addBatch();

		ps.setInt(1, 2);
		ps.setString(2, "123456000002");
		ps.setString(3, "Fried Octopus");
		d = dateFactory.getDate(1988, 4, 8);
		sd = new java.sql.Date(d.getTime());
		ps.setDate(4, sd);
		ps.addBatch();

		ps.executeBatch();
		logger.debug("product table populated");
	}

	public void drop(final Connection conn) throws SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		final String dropText = "drop table product";
		final Statement ps = conn.createStatement();
		try {
			ps.execute(dropText);
			ps.close();
		} catch (final SQLException sqe) {
			logger.error(sqe.getMessage());
		}

	}

	public Connection getConnectionToDatabaseWithProductTable()
			throws SQLException {
		final Connection conn = H2SingletonInstance.getConnection();
		drop(conn);
		createProductTable(conn);
		populateProductTable(conn);
		conn.commit();
		return conn;
	}

}
