package org.javautil.hibernate.persist;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.InitializingBean;

/**
 * An InitializingBean to simplify programmatic transaction management. Not
 * intended to be used in a declarative transaction managed environment.
 * Manipulates hibernate Session and Transaction objects so that application
 * code does not need to directly reference them.
 * 
 * @author bcm
 */
public class TransactionHelper implements InitializingBean {

	private Session session;

	private SessionFactory sessionFactory;

	private Transaction transaction;

	private final Log logger = LogFactory.getLog(getClass());

	public TransactionHelper() {
	}

	public TransactionHelper(final SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	/**
	 * Required for InitializingBean interface. Ensures that a hibernate session
	 * is set or the current session from a SessionFactory is set.
	 */
	@Override
	public void afterPropertiesSet() {
		if (sessionFactory == null) {
			throw new IllegalStateException("sessionFactory is null");
		} else {
			session = sessionFactory.getCurrentSession();
		}
	}

	/**
	 * Returns true when a transaction has been started and is active, that is,
	 * not committed or rolled back.
	 * 
	 * @return if transaction is active
	 */
	public boolean isTransactionActive() {
		return transaction != null && transaction.isActive();
	}

	/**
	 * Starts a transaction or gets the current transaction if one is already
	 * started. Any existing inactive transaction will be dereferenced. If an
	 * active transaction already exists, an IllegalStateException will be
	 * thrown.
	 * 
	 * @throws IllegalStateException
	 * @return the current transaction or a new one
	 */
	public Transaction startTransaction() {
		if (session == null || !session.isOpen()) {
			session = sessionFactory.getCurrentSession();
			if (!session.isOpen()) {
				throw new RuntimeException("The obtained session is not open");
			}
		}
		if (!isTransactionActive()) {
			transaction = session.beginTransaction();
		} else {
			throw new IllegalStateException("a transaction is already started");
		}
		return transaction;
	}

	/**
	 * Gets a current, already started, active transaction. If one does not
	 * already exist, an IllegalStateException will be thrown. Useful when it is
	 * inappropriate to start a new transaction, and an existing transaction is
	 * expected to exist.
	 * 
	 * @throws IllegalStateException
	 * @return
	 */
	public Transaction getTransaction() {
		if (transaction == null) {
			throw new IllegalStateException("no transaction started; "
					+ "call startTransaction() first");
		} else if (!transaction.isActive()) {
			throw new IllegalStateException("transaction is not active; "
					+ "call startTransaction() first");
		}
		return transaction;
	}

	/**
	 * Performs a rollback if an active transaction exists.If an active
	 * transaction does not exist, an IllegalStateException will be thrown.
	 * 
	 * @throws IllegalStateException
	 */
	public void rollback() {
		if (isTransactionActive()) {
			transaction.rollback();
			logger.debug("transaction was rolled back");
		} else {
			throw new IllegalStateException("no transaction to rollback");
		}
	}

	/**
	 * Performs a commit if an active transaction exists. If an active
	 * transaction does not exist, an IllegalStateException will be thrown.
	 * 
	 * @throws IllegalStateException
	 */
	public void commit() {
		if (isTransactionActive()) {
			transaction.commit();
			logger.debug("transaction was committed");
		} else {
			throw new IllegalStateException("no transaction to commit");
		}
	}

	/**
	 * Same as commit and then startTransaction
	 */
	public void commitAndStartTransaction() {
		commit();
		startTransaction();
	}

	/**
	 * Same as rollback and then startTransaction
	 */
	public void rollbackAndStartTransaction() {
		rollback();
		startTransaction();
	}

	/**
	 * The specified dto is to be written to the persitence storage.
	 * 
	 * Todo discuss save or update and
	 * 
	 * @param persistentObject
	 */
	public void save(final Object persistentObject) {
		session.save(persistentObject);
	}

	/**
	 * Move the session level ob jects to the persistence engine.
	 * 
	 * If the persistence engine is a relational database then the insert,
	 * update, delete operations should be performed.
	 * 
	 * 
	 */
	public void flush() {
		session.flush();
	}

	public Session getSession() {
		return session;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Connection getConnection() {
		if (!isTransactionActive()) {
			startTransaction();
		}
		final Session session = getSession();
		return session.connection();
	}
}
