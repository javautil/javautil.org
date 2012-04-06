package org.javautil.sales;

import org.javautil.sales.database.schema.CreateSchemaHibernate;
import org.junit.Test;

public class CreateSchemaHibernateTest {

	@Test
	public void test() {
		final CreateSchemaHibernate creator = new CreateSchemaHibernate();
		creator.go("classpath:application-context.xml");
		// TODO test database objects
	}
}
