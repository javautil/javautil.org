package org.javautil.hibernate.persist;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;


/**
 * Common DAO implementation. Usage: 1) retrieve userDao from application
 * context, where it has session factory injected. 2) instantiate other DAOs:
 * DmDomain domainDao = new DaoImpl<DmDomain, String>(DmDomain.class);
 * 
 * @author tim@softwareMentor.com
 */
public class DaoImpl<T extends Serializable, ID extends Serializable> extends
		BaseDao<T, ID> implements Dao<T, ID> {

	protected Class<T> persistentClass;

	public DaoImpl(final Class<T> persistentClass) {
		super();
		if (persistentClass == null) {
			throw new IllegalArgumentException("persistentClass is null");
		}
		this.persistentClass = persistentClass;
	}

	@Override
	public T save(final T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public String getSimpleClassName() {
		String retval = persistentClass.getSimpleName();
		return retval;
	}
	
	@Override
	public void delete(final T entity) {
		getSession().delete(entity);
	}
	
	public void deleteAll() {
		Session session = getSession();

		String className = persistentClass.getName();
		String deleteText = "delete from " + className;
		if (logger.isDebugEnabled()) {
			logger.debug("about to execute " + deleteText);
		}
		Query q = session.createQuery(deleteText);
		q.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(final ID id, final boolean lock) {
		final T instance = (T) getSession().get(persistentClass, id);
		return instance;
	}

	@Override
	public List<T> findByExample(final T entity) {
		return findByExample(entity, new String[0]);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(final T entity, final String[] excludeProperty) {
		final Criteria crit = getSession().createCriteria(persistentClass);
		final Example example = Example.create(entity);
		for (final String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAllAsList() {
		final List<T> results = getSession().createCriteria(persistentClass)
				.list();
		return results;
	}

	@Override
	public void flush() {
		getSession().flush();
		
	}

	@Override
	public void clear() {
		getSession().clear();
		
	}

	@Override
	public void evict(T instance) {
		getSession().evict(instance);
		
	}

	@Override
	public List<T> getList(String hqlQuery) {
		Session session = getSession();
		Query q = session.createQuery(hqlQuery);
		List<T> retval = (List<T>) q.list();
		return retval;
	
	}


}
