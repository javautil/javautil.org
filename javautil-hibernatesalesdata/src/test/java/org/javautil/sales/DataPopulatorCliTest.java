package org.javautil.sales;

import java.sql.SQLException;

import org.javautil.sales.populate.DataPopulatorCli;
import org.junit.Test;

public class DataPopulatorCliTest {
	@Test
	public void test() throws SQLException {
		final String[] args = "-applicationContext src/test/resources/DataPopulatorContext.xml"
				.split(" ");
		DataPopulatorCli.main(args);
	}
}
