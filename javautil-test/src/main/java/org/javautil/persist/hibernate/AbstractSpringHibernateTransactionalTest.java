package org.javautil.persist.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Abstract junit class to be extended by transactional junit test classes.
 * Classes that extends this will have a transaction automatically started
 * before every method and rolled back after every method. The extending class
 * is expected to have an aplication context with a bean named sessionFactory
 * that is an instance of org.hibernate.SessionFactory for autowiring. The
 * spring ContextConfiguration annotation should also be present on the
 * extending class to expose the application context to the junit test.
 * 
 * @author bcm
 * 
 */
public abstract class AbstractSpringHibernateTransactionalTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		if (sessionFactory == null) {
			throw new IllegalStateException("sessionFactory is null; "
					+ "Check if a single bean named sessionFactory exists in "
					+ "from your application context(s) for autowiring.  Also "
					+ "make sure the ContextConfiguration annotation is "
					+ "present on your junit test class.");
		}
		return sessionFactory.getCurrentSession();
	}

	@Before
	public void setUp() throws Exception {
		getSession().beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		if (getSession().getTransaction().isActive()) {
			getSession().getTransaction().rollback();
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
