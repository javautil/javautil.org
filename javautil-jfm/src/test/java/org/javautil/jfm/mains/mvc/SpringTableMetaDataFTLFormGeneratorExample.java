package org.javautil.jfm.mains.mvc;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import org.javautil.dataset.ColumnAttributes;
import org.javautil.jdbc.H2SingletonInstance;
import org.javautil.jdbc.metadata.JDBCTable;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SpringTableMetaDataFTLFormGeneratorExample {

	private static H2SingletonInstance h2 = new H2SingletonInstance();

	@BeforeClass
	public static void prepareDirectories() {
		new File("target/examples/mvc").mkdirs();
	}

	// TODO fix this test
	@Ignore
	@Test
	public void createFormTest() throws Exception {
		final String createTable = "create table t (t_nbr number(9) not null, x varchar2(1), y date)";
		final String createPk = "alter table t add constraint t_pk primary key(t_nbr)";
		final Connection conn = H2SingletonInstance.getConnection();
		final Statement s = conn.createStatement();
		s.execute(createTable);
		s.execute(createPk);
		// TODO restore JDBCTable, test convert and delete
		final JDBCTable table = new JDBCTable(
				H2SingletonInstance.getSchemaName(),
				H2SingletonInstance.getCatalogName(), "T", null, "TABLE");
		table.init(conn.getMetaData(), conn);
		final ArrayList<ColumnAttributes> columns = (ArrayList<ColumnAttributes>) table
				.getColumns();
		createForm(columns);
	}

	public File createForm(final ArrayList<ColumnAttributes> columns)
			throws Exception {
		final String argsString = "-outputFile=target/examples/mvc/t_form.ftl" //
				+ " -htmlFormAction=/myContext/myServlet/myController/myForm.html" //
				+ " -javaClassFile=src/main/resources/Person.class"; //
		final String[] args = argsString.split(" ");
		SpringFTLFormGenerator.main(args);
		return new File("target/examples/mvc/t_form.ftl");
	}

}
