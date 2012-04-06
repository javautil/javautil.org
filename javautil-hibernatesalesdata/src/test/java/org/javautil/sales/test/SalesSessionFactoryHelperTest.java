package org.javautil.sales.test;

import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.junit.Test;

public class SalesSessionFactoryHelperTest {

	@Test
	public void testGetSessionFactory() {
		final SessionFactory sf = new SalesSessionFactoryHelper()
				.getSessionFactory();
		assertNotNull(sf);
	}
}
