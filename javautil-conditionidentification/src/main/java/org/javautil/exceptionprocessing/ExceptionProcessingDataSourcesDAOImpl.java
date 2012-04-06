package org.javautil.exceptionprocessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ExceptionProcessingDataSourcesDAOImpl implements
		ExceptionProcessingDataSourcesDAO {

	private final Logger logger = Logger.getLogger(getClass());
	private DataSource datasource;
	private Connection conn;
	private Context ctx;

	public ExceptionProcessingDataSourcesDAOImpl() {

		try {
			final Properties props = new Properties();
			// this location is for temp,need to define a directory service
			// and register it to JVM parameters to make sure all the projects
			// points to the same location.
			final String location = System.getProperty("user.dir");
			props.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.fscontext.RefFSContextFactory");
			props.put(Context.PROVIDER_URL, "file://" + location);
			ctx = new InitialContext(props);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	void deleteUnnecessary(final Connection conn, final Integer runNbr,
			final Integer ruleNbr) throws SQLException {
		final String deleteMissing = "delete from ut_table_row_msg utrm where "
				+ "ut_rule_grp_run_nbr = :run_nbr and ut_table_rule_nbr = :ut_table_rule_nbr and "
				+ "not exists  (select 'x' from gtt_ut_table_row_msg  gtt "
				+ " where gtt.ut_table_rule_nbr = utrm.ut_table_rule_nbr and "
				+ "       gtt.ut_rule_grp_run_nbr = utrm.ut_rule_grp_run_nbr and "
				+ "       gtt.primary_key = utrm.primary_key) ";
		logger.info("deleting");

		final PreparedStatement deletePermanentStatement = conn
				.prepareStatement(deleteMissing);
		deletePermanentStatement.setLong(1, runNbr.longValue());
		deletePermanentStatement.setLong(2, ruleNbr.longValue());
		deletePermanentStatement.execute();
		final int deleteCount = deletePermanentStatement.getUpdateCount();
		logger.info("deleted " + deleteCount);
		deletePermanentStatement.close();
	}

	void insertNew(final Connection conn) throws SQLException {

		final String addNew = " MERGE INTO ut_table_row_msg utrm \n "
				+ "       USING ( \n "
				+ "               SELECT  ut_rule_grp_run_nbr, ut_table_rule_nbr, primary_key, msg \n "
				+ "               FROM    gtt_ut_table_row_msg ) g \n "
				+ "       ON ( \n "
				+ "               utrm.ut_rule_grp_run_nbr        =       g.ut_rule_grp_run_nbr AND \n "
				+ "               utrm.ut_table_rule_nbr          =       g.ut_table_rule_nbr AND \n "
				+ "               utrm.primary_key                        =       g.primary_key ) \n "
				+ "       WHEN MATCHED THEN \n "
				+ "               UPDATE \n "
				+ "                       SET     msg     =       g.msg \n "
				+ "               WHERE ( msg is not null and g.msg is not null and msg != g.msg) or  \n "
				+ "                     (  msg is null and g.msg is not null ) or \n "
				+ "                     ( msg is not null and g.msg is null) \n "
				+ "       WHEN NOT MATCHED THEN \n "
				+ "               INSERT (ut_rule_grp_run_nbr, ut_table_rule_nbr, primary_key, msg) \n "
				+ "               VALUES (g.ut_rule_grp_run_nbr, g.ut_table_rule_nbr, g.primary_key, g.msg)  ";
		logger.info("inserting");

		final PreparedStatement newPermanentStatement = conn
				.prepareStatement(addNew);
		newPermanentStatement.execute();

		final int insertCount = newPermanentStatement.getUpdateCount();
		newPermanentStatement.close();
		logger.info("insertCount " + insertCount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.exceptionprocessing.ExceptionProcessingDataSourceDAO#persist
	 * (java.sql.Connection, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void persist(final Connection conn, final Integer runNbr,
			final Integer ruleNbr) throws SQLException {
		conn.setAutoCommit(false);
		logger.info("processing runNbr " + runNbr);
		deleteUnnecessary(conn, runNbr, ruleNbr);
		insertNew(conn);
		logger.info("persisted");
	}

	// get the database connection using JNDI.
	@Override
	public Connection getConnection(final String schemaName) {
		try {
			if (ctx != null) {
				datasource = (DataSource) ctx.lookup(schemaName);
				conn = datasource.getConnection();
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		return conn;
	}

}
