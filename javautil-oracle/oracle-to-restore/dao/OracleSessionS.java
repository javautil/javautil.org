package com.dbexperts.oracle.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import oracle.jdbc.OracleConnection;

import com.dbexperts.oracle.dto.OracleSession;
import com.dbexperts.oracle.dto.OracleSessionBase;


public class OracleSessionS extends OracleSessionBaseS {

	private final OracleConnection dbc;
	private PreparedStatement stmt = null;

	public OracleSessionS(final OracleConnection dbc) {
		this.dbc = dbc;
	}

	public void getAll()
	throws java.sql.SQLException {
		final String stmtText = selectText;
		ResultSet rset=null;
		clear();
		try {
			if (stmt == null) {
				stmt = dbc.prepareStatement(stmtText);
			}
			rset = stmt.executeQuery();
			while (rset.next()) {
				final OracleSession row = new OracleSession();
				getRow(rset, row);
				add(row);
			}
		} catch (final java.sql.SQLException s) {
			throw new java.sql.SQLException(s.getMessage() + "\nwhile processing\n" + stmtText);
		}
	}


	void setOpenCursors()
	throws java.sql.SQLException {
		for (final Iterator<OracleSessionBase> it = getRows().iterator();it.hasNext();) {
			final OpenCursorS cursors = new OpenCursorS(dbc);
			final OracleSession session = (OracleSession) it.next();
			session.setOpenCursors(cursors.getForSession(session.getSid()));
		}
	}
}
