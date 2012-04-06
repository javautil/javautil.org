package com.dbexperts.oracle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.dbexperts.jdbc.DatabaseMetaData.Table;
import com.dbexperts.oracle.DatabaseMetaData.ColumnComment;

public class ColumnComments
{
//	int insertBatchSize = 100;
//	private Connection conn;
//
//	private ResultSet rset = null;
//	private PreparedStatement selectStmt = null;
	static final String columnText = "" +
        "    owner,\n" +
        "    table_name,\n" +
        "    column_name,\n" +
        "    comments\n";

    public static List<ColumnComment> getForTable(Connection conn, Table table) throws SQLException {
	if (conn == null) {
	    throw new IllegalArgumentException("conn is null");
	}
	if (table == null ) {
	    throw new IllegalArgumentException("table is null");
	}
	String schemaName = table.getSchemaName();
	String tableName = table.getTableName();
	if (tableName ==  null) {
	    throw new IllegalArgumentException("tableName is null");
	}
	if (schemaName == null ) {
	    throw new IllegalArgumentException("schemaName is null");
	}
	ArrayList<ColumnComment> comments = new ArrayList<ColumnComment>();
	
	String selectText = "select " + columnText + " from all_col_comments where owner = : owner and tableName = :tableName ";
	PreparedStatement ps = conn.prepareStatement(selectText);
	ps.setString(1,schemaName);
	ps.setString(2, tableName);
	ResultSet rset  = ps.executeQuery();
	while (rset.next()) {
	    ColumnComment cc = new ColumnComment();
	    getRow(rset,cc);
	    comments.add(cc);
	}
	return comments;
    }

    public static void getRow(ResultSet rset, ColumnComment row)
    throws java.sql.SQLException
    {
        String columnName = null;

        try {
            row.setOwner(rset.getString(columnName = "OWNER"));
            row.setTableName(rset.getString(columnName = "TABLE_NAME"));
            row.setColumnName(rset.getString(columnName = "COLUMN_NAME"));
            row.setComments(rset.getString(columnName = "COMMENTS"));
        }
            catch (java.sql.SQLException s) {
                throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
            }
        } // end of getRow 
	

    
}  // end of class
