package org.javautil.oracle.metadata;

import java.sql.SQLException;

import oracle.jdbc.OracleConnection;

import org.javautil.oracle.datasources.OracleDataSourceWrapper;
import org.javautil.oracle.datasources.OracleDataSourceWrapperArguments;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//TODO write or use as data or delete
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:OracleDataSourceWrapperArgumentsContext.xml" })
public class OracleSchemaTest {

	@Autowired
	private OracleDataSourceWrapperArguments arguments;

	@Test
	public void scottTest() throws SQLException {
		OracleDataSourceWrapper odsw = new OracleDataSourceWrapper();
		odsw.setArguments(arguments);
		OracleConnection conn = odsw.getConnection();
		// TODO change
		OracleSchema schema = new OracleSchema(conn, "WEB_GUS");
		schema.getTables();

	}
}
