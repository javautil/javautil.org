package org.javautil.hibernate.persist;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base class for DAO implementation. Supports injected session factory, shared
 * by all DAO implementations.
 * 
 * @author tim@softwareMentor.com
 */
public abstract class BaseDao<T extends Serializable, ID extends Serializable>
		implements Dao<T, ID> {

	final static Logger logger = LoggerFactory.getLogger(BaseDao.class);
	// TODO this is unsatisfactory as multiple session factories may be desired
	@Autowired
	private static SessionFactory sessionFactory;

	public Session getSession() {

		if (sessionFactory == null) {
			throw new IllegalStateException("sessionFactory is null");
		}
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(final SessionFactory aSessionFactory) {
		if (aSessionFactory == null) {
			throw new IllegalArgumentException("aSessionFactory may not be null");
		}
		sessionFactory = aSessionFactory;
	}

}
