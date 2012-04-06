package org.javautil.sales.populate;

import java.util.ArrayList;
import java.util.Random;

import org.javautil.sales.States;
import org.javautil.sales.dto.Customer;
import org.javautil.sales.dto.Salesperson;

/**
 * Generates test Customers.
 * 
 * Creates Customer objects for test purposes.
 * 
 * @author jjs
 * 
 */
public class CustomerGenerator {

	private final ArrayList<Customer> customerList = new ArrayList<Customer>();
	private final Random random = new Random();

	private final SalespersonGenerator salesPeople;
	private boolean initialized = false;

	private final States states = new States();

	public CustomerGenerator(final SalespersonGenerator salesPeople) {
		if (salesPeople == null) {
			throw new IllegalArgumentException("salesPeople is null");
		}
		this.salesPeople = salesPeople;

	}

	public void init() {
		if (initialized) {
			throw new IllegalStateException("already initialized");
		}
		random.setSeed(31415926535L);
		initialized = true;
	}

	// this version works with a salesperson not as a foreign key
	// public Customer getCustomer() {
	// Customer p = new Customer();
	// p.setInsideSalespersonId(salesPeople.getRandomSalesperson().getSalespersonId());
	// customerList.add(p);
	// return p;
	// }

	public Customer getCustomer() {
		final Customer p = new Customer();

		final Salesperson sp = salesPeople.getRandomSalesperson();
		p.setSalesperson(sp);
		p.setState(states.getRandomStateId());
		// p.setInsideSalespersonId(salesPeople.getRandomSalesperson().getSalespersonId());
		customerList.add(p);
		return p;
	}

	public void generateCustomers(final int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n is less than 0");
		}
		for (int i = 0; i < n; i++) {
			getCustomer();
		}
	}

	public Customer getRandomCustomer() {
		final int index = random.nextInt(customerList.size());
		return customerList.get(index);
	}
}
