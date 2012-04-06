package com.dbexperts.oracle.cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Logger;



/**
Version:   $Id: UtCacheS.java,v 1.1 2011/08/31 21:09:57 jjs Exp $
*/
public class UtCacheS implements Iterable<UtCache> {
	private static final Logger logger = Logger.getLogger(UtCacheS.class.getName());
	/**
	 * sql text for inserting all rows into the table
	 */
	protected static String insertText = "insert into ut_cache " + "(\n" + "object_name, \n" + "change_time, \n"
			+ ")\n" + "value  (?,?,?)";

	static String selectText = "" + "SELECT\n" + "    object_name,\n" + "    " + "change_time,\n" + "    " +
			 "class_name " + "FROM ut_cache\n";

	private PreparedStatement stmt = null;

	//private HashMap<String, UtCache> byCacheName = new HashMap<String, UtCache>();
	private final HashMap<String, UtCache> byClassName = new HashMap<String, UtCache>();
	/**
	 * container for rows retrieved from fetches in fetched sequence
	 */
	private final ArrayList<UtCache> rows = new ArrayList<UtCache>();

	/**
	 * HashMap based on obfuscated primary key
	 */
	//private HashMap map = new HashMap();

	private PreparedStatement selectStmt = null;

	//private ResultSet rset = null;

	private PreparedStatement insertStmt = null;

	/**
	 * this variable is employed to support for the efficiencies afforded by
	 * connection pooling while simultaneously providing a mechanism to minimize
	 * statement reparsing, which can be very expensive
	 */
	boolean persistConnection = false;

	static void getRow(final ResultSet rset, final UtCache row) throws java.sql.SQLException {
		String columnName = null;

		try {
			final String ObjectName = rset.getString(columnName = "object_name");
			row.setObjectName(rset.wasNull() ? null : ObjectName);

			final Timestamp ChangeTime = rset.getTimestamp(columnName = "change_time");
			row.setChangeTime(rset.wasNull() ? null : ChangeTime);
			//String className = rset.getString("class_name");
			final String className = rset.getString("class_name");
			final String trimClassName = className.trim();
			if (!className.equals(trimClassName)) {
				logger.warn("className " + className + " needs trimming");
			}
			row.setClassName(trimClassName);
			if (logger.isDebugEnabled()) {
                logger.debug("object_name " + ObjectName + " changeTime " + ChangeTime);
			}
		} catch (final java.sql.SQLException s) {
			throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
		}
	} // end of getRow

	public void add(final UtCache row) {
		//byCacheName.put(row.getObjectName(), row);
		byClassName.put(row.getClassName(),row);
	}

	/**
	 * a template for writing your fetch routines. It is strongly recommended
	 * that routines such as as this be implemented in the non-base persistence
	 * class .
	 */

	public void clear() {
		rows.clear();
	//	map.clear();
	//	byCacheName.clear();
		byClassName.clear();
	}

	public void connectionPersistenceBegin() {
		persistConnection = true;
	}

	public void connectionPersistenceEnd() throws java.sql.SQLException {
		persistConnection = false;
		if (insertStmt != null) {
			insertStmt.close();
			insertStmt = null;
		}
		if (selectStmt != null) {
			selectStmt.close();
			selectStmt = null;
		}
	}

	public UtCache get(final String objectName) {
		return byClassName.get(objectName);
	}

	public UtCache getForClassName(final String className) {
		return byClassName.get(className);
	}

	public String getKeysAsString() {
		final StringBuilder b = new StringBuilder();
		for (final String s : byClassName.keySet()) {
			b.append("'");
			b.append(s);
			b.append("'\n");
		}
		return b.toString();
	}

	/** return all of the tuples in regularly formed xml */
//	public String toXml() {
//		StringBuffer buff = new StringBuffer(1024);
//		Iterator it = rows.iterator();
//		buff.append("<UtCacheS>");
//		while (it.hasNext()) {
//			UtCache tuple = (UtCache) it.next();
//			buff.append(tuple.toXml());
//		}
//		buff.append("</UtCacheS>");
//		return new String(buff);
//	}

	/** insert all tuples into persistent store */
	public void insertAll(final Connection dbc) throws java.sql.SQLException {
		final Iterator<UtCache> it = rows.iterator();
		while (it.hasNext()) {
			final UtCache row = it.next();
			insertRow(row, dbc);
		}
	}

	/**
	 * ConnectionPool safe method for persisting an instance of UtCacheBase if
	 * this is called repeatedly within a transaction, it is highly recommended
	 * that the method pairs connectionPersistenceBegin and
	 * connectionPersistenceEnd be called to reduce sql statement parsing.
	 */
	public void insertRow(final UtCache row, final Connection dbc) throws java.sql.SQLException {
		if (insertStmt == null || !persistConnection) {
			insertStmt = dbc.prepareStatement(insertText);
		}
	//	PreparedStatementHelper helper = new PreparedStatementHelper(insertStmt);
		insertStmt.setString(1, row.getObjectName());
		insertStmt.setTimestamp(2, row.getChangeTime());
	//	helper.setInt(3, row.getCacheChangeNbr());
		insertStmt.executeUpdate();
	}

	public Iterator<UtCache> iterator() {
		return byClassName.values().iterator();
	}

	public void refresh(final OracleConnection dbc) throws java.sql.SQLException {
		ResultSet rset = null;
		clear();
		if (stmt == null) {
			stmt = dbc.prepareStatement(selectText);
		}
		rset = stmt.executeQuery();
		while (rset.next()) {
			final UtCache row = new UtCache();
			getRow(rset, row);
			add(row);
		}
	}

	/** return the size of the rows container */
	public int size() {
		return rows.size();
	}
}
