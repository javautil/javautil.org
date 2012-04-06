package com.dbexperts.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.WeakHashMap;

import oracle.jdbc.OracleConnection;

import com.dbexperts.oracle.StatNames;
import com.dbexperts.oracle.WrappedOracleConnection;
import com.dbexperts.oracle.performance.StatName;
import com.dbexperts.persistence.PersistenceException;

public class StatNameS {

	// todo is really unique on an instance name and host at least until somebody bounces and swaps, screw 'em
	private static TreeMap<String, StatNames> statsByDatabaseName = new TreeMap<String, StatNames>();

	private static WeakHashMap<OracleConnection, String > instanceNameByConnection = new WeakHashMap<OracleConnection,String>();
	
//	public void populate(OracleConnection conn) throws SQLException, PersistenceException {
//		if (conn == null) {
//			throw new IllegalArgumentException("conn is null");
//		}
//		WrappedOracleConnection woc = WrappedOracleConnection.getInstance(conn);
//		String instanceName = woc.getInstanceName();
//		TreeMap<Integer, StatName> map = getForInstanceName(conn, instanceName);
//		
//	}
	/**
	 * Much cheaper to call with 
	 * @param conn
	 * @param statNbr
	 * @throws SQLException 
	 * @throws PersistenceException 
	 */
	public static StatNames getStatNames(OracleConnection conn) throws SQLException {
		String instanceName;
		synchronized (instanceNameByConnection) {
			instanceName = instanceNameByConnection.get(conn);
			
		}
		if (instanceName == null) {
			WrappedOracleConnection woc = WrappedOracleConnection.getInstance(conn);
			instanceName = woc.getInstanceName();
		}
		synchronized (instanceNameByConnection) {
			instanceNameByConnection.put(conn,instanceName);
			
		}
		StatNames statsForInstance = getForInstanceName(conn,instanceName);
		
		return statsForInstance;
	}
	/**
	 * Much cheaper to call with 
	 * @param conn
	 * @param statNbr
	 * @throws SQLException 
	 * @throws PersistenceException 
	 */
	public StatName getStatName(OracleConnection conn, Integer statNbr) throws SQLException, PersistenceException {
		String instanceName;
		synchronized (instanceNameByConnection) {
			instanceName = instanceNameByConnection.get(conn);
			
		}
		if (instanceName == null) {
			WrappedOracleConnection woc = WrappedOracleConnection.getInstance(conn);
			instanceName = woc.getInstanceName();
		}
		synchronized (instanceNameByConnection) {
			instanceNameByConnection.put(conn,instanceName);
			
		}
		StatNames statsForInstance = getForInstanceName(conn,instanceName);
		
		StatName retval = statsForInstance.getStatName(statNbr);
		return retval;
	}
	
	public static StatNames getForInstanceName(OracleConnection conn, String instanceName) throws SQLException
	 {
		StatNames retval = statsByDatabaseName.get(instanceName);
		if (retval == null) {
			ArrayList<StatName> names = new ArrayList<StatName>();
			synchronized (statsByDatabaseName) {
				String sqlText = "select statistic#, name, class from  sys.v_$statname";
				Statement s = conn.createStatement();
				ResultSet rset;
				try {
					try {
						rset = s.executeQuery(sqlText);
					} catch (SQLException sqe) {
						throw new SQLException(new PersistenceException(sqe,sqlText).getMessage());
					}
					while (rset.next()) {
						int statNbr = rset.getInt("statistic#");
						String name = rset.getString("name").intern();
						int classNbr = rset.getInt("class");
						StatName statName = new StatName(statNbr, name, classNbr);
						names.add(statName);
					}
					statsByDatabaseName.put(instanceName, retval);
				} catch (SQLException sqe) {
					String message = new PersistenceException(sqe, sqlText).getMessage();
					throw new SQLException(message);
				}
			}
			retval = new StatNames(names);
		}
		return retval;
	}
}
