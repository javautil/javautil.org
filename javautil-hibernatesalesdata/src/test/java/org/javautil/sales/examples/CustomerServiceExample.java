package org.javautil.sales.examples;

import org.javautil.hibernate.persist.Persistence;
import org.javautil.sales.dataservice.CustomerDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is an example.
 * 
 * Although this is annotated as a test class and in the test directory it is an
 * example and not named ending in Test so that it doesn't get fired by
 * SureFire.s
 * 
 * @author jjs
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class CustomerServiceExample {

	@Test
	public void testServices() {
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"application-context.xml");
		final Persistence daoFactory = (Persistence) context
				.getBean("daoFactory");
		context.getBean("datasource");

		daoFactory.getSession();

		new CustomerDAO(Persistence.getSessionFactory());

	}

}
