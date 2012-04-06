package org.javautil.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.h2.jdbc.JdbcSQLException;
import org.javautil.jdbc.ProductTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class QueryHelperTest {

	private ProductTable productTable = null;
	private Connection conn = null;
	private final Logger logger = Logger.getLogger(getClass());

	@Before
	public void before() throws SQLException {
		logger.debug("about to create table");
		productTable = new ProductTable();
		conn = productTable.getConnectionToDatabaseWithProductTable();
		logger.debug("table created");
	}

	@After
	public void after() throws SQLException {
		logger.debug("about to drop table");
		productTable.drop(conn);
		logger.info("table dropped");
		conn.close();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNullConnection1() {
		new QueryHelper(null, "test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNullConnection2() {
		new QueryHelper("test", null, "test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNullName() {
		final Connection conn = null;
		new QueryHelper(null, conn, "test");
	}

	@Test
	public void testExecuteQueryWithNoBinds() throws Exception {
		final String sqlText = "select * from product order by product_id";
		QueryHelper sql = null;
		sql = new QueryHelper("test1", conn, sqlText);
		sql.destroy(); // should do nothing
		final ResultSet rset = sql.executeQuery();
		Assert.assertTrue(rset.next());
		Assert.assertEquals(1, rset.getInt("product_id"));
		Assert.assertTrue(rset.next());
		sql.destroy();
	}

	@Test
	public void testExecuteQueryWithMapBind() throws Exception {
		final String sqlText = "select product_id, mfr_id from product where product_id = :product_id order by product_id";
		final QueryHelper sql = new QueryHelper("test", conn, sqlText);
		final ResultSet rset = sql.executeQuery(toMap("product_id",
				new Integer(1)));
		Assert.assertTrue(rset.next());
		Assert.assertEquals(1, rset.getInt(1));
		final boolean hasMore = rset.next();
		Assert.assertFalse(hasMore);
		sql.destroy();
	}

	@Test
	public void testExecuteQueryWithRepeatedParameter() throws Exception {
		final String sqlText = "select * from product where product_id = :product_id and product_id = :product_id order by product_id";
		QueryHelper sql = null;

		sql = new QueryHelper("test", conn, sqlText);
		final ResultSet rset = sql.executeQuery(toMap("product_id",
				new Integer(1)));
		Assert.assertTrue(rset.next());
		Assert.assertEquals(1, rset.getInt(1));
		final boolean hasMore = rset.next();
		Assert.assertFalse(hasMore);
		sql.destroy();
	}

	@Test
	public void testExecuteQueryWithSimilarParameterNames() throws Exception {
		final String sqlText = "select * from product where product_id = :product_id and product_id = :product_idd order by product_id";
		final QueryHelper sql = new QueryHelper("test", conn, sqlText);
		sql.setNumber("product_id", 1);
		sql.setNumber("product_idd", 1);
		final ResultSet rset = sql.executeQuery();
		Assert.assertTrue(rset.next());
		Assert.assertEquals(1, rset.getInt(1));
		final boolean hasMore = rset.next();
		Assert.assertFalse(hasMore);
		sql.destroy();
	}

	// TODO shouldn't this blow up?
	@Test
	public void testExecuteAfterDestroy() throws Exception {
		final String sqlText = "select * from product where product_id = :product_id and product_id = :product_idd order by product_id";
		final QueryHelper sql = new QueryHelper("test", conn, sqlText);
		sql.setNumber("product_id", 1);
		sql.setNumber("product_idd", 1);
		sql.destroy();
		final ResultSet rset = sql.executeQuery();

		Assert.assertTrue(rset.next());
		Assert.assertEquals(1, rset.getInt(1));
		final boolean hasMore = rset.next();
		Assert.assertFalse(hasMore);
		sql.destroy();
	}

	// TODO this blows up, parsing need to strip comments
	@Ignore
	@Test
	public void testCommentedBind() throws Exception {
		final String sqlText = "select * from product where product_id = :product_id /* and product_id = :product_idd */ order by product_id";
		final QueryHelper sql = new QueryHelper("test", conn, sqlText);
		sql.setNumber("product_id", 1);
		//sql.setNumber("product_idd", 1);
		//sql.destroy();
		final ResultSet rset = sql.executeQuery();

		Assert.assertTrue(rset.next());
		Assert.assertEquals(1, rset.getInt(1));
		final boolean hasMore = rset.next();
		Assert.assertFalse(hasMore);
		sql.destroy();
	}

	@Test
	public void testSetBinds() throws Exception {
		final String sqlText = "select product_id, mfr_id from product where product_id = :product_id order by product_id";
		final QueryHelper sql = new QueryHelper("test", conn, sqlText);

		sql.setAllowExtraBindVariables(true);
		Assert.assertTrue(sql.isAllowExtraBindVariables());
		sql.setDate("someday", new Date(new java.util.Date().getTime()));
		sql.setNull("somehow");
		sql.setNumber("somenumber", 42);
		sql.setObject("something",
				new Timestamp(new java.util.Date().getTime()));
		sql.setString("somestring", "superduper");

		sql.clear();
		sql.setNumber("product_id", 1);
		sql.setAllowExtraBindVariables(false);
		Assert.assertFalse(sql.isAllowExtraBindVariables());
		final ResultSet rset = sql.executeQuery();
		Assert.assertTrue(rset.next());
		Assert.assertEquals(1, rset.getInt(1));
		Assert.assertFalse(rset.next());
		sql.destroy();
	}

	@Test(expected = SQLBindException.class)
	public void testExecuteQueryWithMissingBinds() throws Exception {
		logger.info("entering testExecuteQueryWithMissingBinds");
		final String sqlText = "select * from product where product_id = :product_id and product_id = :product_idd order by product_id";
		final QueryHelper sql = new QueryHelper("test", conn, sqlText);
		sql.setNumber("product_id", 1);
		sql.destroy();
		sql.executeQuery();
		sql.destroy();
	}

	@Test(expected = SQLBindException.class)
	public void testExecuteQueryLateValidationForExtraBinds()
			throws SQLException {
		QueryHelper sql = null;
		final String sqlText = "select product_id, mfr_id from product where product_id = :product_id order by product_id";
		sql = new QueryHelper("test", conn, sqlText);
		sql.setNumber("product_id", 1);
		sql.setAllowExtraBindVariables(true);
		Assert.assertTrue(sql.isAllowExtraBindVariables());
		sql.setString("somestring", "superduper");
		sql.setAllowExtraBindVariables(false);
		Assert.assertFalse(sql.isAllowExtraBindVariables());
		try {
			sql.executeQuery();
		} catch (final SQLException se) {
			after();
			throw se;
		}
		// sql.destroy();
	}

	@Test(expected = SQLBindException.class)
	public void testExecuteQueryWithUnknownBindSpecified() throws Exception {
		QueryHelper sql = null;
		try {
			sql = new QueryHelper("test", conn, "select * from product");
			sql.setString("i_dont_exist", "so_i_will_cause_an_error");
			sql.executeQuery();
		} finally {
			sql.destroy();
		}
	}

	@Test
	public void testExecuteQueryWithMissingBindsSetToNull() throws Exception {
		final String sqlText = "select * from product where product_id = :product_id order by product_id";
		final QueryHelper sql = new QueryHelper("test", conn, sqlText);
		sql.setNumber("product_id", 1);
		sql.destroy();
		sql.executeQuery();
		sql.destroy();
		new HashMap<String, Object>();

		// any missing binds should be set to null
		final ResultSet rset = sql.executeQuery(new HashMap<String, Object>(),
				true);
		Assert.assertFalse(rset.next());
	}

	@Test(expected = JdbcSQLException.class)
	public void testExecutePropagatesSQLException() throws Exception {
		QueryHelper sql = null;
		try {
			sql = new QueryHelper("test", conn,
					"select 404 from not_valid_table_name where test = :fail");
			sql.executeQuery(toMap("fail", "this isn't going to work"));
		} finally {
			sql.destroy();
		}
	}

	private Map<String, Object> toMap(final String parm, final Object value) {
		final TreeMap<String, Object> parms = new TreeMap<String, Object>();
		parms.put(parm, value);
		return parms;
	}
	//
	// private Dataset getDataset(String query) throws SQLException {
	// Statement stmt = null;
	// Dataset ds = null;
	// try {
	// stmt = conn.createStatement();
	// ds = DisassociatedResultSetDataset.getDataset(stmt
	// .executeQuery(query));
	// } finally {
	// if (stmt != null) {
	// stmt.close();
	// }
	// }
	// return ds;
	// }
}
