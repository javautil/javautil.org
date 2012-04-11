package org.javautil.sales.examples;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
public class CustomerDelete {

	@Autowired
	private Persistence persistence;

	private final Logger logger = Logger.getLogger(getClass());

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
	 * this doesn't work either, wtf?
	 */
	// TODO fix
	@Ignore
	@Test
	public void listDelete() {
		// get some salespersons
		final List<Salesperson> salespersons = getSalesPeople("A", "D");
		// int initialSalespersonsSize = salespersons.size();
		logger.info("salespersons count " + salespersons.size());
		System.out.println("*******************************");

		System.out.println("salespersons count " + salespersons.size());
		// remove one from the list
		final Salesperson salesperson = salespersons.get(0);
		// todo what happens when I
		final Set<Customer> customers = salesperson.getCustomers();

		final int initialCustomerSetSize = customers.size();

		final List<Customer> custList = new ArrayList<Customer>();
		custList.addAll(customers);
		final Customer cust = custList.get(0); // todo ensure we have some

		customers.remove(cust); // if this is a backed list it should delete an
								// entry

		// int customers2size =
		// persistence.getSession().createQuery("from Customer where inside_salesperson_id = :salesperson_id  ").
		// setInteger("salesperson_id",
		// salesperson.getSalespersonId()).list().size();
		// assertEquals(initialCustomerSetSize - 1 , customers2size);

		persistence.flushAndClear();

		final int customers3size = persistence
				.getSession()
				.createQuery(
						"from Customer where inside_salesperson_id = :salesperson_id  ")
				.setInteger("salesperson_id", salesperson.getSalespersonId())
				.list().size();
		assertEquals(initialCustomerSetSize - 1, customers3size);
	}

}
