package org.javautil.hibernate;

import java.sql.Timestamp;

import org.hibernate.Query;

public class HqlUtils {
	/**
	 * Binds ?'s in a PreparedStatement
	 * 
	 * @param stmt
	 *            PreparedStatement
	 * @param bindVars
	 *            Object[]
	 */
	public void doStatementBind(Query stmt, Object ... bindVars) {
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
