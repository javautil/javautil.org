package org.javautil.oracle.dao;

import java.sql.ResultSet;

import org.javautil.oracle.dto.OracleForeignKey;

public class OracleForeignKeyDaoImpl {
	/**
	 * A template for connection pool safe single call.
	 */
	/*
	 * public void getForPk(Connection dbc, Integer pk) throws
	 * java.sql.SQLException { String stmtText = selectText +
	 * " where primaryKey = ?"; PreparedStatement stmt = null; ResultSet
	 * rset=null; clear(); try { stmt = dbc.prepareStatement(stmtText);
	 * stmt.setInt(1,pk.intValue()); rset = stmt.executeQuery(); while
	 * (rset.next()) { OracleForeignKey row = new OracleForeignKey();
	 * getRow(rset, row); add(row); } } catch (java.sql.SQLException s) { throw
	 * new java.sql.SQLException(s.getMessage() + "\nwhile processing\n" +
	 * stmtText); } finally { if (stmt != null) { stmt.close(); } } }
	 */
	public static void getRow(ResultSet rset, OracleForeignKey row)
			throws java.sql.SQLException {
		String columnName = null;

		try {
			row.setOwner(rset.getString(columnName = "OWNER"));
			row.setConstraintName(rset
					.getString(columnName = "CONSTRAINT_NAME"));
			row.setConstraintType(rset
					.getString(columnName = "CONSTRAINT_TYPE"));
			row.setTableName(rset.getString(columnName = "TABLE_NAME"));
			row.setSearchCondition(rset
					.getString(columnName = "SEARCH_CONDITION"));
			row.setROwner(rset.getString(columnName = "R_OWNER"));
			row.setRConstraintName(rset
					.getString(columnName = "R_CONSTRAINT_NAME"));
			row.setDeleteRule(rset.getString(columnName = "DELETE_RULE"));
			row.setStatus(rset.getString(columnName = "STATUS"));
			row.setDeferrable(rset.getString(columnName = "DEFERRABLE"));
			row.setDeferred(rset.getString(columnName = "DEFERRED"));
			row.setValidated(rset.getString(columnName = "VALIDATED"));
			row.setGenerated(rset.getString(columnName = "GENERATED"));
			row.setBad(rset.getString(columnName = "BAD"));
			row.setRely(rset.getString(columnName = "RELY"));
			row.setLastChange(rset.getTimestamp(columnName = "LAST_CHANGE"));
			row.setIndexOwner(rset.getString(columnName = "INDEX_OWNER"));
			row.setIndexName(rset.getString(columnName = "INDEX_NAME"));
			row.setInvalid(rset.getString(columnName = "INVALID"));
			row.setViewRelated(rset.getString(columnName = "VIEW_RELATED"));
			row.setPkTableName(rset.getString(columnName = "PK_TABLE_NAME"));
			row.setPkSearchConditioni(rset
					.getString(columnName = "PK_SEARCH_CONDITIONI"));
			row.setPkDeleteRule(rset.getString(columnName = "PK_DELETE_RULE"));
			row.setPkStatus(rset.getString(columnName = "PK_STATUS"));
			row.setPkDeferrablle(rset.getString(columnName = "PK_DEFERRABLLE"));
			row.setPkDeferred(rset.getString(columnName = "PK_DEFERRED"));
			row.setPkValidated(rset.getString(columnName = "PK_VALIDATED"));
			row.setPkGenerated(rset.getString(columnName = "PK_GENERATED"));
			row.setPkBad(rset.getString(columnName = "PK_BAD"));
			row.setPkRely(rset.getString(columnName = "PK_RELY"));
			row.setPkLastChange(rset
					.getTimestamp(columnName = "PK_LAST_CHANGE"));
			row.setPkIndexOwner(rset.getString(columnName = "PK_INDEX_OWNER"));
			row.setPkIndexName(rset.getString(columnName = "PK_INDEX_NAME"));
			row.setPkInvalid(rset.getString(columnName = "PK_INVALID"));
			row.setPkViewRelated(rset.getString(columnName = "PK_VIEW_RELATED"));
		} catch (java.sql.SQLException s) {
			throw new java.sql.SQLException("error processing column"
					+ columnName + "\n" + s.getMessage(), s);
		}
	} // end of getRow
}