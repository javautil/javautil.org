package com.dbexperts.oracle.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import oracle.jdbc.OracleConnection;

import com.dbexperts.oracle.dto.OpenCursor;

public class OpenCursorS {
	static final String				selectText			=  "SELECT   saddr,   sid,         user_name,\n"
														+  "         address, hash_value,  sql_text\n"
														+  "FROM v$open_cursor\n";

	private static String			stmtText			= selectText + " where sid = ?";

	private PreparedStatement		stmt				= null;

	/**
	 * Container for rows retrieved from fetches in fetched sequence.
	 */
	private final ArrayList<OpenCursor>	rows				= new ArrayList<OpenCursor>();

	/**
	 * Maintain persistent connection true, connection pool safe, true.
	 */

	//private ResultSet				rset				= null;

	//private PreparedStatement		selectStmt			= null;

	private OracleConnection		dbc;

	protected static void getRow(final ResultSet rset, final OpenCursor row) throws java.sql.SQLException {
		String columnName = null;

		try {
			row.setSaddr(rset.getBytes(columnName = "saddr"));

			row.setSid(rset.getInt(columnName = "sid"));
			row.setUserName(rset.getString(columnName = "user_name"));
			row.setAddress(rset.getBytes(columnName = "address"));

			row.setHashValue(rset.getInt(columnName = "hash_value"));
			row.setSqlText(rset.getString(columnName = "sql_text"));
		} catch (final java.sql.SQLException s) {
			throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
		}
	} // end of getRow

	/** Default constructor for OpenCursorBase. */
	public OpenCursorS() {
	}

	public OpenCursorS(final OracleConnection dbc) {
		this.dbc = dbc;
	}

	public void add(final OpenCursor row) {
		rows.add(row);
	}

	public void clear() {
		rows.clear();
	}


	public OpenCursor[] getForSession(final Integer sid) throws java.sql.SQLException {
		ResultSet rset = null;
		final ArrayList<OpenCursor> rows = new ArrayList<OpenCursor>();
		OpenCursor[] rc = null;
		int i = 0;
		try {
			stmt = dbc.prepareStatement(stmtText);
			stmt.setInt(1, sid.intValue());
			rset = stmt.executeQuery();
			while (rset.next()) {
				final OpenCursor row = new OpenCursor();
				getRow(rset, row);
				rows.add(row);
			}
			rc = new OpenCursor[rows.size()];
			for (final Iterator<OpenCursor> it = rows.iterator(); it.hasNext();) {
				rc[i++] = it.next();
			}
			rset.close();
			return rc;
		} catch (final java.sql.SQLException s) {
			throw new java.sql.SQLException(s.getMessage() + "\nwhile processing\n" + stmtText);
		}
	}

	/**
	 * Will close the cursor, even if invoked
	 */
	public OpenCursor[] getForSession(final OracleConnection dbc, final Integer sid) throws java.sql.SQLException {
		this.dbc = dbc;
		final OpenCursor[] rc = getForSession(sid);
		stmt.close();
		return rc;
	}

	/** Return the rows iterator. */
	public Iterator<OpenCursor> iterator() {
		return rows.iterator();
	}

	/** Return the number of the rows contained. */
	public int size() {
		return rows.size();
	}


} // end of class
