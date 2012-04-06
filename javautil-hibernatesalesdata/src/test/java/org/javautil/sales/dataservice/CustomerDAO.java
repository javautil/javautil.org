package org.javautil.sales.dataservice;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javautil.sales.dto.Customer;

public class CustomerDAO extends BaseSalesDao {

	public CustomerDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Customer> ListCustomers(final Session session) {
		final Query query = session.createQuery(" from Customer");
		return query.list();
	}

	public Customer getCustomerForId(final int customerId, final Session session) {
		final Query query = session
				.createQuery(" from Customer where customerId = :cstId");
		query.setInteger("cstId", customerId);
		return (Customer) query.list();
	}

	public List getCustomerByName(final String customerName,
			final Session session) {
		final List<Customer> retval = session
				.createQuery(" from Customer where name = :cstName")
				.setString("cstName", customerName).list();
		session.close();
		return retval;
	}
}
