package org.javautil.exceptionprocessing;

import java.sql.Connection;
import java.sql.SQLException;

public interface ExceptionProcessingDataSourcesDAO {

	/*
	 * TODO explain what this does (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.exceptionprocessing.ExceptionProcessingDataSourceDAO#persist
	 * (java.sql.Connection, java.lang.Integer, java.lang.Integer)
	 */
	public abstract void persist(Connection conn, Integer runNbr,
			Integer ruleNbr) throws SQLException;

	public abstract Connection getConnection(String schemaName)
			throws RuntimeException;

}