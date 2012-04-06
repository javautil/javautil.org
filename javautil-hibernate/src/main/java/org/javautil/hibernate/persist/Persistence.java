package org.javautil.hibernate.persist;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.javautil.logging.Events;
//import org.javautil.oracle.OracleConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;

/**
 * Convenience factory for DAO creation.
 * 
 * @author tim@softwareMentor.com
 */
public class Persistence {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	private static Map<Class, DaoImpl> daoMap = new HashMap<Class, DaoImpl>();

	/**
	 * TODO the problem with this is that
	 */
	private static SessionFactory sessionFactory;

	@Resource
	private NativeJdbcExtractor extractor;

	@SuppressWarnings("unchecked")
	public <T extends Serializable> Dao<T, Serializable> get(
			final Class<T> clazz) {
		DaoImpl dao = daoMap.get(clazz);
		if (dao == null) {
			synchronized (this) {
				dao = new DaoImpl<T, Serializable>(clazz);
				dao.setSessionFactory(sessionFactory);
				daoMap.put(clazz, dao);
			}
		}
		return dao;
	}

	/**
	 * Provide an extracted native connection object from session connection.
	 * 
	 * @return conn
	 * @throws SQLException
	 */
	public Connection getNativeConnection() throws SQLException {
		return getNativeConnection(true);
	}

	/**
	 * Provide an extracted native connection object from session connection,
	 * and optionally begin a new transaction.
	 * 
	 * @param boolean beginTx
	 * @return conn
	 * @throws SQLException
	 */
	@SuppressWarnings("deprecation")
	public Connection getNativeConnection(final boolean beginTx)
			throws SQLException {
		if (beginTx) {
			beginTransaction();
		}
		final Connection conn = getSession().connection();
		final Connection nconn = extractor.getNativeConnection(conn);
		return nconn;
	}

	public Transaction beginTransaction() {
		if (getSession().getTransaction().isActive()) {
			logger.debug("clearing session and beginning new transaction");
			getSession().clear();
		} else {
			logger.debug("beginning new transaction");
		}
		final Transaction tx = getSession().beginTransaction();
//		if (logger.isDebugEnabled() && Events.isRegistered("oraclesid")) {
//			try {
//				final Connection conn = getSession().connection();
//				final Connection nconn = extractor.getNativeConnection(conn);
//				logger.debug("oracle sid = "
//						+ OracleConnectionUtil.getSid(nconn));
//			} catch (final SQLException e) {
//				throw new RuntimeException(e);
//			}
//		}
		return tx;
	}

	public void commit() {
		flushAndClear();
		getSession().getTransaction().commit();
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(final SessionFactory aSessionFactory) {
		sessionFactory = aSessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public NativeJdbcExtractor getExtractor() {
		return extractor;
	}

	public void setExtractor(final NativeJdbcExtractor extractor) {
		this.extractor = extractor;
	}

	public void flushAndClear() {
		getSession().flush();
		getSession().clear();

	}

	public void flush() {
		getSession().flush();
	}

}
