package com.dbexperts.oracle.dto;

import oracle.jdbc.OracleConnection;

import com.dbexperts.oracle.dao.OpenCursorS;


public class OracleSession extends OracleSessionBase
{
	OpenCursor[] openCursors = null;

	OpenCursor[] getOpenCursor()
	{
		return openCursors;
	}

	OpenCursor[] getOpenCursor(final OracleConnection dbc)
	throws java.sql.SQLException
	{
		final OpenCursorS cursors = new OpenCursorS();
		return cursors.getForSession(dbc,this.getSid());
	}

	public void setOpenCursors(final OpenCursor[] cursors) {
		this.openCursors = cursors;
	}
}
