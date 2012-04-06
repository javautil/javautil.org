package org.javautil.sales.database.schema;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javautil.hibernate.persist.Persistence;
import org.javautil.sales.dataservice.SalesServicesJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CreateSchemaHibernate implements CreateSchema {
	// TODO why is this bound to
	@Autowired
	private SessionFactory sessionFactory;

	public void afterPropertiesSet() {
		if (sessionFactory == null) {
			throw new IllegalStateException("sessionFactory is null");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.sales.database.schema.CreateSchema#createDatabaseSchema()
	 */
	@Override
	public void createDatabaseSchema() {
		afterPropertiesSet();
		CreateSalesDatabaseObjects.createDatabaseObjects();
		final SalesServicesJDBC ss = new SalesServicesJDBC();
		ss.setConnection(getConnection());
		assert (0 == ss.getSalespersonCount());
		releaseConnection();
	}

	// TODO cut and pasted from DataPopulatorTests
	Connection getConnection() {
		final Session session = Persistence.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		final Connection connection = Persistence.getSessionFactory()
				.getCurrentSession().connection();
		return connection;
	}

	void releaseConnection() {
		final Session session = Persistence.getSessionFactory()
				.getCurrentSession();
		session.close();
	}

	public void go(final String contextName) {

		if (contextName == null) {
			throw new IllegalArgumentException("contextName is null");
		}
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				contextName);
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		// datasource = (DataSource) context.getBean("datasource");
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
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
