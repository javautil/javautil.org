package org.javautil.sales.dataservice;

import org.hibernate.SessionFactory;

public class BaseSalesDao {
	private SessionFactory sessionFactory;

	public BaseSalesDao(final SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
