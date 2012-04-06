package org.javautil.exceptionprocessing;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.javautil.exceptionprocessing.dto.GttUtTableRowMsg;
import org.javautil.exceptionprocessing.dto.UtRuleGrpRun;

public class ExceptionProcessingRulesDAOImpl implements
		ExceptionProcessingRulesDAO {

	private final Logger logger = Logger.getLogger(getClass());
	private Session ses;
	private SessionFactory sFactory;
	private UtRuleGrpRun rGrpRun;
	private Transaction tx;
	private Connection conn;

	@Override
	public UtRuleGrpRun getUtGrpRun(final Integer runNbr) {

		logger.info("Getting UtRuleGrpRun");
		try {

			sFactory = getSessionFactory();
			ses = getSession();
			rGrpRun = (UtRuleGrpRun) ses.createCriteria(UtRuleGrpRun.class)
					.add(Restrictions.idEq(runNbr)).uniqueResult();

		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
		return rGrpRun;
	}

	public SessionFactory getSessionFactory() {
		sFactory = new Configuration().configure().buildSessionFactory();
		return sFactory;
	}
	
	// TODO should do getSession 

	@Override
	public Transaction beginTransaction() {
		if (ses != null) {
			tx = ses.beginTransaction();
		}
		return tx;
	}

	// TODO WTF ?  
	public Session getSession() {
		final Session ses = (Session) (sFactory != null ? sFactory
				.openSession() : "");
		return ses;
	}

	@Override
	public void closeSession() {
		if (ses != null) {
			ses.close();
		}
	}

	@Override
	public void save(final GttUtTableRowMsg m) {
		if (ses == null) {
			throw new IllegalStateException("ses is null");
		}
			ses.save(m);
	}

	@Override
	public void commit(final Transaction tx) {
		tx.commit();

	}

	@Override
	public void flush() {
		if (ses != null) {
			ses.flush();
		}
	}

	@Override
	public void evict(final Class<GttUtTableRowMsg> class1) {
		if (ses != null) {
			ses.evict(class1);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public Connection connection() {
		if (ses != null) {
			conn = ses.connection();
		}
		return conn;
	}

	@Override
	public void clearTemporary() {
		ses.createSQLQuery("delete from GTT_UT_TABLE_ROW_MSG").executeUpdate();
	}
}
