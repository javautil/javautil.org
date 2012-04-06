package org.javautil.service;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides very basic calls for use with a hibernate SessionFactory. Provided
 * for extension by service classes that need to use a hibernate session.
 * 
 * @author bcm
 */
public abstract class AbstractHibernateService {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		if (getSessionFactory() == null) {
			throw new IllegalStateException("sessionFactory is null");
		}
		return getSessionFactory().getCurrentSession();
	}

	protected Query createQuery(final String queryText) {
		return getSession().createQuery(queryText);
	}

	@SuppressWarnings("unchecked")
	protected <T> T findById(final Class<T> dtoClass, final Object pkey) {
		final Criteria criteria = getSession().createCriteria(dtoClass);
		criteria.add(Restrictions.idEq(pkey));
		return (T) criteria.uniqueResult();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
