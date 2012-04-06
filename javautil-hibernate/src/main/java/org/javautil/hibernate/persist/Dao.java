package org.javautil.hibernate.persist;

import java.io.Serializable;
import java.util.List;

/**
 * Common interface for all DAO implementations.
 * 
 * @author tim@softwareMentor.com
 * 
 * TODO jjs I find this goofy the ID part that is.
 */
public interface Dao<T, ID extends Serializable> {

	T save(T entity);

	void delete(T entity);

	T findById(ID id, boolean lock);

	List<T> findByExample(T entity);

	List<T> findByExample(T entity, String[] excludeProperty);

	List<T> findAllAsList();
	
	void deleteAll();
	
	void flush();
	
	void clear();
	
	void evict(T instance);
	
	List<T> getList(String hqlQuery);

}
