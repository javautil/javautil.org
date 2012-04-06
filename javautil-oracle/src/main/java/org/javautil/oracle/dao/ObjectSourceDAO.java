package org.javautil.oracle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.javautil.jdbc.metadata.DatabaseObject;
import org.javautil.jdbc.metadata.DatabaseObjectType;
import org.javautil.oracle.metadata.DdSourceLine;

public class ObjectSourceDAO {

	// static final String selectText = "" +
	// "SELECT\n" +
	// "    owner,\n" +
	// "    name,\n" +
	// "    type,\n" +
	// "    line,\n" +
	// "    text\n" +
	// "FROM DBA_SOURCE\n";

	static final String columnList = "    owner, name, type, line, text ";

	// PreparedStatement insertStmt = null;

	public static List<String> getForObject(final Connection conn,
			final boolean useDbaView, final DatabaseObject dbo)
			throws SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		final DatabaseObjectType type = dbo.getDatabaseObjectType();
		if (!type.hasSource()) {
			throw new IllegalArgumentException("Source not applicable to type "
					+ dbo.toString());
		}
		if (!dbo.isFullyQualified()) {
			throw new IllegalArgumentException(
					"The database object is not fully qualified: "
							+ dbo.toString()); // todo test
		}
		final ArrayList<String> retval = new ArrayList<String>();
		final String viewName = useDbaView ? "dba_" : "all_" + "source";
		final String sqlText = "select text from " + viewName
				+ " where owner = :owner and \n" + "  name = :name and \n"
				+ " type = :type \n " + " order by type, line  ";
		final PreparedStatement ps = conn.prepareStatement(sqlText);
		ps.setString(1, dbo.getOwner());
		ps.setString(2, dbo.getName());
		final String t = dbo.getDatabaseObjectType().getDbaSourceType();

		ps.setString(3, t);

		final ResultSet rset = ps.executeQuery();
		while (rset.next()) {
			final String text = rset.getString(1);
			retval.add(text);
		}
		ps.close();
		return retval;
	}

	public static void getRow(final ResultSet rset, final DdSourceLine row)
			throws java.sql.SQLException {
		String columnName = null;

		try {
			row.setOwner(rset.getString(columnName = "OWNER"));
			row.setName(rset.getString(columnName = "NAME"));
			row.setType(rset.getString(columnName = "TYPE"));

			row.setLine(rset.getInt(columnName = "LINE"));
			row.setText(rset.getString(columnName = "TEXT"));
		} catch (final java.sql.SQLException s) {
			throw new java.sql.SQLException("error processing column"
					+ columnName + "\n" + s.getMessage());
		}
	} // end of getRow
	// String getSelectText() {
	// return selectText;
	// }

} // end of class