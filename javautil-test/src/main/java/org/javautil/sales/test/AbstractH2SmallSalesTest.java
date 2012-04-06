package org.javautil.sales.test;

import org.javautil.persist.hibernate.AbstractSpringHibernateTransactionalTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Base class for extension by test classes that require a small sales database.
 * The database is created using H2 and test methods extending this class will
 * automatically rollback after execution, see the super class of this classs,
 * AbstractSpringHibernateTransactionalTest, for more details on this behavior.
 * 
 * For more information on the small sales database, see org.javautil.sales
 * inside of the this (javautil-hibernate) project.
 * 
 * @See {@link AbstractSpringHibernateTransactionalTest}
 * @See {@link AbstractTransactionalJUnit4SpringContextTests}
 * 
 * @author bcm
 * 
 *         This is not in the test tree so other packages can access it.
 */
@ContextConfiguration(locations = { "/org/javautil/sales/h2SmallSalesTest.xml" })
public abstract class AbstractH2SmallSalesTest extends
		AbstractSpringHibernateTransactionalTest {

}
