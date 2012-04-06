package com.dbexperts.oracle.cache;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

import com.dbexperts.oracle.WrappedOracleConnection;

public class DBChangeNotification2 {
	// todo commented out while working on jdbc 10.2 driver
	static final String USERNAME = "scott";
	static final String PASSWORD = "tiger";
	static String URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=localhost)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=DEV11B)))";
	private final ArrayList<Statement> registeredStatements = new ArrayList<Statement>();
	private final ArrayList<ResultSet> registeredRsets = new ArrayList<ResultSet>();
	private final Logger logger = Logger.getLogger(getClass().getName());
	// todo this is a hack
	//private ResultSet rs;
	private static final String newline = System.getProperty("line.separator");

	public static void main(final String[] argv) {
		final DBChangeNotification2 dcn = new DBChangeNotification2();
		// dcn.initializeLogging();

		try {
			dcn.run();
		} catch (final SQLException mainSQLException) {
			mainSQLException.printStackTrace();
		}
	}



	private void registerStatement(final OracleConnection conn, final String sqlText, final DatabaseChangeRegistration dcr) throws SQLException {
		final OracleStatement stmt = (OracleStatement) conn.createStatement();
		// associate the statement with the registration:
		stmt.setDatabaseChangeRegistration(dcr);
		// ResultSet rs = stmt.executeQuery(sqlText);
		final ResultSet rs = stmt.executeQuery(sqlText);
		//registeredStatements.add(stmt);
		// todo should wrap cancel timeout
		 while (rs.next()) {
			}
		   final String[] tableNames = dcr.getTables();
		      for(int i=0;i<tableNames.length;i++) {
				System.out.println(tableNames[i]+" is part of the registration.");
			}
		      rs.close();
		      stmt.close();

		// todo do we really have to read them all to get an answer? What
		// happens to rows inserted after query started

		// registeredRsets.add(rs);
	}

	String getTableNames(final DatabaseChangeRegistration dcr) {
		final String[] tableNames = dcr.getTables();
		final StringBuilder b = new StringBuilder();
		for (int i = 0; i < tableNames.length; i++) {
			b.append(tableNames[i]);
			b.append(newline);
		}
		return b.toString();
	}

	void run() throws SQLException {
		final OracleConnection conn = connect();
		final WrappedOracleConnection wconn = WrappedOracleConnection.getInstance(conn);

		/*
		 * if connected through the VPN, you need to provide the TCP address of
		 * the client. For example:
		 * prop.setProperty(OracleConnection.NTF_LOCAL_HOST,"14.14.13.12");
		 *
		 * Ask the server to send the ROWIDs as part of the DCN events (small
		 * performance cost):
		 */

		logger.info(wconn.getDriverInfo());
		// showDriverInfo(conn);

		/*
		 * The following operation does a roundtrip to the database to create a
		 * new registration for DCN. It sends the client address (ip address and
		 * port) that the server will use to connect to the client and send the
		 * notification when necessary. Note that for now the registration is
		 * empty (we haven't registered any table). This also opens a new thread
		 * in the drivers. This thread will be dedicated to DCN (accept
		 * connection to the server and dispatch the events to the listeners).
		 */
		// first step: create a registration on the server:
		final Properties prop = new Properties();
		prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
		final DatabaseChangeRegistration dcr = conn.registerDatabaseChangeNotification(prop);

		try {
			// add the listener:

			dcr.addListener(new ChangeLogger(this));

			// second step: add objects in the registration:
//		     Statement stmt = conn.createStatement();
//		      // associate the statement with the registration:
//		      ((OracleStatement)stmt).setDatabaseChangeRegistration(dcr);

			registerStatement(conn, "select * from dept where deptno='45'", dcr);

			logger.info("registered tables " + getTableNames(dcr));
//			stmt.close();

		} catch (final SQLException ex) {
			/*
			 if an exception occurs, we need to close the registration in
			 order
			 to interrupt the thread otherwise it will be hanging around.
			*/

				conn.unregisterDatabaseChangeNotification(dcr);
			throw ex;
		} finally {
			try {
				// Note that we close the connection!
				conn.close();
			} catch (final Exception innerex) {
				innerex.printStackTrace();
			}
		}

		synchronized (this) {
			// The following code modifies the dept table and commits:
			try {
				final OracleConnection conn2 = connect();
				conn2.setAutoCommit(false);
				final Statement stmt2 = conn2.createStatement();
				stmt2.executeUpdate("insert into dept (deptno,dname) values ('45','cool dept')", Statement.RETURN_GENERATED_KEYS);
				ResultSet autoGeneratedKey = stmt2.getGeneratedKeys();
				if (autoGeneratedKey.next()) {
					System.out.println("inserted one row with ROWID=" + autoGeneratedKey.getString(1));
				}
				stmt2.executeUpdate("insert into dept (deptno,dname) values ('50','fun dept')", Statement.RETURN_GENERATED_KEYS);
				autoGeneratedKey = stmt2.getGeneratedKeys();
				if (autoGeneratedKey.next()) {
					System.out.println("inserted one row with ROWID=" + autoGeneratedKey.getString(1));
				}
				stmt2.close();
				conn2.commit();
				conn2.close();
			} catch (final SQLException ex) {
				ex.printStackTrace();
			}

			// wait until we get the event
			try {
				this.wait();
			} catch (final InterruptedException ie) {
			}
		}

		// At the end: close the registration (comment out these 3 lines in
		// order
		// to leave the registration open).
		final OracleConnection conn3 = connect();
		conn3.unregisterDatabaseChangeNotification(dcr);
		conn3.close();
	}

	/**
	 * Creates a connection the database.
	 */
	OracleConnection connect() throws SQLException {
		final OracleDriver dr = new OracleDriver();
		final Properties prop = new Properties();
		prop.setProperty("user", DBChangeNotification2.USERNAME);
		prop.setProperty("password", DBChangeNotification2.PASSWORD);
		return (OracleConnection) dr.connect(DBChangeNotification2.URL, prop);
	}
}

