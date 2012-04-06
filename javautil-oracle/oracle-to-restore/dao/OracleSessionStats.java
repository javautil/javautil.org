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
import com.dbexperts.oracle.OracleSessionStatSet;
import com.dbexperts.oracle.dto.OracleSessionStat;

public class OracleSessionStats {
	private static final String	newline		= System
													.getProperty("line.separator");

	private static final String	selectText	= ""
													+ "select  ss.sid, "
													+ "    ss.statistic#,"
													+ "    ss.value "
													+ "from sys.v_$sesstat ss "
													+ " where ss.value > 0";

	private final Logger				logger		= Logger.getLogger(this.getClass()
													.getName());

	public static String format(final Collection<OracleSessionStat> stats) {
		final StringBuilder b = new StringBuilder();
		// b
		// .append("SEQ EVENT P1TEXT P2TEXT P3TEXT WAIT_CLASS WAIT TIME SECONDS
		// IN WAIT");
		for (final OracleSessionStat stat : stats) {
			b.append(stat.getSid());
			b.append(" ");
			b.append(stat.getStatisticNbr());
			b.append(" ");
			b.append(stat.getValue());

			b.append(newline);
		}
		return b.toString();
	}

	public static OracleSessionStatSet getForMySession(
			final OracleConnection conn) throws SQLException {
		final int sid = OracleConnectionHelper.getSid(conn);
		return getForSession(conn, sid);
	}

	public static OracleSessionStatSet getForSession(final Connection conn,
			final int sid) throws SQLException {
		final Logger logger = Logger.getLogger(OracleSessionStats.class.getName());
		String sqlText = null;
		final ArrayList<OracleSessionStat> stats = new ArrayList<OracleSessionStat>();
		logger.info("fetching for sid " + sid);
		int rowCount = 0;
		try {

			sqlText = selectText + " and sid = :sid";
			final PreparedStatement ps = conn.prepareStatement(sqlText);
			ps.setInt(1, sid);
			final ResultSet rset = ps.executeQuery();
			while (rset.next()) {
				final OracleSessionStat row = new OracleSessionStat();
				getRow(rset, row);
				stats.add(row);
				rowCount++;
			}
			ps.close();
		} catch (final SQLException sqe) {
			final String message = "while processing: " + newline + sqlText + newline
					+ sqe.getMessage();
			throw new SQLException(message);
		}
		logger.error("rowcount " + rowCount);
		OracleSessionStatSet retval = new OracleSessionStatSet(stats);
		return retval;
	}

	public static void getRow(final ResultSet rset, final OracleSessionStat row)
			throws java.sql.SQLException {
		String columnName = null;

		try {
			row.setSid(rset.getInt(columnName = "SID"));
			row.setStatisticNbr(rset.getInt(columnName = "STATISTIC#"));
			row.setValue(rset.getDouble(columnName = "VALUE"));
		} catch (final java.sql.SQLException s) {
			throw new java.sql.SQLException("error processing column"
					+ columnName + "\n" + s.getMessage());
		}
	} // end of getRow

	String getSelectText() {
		return selectText;
	}
} // end of class
