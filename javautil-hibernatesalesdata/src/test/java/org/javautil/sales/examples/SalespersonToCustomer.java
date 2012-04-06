package org.javautil.sales.examples;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.javautil.hibernate.persist.Persistence;
import org.javautil.sales.dto.Customer;
import org.javautil.sales.dto.Salesperson;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * todo show how using an Oracle Connection with caching decreases parses
 * 
 * @author jjs
 * 
 */
// todo make this some real unit test
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class SalespersonToCustomer {

	@Autowired
	private Persistence persistence;

	private final Logger logger = Logger.getLogger(getClass());

	// @BeforeClass
	// public static void beforeClass() {
	// BasicConfigurator.configure();
	// }

	@SuppressWarnings("unchecked")
	public List<Salesperson> getSalesPeople(final String lowerLastName,
			final String upperLastName) {
		persistence.beginTransaction(); // todo think about transaction
										// management
		final Query q = persistence.getSession().createQuery(
				" from Salesperson"
						+ //
						" where upper(last_name) >= :lowerLastName "
						+ " and upper(last_name) <=  :upperLastName) ");

		q.setString("lowerLastName", lowerLastName);
		q.setString("upperLastName", upperLastName);
		return q.list();

	}

	/**
	 * The list on an HQL query is not backed .
	 * 
	 * this doesn't work either, wtf? TODO fix
	 */
	@Ignore
	@Test
	public void visitCustomers() {
		// get some salespersons
		final List<Salesperson> salespersons = getSalesPeople("A", "D");
		logger.debug("salespersons count " + salespersons);
		for (final Salesperson salesperson : salespersons) {
			final Set<Customer> customers = salesperson.getCustomers();
			for (final Customer customer : customers) {
				customer.getName();
			}
		}

	}

}
