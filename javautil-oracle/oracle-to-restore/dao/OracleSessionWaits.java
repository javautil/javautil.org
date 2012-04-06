package com.dbexperts.oracle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Logger;

import com.dbexperts.oracle.OracleConnectionHelper;
import com.dbexperts.oracle.dto.OracleSessionWait;

public class OracleSessionWaits {
	private static final String	newline		= System.getProperty("line.separator");

	static final String			selectText	= "SELECT sid,\n" + "    sid,\n" + "    seq#,\n" + "    event,\n" + "    p1text,\n"
													+ "    p1,\n" + "    p1raw,\n" + "    p2text,\n" + "    p2,\n" + "    p2raw,\n"
													+ "    p3text,\n" + "    p3,\n" + "    p3raw,\n" + "    wait_class_id,\n"
													+ "    wait_class#,\n" + "    wait_class,\n" + "    wait_time,\n"
													+ "    seconds_in_wait,\n" + "    state\n" + "FROM sys.V_$SESSION_WAIT\n";

	PreparedStatement			insertStmt	= null;

	public static String format(final Collection<OracleSessionWait> waits) {
		final StringBuilder b = new StringBuilder();
		b.append("SID    EVENT   P1TEXT  SECONDS IN WAIT");
		b.append(newline);
		for (final OracleSessionWait wait : waits) {
			b.append(wait.getSid());
			b.append(" ");
			b.append(wait.getEvent());
			b.append(" ");
			b.append(wait.getP1text());
			b.append(" ");
			b.append(wait.getSecondsInWait());
			b.append(newline);
		}
		return b.toString();
	}

	public static ArrayList<OracleSessionWait> getForMySession(final OracleConnection conn) throws SQLException {
		final int sid = OracleConnectionHelper.getSid(conn);
		return getForSession(conn, sid);
	}

	public static ArrayList<OracleSessionWait> getForSession(final Connection conn, final int sid) throws SQLException {
		final Logger logger = Logger.getLogger(OracleSessionWaits.class.getName());
		String sqlText = null;
		final ArrayList<OracleSessionWait> retval = new ArrayList<OracleSessionWait>();
		int rowCount = 0;
		try {

			sqlText = selectText + " where sid = :sid";
			final PreparedStatement ps = conn.prepareStatement(sqlText);
			ps.setInt(1, sid);
			final ResultSet rset = ps.executeQuery();
			while (rset.next()) {
				final OracleSessionWait row = new OracleSessionWait();
				getRow(rset, row);
				retval.add(row);
				rowCount++;
			}
			ps.close();
		} catch (final SQLException sqe) {
			final String message = "while processing: " + newline + sqlText + newline + sqe.getMessage();
			throw new SQLException(message);
		}
		logger.error("wait row count is " + rowCount);
		return retval;
	}

	public static void getRow(final ResultSet rset, final OracleSessionWait row) throws java.sql.SQLException {
		String columnName = null;

		try {

			row.setSid(rset.getInt(columnName = "SID"));

			row.setSeqNbr(rset.getInt(columnName = "SEQ#"));
			row.setEvent(rset.getString(columnName = "EVENT"));
			row.setP1text(rset.getString(columnName = "P1TEXT"));

			row.setP1(rset.getBigDecimal(columnName = "P1"));
			row.setP1raw(rset.getBytes(columnName = "P1RAW"));
			row.setP2text(rset.getString(columnName = "P2TEXT"));

			row.setP2(rset.getBigDecimal(columnName = "P2"));
			row.setP2raw(rset.getBytes(columnName = "P2RAW"));
			row.setP3text(rset.getString(columnName = "P3TEXT"));

			row.setP3(rset.getBigDecimal(columnName = "P3"));
			row.setP3raw(rset.getBytes(columnName = "P3RAW"));

			row.setWaitClassId(rset.getBigDecimal(columnName = "WAIT_CLASS_ID"));

			row.setWaitClassNbr(rset.getBigDecimal(columnName = "WAIT_CLASS#"));
			row.setWaitClass(rset.getString(columnName = "WAIT_CLASS"));

			row.setWaitTime(rset.getBigDecimal(columnName = "WAIT_TIME"));

			row.setSecondsInWait(rset.getBigDecimal(columnName = "SECONDS_IN_WAIT"));
			row.setState(rset.getString(columnName = "STATE"));
		} catch (final java.sql.SQLException s) {
			throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
		}
	} // end of getRow

	String getSelectText() {
		return selectText;
	}
} // end of class
