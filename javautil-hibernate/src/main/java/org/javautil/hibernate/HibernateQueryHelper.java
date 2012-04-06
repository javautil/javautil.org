package org.javautil.hibernate;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javautil.util.Timer;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateQueryHelper<T extends Serializable> {
	@Autowired
	private  SessionFactory sessionFactory;
	
	@Autowired 
	private String hql;
	
	private Query query;
	
	private HqlUtils utils = new HqlUtils();
	
	private int executionCount;
	
	private long minNanos;
	
	private long maxNanos;
	
	private int totalRowCount;
	
	private Logger logger = Logger.getLogger(getClass());
	
	public HibernateQueryHelper(SessionFactory sessionFactory, String hql) {
		if (sessionFactory == null ) {
			throw new IllegalArgumentException("sessionFactory is null");
		}
		if (hql == null) {
			throw new IllegalArgumentException("hql is null");
		}
		this.hql = hql;
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public  void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}




	
	
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	void updateStatistics(long beginNanos, long endNanos, int rowCount) {
		totalRowCount += rowCount;
		long elapsed = endNanos - beginNanos;
		if (minNanos > 0 && minNanos > elapsed) {
			minNanos = elapsed;
		}
		if (maxNanos < elapsed) {
			maxNanos = elapsed;
		}
		executionCount++;
	}

	public List<T> getList(Object ... binds) {
		Timer timer = new Timer();
		long beginNanos = System.nanoTime();
		Session session = getSession();
		if (query == null) {
	       query = session.createQuery(hql);
		}
		utils.doStatementBind(query,binds);
		List<T> retval = (List<T>) query.list();
		long endNanos = System.nanoTime();
		updateStatistics(beginNanos, endNanos, retval.size());
		
		return retval;
	
	}
	
	public List<T> getList() {
		long beginNanos = System.nanoTime();
		Session session = getSession();
		if (query == null) {
			Timer timer = new Timer();
	       query = session.createQuery(hql);
	       String message = "millis " + timer.getElapsedMillis() + " to parse " + hql;
	       System.out.println(message);
	       if (logger.isDebugEnabled()) {
	    	 
	    	   logger.debug(message);
	       }
		}
		Timer timer = new Timer();
		List<T> retval = null;
		
		try {
			retval = (List<T>) query.list();
			 String message = "millis " + timer.getElapsedMillis() + " row count " + retval.size() + " to execute " + hql;
			 System.out.println(message);
			 if (logger.isDebugEnabled()) {
		    	//   String message = "millis " + timer.getElapsedMillis() + " row count " + retval.size() + " to execute " + hql;
		    	  
		    	   logger.debug(message);
		       }
			}
		catch (org.hibernate.exception.SQLGrammarException e) {
			throw new RuntimeException("unable to parse " + hql,e);
		}
		long endNanos = System.nanoTime();
		updateStatistics(beginNanos, endNanos, retval.size());
		
		return retval;
	}

}
