package org.javautil.hibernate;

import java.sql.Timestamp;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateUtils {
	@Autowired
	private  SessionFactory sessionFactory;
	
	public HibernateUtils(SessionFactory sessionFactory) {
		if (sessionFactory == null ) {
			throw new IllegalArgumentException("sessionFactory is null");
		}
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

	public  void executeSql(String sqlText, Object... bindVars) {
		Session sf = sessionFactory.getCurrentSession();
		SQLQuery query = sf.createSQLQuery(sqlText);
		doStatementBind(query, bindVars);
		query.executeUpdate();
	}

	/**
	 * Executes a procedure call against the thread's Hibernate Session
	 * 
	 * @param procName
	 * @param bindVars
	 */
	public void executeProcedure(String procName, Object... bindVars) {
		Session sf = sessionFactory.getCurrentSession();
		String sqlText = "{ call " + procName + createArguements(bindVars)
				+ " }";
		SQLQuery query = sf.createSQLQuery(sqlText);
		doStatementBind(query, bindVars);
		query.executeUpdate();
	}

	/**
	 * Returns string useful for appending to function / procedure name when
	 * called for a specific set of bind parameters
	 * 
	 * @param bindVars
	 *            Object[]
	 * @return String
	 */
	private String createArguements(Object[] bindVars) {
		String fCall = "(";
		for (int index = 0; bindVars != null && index < bindVars.length; index++) {
			if (index != 0) {
				fCall += " , ";
			}

			Object bindVar = bindVars[index];
			boolean isNull = (bindVar == null) //
					|| (bindVar instanceof String && ((String) bindVar)
							.length() == 0);

			if (isNull) {
				fCall += "null";
			} else {
				fCall += "?";
			}
		}
		fCall += ")";
		return fCall;
	}

	/**
	 * Binds ?'s in a PreparedStatement
	 * 
	 * @param stmt
	 *            PreparedStatement
	 * @param bindVars
	 *            Object[]
	 */
	private  void doStatementBind(SQLQuery stmt, Object[] bindVars) {
		if (bindVars == null)
			return;

		loop: for (int n = 0; n < bindVars.length; n++) {
			Object bindVar = bindVars[n];
			if (bindVar == null
					|| (bindVar instanceof java.lang.String && (((String) bindVar)
							.equals("")))) {
				// do nothing; we dont want to bind the NULL object, rather we
				// have
				// set the ? in the call to null instead earlier
				// see createArguements() method
				continue loop;
			} else if (bindVar instanceof java.lang.String) {
				stmt.setString(n, (String) bindVar);
			} else if (bindVar instanceof java.lang.Integer) {
				stmt.setInteger(n, ((Integer) bindVar).intValue());
			} else if (bindVar instanceof java.lang.Double) {
				stmt.setDouble(n, ((Double) bindVar).doubleValue());
			} else if (bindVar instanceof java.lang.Long) {
				stmt.setLong(n, ((Long) bindVar).longValue());
			} else if (bindVar instanceof java.lang.Character) {
				stmt.setString(n, ((Character) bindVar).toString());
			} else if (bindVar instanceof java.sql.Timestamp) {
				stmt.setTimestamp(n, ((Timestamp) bindVar));
			} else if (bindVar instanceof java.lang.Float) {
				stmt.setFloat(n, ((Float) bindVar).floatValue());
			} else if (bindVar instanceof java.lang.Boolean) {
				stmt.setBoolean(n, ((Boolean) bindVar).booleanValue());
			} else if (bindVar instanceof java.sql.Date) {
				stmt.setDate(n, ((java.sql.Date) bindVar));
			} else if (bindVar instanceof Byte) {
				stmt.setByte(n, ((Byte) bindVar).byteValue());
			} else if (bindVar instanceof byte[]) {
				stmt.setBinary(n, ((byte[]) bindVar));
			} else if (bindVar instanceof char[]) {
				stmt.setString(n, (String) new String((char[]) bindVar));
			} else
				throw new IllegalArgumentException(
						"Objects of Class "
								+ bindVar.getClass().getName()
								+ " are not yet implemented by "
								+ "SQLUtils / BindSet; try Double / String / Long instead");
		}

	}

}
