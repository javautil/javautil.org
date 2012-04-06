package org.javautil.jdbc.datasources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javautil.oracle.OracleConnectionUtil;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class OracleConnectionUtilTest {
	private final Logger logger = Logger.getLogger(getClass());

	@BeforeClass
	public static void setDataSourcesLocation() {
		System.setProperty(SimpleDatasourcesFactory.DATASOURCES_FILE,
				"./examples/datasources.xml");
	}

	// TODO this test relies on CDSI information, it should
	// be rewritten or thrown away
	@Ignore
	@Test
	public void testPooledGenericSource() throws SQLException {
		final DataSource ds = SimpleDatasourcesFactory
				.getDataSource("generic_princeton");

		final Connection conn = ds.getConnection();
		final OracleConnectionUtil ocu = new OracleConnectionUtil(conn);
		// . ocu.setTraceFileIdentifier("squirrel");
		ocu.traceOn("squirrel");

		final PreparedStatement pstat = conn
				.prepareStatement("select 'x' from dual");
		final ResultSet rset = pstat.executeQuery();
		rset.next();
		rset.close();
		pstat.close();
		conn.close();
		ocu.dispose();
	}

}
