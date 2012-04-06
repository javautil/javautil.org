package com.dbexperts.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceNumber {

	public static Long getSequenceNumber(final Connection conn, final String sequenceName) throws SQLException {
		final String sqlText = "select " + sequenceName + ".nextval from dual";
		PreparedStatement ps = null;
		Long retval;
		try {
			ps = conn.prepareStatement(sqlText);
			final ResultSet rset = ps.executeQuery();
			rset.next();
			retval = rset.getLong(1);

		} catch (final SQLException e) {
			throw new SQLException("while processing " + sqlText + " " + e.getMessage());
		} finally {
			if (ps != null) {
			ps.close();
			}
		}

		return retval;

	}
}
