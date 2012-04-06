package org.javautil.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.javautil.jdbc.metadata.dao.TableJdbc;
import org.junit.BeforeClass;
import org.junit.Test;

public class JdbcTableTest {
	private static H2SingletonInstance h2 = new H2SingletonInstance();
	private Logger logger = Logger.getLogger(getClass());
	
	@BeforeClass
	public static void beforeClass() {
		BasicConfigurator.configure();
	}

	void dropTable(Connection conn) throws SQLException {
		final String dropTable = "drop table t";
		final Statement s = conn.createStatement();
		try {
		s.execute(dropTable);
		} catch (SQLException sqe) {
			logger.warn(sqe.getMessage());
		}
	}
	
	void createTable(Connection conn) throws SQLException {
		final String createTable = "create table T (t_nbr number(9) not null, x varchar2(1), y date)";
		final String createPk = "alter table t add constraint t_pk primary key(t_nbr)";

		final Statement s = conn.createStatement();
		s.execute(createTable);
		s.execute(createPk);
		logger.debug("table created");
	}
	
	@Test
	public void test() throws SQLException {
		final Connection conn = H2SingletonInstance.getConnection();
		dropTable(conn);
		createTable(conn);
		String schemaName= H2SingletonInstance.getSchemaName();
		String catalogName = H2SingletonInstance.getCatalogName();
		logger.debug("schemaName " + schemaName + " catalogName " + catalogName);
		
		
		final TableJdbc table = new TableJdbc(
				schemaName,
				catalogName, "T", null, "TABLE");
		table.init(conn.getMetaData(), conn);
		final ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.addAll(table.getPrimaryKey().getColumnNames());
		assertEquals("T_NBR", columnNames.get(0));

	}

}
